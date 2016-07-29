package com.mdground.moduleedittest.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.mdground.moduleedittest.R;
import com.mdground.moduleedittest.presenter.ProductionPresenter;
import com.mdground.moduleedittest.utils.CommonUtils;
import com.mdground.moduleedittest.utils.ViewUtils;
import com.mdground.moduleedittest.views.DrawingBoardView;
import com.mdground.moduleedittest.views.IProductionView;
import com.mdground.moduleedittest.views.ProductionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IProductionView {

    private FrameLayout fltViewGroup;

    private ProductionPresenter mProductionPresenter;

    private List<String> mViewKeyArrayList;
    private HashMap<String, ProductionView> mProductionViewHashMap;

    private float mMaxHeight;
    private float mMaxWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMaxHeight = ((((float) ViewUtils.screenHeight())
                - getResources().getDimension(R.dimen.dp_110))
                - ((float) (ViewUtils.screenWidth() / 5)))
                - ((float) CommonUtils.getStatusBarHeight(getApplicationContext()));

        mMaxWidth = ((float) ViewUtils.screenWidth()) - getResources().getDimension(R.dimen.dp_24);

        initView();
        initProductionView();
        initPresenter();
    }

    private void initView() {
        this.fltViewGroup = (FrameLayout) findViewById(R.id.viewgroup);
    }

    private void initProductionView() {
        this.mViewKeyArrayList = new ArrayList();
        this.mViewKeyArrayList.add("one");
        this.mViewKeyArrayList.add("two");
        this.mProductionViewHashMap = new HashMap();
        this.mProductionViewHashMap.put((String) this.mViewKeyArrayList.get(0), new ProductionView(this));
        this.mProductionViewHashMap.put((String) this.mViewKeyArrayList.get(1), new ProductionView(this));
        this.fltViewGroup.addView((View) this.mProductionViewHashMap.get(this.mViewKeyArrayList.get(1)));
        this.fltViewGroup.addView((View) this.mProductionViewHashMap.get(this.mViewKeyArrayList.get(0)));
    }

    private void initPresenter() {
        Intent intent = getIntent();
        this.mProductionPresenter = new ProductionPresenter(this, this, intent.getStringExtra("workid"), intent.getStringArrayListExtra("selected"));
    }

    private ProductionView firstProductView() {
        return (ProductionView) this.mProductionViewHashMap.get(this.mViewKeyArrayList.get(0));
    }

    @Override
    public void addDrawBroad(float dx, float dy, float w, float h, Bitmap mould, Bitmap photo, Matrix matrix, float rate, int position) {
        DrawingBoardView drawingBoardView = new DrawingBoardView(this, w, h, mould, photo, matrix, rate);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) w, (int) h);
        layoutParams.setMargins((int) dx, (int) dy, 0, 0);
        drawingBoardView.setTag(Integer.valueOf(position));
        drawingBoardView.setLayoutParams(layoutParams);
        firstProductView().drawBoardLayer.addView(drawingBoardView, position);
        firstProductView().textLayer.addView(new View(this), position);
        firstProductView().drawingBoardViewList.add(drawingBoardView);
    }

    @Override
    public void addText(String str, String str2, String str3, int i, int i2, float f, float f2, String str4) {

    }

    @Override
    public void cancalProgress() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void close() {

    }

    @Override
    public String getIBackground() {
        return null;
    }

    @Override
    public String getIColor() {
        return null;
    }

    @Override
    public String getIImage(int i) {
        return null;
    }

    @Override
    public List<String> getIImage() {
        return null;
    }

    @Override
    public Matrix getIMatrix(int position) {
        return ((DrawingBoardView) firstProductView().drawingBoardViewList.get(getImageTag(position))).getIMatrix();
    }

    @Override
    public String getIMould() {
        return null;
    }

    @Override
    public String getIShading() {
        return null;
    }

    @Override
    public String getIText(int i) {
        return null;
    }

    @Override
    public String getITextColor(int i) {
        return null;
    }

    @Override
    public String getITextFont(int i) {
        return null;
    }

    @Override
    public float getITextSize(int i) {
        return 0;
    }

    @Override
    public List<Matrix> getMatrixs() {
        return null;
    }

    @Override
    public List<PointF> getOffset() {
        return null;
    }

    @Override
    public List<String> getTexts() {
        return null;
    }

    @Override
    public void imageBigger(int i) {

    }

    @Override
    public void imageMirror(int i) {

    }

    @Override
    public void imageRotate(int i) {

    }

    @Override
    public void imageSmaller(int i) {

    }

    @Override
    public void init() {

    }

    @Override
    public void jump() {

    }

    @Override
    public void progress(int i, String str) {

    }

    @Override
    public void setAnimListener(Animation.AnimationListener animationListener) {

    }

    @Override
    public float setEditHeight(float height) {
        float scaleWin;
        if (height > mMaxHeight) {
            scaleWin = mMaxHeight / height;
        } else {
            scaleWin = 1.0f;
            mMaxHeight = height;
        }
        mMaxWidth *= scaleWin;
        LayoutParams params = new LayoutParams((int) mMaxWidth, (int) mMaxHeight);
        firstProductView().bgColorLayer.setLayoutParams(params);

        return scaleWin;
    }

    @Override
    public void setIBackground(Bitmap bitmap) {
        firstProductView().backgroundLayer.setImageBitmap(bitmap);
    }

    @Override
    public void setIColor(String color) {
        firstProductView().bgColorLayer.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void setIImage(Bitmap bitmap, Matrix matrix, int mould_id, float rate) {
        DrawingBoardView drawingBoardView = firstProductView().drawingBoardViewList.get(getImageTag(mould_id));
        drawingBoardView.setPhoto(bitmap, matrix, rate);
    }

    @Override
    public void setIImage(String str, Matrix matrix) {

    }

    @Override
    public void setIImage(List<String> list) {

    }

    @Override
    public void setIMatrix() {

    }

    @Override
    public void setIMould(Bitmap bitmap, int id, float dx, float dy, float width, float height) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) width, (int) height);
        params.topMargin = (int) dy;
        params.leftMargin = (int) dx;

        DrawingBoardView drawingBoardView = firstProductView().drawingBoardViewList.get(getImageTag(id));
        drawingBoardView.setMould(width, height, bitmap);
        drawingBoardView.setLayoutParams(params);
    }

    private int getImageTag(int position) {
        int i = 0;
        for (DrawingBoardView v : firstProductView().drawingBoardViewList) {
            if (((Integer) v.getTag()).intValue() == position) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public void setIShading(Bitmap bitmap) {

    }

    @Override
    public void setIText(String str, int i) {

    }

    @Override
    public void setIText(String str, int i, float f, float f2, float f3, float f4) {

    }

    @Override
    public void setITextColor(String str, int i) {

    }

    @Override
    public void setITextFont(String str, int i) {

    }

    @Override
    public void setITextSize(float f, int i) {

    }

    @Override
    public void setMoBanIcon(String str, int i) {

    }

    @Override
    public void setPageindex(int i, int i2) {

    }

    @Override
    public void show(String str) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void toLeftAnim() {

    }

    @Override
    public void toRightAnim() {

    }

    @Override
    public void waitOver() {

    }

    @Override
    public void waitToMistiness() {

    }
}

