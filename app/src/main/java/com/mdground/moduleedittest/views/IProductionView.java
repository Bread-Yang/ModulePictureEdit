package com.mdground.moduleedittest.views;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.animation.Animation.AnimationListener;
import java.util.List;

public interface IProductionView {

    void addDrawBroad(float dx, float dy, float w, float h, Bitmap mould, Bitmap photo, Matrix matrix, float rate, int position);

    void addText(String str, String str2, String str3, int i, int i2, float f, float f2, String str4);

    void cancalProgress();

    void clear();

    void close();

    String getIBackground();

    String getIColor();

    String getIImage(int i);

    List<String> getIImage();

    Matrix getIMatrix(int i);

    String getIMould();

    String getIShading();

    String getIText(int i);

    String getITextColor(int i);

    String getITextFont(int i);

    float getITextSize(int i);

    List<Matrix> getMatrixs();

    List<PointF> getOffset();

    List<String> getTexts();

    void imageBigger(int i);

    void imageMirror(int i);

    void imageRotate(int i);

    void imageSmaller(int i);

    void init();

    void jump();

    void progress(int i, String str);

    void setAnimListener(AnimationListener animationListener);

    float setEditHeight(float f);

    void setIBackground(Bitmap bitmap);

    void setIColor(String color);

    void setIImage(Bitmap bitmap, Matrix matrix, int i, float f);

    @Deprecated
    void setIImage(String str, Matrix matrix);

    void setIImage(List<String> list);

    void setIMatrix();

    void setIMould(Bitmap bitmap, int i, float f, float f2, float f3, float f4);

    void setIShading(Bitmap bitmap);

    void setIText(String str, int i);

    void setIText(String str, int i, float f, float f2, float f3, float f4);

    void setITextColor(String str, int i);

    void setITextFont(String str, int i);

    void setITextSize(float f, int i);

    void setMoBanIcon(String str, int i);

    void setPageindex(int i, int i2);

    void show(String str);

    void showProgress();

    void toLeftAnim();

    void toRightAnim();

    void waitOver();

    void waitToMistiness();
}
