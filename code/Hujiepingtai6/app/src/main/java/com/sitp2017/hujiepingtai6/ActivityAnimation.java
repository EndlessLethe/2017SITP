package com.sitp2017.hujiepingtai6;

import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionInflater;

/**
 * Created by gameben on 2017/5/5.
 */
public class ActivityAnimation { //实现活动切换的动画效果（此类暂时未使用，动画效果在 styles.xml 中的实现）

    public Transition explode;

    public Transition slideStart;

    public Transition slideEnd;

    public Transition fade;

    public ActivityAnimation(Context context) {
        super();
        explode = TransitionInflater.from(context).inflateTransition(R.transition.explode);

        slideStart = TransitionInflater.from(context).inflateTransition(R.transition.slide_start);

        slideEnd = TransitionInflater.from(context).inflateTransition(R.transition.slide_end);

        fade = TransitionInflater.from(context).inflateTransition(R.transition.fade);
    }
}
