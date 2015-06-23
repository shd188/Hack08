package com.aimer.shd188.hack08;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;

import java.util.Random;


public class MainActivity extends Activity implements AnimatorListener {

    private FrameLayout mContainer;
    private ImageView mView;
    private Random mRandom = new Random();
    private int mIndex = 0;

    private static final int[] PHOTOS = new int[]{R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4, R.drawable.photo5, R.drawable.photo6};
    private static final int ANIM_COUNT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建布局容器
        mContainer = new FrameLayout(this);
        mContainer.setLayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //创建ImageView，并将其添加到布局容器中
        mView = createNewView();
        mContainer.addView(mView);

        setContentView(mContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nextAnimation();
    }

    private ImageView createNewView() {
        ImageView ret = new ImageView(this);
        ret.setLayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ret.setScaleType(ImageView.ScaleType.FIT_XY);
        ret.setImageResource(PHOTOS[mIndex]);
        mIndex = (mIndex + 1 < PHOTOS.length) ? mIndex + 1 : 0;
        return ret;
    }

    private void nextAnimation() {
        AnimatorSet anim = new AnimatorSet();

        final int index = mRandom.nextInt(ANIM_COUNT);

        switch (index) {
            case 0:
                anim.playTogether(ObjectAnimator.ofFloat(mView, "scaleX", 1.5f, 1f), ObjectAnimator.ofFloat(mView, "scaleY", 1.5f, 1f));
                break;
            case 1:
                anim.playTogether(
                        ObjectAnimator.ofFloat(mView, "scaleX", 1, 1.5f),
                        ObjectAnimator.ofFloat(mView, "scaleY", 1, 1.5f));
                break;

            case 2:
                AnimatorProxy.wrap(mView).setScaleX(1.5f);
                AnimatorProxy.wrap(mView).setScaleY(1.5f);
                anim.playTogether(ObjectAnimator.ofFloat(mView,
                        "translationY", 80f, 0f));
                break;

            case 3:
            default:
                AnimatorProxy.wrap(mView).setScaleX(1.5f);
                AnimatorProxy.wrap(mView).setScaleY(1.5f);
                anim.playTogether(ObjectAnimator.ofFloat(mView,
                        "translationX", 0f, 40f));
                break;
        }
        anim.setDuration(3000);
        anim.addListener(this);
        anim.start();
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        mContainer.removeView(mView);
        mView = createNewView();
        mContainer.addView(mView);

        nextAnimation();
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
