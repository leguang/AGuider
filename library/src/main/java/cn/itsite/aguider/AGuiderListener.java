package cn.itsite.aguider;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public interface AGuiderListener<T extends IGuide> {

    interface OnGuideListener<T extends IGuide> {
        /**
         * Called when IGuide is started
         */
        void onStart(T target);

        /**
         * Called when IGuide is started
         */
        void onStop(T target);
    }

    interface OnGuidertStartListener {
        /**
         * Called when guider is started
         */
        void onStart();
    }

    interface OnGuidertStopListener {
        /**
         * Called when Spotlight is ended
         */
        void onStop();
    }
}
