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

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw && h != oldh) {
            for (Guide guide : builder.guides) {
                if (guide.getPointView() != null) {
                    guide.setPointView(guide.getPointView());
                    guide.getHighlight().setWidth(guide.getPointView().getWidth());
                    guide.getHighlight().setHeight(guide.getPointView().getHeight());
                    guide.getHighlight().init();
                    guide.getAnimator().setIntValues(0, guide.getHighlight().getMax());
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
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

                if (position != null) {
                    final int childLeft = position.left(x, highlight.getWidth(), width) + lp.leftMargin;
                    final int childTop = position.top(y, highlight.getHeight(), height) + lp.topMargin;
                    final int childRight = childLeft + width - lp.rightMargin;
                    final int childBottom = childTop + height - lp.bottomMargin;
                    child.layout(childLeft, childTop, childRight, childBottom);
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
        if (builder.guides != null && !builder.guides.isEmpty()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (builder.mode == Guider.MODE_NEXT) {
                        removeView(currentGuide.getView());
                        ++index;
                        if (index == builder.guides.size()) {
                            removeAllViews();
                            if (getParent() instanceof ViewGroup) {
                                ((ViewGroup) getParent()).removeView(this);
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (builder.mode == Guider.MODE_NEXT) {
                        if (index < builder.guides.size()) {
                            currentGuide = builder.guides.get(index);
                            addView(currentGuide.getView());
                            showGuide(currentGuide);
                        }
                    } else if (builder.mode == Guider.MODE_TOGETHER) {
                        removeAllViews();
                        if (getParent() instanceof ViewGroup) {
                            ((ViewGroup) getParent()).removeView(this);
                        }
                    }
                    break;
                default:
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void initData(Guider.Builder builder) {
        this.builder = builder;

        if (builder.mode == Guider.MODE_NEXT) {
            if (builder.guides != null && !builder.guides.isEmpty()) {
                currentGuide = builder.guides.get(index);
                addView(currentGuide.getView());
            }
        } else if (builder.mode == Guider.MODE_TOGETHER) {
            for (Guide guide : builder.guides) {
                addView(guide.getView());
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        builder.onStartListener.onStart();
        if (builder.mode == Guider.MODE_NEXT) {
            showGuide(currentGuide);
        } else if (builder.mode == Guider.MODE_TOGETHER) {
            for (Guide guide : builder.guides) {
                showGuide(guide);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        builder.onStopListener.onStop();
    }

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
