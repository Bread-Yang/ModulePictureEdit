package com.mdground.moduleedittest.model;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;

import com.mdground.moduleedittest.utils.BitMapUtil;

import java.util.List;

public interface IProductionModel {
    public static final int LOAD_FROM_DATABASE = 3;
    public static final int LOAD_MODEl = 4;
    public static final int LOAD_PAGE = 5;
    public static final int SAVE_ALL_TO_DATABASE = 1;
    public static final int SAVE_ONE_TO_DATABASE = 2;

    void closePage(int i, int i2);

    void create(int i, float f);

    void createAll(float f);

    void createIcon(int i, float f, Handler handler);

    float getEditHeightOnAndroid();

    float getEdit_w();

    Bitmap getIBackground(int i);

    String getIColor(int i);

    Bitmap getIImage(int pagePosition, int modulePosition);

    Bitmap getIImage(String str, int i, int i2);

    List<String> getIImages(int i);

    Matrix getIMatrix(int i, int i2);

    List<Matrix> getIMatrixs(int i);

    Bitmap getIMould(int i, int i2);

    List<Bitmap> getIMoulds(int i);

    Bitmap getIShading(int i);

    Bitmap getIShading(int i, String str);

    String getIText(int i, int i2);

    String getITextColor(int i, int i2);

    String getITextFont(int i, int i2);

    String getITextFontDirect(int i, int i2);

    int getITextSize(int i, int i2);

    List<String> getIconPaths();

    BitMapUtil.Size getMouldImageSize(int page_id, int mould_id);

    JMould getMould(int i, int i2);

    int getMouldCount(int i);

    float getMouldHeight(int i, int i2);

    List<Float> getMouldHeights(int i);

    Point getMouldLocationOnAndroid(int i, int i2);

    Point getMouldSizeOnAndroid(int i, int i2);

    float getMouldWidth(int i, int i2);

    List<Float> getMouldWidths(int i);

    float getMouldX(int i, int i2);

    List<Float> getMouldXs(int i);

    float getMouldY(int i, int i2);

    List<Float> getMouldYs(int i);

    JPage getPage(int i);

    int getPageCount();

    float getPage_h();

    float getPage_w();

    float getRate(int pagePosition, int modulePosition);

    float getRateOfEditWidthOnAndroid();

    Point getShadingSize(String str);

    int getType(int i, int i2);

    String getUID();

    void getWorkIDOnPC(ProductionModel.InitCompleteListener initCompleteListener);

    void imageBigger(int i, int i2);

    void imageMirror(int i, int i2);

    void imageRotate(int i, int i2);

    void imageSmaller(int i, int i2);

    String loadFromdatabase(Handler handler);

    IProductionModel loadModel(int i, Handler handler);

    void loadPage(int i, Handler handler);

    void savePage(ProductionModel.InitCompleteListener initCompleteListener);

    String saveall2database(Handler handler);

    String saveone2database(int i, Handler handler);

    void setIBackground(String str, int i);

    void setIColor(String str, int i);

    void setIImage(String str, int i, int i2);

    void setIImage(String str, Matrix matrix, int i, int i2);

    void setIImages(List<String> list, int i);

    void setIMatrix(Matrix matrix, int i, int i2);

    void setIMould(String str, int i, int i2);

    void setIShading(String str, int i);

    void setIText(String str, int i, int i2);

    void setITextColor(String str, int i, int i2);

    void setITextFont(String str, int i, int i2);

    void setITextSize(int i, int i2, int i3);

    void setMatrixs(int i, List<Matrix> list, List<PointF> list2);

    void setScaleWin(float f);

    void setTexts(int i, List<String> list);
}
