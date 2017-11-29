package cn.itsite.aguider;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.socks.library.KLog;

import cn.itsite.aguider.highlight.IHighlight;
import cn.itsite.aguider.position.IPosition;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class GuiderView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = GuiderView.class.getSimpleName();
    private Paint mPaint;
    private int backgroundColor = 0xB0000000;
    private Guider.Builder builder;
    private Guide currentGuide;
    private int index = 0;

    public GuiderView(@NonNull Context context) {
        this(context, null);
    }

    public GuiderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GuiderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        //设置画笔遮罩滤镜,可以传入BlurMaskFilter或EmbossMaskFilter，前者为模糊遮罩滤镜而后者为浮雕遮罩滤镜
        //这个方法已经被标注为过时的方法了，如果你的应用启用了硬件加速，你是看不到任何阴影效果的
        mPaint.setMaskFilter(new BlurMaskFilter(3, BlurMaskFilter.Blur.INNER));
        //关闭当前view的硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setClickable(true);
        //ViewGroup默认设定为true，会使onDraw方法不执行，如果复写了onDraw(Canvas)方法，需要清除此标记
        setWillNotDraw(false);
    }

    @Override
    public void onGlobalLayout() {

        KLog.e("onGlobalLayout");

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                KLog.e("getViewTreeObserver()-->onGlobalLayout");
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        KLog.e("sizeWidth--" + sizeWidth + "sizeHeight--" + sizeHeight);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        KLog.e("changed:" + changed + "  left:" + left + "  top：" + top + "  right：" + right + "  bottom：" + bottom);
        KLog.e("getMeasuredWidth():" + getMeasuredWidth());
        KLog.e("getMeasuredHeight():" + getMeasuredHeight());
        KLog.e("getWidth():" + getWidth());
        KLog.e("getHeight():" + getHeight());

        if (getChildCount() > 0) {
            KLog.e("getChildAt(0).getMeasuredWidth():" + getChildAt(0).getMeasuredWidth());
            KLog.e("getChildAt(0).getMeasuredHeight():" + getChildAt(0).getMeasuredHeight());
            KLog.e("getChildAt(0).getWidth():" + getChildAt(0).getWidth());
            KLog.e("getChildAt(0).getHeight():" + getChildAt(0).getHeight());
        }


        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                final int width = child.getWidth();
                final int height = child.getHeight();
                IPosition position = currentGuide.getPosition();
                final int x = currentGuide.getX();
                final int y = currentGuide.getY();
                IHighlight highlight = currentGuide.getIHighlight();
                final int childLeft = position.left(x, highlight.getWidth(), width) + lp.leftMargin;
                final int childTop = position.top(y, highlight.getHeight(), height) + lp.topMargin;
                final int childRight = childLeft + width - lp.rightMargin;
                final int childBottom = childTop + height - lp.bottomMargin;

                KLog.e("childLeft:" + childLeft);
                KLog.e("childTop:" + childTop);
                KLog.e("childRight:" + childRight);
                KLog.e("childBottom:" + childBottom);
                child.layout(childLeft, childTop, childRight, childBottom);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        KLog.e("onDraw………………………………………………………………");
        canvas.drawColor(builder.backgroundColor == 0 ? backgroundColor : builder.backgroundColor);
//        if (builder.guides != null) {
//            for (Guide guide : builder.guides) {
//                if (builder.guides.get(0).getAnimator() != null) {
//                    guide.getIHighlight().draw(canvas, mPaint, guide.getX(), guide.getY(), (int) builder.guides.get(0).getAnimator().getAnimatedValue());
//
//                }
//
//            }
//        }

        if (currentGuide.getAnimator() != null) {
            currentGuide.getIHighlight().draw(canvas, mPaint, currentGuide.getX(), currentGuide.getY(), (int) currentGuide.getAnimator().getAnimatedValue());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.e("onTouchEvent-----index::" + index);

        if (builder.guides != null && !builder.guides.isEmpty()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    removeView(currentGuide.getDescriptionView());
                    ++index;
                    KLog.e("_DOWN++index---" + index);
                    if (index < builder.guides.size()) {
                        currentGuide = builder.guides.get(index);
                    }

                    if (index == builder.guides.size()) {
                        removeAllViews();
                        if (getParent() instanceof ViewGroup) {
                            KLog.e("移除这个GuiderView。。。");
                            ((ViewGroup) getParent()).removeView(this);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    KLog.e("_UP++index---" + index);
                    addView(currentGuide.getDescriptionView());

                    currentGuide.getAnimator().addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            invalidate();
                        }
                    });
                    currentGuide.getAnimator().start();
                    break;
                default:
                    KLog.e("ACTION^^^^其他");
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void initData(Guider.Builder builder) {
        KLog.e("initData..");

        this.builder = builder;

//        for (Guide guide : builder.guides) {
//            addView(guide.getDescriptionView());
//            KLog.e("initData");
//
//
//        }

        if (builder.guides != null && !builder.guides.isEmpty()) {
            currentGuide = builder.guides.get(index);
            addView(currentGuide.getDescriptionView());
            KLog.e("initData---添加了一个xml描述。。。");
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        KLog.e("onAttachedToWindow");
        /**
         * 考虑这里是否可以作为启动Guider的标志，如果可以就把监听器掉这里。
         */

        currentGuide.getAnimator().addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        currentGuide.getAnimator().start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        KLog.e("onDetachedFromWindow...");
        /**
         * 考虑结束的回调因不应该出现在这里。
         * 因为要看一下如果Activity返回桌面的时候会不会调用这里。如果不会，那就可以把这个当做从decorView中Remove的标志。
         */

    }
}
