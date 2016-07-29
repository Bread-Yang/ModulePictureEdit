package com.mdground.moduleedittest.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mdground.moduleedittest.Constant.ApiUrl;
import com.mdground.moduleedittest.R;
import com.mdground.moduleedittest.utils.BitMapUtil;
import com.mdground.moduleedittest.utils.BitMapUtil.Size;
import com.mdground.moduleedittest.utils.TextUtil;
import com.mdground.moduleedittest.utils.ViewUtils;
import com.socks.library.KLog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by yoghourt on 7/15/16.
 */

public class ProductionModel implements IProductionModel {

    public static final String WORK_FILE
            = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory()
            .getAbsolutePath()))
            .append("/tiantianmeiyin/works").toString();

    private String mImageFolerPath;

    private String work_id = "193";

    private Set<String> downloadList;

    private float mScaleWin = 1.0f;

    private Context mContext;

    private float mDp24;

    private JWork mJWork;

    private List<JPage> mJPageList;

    private List<String> mSelectedLocalImageFiles;

    private InitCompleteListener mInitCompleteListener;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<String> params = new ArrayList();
            params.add((String) msg.obj);
            ProductionModel.this.loadImage(params, null);
        }
    };

    public interface InitCompleteListener {
        void complete(boolean z);

        void progress(int i, String str);
    }

    public ProductionModel(Context context, String mWork_id,
                           List<String> imageFiles, Handler handler,
                           InitCompleteListener initCompleteListener) {
        this.mContext = context;

        mImageFolerPath = WORK_FILE + "/" + this.work_id + "/";

        initJWorkData(context);
        intSelectedImageFiles();

        mDp24 = context.getResources().getDimension(R.dimen.dp_24);

        mInitCompleteListener = initCompleteListener;

        if (mInitCompleteListener != null) {
            mInitCompleteListener.complete(true);
        }
    }

    private void initJWorkData(Context context) {
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open("jwork.txt");
            String jsonString = new TextUtil(context)
                    .readTextFile(inputStream);

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

            mJWork = gson.fromJson(jsonString, JWork.class);

            mJPageList = mJWork.getPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void intSelectedImageFiles() {
        mSelectedLocalImageFiles = new ArrayList<>();

        mSelectedLocalImageFiles.add("/storage/emulated/0/handyprint/template/148/a38537d9e22b13469ad4b5685358e161.jpg");
        mSelectedLocalImageFiles.add("/storage/emulated/0/handyprint/template/148/4e03a0fe559895a937a3f0b7a55ea7a2.jpg");
        mSelectedLocalImageFiles.add("/storage/emulated/0/handyprint/template/148/963f42e5b385363b312f9add78ea2f90.jpg");
        mSelectedLocalImageFiles.add("/storage/emulated/0/handyprint/template/148/77316a3d49106b762235ffddfc129c11.jpg");
        mSelectedLocalImageFiles.add("/storage/emulated/0/handyprint/template/148/7e5e4e46a0f0141b576477f98dab2434.png");
        mSelectedLocalImageFiles.add("/storage/emulated/0/handyprint/template/148/25c88bf93fb560ddc7d55bd168805985.jpg");
        mSelectedLocalImageFiles.add("/storage/emulated/0/handyprint/template/148/fc3978e20f0d29630a94d78024ff7404.jpg");
        mSelectedLocalImageFiles.add("/storage/emulated/0/handyprint/template/148/ff7abe65f7ccc08120577cf7ffab57bd.jpg");
    }

    @Override
    public void closePage(int i, int i2) {

    }

    @Override
    public void create(int i, float f) {

    }

    @Override
    public void createAll(float f) {

    }

    @Override
    public void createIcon(int i, float f, Handler handler) {

    }

    /**
     * android编辑区域的高度 = (android编辑区域的宽度 / JWork返回的page width) * JWork返回的page height
     * @return
     */
    @Override
    public float getEditHeightOnAndroid() {
        float window_w = ((float) ViewUtils.screenWidth()) - mDp24;
        float page_w = Float.parseFloat(this.mJWork.getPage_w());
        return (window_w / page_w) * Float.parseFloat(this.mJWork.getPage_h());
    }

    @Override
    public float getEdit_w() {
        return Float.parseFloat(this.mJWork.getEdit_w());
    }

    @Override
    public Bitmap getIBackground(int page_id) {
        String path = ((JPage) this.mJPageList.get(page_id)).getBackground();
        float r = getRateOfPageWidthOnAndroid();
        return getBitmap(path, (int) ((((float) Integer.parseInt(this.mJWork.getPage_w())) * r) * this.mScaleWin),
                (int) ((((float) Integer.parseInt(this.mJWork.getPage_h())) * r) * this.mScaleWin));
    }

    @Override
    public String getIColor(int i) {
        return null;
    }

    @Override
    public Bitmap getIImage(int pagePosition, int modulePosition) {
        JMould mould = getMould(pagePosition, modulePosition);
//        String path = mould.getPhoto();
        String path = mSelectedLocalImageFiles.get(modulePosition);
        KLog.e("选择的图片的路径是 : " + path);
        float r = getRateOfEditWidthOnAndroid();
        return getBitmap(path, (int) (((float) Integer.parseInt(mould.getW())) * r), (int) (((float) Integer.parseInt(mould.getH())) * r));
    }

    @Override
    public Bitmap getIImage(String str, int i, int i2) {
        return null;
    }

    @Override
    public List<String> getIImages(int i) {
        return null;
    }

    @Override
    public Matrix getIMatrix(int page_id, int mould_id) {
        String matrixString = getMould(page_id, mould_id).getMatrix();
        Matrix m = new Matrix();
        if (!(matrixString == null || matrixString.equals(""))) {
            float[] values = new float[9];
            m.getValues(values);
            StringBuffer buff = new StringBuffer();
            int i = 0;
            for (char c : matrixString.toCharArray()) {
                if (c != '[') {
                    if (c != ',') {
                        if (c == ']') {
                            break;
                        }
                        buff.append(c);
                    } else {
                        values[i] = Float.parseFloat(buff.toString());
                        buff = new StringBuffer();
                        i++;
                    }
                }
            }
            values[2] = values[2] * this.mScaleWin;
            values[5] = values[5] * this.mScaleWin;
            m.setValues(values);
        }
        return m;
    }

    @Override
    public List<Matrix> getIMatrixs(int page_id) {
        List<Matrix> matrixs = new ArrayList();
        int length = ((JPage) this.mJPageList.get(page_id)).getMoulds().size();
        for (int i = 0; i < length; i++) {
            matrixs.add(getIMatrix(page_id, i));
        }
        return matrixs;
    }

    @Override
    public Bitmap getIMould(int page_id, int mould_id) {
        JMould mould = getMould(page_id, mould_id);
        String file = mould.getMould();
        KLog.e("模版的图片是路径是 : " + file);
        float r = getRateOfEditWidthOnAndroid();
        return getBitmap(file, (int) (((float) Integer.parseInt(mould.getW())) * r), (int) (((float) Integer.parseInt(mould.getH())) * r));
    }

    @Override
    public List<Bitmap> getIMoulds(int i) {
        return null;
    }

    @Override
    public Bitmap getIShading(int i) {
        return null;
    }

    @Override
    public Bitmap getIShading(int i, String str) {
        return null;
    }

    @Override
    public String getIText(int i, int i2) {
        return null;
    }

    @Override
    public String getITextColor(int i, int i2) {
        return null;
    }

    @Override
    public String getITextFont(int i, int i2) {
        return null;
    }

    @Override
    public String getITextFontDirect(int i, int i2) {
        return null;
    }

    @Override
    public int getITextSize(int i, int i2) {
        return 0;
    }

    @Override
    public List<String> getIconPaths() {
        return null;
    }

    @Override
    public JMould getMould(int pagePosition, int mouldPosition) {
        return this.mJPageList.get(pagePosition).getMoulds().get(mouldPosition);
    }

    @Override
    public int getMouldCount(int page_id) {
        List<JMould> moulds = ((JPage) this.mJPageList.get(page_id)).getMoulds();
        if (moulds == null) {
            return 0;
        }
        return moulds.size();
    }

    @Override
    public float getMouldHeight(int page_id, int mould_id) {
        JPage jPage = mJPageList.get(page_id);
        JMould jMould = jPage.getMoulds().get(mould_id);
        float height = Float.parseFloat(jMould.getH());
        return height * mScaleWin;
    }

    @Override
    public List<Float> getMouldHeights(int page_id) {
        List<Float> floatList = new ArrayList();
        for (JMould m : ((JPage) this.mJPageList.get(page_id)).getMoulds()) {
            floatList.add(Float.valueOf(Float.parseFloat(m.getH())));
        }
        return floatList;
    }

    @Override
    public Point getMouldLocationOnAndroid(int page_id, int mould_id) {
        float dx = getMouldX(page_id, mould_id);
        float dy = getMouldY(page_id, mould_id);
        float r = getRateOfEditWidthOnAndroid();
        Point size = new Point();
        size.set((int) (dx * r), (int) (dy * r));
        return size;
    }

    @Override
    public Point getMouldSizeOnAndroid(int page_id, int mould_id) {
        float h = getMouldHeight(page_id, mould_id);
        float w = getMouldWidth(page_id, mould_id);
        float r = getRateOfEditWidthOnAndroid();
        Point size = new Point();
        size.set((int) (w * r), (int) (h * r));
        return size;
    }

    @Override
    public float getMouldWidth(int page_id, int mould_id) {
        JPage jPage = mJPageList.get(page_id);
        JMould jMould = jPage.getMoulds().get(mould_id);
        float width = Float.parseFloat(jMould.getW());
        return width * mScaleWin;
    }

    @Override
    public List<Float> getMouldWidths(int page_id) {
        List<Float> floatList = new ArrayList();
        for (JMould m : ((JPage) this.mJPageList.get(page_id)).getMoulds()) {
            floatList.add(Float.valueOf(Float.parseFloat(m.getW())));
        }
        return floatList;
    }

    @Override
    public float getMouldX(int page_id, int mould_id) {
        JPage jPage = mJPageList.get(page_id);
        JMould jMould = jPage.getMoulds().get(mould_id);

        return Float.parseFloat(((JMould) ((JPage) this.mJPageList.get(page_id)).getMoulds().get(mould_id)).getX()) * this.mScaleWin;
    }

    @Override
    public List<Float> getMouldXs(int page_id) {
        List<Float> fs = new ArrayList();
        for (JMould m : ((JPage) this.mJPageList.get(page_id)).getMoulds()) {
            fs.add(Float.valueOf(Float.parseFloat(m.getX())));
        }
        return fs;
    }

    @Override
    public float getMouldY(int page_id, int mould_id) {
        return Float.parseFloat(((JMould) ((JPage) this.mJPageList.get(page_id)).getMoulds().get(mould_id)).getY()) * this.mScaleWin;
    }

    @Override
    public List<Float> getMouldYs(int page_id) {
        List<Float> fs = new ArrayList();
        for (JMould m : ((JPage) this.mJPageList.get(page_id)).getMoulds()) {
            fs.add(Float.valueOf(Float.parseFloat(m.getY())));
        }
        return fs;
    }

    @Override
    public JPage getPage(int page_id) {
        return (JPage) this.mJPageList.get(page_id);
    }

    @Override
    public int getPageCount() {
        return this.mJPageList.size();
    }

    @Override
    public float getPage_h() {
        return Float.parseFloat(this.mJWork.getPage_h());
    }

    @Override
    public float getPage_w() {
        return Float.parseFloat(this.mJWork.getPage_w());
    }

    @Override
    public float getRate(int page_id, int mould_id) {
        float r = getRateOnPC();
        float h = (getMouldHeight(page_id, mould_id) * r) / this.mScaleWin;
        float w = (getMouldWidth(page_id, mould_id) * r) / this.mScaleWin;
        BitMapUtil.Size s = getMouldImageSize(page_id, mould_id);
        if (((float) s.getHeight()) / h < ((float) s.getWidth()) / w) {
            return ((float) s.getHeight()) / h;
        }
        return ((float) s.getWidth()) / w;
    }

    /**
     * "页面总宽度" 和 "编辑宽度" 相比
     *
     * @return
     */
    private float getRateOnPC() {
        return Float.parseFloat(mJWork.getPage_w()) / Float.parseFloat(mJWork.getEdit_w());
    }

    @Override
    public float getRateOfEditWidthOnAndroid() {
        return (((float) ViewUtils.screenWidth()) - mDp24) / Float.parseFloat(mJWork.getEdit_w());
    }

    private float getRateOfPageWidthOnAndroid() {
        return (((float) ViewUtils.screenWidth()) - mDp24) / Float.parseFloat(mJWork.getPage_w());
    }

    @Override
    public Size getMouldImageSize(int page_id, int mould_id) {
        KLog.e("getMouldImageSize : page_id : "  + page_id + " mould_id : " + mould_id);
        JMould jMould = mJPageList.get(page_id).getMoulds().get(mould_id);
        return BitMapUtil.getBitMapSize(mImageFolerPath + jMould.getImage());
    }

    @Override
    public Point getShadingSize(String str) {
        return null;
    }

    @Override
    public int getType(int i, int i2) {
        return 0;
    }

    @Override
    public String getUID() {
        return null;
    }

    @Override
    public void getWorkIDOnPC(InitCompleteListener initCompleteListener) {

    }

    @Override
    public void imageBigger(int i, int i2) {

    }

    @Override
    public void imageMirror(int i, int i2) {

    }

    @Override
    public void imageRotate(int i, int i2) {

    }

    @Override
    public void imageSmaller(int i, int i2) {

    }

    @Override
    public String loadFromdatabase(Handler handler) {
        return null;
    }

    @Override
    public IProductionModel loadModel(int i, Handler handler) {
        return null;
    }

    @Override
    public void loadPage(int i, Handler handler) {

    }

    @Override
    public void savePage(InitCompleteListener initCompleteListener) {

    }

    @Override
    public String saveall2database(Handler handler) {
        return null;
    }

    @Override
    public String saveone2database(int i, Handler handler) {
        return null;
    }

    @Override
    public void setIBackground(String str, int i) {

    }

    @Override
    public void setIColor(String str, int i) {

    }

    @Override
    public void setIImage(String str, int i, int i2) {

    }

    @Override
    public void setIImage(String str, Matrix matrix, int i, int i2) {

    }

    @Override
    public void setIImages(List<String> list, int i) {

    }

    @Override
    public void setIMatrix(Matrix matrix, int i, int i2) {

    }

    @Override
    public void setIMould(String str, int i, int i2) {

    }

    @Override
    public void setIShading(String str, int i) {

    }

    @Override
    public void setIText(String str, int i, int i2) {

    }

    @Override
    public void setITextColor(String str, int i, int i2) {

    }

    @Override
    public void setITextFont(String str, int i, int i2) {

    }

    @Override
    public void setITextSize(int i, int i2, int i3) {

    }

    @Override
    public void setMatrixs(int i, List<Matrix> list, List<PointF> list2) {

    }

    @Override
    public void setScaleWin(float f) {
        this.mScaleWin = mScaleWin;
    }

    @Override
    public void setTexts(int i, List<String> list) {

    }

    private Bitmap getBitmap(String fileName, int w, int h) {
        String path;
        if (fileName.indexOf("/") == -1) {
            path = WORK_FILE + "/" + this.work_id + "/" + fileName;
        } else {
            path = fileName;
        }
        Bitmap bitmap = BitMapUtil.getBitmap(path, w, h);
        if (bitmap != null || fileName.indexOf("/") != -1) {
            return bitmap;
        }
        if (this.downloadList == null) {
            this.downloadList = new HashSet();
        }
        if (this.downloadList.contains(fileName)) {
            return null;
        }
        this.downloadList.add(fileName);
        Message msg = Message.obtain();
        msg.obj = fileName;
        mHandler.sendMessage(msg);
        return bitmap;
    }

    protected void loadImage(List<String> params, InitCompleteListener initCompleteListener) {
        int i = 0;
        for (String p : params) {
            int j = i + 1;
            String url = new StringBuilder(ApiUrl.qiniu_head).append((String) params.get(i)).toString();
//            HttpUtil.get(url, new MyBinaryHttpResponseHandler(p, url, initCompleteListener));
            i = j;
        }
    }
}
