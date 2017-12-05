package cn.itsite.aguider;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class Guider {
    public static final String TAG = Guider.class.getSimpleName();
    public static final int MODE_NEXT = 0;
    public static final int MODE_TOGETHER = 1;
    private int mode = MODE_NEXT;
    private Object anchor;
    private AGuiderListener.OnGuidertStartListener onStartListener;
    private AGuiderListener.OnGuidertStopListener onStopListener;
    private List<Guide> guides;
    private ViewGroup root;

    public Guider(Builder builder) {
        this.anchor = builder.anchor;
        this.onStartListener = builder.onStartListener;
        this.onStopListener = builder.onStopListener;
        this.guides = builder.guides;
        this.mode = builder.mode;
    }

    public Guider show() {
        Activity activity;
        if (anchor != null) {
            if (anchor instanceof Activity) {
                activity = (Activity) anchor;
            } else if (anchor instanceof Fragment) {
                Fragment fragment = (Fragment) anchor;
                activity = fragment.getActivity();
            } else if (anchor instanceof View) {
                View view = (View) anchor;
                if (view.getContext() instanceof Activity) {
                    activity = (Activity) view.getContext();
                } else {
                    throw new IllegalArgumentException("the Context of the view must be an Activity ");
                }
            } else {
                throw new IllegalArgumentException("the anchor's type must be Fragment or Activity or a view ");
            }
            root = (ViewGroup) activity.getWindow().getDecorView();
            //创建并初始化引导者View。
            GuiderView guiderView = new GuiderView(activity);
            guiderView.setGuides(guides);
            guiderView.setOnGuidertStartListener(onStartListener);
            guiderView.setOnGuidertStopListener(onStopListener);
            guiderView.setMode(mode);

            guiderView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            root.getViewTreeObserver().addOnGlobalLayoutListener(guiderView);
            root.addView(guiderView);
        } else {
            throw new IllegalArgumentException("the anchor can not null ");
        }
        return this;
    }

    public static class Builder {
        Object anchor;
        AGuiderListener.OnGuidertStartListener onStartListener;
        AGuiderListener.OnGuidertStopListener onStopListener;
        List<Guide> guides = new ArrayList<>();
        int mode = MODE_NEXT;

        /**
         * 规定：如果有设置锚点，则把锚点作为容器。
         * 如果没有设置，则看目标point，如果point设置的是View，则把View所在context的decorView作为容器。
         *
         * @param anchor
         * @return
         */
        public Builder setAnchor(@NonNull Object anchor) {
            this.anchor = anchor;
            return this;
        }

        public Builder addGuide(@NonNull Guide guide) {
            this.guides.add(guide);
            return this;
        }

        public Builder addGuides(@NonNull Guide... guides) {
            this.guides.addAll(Arrays.asList(guides));
            return this;
        }

        public Builder setOnGuidertStartListener(AGuiderListener.OnGuidertStartListener listener) {
            this.onStartListener = listener;
            return this;
        }

        public Builder setOnGuidertStopListener(AGuiderListener.OnGuidertStopListener listener) {
            this.onStopListener = listener;
            return this;
        }

        public Builder setMode(int mode) {
            this.mode = mode;
            return this;
        }

        public Guider build() {
            return new Guider(this);
        }

        public Guider show() {
            Guider guider = build();
            guider.show();
            return guider;
        }
    }
}
