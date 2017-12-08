package cn.itsite.aguider;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class AGuider {
    public static final String TAG = AGuider.class.getSimpleName();
    private AGuiderListener.OnAGuiderStartListener onStartListener;
    private AGuiderListener.OnAGuiderStopListener onStopListener;
    private List<Guider> guiders;
    private int index = 0;

    public AGuider(Builder builder) {
        this.onStartListener = builder.onStartListener;
        this.onStopListener = builder.onStopListener;
        this.guiders = builder.guiders;
        init();
    }

    private void init() {
        for (Guider guider : guiders) {
            guider.addOnGuidertStartListener(new OnGuiderStartListener());
            guider.addOnGuidertStopListener(new OnGuiderStopListener());
        }
    }

    public AGuider show() {
        if (guiders != null && !guiders.isEmpty()) {
            guiders.get(index).show();
        }
        return this;
    }

    /**
     * dismiss current GuiderView.
     */
    public void dismissGuider() {
        if (guiders != null && !guiders.isEmpty()) {
            if (index < guiders.size()) {
                guiders.get(index).dismiss();
            }
        }
    }

    /**
     * dismiss all GuiderView.
     */
    public void dismiss() {
        if (guiders != null && !guiders.isEmpty()) {
            if (index < guiders.size()) {
                Guider guider = guiders.get(index);
                guiders.clear();
                guider.dismiss();
            }
        }
    }

    public class OnGuiderStartListener implements AGuiderListener.OnGuiderStartListener {

        @Override
        public void onStart() {
            if (index == 0 && onStartListener != null) {
                onStartListener.onStart();
            }
        }
    }

    public class OnGuiderStopListener implements AGuiderListener.OnGuiderStopListener {

        @Override
        public void onStop() {
            if (guiders != null) {
                if (++index < guiders.size()) {
                    guiders.get(index).show();
                }

                if (index >= guiders.size() && onStopListener != null) {
                    onStopListener.onStop();
                }
            }
        }
    }

    public static class Builder {
        private AGuiderListener.OnAGuiderStartListener onStartListener;
        private AGuiderListener.OnAGuiderStopListener onStopListener;
        private List<Guider> guiders = new ArrayList<>();

        public Builder addGuider(@NonNull Guider guider) {
            this.guiders.add(guider);
            return this;
        }

        public Builder addGuiders(@NonNull Guider... guiders) {
            this.guiders.addAll(Arrays.asList(guiders));
            return this;
        }

        public Builder setOnAGuidertStartListener(AGuiderListener.OnAGuiderStartListener listener) {
            this.onStartListener = listener;
            return this;
        }

        public Builder setOnAGuidertStopListener(AGuiderListener.OnAGuiderStopListener listener) {
            this.onStopListener = listener;
            return this;
        }

        public AGuider build() {
            return new AGuider(this);
        }

        public AGuider show() {
            AGuider aGuider = build();
            aGuider.show();
            return aGuider;
        }
    }
}
