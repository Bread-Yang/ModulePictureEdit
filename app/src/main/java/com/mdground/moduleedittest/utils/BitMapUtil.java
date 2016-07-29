package com.mdground.moduleedittest.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import com.socks.library.KLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by yoghourt on 7/15/16.
 */

public class BitMapUtil {

    private static final LinkedList<String> CACHE_ENTRIES = new LinkedList();
    private static int CACHE_SIZE;
    private static final Map<String, Bitmap> IMG_CACHE_INDEX = new HashMap();
    private static final byte[] LOCKED = new byte[0];
    private static final Options OPTIONS_DECODE = new Options();
    private static final Options OPTIONS_GET_SIZE = new Options();
    private static final Queue<QueueEntry> TASK_QUEUE = new LinkedList();
    private static final Set TASK_QUEUE_INDEX = new HashSet();
    private static final Size ZERO_SIZE = new Size(0, 0);
    private static final Matrix f2235m = new Matrix();

    static class QueueEntry {
        public int height;
        public String path;
        public int width;

        QueueEntry() {
        }
    }

    public static class Size {
        private int height;
        private int width;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }

    public static Bitmap getBitmap(String path, int width, int height) {
        if (path == null || width <= 0 || height <= 0) {
            return null;
        }
        try {
            if (CACHE_ENTRIES.size() >= CACHE_SIZE) {
//                destoryLast();
            }
            Bitmap bitMap = useBitmap(path, width, height);
            if (bitMap != null && !bitMap.isRecycled()) {
                return bitMap;
            }
            bitMap = createBitmap1(path, width, height);
            String key = createKey(path, width, height);
            synchronized (LOCKED) {
                IMG_CACHE_INDEX.put(key, bitMap);
                CACHE_ENTRIES.addFirst(key);
            }
            return bitMap;
        } catch (OutOfMemoryError e) {
            KLog.e("抛出异常");
            destoryLast();
            System.out.println(CACHE_SIZE);
            return createBitmap1(path, width, height);
        }
    }

    private static Bitmap useBitmap(String path, int width, int height) {
        String key = createKey(path, width, height);
        synchronized (LOCKED) {
            Bitmap bitMap = (Bitmap) IMG_CACHE_INDEX.get(key);
            if (bitMap == null || bitMap.isRecycled()) {
                CACHE_ENTRIES.remove(key);
                return null;
            }
            if (CACHE_ENTRIES.remove(key)) {
                CACHE_ENTRIES.addFirst(key);
            }
            return bitMap;
        }
    }

    private static Bitmap createBitmap1(String path, int width, int height) {
        FileNotFoundException e;
        Throwable th;
        Bitmap bitmap;
        File file = new File(path);
        if (file.exists()) {
            InputStream in = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                InputStream bis2 = null;
                InputStream fileInputStream = new FileInputStream(file);
                try {
                    bis2 = new BufferedInputStream(fileInputStream);
                } catch (Throwable th3) {
                    th = th3;
                    in = fileInputStream;
                    closeInputStream(in);
                    closeInputStream(bufferedInputStream);
                }
                try {
                    Size size = getBitMapSize(path);
                    if (size.equals(ZERO_SIZE)) {
                        closeInputStream(fileInputStream);
                        closeInputStream(bis2);
                        return null;
                    }
                    int scale = Math.max(size.getWidth() / width, size.getHeight() / height);
                    synchronized (OPTIONS_DECODE) {
                        Bitmap bitMap;
                        OPTIONS_DECODE.inSampleSize = scale;
                        try {
                            bitMap = BitmapFactory.decodeStream(bis2, null, OPTIONS_DECODE);
                        } catch (OutOfMemoryError e3) {
                            destoryLast();
                            bitMap = BitmapFactory.decodeStream(bis2, null, OPTIONS_DECODE);
                        }
                        bitmap = null;
                        if (bitMap != null) {
                            Matrix matrix = new Matrix();
                            matrix.postRotate((float) bitmapDigree(path));
                            try {
                                bitmap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(), bitMap.getHeight(), matrix, true);
                                if (bitmap != bitMap) {
                                    bitMap.recycle();
                                }
                            } catch (OutOfMemoryError e4) {
                                destoryLast();
                                bitmap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(), bitMap.getHeight(), matrix, true);
                                if (bitmap != bitMap) {
                                    bitMap.recycle();
                                }
                            } catch (Throwable th4) {
                                if (bitmap != bitMap) {
                                    bitMap.recycle();
                                }
                            }
                        }
                    }
                    closeInputStream(fileInputStream);
                    closeInputStream(bis2);
                    return bitmap;
                } catch (Throwable th5) {
                    th = th5;
                    InputStream bis3 = bis2;
                    in = fileInputStream;
                    closeInputStream(in);
                    closeInputStream(bufferedInputStream);
                }
            } catch (FileNotFoundException e6) {
                e = e6;
                closeInputStream(in);
                closeInputStream(bufferedInputStream);
                return null;
            }
        }
        return null;
    }

    private static String createKey(String path, int width, int height) {
        if (path == null || path.length() == 0) {
            return "";
        }
        return new StringBuilder(String.valueOf(path)).append("_").append(0).append("_").append(0).toString();
    }

    public static Size getBitMapSize(String path) {
        Size size = null;
        Throwable th;
        File file = new File(path);
        if (!file.exists()) {
            return ZERO_SIZE;
        }
        InputStream in = null;
        try {
            InputStream in2 = new FileInputStream(file);
            try {
                BitmapFactory.decodeStream(in2, null, OPTIONS_GET_SIZE);
                size = new Size(OPTIONS_GET_SIZE.outWidth, OPTIONS_GET_SIZE.outHeight);
                closeInputStream(in2);
                return size;
            } catch (Throwable th3) {
                th = th3;
                in = in2;
                closeInputStream(in);
            }
        } catch (FileNotFoundException e2) {
            size = ZERO_SIZE;
            closeInputStream(in);
            return size;
        }
        return size;
    }

    private static void closeInputStream(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                Log.e("BitMapUtil", "closeInputStream==" + e.toString());
            }
        }
    }

    private static void destoryLast() {
        synchronized (LOCKED) {
            String key = (String) CACHE_ENTRIES.removeLast();
            if (key.length() > 0) {
                Bitmap bitMap = (Bitmap) IMG_CACHE_INDEX.remove(key);
                if (!(bitMap == null || bitMap.isRecycled())) {
                    bitMap.recycle();
                }
            }
        }
    }

    public static int bitmapDigree(String imgpath) {
        ExifInterface exif;
        try {
            exif = new ExifInterface(imgpath);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif == null) {
            return 0;
        }
        switch (exif.getAttributeInt("Orientation", 0)) {
            case 3:
                return 180;
            case 6 /*6*/:
                return 90;
            case 8 /*8*/:
                return 270;
            default:
                return 0;
        }
    }
}
