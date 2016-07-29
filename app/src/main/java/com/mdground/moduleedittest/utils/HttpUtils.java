package com.mdground.moduleedittest.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

import com.mdground.moduleedittest.Constant.ApiUrl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

    public static InputStream getStreamFromURL(String imageURL) {
        InputStream in = null;
        try {
            in = ((HttpURLConnection) new URL(imageURL).openConnection()).getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return in;
    }

    public static Bitmap getBitmapFromUrl(String imageurl) {
        Bitmap bitmap;
        String filePath = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/tiantianmeiyin/").append(imageurl).toString();
        File file = new File(filePath);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(filePath);
        } else {
            bitmap = BitmapFactory.decodeStream(getStreamFromURL(new StringBuilder(ApiUrl.qiniu_head).append(imageurl).append("?imageView2/2/w/90").toString()));
            if (bitmap == null) {
                return null;
            }
            File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "tiantianmeiyin");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                    System.gc();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                    System.gc();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                    System.gc();
                }
            } catch (Throwable th) {
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                    System.gc();
                }
            }
        }
        return bitmap;
    }

    public static InputStream getImageViewInputStream(String imgurl) throws IOException {
        URL url = new URL(imgurl);
        if (url == null) {
            return null;
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(3000);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoInput(true);
        if (httpURLConnection.getResponseCode() == 200) {
            return httpURLConnection.getInputStream();
        }
        return null;
    }

    public static String submitPostData(String strUrlPath, Map<String, String> params, String encode) {
        OutputStream outputStream = null;
        InputStream inptStream = null;
        HttpURLConnection httpURLConnection = null;
        byte[] data = getRequestData(params, encode).toString().getBytes();
        String dealResponseResult;
        try {
            httpURLConnection = (HttpURLConnection) new URL(strUrlPath).openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            if (httpURLConnection.getResponseCode() == 200) {
                inptStream = httpURLConnection.getInputStream();
                dealResponseResult = dealResponseResult(inptStream);
                if (inptStream != null) {
                    try {
                        inptStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return dealResponseResult;
                    }
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpURLConnection == null) {
                    return dealResponseResult;
                }
                httpURLConnection.disconnect();
                return dealResponseResult;
            }
            if (inptStream != null) {
                try {
                    inptStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return "-1";
        } catch (IOException e22) {
            dealResponseResult = "err: " + e22.getMessage().toString();
            if (inptStream != null) {
                try {
                    inptStream.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                    return dealResponseResult;
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection == null) {
                return dealResponseResult;
            }
            httpURLConnection.disconnect();
            return dealResponseResult;
        } catch (Throwable th) {
            if (inptStream != null) {
                try {
                    inptStream.close();
                } catch (IOException e2222) {
                    e2222.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append((String) entry.getKey()).append("=").append(URLEncoder.encode((String) entry.getValue(), encode)).append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    public static String dealResponseResult(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            try {
                int len = inputStream.read(data);
                if (len == -1) {
                    break;
                }
                byteArrayOutputStream.write(data, 0, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new String(byteArrayOutputStream.toByteArray());
    }
}
