package cn.itsite.aguider;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import cn.itsite.aguider.highlight.IHighlight;
import cn.itsite.aguider.position.IPosition;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class Guide implements IGuide {
    private int x;
    private int y;
    private IPosition position;
    private IHighlight IHighlight;
    private View targetView;
    private int targetLayoutId;
    private View descriptionView;
    private int descriptionLayoutId;
    private AGuiderListener.OnGuideListener listener;
    private ValueAnimator animator;

    public Guide(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.position = builder.position;
        this.IHighlight = builder.IHighlight;
        this.targetView = builder.targetView;
        this.targetLayoutId = builder.targetLayoutId;
        this.descriptionView = builder.descriptionView;
        this.descriptionLayoutId = builder.descriptionLayoutId;
        this.listener = builder.listener;
        this.animator = builder.animator;
    }

    public AGuiderListener.OnGuideListener getListener() {
        return listener;
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

    public IPosition getPosition() {
        return position;
    }

    public void setPosition(IPosition position) {
        this.position = position;
    }

    public IHighlight getIHighlight() {
        return IHighlight;
    }

    public void setIHighlight(IHighlight IHighlight) {
        this.IHighlight = IHighlight;
    }

    public View getTargetView() {
        return targetView;
    }

    public void setTargetView(View targetView) {
        this.targetView = targetView;
    }

    public int getTargetLayoutId() {
        return targetLayoutId;
    }

    public void setTargetLayoutId(int targetLayoutId) {
        this.targetLayoutId = targetLayoutId;
    }

    public View getDescriptionView() {
        return descriptionView;
    }

    public void setDescriptionView(View descriptionView) {
        this.descriptionView = descriptionView;
    }

    public int getDescriptionLayoutId() {
        return descriptionLayoutId;
    }

    public void setDescriptionLayoutId(int descriptionLayoutId) {
        this.descriptionLayoutId = descriptionLayoutId;
    }

    public void setListener(AGuiderListener.OnGuideListener listener) {
        this.listener = listener;
    }

    public ValueAnimator getAnimator() {
        return animator;
    }

    public void setAnimator(ValueAnimator animator) {
        this.animator = animator;
    }

    /**
     * Builder class which makes it easier to create {@link Guide}
     */
    public static class Builder {
        int x;
        int y;
        IPosition position;
        IHighlight IHighlight;
        int targetLayoutId;
        View targetView;
        int descriptionLayoutId;
        View descriptionView;
        AGuiderListener.OnGuideListener listener;
        ValueAnimator animator;

        /**
         * Sets the initial position of IHighlight
         *
         * @param y starting position of y where spotlight reveals
         * @param x starting position of x where spotlight reveals
         * @return This Builder
         */
        Builder setPoint(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        /**
         * Sets the initial position of IHighlight
         *
         * @param point starting position where spotlight reveals
         * @return This Builder
         */
        public Builder setPoint(@NonNull Point point) {
            return setPoint(point.x, point.y);
        }

        /**
         * Sets the initial position of IHighlight
         * Make sure the view already has a fixed position
         *
         * @param view starting position where spotlight reveals
         * @return This Builder
         */
        public Builder setPoint(@NonNull View view) {
            int[] location = new int[2];
            view.getLocationInWindow(location);
            int x = location[0] + view.getWidth() / 2;
            int y = location[1] + view.getHeight() / 2;
            return setPoint(x, y);
        }

        public Builder setOnGuideListener(@NonNull final AGuiderListener.OnGuideListener<Guide> listener) {
            this.listener = listener;
            return this;
        }

        public Builder setIHighlight(@NonNull IHighlight IHighlight) {
            this.targetLayoutId = 0;
            this.targetView = null;
            this.IHighlight = IHighlight;
            this.animator = ValueAnimator.ofInt(0, IHighlight.getMax());
            this.animator.setInterpolator(new DecelerateInterpolator(2F));
            this.animator.setDuration(1000);
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

        public Builder setPosition(IPosition position) {
            this.position = position;
            return this;
        }

        public Builder setDescription(@LayoutRes int descriptionLayoutId) {
            this.descriptionView = null;
            this.descriptionLayoutId = descriptionLayoutId;
            return this;
        }

        public Builder setDescription(@NonNull View descriptionView) {
            this.descriptionLayoutId = 0;
            this.descriptionView = descriptionView;
            return this;
        }

        public Builder setAnimator(@NonNull ValueAnimator animator) {
            this.animator = animator;
            return this;
        }

        public Guide build() {
            return new Guide(this);
        }
    }
}