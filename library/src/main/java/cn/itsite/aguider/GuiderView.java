package cn.itsite.aguider;

import android.animation.Animator;
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
        KLog.e("getTargetView().getWidth()" + builder.guides.get(0).getPointView().getWidth());
        KLog.e("getTargetView().getWidth()" + builder.guides.get(0).getPointView().getHeight());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w == oldw && h == oldh) {
            for (Guide guide : builder.guides) {
                guide.setPointView(guide.getPointView());
                guide.getHighlight().setWidth(guide.getPointView().getWidth());
                guide.getHighlight().setHeight(guide.getPointView().getHeight());
                guide.getHighlight().init();
                guide.getAnimator().setIntValues(0, guide.getHighlight().getMax());
            }
        }
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

                IPosition position = null;
                int x = 0;
                int y = 0;
                IHighlight highlight = null;

                if (builder.mode == Guider.MODE_NEXT) {
                    position = currentGuide.getPosition();
                    x = currentGuide.getX();
                    y = currentGuide.getY();
                    highlight = currentGuide.getHighlight();
                } else if (builder.mode == Guider.MODE_TOGETHER) {
                    position = builder.guides.get(i).getPosition();
                    x = builder.guides.get(i).getX();
                    y = builder.guides.get(i).getY();
                    highlight = builder.guides.get(i).getHighlight();
                }


                KLog.e("x::" + x);
                KLog.e("y::" + y);
                KLog.e("highlight.getWidth::" + highlight.getWidth());
                KLog.e("highlight.getHeight::" + highlight.getHeight());
                KLog.e("width::" + width);
                KLog.e("height::" + height);

                if (position != null) {
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        KLog.e("onDraw………………………………………………………………");
        if (builder.mode == Guider.MODE_NEXT) {
            canvas.drawColor(currentGuide.getBackgroundColor());
            currentGuide.getHighlight().draw(canvas, mPaint, currentGuide.getX(), currentGuide.getY(), (int) currentGuide.getAnimator().getAnimatedValue());
        } else if (builder.mode == Guider.MODE_TOGETHER) {
            canvas.drawColor(builder.guides.get(builder.guides.size() - 1).getBackgroundColor());
            for (Guide guide : builder.guides) {
                guide.getHighlight().draw(canvas, mPaint, guide.getX(), guide.getY(), (int) guide.getAnimator().getAnimatedValue());
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.e("onTouchEvent-----index::" + index);

        if (builder.guides != null && !builder.guides.isEmpty()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (builder.mode == Guider.MODE_NEXT) {
                        removeView(currentGuide.getView());
                        ++index;
                        KLog.e("_DOWN++index---" + index);

                        if (index == builder.guides.size()) {
                            removeAllViews();
                            if (getParent() instanceof ViewGroup) {
                                KLog.e("移除这个GuiderView。。。");
                                ((ViewGroup) getParent()).removeView(this);
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    KLog.e("_UP++index---" + index);
                    if (builder.mode == Guider.MODE_NEXT) {
                        if (index < builder.guides.size()) {
                            currentGuide = builder.guides.get(index);
                            addView(currentGuide.getView());
                            showGuide(currentGuide);
                        }
                    } else if (builder.mode == Guider.MODE_TOGETHER) {
                        removeAllViews();
                        if (getParent() instanceof ViewGroup) {
                            KLog.e("移除这个GuiderView。。。");
                            ((ViewGroup) getParent()).removeView(this);
                        }
                    }
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

        if (builder.mode == Guider.MODE_NEXT) {
            if (builder.guides != null && !builder.guides.isEmpty()) {
                currentGuide = builder.guides.get(index);
                addView(currentGuide.getView());
                KLog.e("initData---添加了一个xml描述。。。");
            }
        } else if (builder.mode == Guider.MODE_TOGETHER) {
            for (Guide guide : builder.guides) {
                addView(guide.getView());
                KLog.e("initData");
            }
        }
    }

    /**
     * 考虑这里是否可以作为启动Guider的标志，如果可以就把监听器掉这里。
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        KLog.e("onAttachedToWindow");

        KLog.e("getTargetView().getWidth()" + builder.guides.get(0).getPointView().getWidth());
        KLog.e("getTargetView().getWidth()" + builder.guides.get(0).getPointView().getHeight());

        builder.onStartListener.onStart();

        if (builder.mode == Guider.MODE_NEXT) {
            showGuide(currentGuide);
        } else if (builder.mode == Guider.MODE_TOGETHER) {
            for (Guide guide : builder.guides) {
                showGuide(guide);
            }
        }
    }

    /**
     * 考虑结束的回调因不应该出现在这里。
     * 因为要看一下如果Activity返回桌面的时候会不会调用这里。如果不会，那就可以把这个当做从decorView中Remove的标志。
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        builder.onStopListener.onStop();
        KLog.e("onDetachedFromWindow...");
    }


    /**
     * 考虑把addView这一行代码也添加到这个函数中。
     *
     * @param guide
     */
    private void showGuide(final Guide guide) {
        guide.getAnimator().addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                invalidate();
                postInvalidate();
            }
        });
        guide.getAnimator().addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (guide.getListener() != null) {
                    guide.getListener().onStart(guide);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (guide.getListener() != null) {
                    guide.getListener().onStop(guide);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        guide.getAnimator().start();
    }
}
