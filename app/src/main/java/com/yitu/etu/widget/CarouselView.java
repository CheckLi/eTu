package com.yitu.etu.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.yitu.etu.R;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.widget.image.RoundImageView2;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/28.
 */
public class CarouselView extends FrameLayout {
    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {0f, 0f, 0f, 0f, 0.0f, 0.0f, 0.0f, 0.0f,};

    private ConvenientBanner convenientBanner;
    private int topRadius;
    public CarouselView(Context context) {
        super(context);
        init();
    }

    public CarouselView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CarouselView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    List<String> paths;

    public void setPath(List<String> paths) {
        this.paths = paths;
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, paths);
        if(paths.size()>1) {
            convenientBanner
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        }
        else{
            convenientBanner.setManualPageable(false);//设置不能手动影响
        }

    }

    public void setTopRadius(int topRadius) {
        this.topRadius=topRadius;
        if(topRadius>0){
            int width = getResources().getDisplayMetrics().widthPixels- Tools.dp2px(getContext(),20);
            convenientBanner.setLayoutParams(new FrameLayout.LayoutParams(width, width * 2 / 3));
        }
    }

    public void init() {
        convenientBanner = new ConvenientBanner(getContext());
        int width = getResources().getDisplayMetrics().widthPixels;
        convenientBanner.setLayoutParams(new FrameLayout.LayoutParams(width, width * 2 / 3));
        addView(convenientBanner);

        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。


    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, widthMeasureSpec * 2 / 3);
//    }
    public class LocalImageHolderView implements Holder<String> {
        private RoundImageView2 imageView;

        @Override
        public View createView(Context context) {
            imageView = new RoundImageView2(context);
            imageView.setTopRadius(topRadius);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            if(!data.startsWith(".%")){
                ImageLoadUtil.getInstance().loadImage(imageView,Urls.address + data,R.drawable.ic_default_image,-1,-1);
            }
            else{
                imageView.setImageDrawable(getResources().getDrawable(Integer.parseInt(data.replace(".%",""))));
            }
        }
    }
}
