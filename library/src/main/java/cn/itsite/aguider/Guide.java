package cn.itsite.aguider;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import cn.itsite.aguider.highlight.IHighlight;
import cn.itsite.aguider.position.IPosition;
import cn.itsite.aguider.position.Position;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class Guide implements IGuide {
    private int x;
    private int y;
    private View pointView;
    private IPosition position;
    private IHighlight highlight;
    private View targetView;
    private int targetLayoutId;
    private View view;
    private int viewId;
    private AGuiderListener.OnConvertListener onConvertListener;
    private AGuiderListener.OnGuideListener onGuideListener;
    private ValueAnimator animator;
    private int backgroundColor;

    public Guide(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.pointView = builder.pointView;
        this.position = builder.position;
        this.highlight = builder.highlight;
        this.targetView = builder.targetView;
        this.targetLayoutId = builder.targetLayoutId;
        this.view = builder.view;
        this.viewId = builder.viewId;
        this.onGuideListener = builder.onGuideListener;
        this.onConvertListener = builder.onConvertListener;
        this.animator = builder.animator;
        this.backgroundColor = builder.backgroundColor;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public View getPointView() {
        return pointView;
    }

    public void setPointView(@NonNull View pointView) {
        this.pointView = pointView;
        int[] location = new int[2];
        pointView.getLocationInWindow(location);
        this.x = location[0] + pointView.getWidth() / 2;
        this.y = location[1] + pointView.getHeight() / 2;
    }

    public IPosition getPosition() {
        return position;
    }

    public void setPosition(@NonNull IPosition position) {
        this.position = position;
    }

    public IHighlight getHighlight() {
        return highlight;
    }

    public void setHighlight(IHighlight highlight) {
        this.highlight = highlight;
    }

    public View getTargetView() {
        return targetView;
    }

    public void setTargetView(@NonNull View targetView) {
        this.targetView = targetView;
    }

    public int getTargetLayoutId() {
        return targetLayoutId;
    }

    public void setTargetLayoutId(int targetLayoutId) {
        this.targetLayoutId = targetLayoutId;
    }

    public View getView() {
        return view;
    }

    public void setView(@NonNull View view) {
        this.view = view;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(@LayoutRes int viewId) {
        this.viewId = viewId;
    }

    public void setOnGuideListener(AGuiderListener.OnGuideListener onGuideListener) {
        this.onGuideListener = onGuideListener;
    }

    public AGuiderListener.OnGuideListener getOnGuideListener() {
        return onGuideListener;
    }

    public void setOnConvertListener(AGuiderListener.OnConvertListener onConvertListener) {
        this.onConvertListener = onConvertListener;
    }

    public AGuiderListener.OnConvertListener getOnConvertListener() {
        return onConvertListener;
    }

    public ValueAnimator getAnimator() {
        return animator;
    }

    public void setAnimator(ValueAnimator animator) {
        this.animator = animator;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Builder class which makes it easier to create {@link Guide}
     */
    public static class Builder {
        int x;
        int y;
        View pointView;
        IPosition position = Position.center();
        IHighlight highlight;
        int targetLayoutId;
        View targetView;
        int viewId;
        View view;
        AGuiderListener.OnGuideListener onGuideListener;
        AGuiderListener.OnConvertListener onConvertListener;
        ValueAnimator animator;
        int backgroundColor = 0xB0000000;

        /**
         * Sets the initial position of highlight
         *
         * @param y starting position of y where spotlight reveals
         * @param x starting position of x where spotlight reveals
         * @return This Builder
         */
        public Builder setPoint(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        /**
         * Sets the initial position of highlight
         *
         * @param point starting position where spotlight reveals
         * @return This Builder
         */
        public Builder setPoint(@NonNull Point point) {
            return setPoint(point.x, point.y);
        }

        /**
         * Sets the initial position of highlight
         * Make sure the view already has a fixed position
         *
         * @param pointView starting position where spotlight reveals
         * @return This Builder
         */
        public Builder setPoint(@NonNull View pointView) {
            this.pointView = pointView;
            int[] location = new int[2];
            pointView.getLocationInWindow(location);
            int x = location[0] + pointView.getWidth() / 2;
            int y = location[1] + pointView.getHeight() / 2;
            return setPoint(x, y);
        }

        public Builder setOnGuideListener(@NonNull final AGuiderListener.OnGuideListener listener) {
            this.onGuideListener = listener;
            return this;
        }

        public Builder setOnConvertListener(@NonNull final AGuiderListener.OnConvertListener listener) {
            this.onConvertListener = listener;
            return this;
        }

        public Builder setHighlight(@NonNull IHighlight highlight) {
            this.targetLayoutId = 0;
            this.targetView = null;
            this.highlight = highlight;
            this.animator = ValueAnimator.ofInt(0, highlight.getMax());
            this.animator.setInterpolator(new DecelerateInterpolator(2F));
            this.animator.setDuration(1500);
            return this;
        }

        public Builder setTarget(@NonNull View targetView) {
            this.targetLayoutId = 0;
            this.targetView = targetView;
            return this;
        }

        public Builder setTarget(@LayoutRes int layoutId) {
            this.targetLayoutId = layoutId;
            this.targetView = null;
            return this;
        }

        public Builder setPosition(@NonNull IPosition position) {
            this.position = position;
            return this;
        }

        public Builder setView(@LayoutRes int viewId) {
            this.view = null;
            this.viewId = viewId;
            return this;
        }

        public Builder setView(@NonNull View descriptionView) {
            this.viewId = 0;
            this.view = descriptionView;
            return this;
        }

        public Builder setAnimator(@NonNull ValueAnimator animator) {
            this.animator = animator;
            return this;
        }

        public Builder setBackground(@ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Guide build() {
            return new Guide(this);
        }
    }
}