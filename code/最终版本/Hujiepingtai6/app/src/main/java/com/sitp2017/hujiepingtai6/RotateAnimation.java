package com.sitp2017.hujiepingtai6;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by gameben on 2017/5/12.
 */
public class RotateAnimation { //翻转动画类，用于在主界面中选择身份

    private ImageView[] images = new ImageView[2];

    private ScaleAnimation sato0 = new ScaleAnimation(1,0,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);

    private ScaleAnimation sato1 = new ScaleAnimation(0,1,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);

    private void showImage(int pos){

        images[pos].setVisibility(View.VISIBLE);
        images[1-pos].setVisibility(View.GONE);
    }

    public void startAnimation() {
        if (images[0].getVisibility() == View.VISIBLE){
            images[0].startAnimation(sato0);
        }else{
            images[1].startAnimation(sato0);
        }
    }

    public RotateAnimation(ImageView image_1, ImageView image_2) {
        images[0] = image_1;
        images[1] = image_2;

        sato0.setDuration(500);
        sato1.setDuration(500);

        showImage(0);

        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (images[0].getVisibility() == View.VISIBLE){
                    images[0].setAnimation(null);
                    showImage(1);
                    images[1].startAnimation(sato1);
                }else{
                    images[1].setAnimation(null);
                    showImage(0);
                    images[0].startAnimation(sato1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}


