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

import java.util.List;

import cn.itsite.aguider.highlight.IHighlight;
import cn.itsite.aguider.position.IPosition;

import static cn.itsite.aguider.Guider.MODE_NEXT;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class GuiderView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final String TAG = GuiderView.class.getSimpleName();
    private Paint mPaint;
    private Guide currentGuide;
    private int index = 0;
    private AGuiderListener.OnGuidertStartListener onStartListener;
    private AGuiderListener.OnGuidertStopListener onStopListener;
    private List<Guide> guides;
    private int mode = MODE_NEXT;

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
            for (Guide guide : guides) {
                View pointView = guide.getPointView();
                if (pointView != null) {
                    guide.setPointView(pointView);
                    IHighlight highlight = guide.getHighlight();
                    if (highlight != null) {
                        highlight.setWidth(pointView.getWidth());
                        highlight.setHeight(pointView.getHeight());
                        highlight.init();
                        ValueAnimator animator = guide.getAnimator();
                        if (animator != null) {
                            guide.getAnimator().setIntValues(0, highlight.getMax());
                        }
                    }
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

                if (mode == MODE_NEXT) {
                    position = currentGuide.getPosition();
                    x = currentGuide.getX();
                    y = currentGuide.getY();
                    highlight = currentGuide.getHighlight();
                } else if (mode == Guider.MODE_TOGETHER) {
                    position = guides.get(i).getPosition();
                    x = guides.get(i).getX();
                    y = guides.get(i).getY();
                    highlight = guides.get(i).getHighlight();
                }

                if (position != null) {
                    final int childLeft = position.left(x, highlight == null ? 0 : highlight.getWidth(), width) + lp.leftMargin;
                    final int childTop = position.top(y, highlight == null ? 0 : highlight.getHeight(), height) + lp.topMargin;
                    final int childRight = childLeft + width - lp.rightMargin;
                    final int childBottom = childTop + height - lp.bottomMargin;
                    child.layout(childLeft, childTop, childRight, childBottom);
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mode == MODE_NEXT) {
            canvas.drawColor(currentGuide.getBackgroundColor());
            IHighlight highlight = currentGuide.getHighlight();
            if (highlight != null) {
                highlight.draw(canvas, mPaint, currentGuide.getX(), currentGuide.getY(),
                        currentGuide.getAnimator() == null ? 0 : (int) currentGuide.getAnimator().getAnimatedValue());
            }
        } else if (mode == Guider.MODE_TOGETHER) {
            canvas.drawColor(guides.get(guides.size() - 1).getBackgroundColor());
            for (Guide guide : guides) {
                IHighlight highlight = guide.getHighlight();
                if (highlight != null) {
                    highlight.draw(canvas, mPaint, guide.getX(), guide.getY(),
                            guide.getAnimator() == null ? 0 : (int) guide.getAnimator().getAnimatedValue());
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (guides != null && !guides.isEmpty()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mode == MODE_NEXT) {
                        removeView(currentGuide.getView());
                        ++index;
                        if (index == guides.size()) {
                            removeAllViews();
                            if (getParent() instanceof ViewGroup) {
                                ((ViewGroup) getParent()).removeView(this);
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mode == MODE_NEXT) {
                        if (index < guides.size()) {
                            currentGuide = guides.get(index);
                            showGuide(currentGuide);
                        }
                    } else if (mode == Guider.MODE_TOGETHER) {
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (onStartListener != null) {
            onStartListener.onStart();
        }

        if (mode == MODE_NEXT) {
            if (guides != null && !guides.isEmpty()) {
                currentGuide = guides.get(index);
                showGuide(currentGuide);
            }
        } else if (mode == Guider.MODE_TOGETHER) {
            for (Guide guide : guides) {
                showGuide(guide);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (onStopListener != null) {
            onStopListener.onStop();
        }
    }

    private void showGuide(final Guide guide) {
        if (guide.getView() != null) {
            addView(guide.getView(), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        if (guide.getAnimator() == null) {
            return;
        }

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
                if (guide.getOnGuideListener() != null) {
                    guide.getOnGuideListener().onStart(guide);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (guide.getOnGuideListener() != null) {
                    guide.getOnGuideListener().onStop(guide);
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

    public Guide getCurrentGuide() {
        return currentGuide;
    }

    public void setCurrentGuide(Guide currentGuide) {
        this.currentGuide = currentGuide;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public AGuiderListener.OnGuidertStartListener getOnGuidertStartListener() {
        return onStartListener;
    }

    public void setOnGuidertStartListener(AGuiderListener.OnGuidertStartListener onStartListener) {
        this.onStartListener = onStartListener;
    }

    public AGuiderListener.OnGuidertStopListener getOnGuidertStopListener() {
        return onStopListener;
    }

    public void setOnGuidertStopListener(AGuiderListener.OnGuidertStopListener onStopListener) {
        this.onStopListener = onStopListener;
    }

    public List<Guide> getGuides() {
        return guides;
    }

    public void setGuides(List<Guide> guides) {
        for (Guide guide : guides) {
            if (guide.getViewId() != 0) {
                guide.setView(inflate(getContext(), guide.getViewId(), null));
                if (guide.getOnConvertListener() != null) {
                    guide.getOnConvertListener().convert(new BaseViewHolder(guide.getView()), this);
                }
            }
        }
        this.guides = guides;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
