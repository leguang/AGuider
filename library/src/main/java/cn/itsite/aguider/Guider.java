package cn.itsite.aguider;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
    private List<AGuiderListener.OnGuiderStartListener> onStartListeners;
    private List<AGuiderListener.OnGuiderStopListener> onStopListeners;
    private List<Guide> guides;
    private GuiderView guiderView;

    public Guider(Builder builder) {
        this.anchor = builder.anchor;
        this.onStartListeners = builder.onStartListeners;
        this.onStopListeners = builder.onStopListeners;
        this.guides = builder.guides;
        this.mode = builder.mode;
    }

    public Guider show() {
        if (anchor != null) {
            //检测锚点。
            Activity activity = initAnchor(anchor);
            //创建并初始化引导者View。
            initGuiderView(activity);
            //添加到DecorView中。
            add2Decor(activity, guiderView);
        } else {
            throw new IllegalArgumentException("the Context of the view must be an Activity ");
        }
        return this;
    }

    private void add2Decor(Activity activity, GuiderView guiderView) {
        ViewGroup root = (ViewGroup) activity.getWindow().getDecorView();
        root.getViewTreeObserver().addOnGlobalLayoutListener(guiderView);
        root.addView(guiderView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public Guider showInWindow() {
        if (anchor != null) {
            //检测锚点。
            Activity activity = initAnchor(anchor);
            //创建并初始化引导者View。
            initGuiderView(activity);
            //添加到当前Window中。
            add2Window(activity, guiderView);
        } else {
            throw new NullPointerException("the anchor can not null ");
        }
        return this;
    }

    private void add2Window(Activity activity, GuiderView guiderView) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addContentView(guiderView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private Activity initAnchor(Object anchor) {
        if (anchor instanceof Activity) {
            return (Activity) anchor;
        } else if (anchor instanceof Fragment) {
            Fragment fragment = (Fragment) anchor;
            return fragment.getActivity();
        } else if (anchor instanceof View) {
            View view = (View) anchor;
            if (view.getContext() instanceof Activity) {
                return (Activity) view.getContext();
            } else {
                throw new IllegalArgumentException("the Context of the view must be an Activity ");
            }
        } else {
            throw new IllegalArgumentException("the anchor's type must be Fragment or Activity or a view ");
        }
    }

    private GuiderView initGuiderView(Activity activity) {
        guiderView = new GuiderView(activity);
        guiderView.setGuides(guides);
        guiderView.setOnGuidertStartListeners(onStartListeners);
        guiderView.setOnGuidertStopListeners(onStopListeners);
        guiderView.setMode(mode);
        return guiderView;
    }

    public void dismiss() {
        if (guiderView != null) {
            guiderView.dismiss();
            guiderView = null;
        }
    }

    public boolean isVisible() {
        return guiderView != null && guiderView.isVisible();
    }

    public Guider addOnGuidertStartListener(AGuiderListener.OnGuiderStartListener listener) {
        if (onStartListeners == null) {
            onStartListeners = new ArrayList<>();
        }
        onStartListeners.add(listener);
        return this;
    }

    public Guider addOnGuidertStopListener(AGuiderListener.OnGuiderStopListener listener) {
        if (onStopListeners == null) {
            onStopListeners = new ArrayList<>();
        }
        onStopListeners.add(listener);
        return this;
    }

    public static class Builder {
        Object anchor;
        List<AGuiderListener.OnGuiderStartListener> onStartListeners = new ArrayList<>();
        List<AGuiderListener.OnGuiderStopListener> onStopListeners = new ArrayList<>();
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

        public Builder addOnGuidertStartListener(AGuiderListener.OnGuiderStartListener listener) {
            if (onStartListeners == null) {
                onStartListeners = new ArrayList<>();
            }
            onStartListeners.add(listener);
            return this;
        }

        public Builder addOnGuidertStopListener(AGuiderListener.OnGuiderStopListener listener) {
            if (onStopListeners == null) {
                onStopListeners = new ArrayList<>();
            }
            onStopListeners.add(listener);
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

        public Guider showInWindow() {
            Guider guider = build();
            guider.showInWindow();
            return guider;
        }
    }
}
