package org.jeet.timelineviewtest;

import android.view.animation.Animation;
import android.view.animation.Transformation;


import ch.halcyon.squareprogressbar.SquareProgressBar;

/**
 * Created by jeet on 22/3/17.
 */

public class ProgressBarAnimation extends Animation {
    private SquareProgressBar progressBar;
    private float from;
    private float  to;

    public ProgressBarAnimation(SquareProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }

}
