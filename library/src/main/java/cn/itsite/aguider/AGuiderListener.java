package cn.itsite.aguider;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public interface AGuiderListener {

    interface OnConvertListener {

        void convert(BaseViewHolder holder, GuiderView guiderView);
    }

    interface OnGuideListener {
        /**
         * Called when IGuide is started
         */
        void onStart(Guide guide);

        /**
         * Called when IGuide is started
         */
        void onStop(Guide guide);
    }

    interface OnGuiderStartListener {
        /**
         * Called when guider is started
         */
        void onStart();
    }

    interface OnGuiderStopListener {
        /**
         * Called when Spotlight is ended
         */
        void onStop();
    }

    interface OnAGuiderStartListener {
        /**
         * Called when aguider is started
         */
        void onStart();
    }

    interface OnAGuiderStopListener {
        /**
         * Called when aguider is ended
         */
        void onStop();
    }
}
