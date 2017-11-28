package cn.itsite.aguider;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;

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
    private View mAnchor;
    private AGuiderListener.OnGuidertStartListener onGuidertStartListener;
    private AGuiderListener.OnGuidertStopListener onGuidertStopListener;
    private List<Guide> guides;
    private int backgroundColor;

    public AGuider(Builder builder) {
        this.mAnchor = builder.mAnchor;
        this.onGuidertStartListener = builder.onGuidertStartListener;
        this.onGuidertStopListener = builder.onGuidertStopListener;
        this.guides = builder.guides;
        this.backgroundColor = builder.backgroundColor;
    }

    public AGuider show() {

        return this;
    }


    public static class Builder {
        private View mAnchor;
        private AGuiderListener.OnGuidertStartListener onGuidertStartListener;
        private AGuiderListener.OnGuidertStopListener onGuidertStopListener;
        private List<Guide> guides = new ArrayList<>();
        private int backgroundColor;

        public Builder() {
        }

        public Builder addGuide(@NonNull Guide guide) {
            this.guides.add(guide);
            return this;
        }

        public Builder addGuides(@NonNull Guide... guides) {
            this.guides.addAll(Arrays.asList(guides));
            return this;
        }

        /**
         * 引导层背景色
         */
        public Builder background(@ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }


        /**
         * 设置引导层隐藏，显示监听
         */
        public Builder OnGuidertStartListener(AGuiderListener.OnGuidertStartListener listener) {
            this.onGuidertStartListener = listener;
            return this;
        }

        public Builder setOnGuidertStopListener(AGuiderListener.OnGuidertStopListener listener) {
            this.onGuidertStopListener = listener;
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
