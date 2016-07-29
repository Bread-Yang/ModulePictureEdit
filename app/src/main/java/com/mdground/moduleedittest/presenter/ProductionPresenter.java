package com.mdground.moduleedittest.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.AsyncTask;
import android.view.animation.Animation;

import com.mdground.moduleedittest.model.IProductionModel;
import com.mdground.moduleedittest.model.ProductionModel;
import com.mdground.moduleedittest.views.IProductionView;
import com.socks.library.KLog;

import java.util.List;


/**
 * Created by yoghourt on 7/15/16.
 */

public class ProductionPresenter {

    private Animation.AnimationListener mAnimationListener;
    private int mMouldCount;
    private boolean mDrawCompletion;
    private int mPagePosition = 3, mPrePagePosition = 2;
//    private int mPagePosition, mPrePagePosition;
    private IProductionModel mProductionModel;
    private IProductionView mIProductionView;

    public ProductionPresenter(final Activity context, IProductionView iProductionView,
                               String work_id, List<String> imageFiles) {
        mIProductionView = iProductionView;

        // 下载完数据后,加载页面
        mProductionModel = new ProductionModel(context, work_id, imageFiles, null,
                new ProductionModel.InitCompleteListener() {
                    @Override
                    public void complete(boolean z) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);

                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setHeight();
                                            draw1st();
                                            mIProductionView.cancalProgress();
                                            mIProductionView.init();
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }

                    @Override
                    public void progress(int i, String str) {

                    }
                });
    }

    public void setHeight() {
        mProductionModel.setScaleWin(this.mIProductionView.setEditHeight(this.mProductionModel.getEditHeightOnAndroid()));
    }

    public void draw1st() {
        mDrawCompletion = false;

        // 开始生成页面
        mMouldCount = mProductionModel.getMouldCount(mPagePosition);
        mIProductionView.setIColor("#ffffff");

        for (int i = 0; i < this.mMouldCount; i++) {
            if (mProductionModel.getType(this.mPagePosition, i) == 1) {
                initText(i);
            } else if (this.mProductionModel.getType(this.mPagePosition, i) == 0) {
                this.mIProductionView.addDrawBroad(0.0f, 0.0f, 0.0f, 0.0f, null, null, null, 1.0f, i);
                // 加载图片,通过ProductionModel获取Matrix,Module图片,用户选择的图片
                new ImageTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[]{Integer.valueOf(i)});
            } else {
//                this.mIProductionView.addText("", "#000000", null, 0, i, 0.0f, 0.0f, 0);
            }
        }
    }

    private void initText(int i) {
        Point startPosition = mProductionModel.getMouldLocationOnAndroid(mPagePosition, i);
        String text = mProductionModel.getIText(mPagePosition, i);
        String textColor = mProductionModel.getITextColor(mPagePosition, i);
        String textFont = mProductionModel.getITextFont(mPagePosition, i);
        int textSize = mProductionModel.getITextSize(mPagePosition, i);
        String font_direct = mProductionModel.getITextFontDirect(mPagePosition, i);
        this.mIProductionView.addText(text, textColor, textFont, textSize, i, (float) startPosition.x, (float) startPosition.y, font_direct);
    }

    private class ImageTask extends AsyncTask<Integer, Integer, Bitmap[]> {

        private float dx, dy;
        private float width, height;
        private float rate;
        private int modulePosition;
        private Matrix matrix;

        @Override
        protected Bitmap[] doInBackground(Integer... integers) {
            this.modulePosition = integers[0].intValue();
            Point moduleSizePoint = ProductionPresenter.this.mProductionModel.getMouldSizeOnAndroid(ProductionPresenter.this.mPagePosition, this.modulePosition);
            Point moduleStartPositionPoint = ProductionPresenter.this.mProductionModel.getMouldLocationOnAndroid(ProductionPresenter.this.mPagePosition, this.modulePosition);
            this.width = (float) moduleSizePoint.x;
            this.height = (float) moduleSizePoint.y;

            KLog.e("开始获取图片...page: " + ProductionPresenter.this.mPagePosition + " >image: " + this.modulePosition);
            Bitmap mould = mProductionModel.getIMould(ProductionPresenter.this.mPagePosition, this.modulePosition);
            Bitmap photo = mProductionModel.getIImage(ProductionPresenter.this.mPagePosition, this.modulePosition);
            Bitmap backgroundBitmap = mProductionModel.getIBackground(ProductionPresenter.this.mPagePosition);

            KLog.e("获取模版图片完成..., mould : " + mould);
            KLog.e("获取用户选择图片完成.., photo : " + photo);

            this.matrix = ProductionPresenter.this.mProductionModel.getIMatrix(ProductionPresenter.this.mPagePosition, this.modulePosition);
            this.rate = ProductionPresenter.this.mProductionModel.getRate(ProductionPresenter.this.mPagePosition, this.modulePosition);
            this.dx = (float) moduleStartPositionPoint.x;
            this.dy = (float) moduleStartPositionPoint.y;
            return new Bitmap[]{mould, photo, backgroundBitmap};
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmaps) {
            mIProductionView.setIMould(bitmaps[0], this.modulePosition, this.dx, this.dy, this.width, this.height);
            mIProductionView.setIImage(bitmaps[1], this.matrix, this.modulePosition, this.rate);
            mIProductionView.setIBackground(bitmaps[2]);
            super.onPostExecute(bitmaps);
        }
    }
}
