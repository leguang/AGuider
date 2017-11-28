package cn.itsite.aguider;

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
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.socks.library.KLog;

import cn.itsite.aguider.position.IPosition;
import cn.itsite.aguider.shape.Highlight;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class GuiderView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = GuiderView.class.getSimpleName();
    private Paint mPaint;
    private int backgroundColor = 0xb2000000;
    private Guider.Builder builder;

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
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                KLog.e("onGlobalLayoutonGlobalLayout");
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
        KLog.e("changed--" + changed + "  left--" + left + "  top--" + top + "  right--" + right + "  bottom--" + bottom);
        KLog.e("getMeasuredWidth()--" + getMeasuredWidth());
        KLog.e("getMeasuredHeight()--" + getMeasuredHeight());
        KLog.e("getWidth()--" + getWidth());
        KLog.e("getHeight()--" + getHeight());
        KLog.e("getChildAt(0).getMeasuredWidth()--" + getChildAt(0).getMeasuredWidth());
        KLog.e("getChildAt(0).getMeasuredHeight()--" + getChildAt(0).getMeasuredHeight());
        KLog.e("getChildAt(0).getWidth()--" + getChildAt(0).getWidth());
        KLog.e("getChildAt(0).getHeight()--" + getChildAt(0).getHeight());
//        for (Guide guide : builder.guides) {
//            View view = guide.getDescriptionView();
//            view.setX(guide.getX());
//            view.setY(guide.getY());
//        }

//        inflate()


        final int count = getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int width = child.getWidth();
                final int height = child.getHeight();

                KLog.e("lp.leftMargin--" + lp.leftMargin);
                KLog.e("lp.topMargin--" + lp.topMargin);


                //左上角
//                final int childLeft = builder.guides.get(i).getX() - builder.guides.get(i).getHighlight().getWidth() - width + lp.leftMargin;
//                final int childTop = builder.guides.get(i).getY() - builder.guides.get(i).getHighlight().getHeight() - height + lp.topMargin;
//                final int childRight = childLeft + width - lp.rightMargin;
//                final int childBottom = childTop + height - lp.bottomMargin;

                //上边
//                final int childLeft = builder.guides.get(i).getX() - width / 2 + lp.leftMargin;
//                final int childTop = builder.guides.get(i).getY() - builder.guides.get(i).getHighlight().getHeight() - height + lp.topMargin;
//                final int childRight = childLeft + width - lp.rightMargin;
//                final int childBottom = childTop + height - lp.bottomMargin;

                //右上角
//                final int childLeft = builder.guides.get(i).getX() + builder.guides.get(i).getHighlight().getWidth() + lp.leftMargin;
//                final int childTop = builder.guides.get(i).getY() - builder.guides.get(i).getHighlight().getHeight() - height + lp.topMargin;
//                final int childRight = childLeft + width - lp.rightMargin;
//                final int childBottom = childTop + height - lp.bottomMargin;

                //右边
//                final int childLeft = builder.guides.get(i).getX() + builder.guides.get(i).getHighlight().getWidth() + lp.leftMargin;
//                final int childTop = builder.guides.get(i).getY() - height / 2 + lp.topMargin;
//                final int childRight = childLeft + width - lp.rightMargin;
//                final int childBottom = childTop + height - lp.bottomMargin;

                //右下角
//                final int childLeft = builder.guides.get(i).getX() + builder.guides.get(i).getHighlight().getWidth() + lp.leftMargin;
//                final int childTop = builder.guides.get(i).getY() + builder.guides.get(i).getHighlight().getHeight() + lp.topMargin;
//                final int childRight = childLeft + width - lp.rightMargin;
//                final int childBottom = childTop + height - lp.bottomMargin;

                //下边
//                final int childLeft = builder.guides.get(i).getX() - width / 2 + lp.leftMargin;
//                final int childTop = builder.guides.get(i).getY() + builder.guides.get(i).getHighlight().getHeight() - lp.topMargin;
//                final int childRight = childLeft + width - lp.rightMargin;
//                final int childBottom = childTop + height - lp.bottomMargin;

                //左下边
//                final int childLeft = builder.guides.get(i).getX() - builder.guides.get(i).getHighlight().getWidth() - width + lp.leftMargin;
//                final int childTop = builder.guides.get(i).getY() + builder.guides.get(i).getHighlight().getHeight() + lp.topMargin;
//                final int childRight = childLeft + width - lp.rightMargin;
//                final int childBottom = childTop + height - lp.bottomMargin;

                //左边
//                final int childLeft = builder.guides.get(i).getX() - builder.guides.get(i).getHighlight().getWidth() - width + lp.leftMargin;
//                final int childTop = builder.guides.get(i).getY() - height / 2 + lp.topMargin;
//                final int childRight = childLeft + width - lp.rightMargin;
//                final int childBottom = childTop + height - lp.bottomMargin;

                Guide guide = builder.guides.get(i);
                IPosition position = guide.getPosition();
                int x = guide.getX();
                int y = guide.getY();
                Highlight highlight = guide.getHighlight();
                final int childLeft = position.left(x, highlight.getWidth(), width) + lp.leftMargin;
                final int childTop = position.top(y, highlight.getHeight(), height);
                final int childRight = childLeft + width - lp.rightMargin;
                final int childBottom = childTop + height - lp.bottomMargin;


                KLog.e("childLeft--" + childLeft);
                KLog.e("childTop--" + childTop);
                KLog.e("childRight--" + childRight);
                KLog.e("childBottom--" + childBottom);

                child.layout(childLeft, childTop, childRight, childBottom);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        KLog.e("onDraw………………………………………………………………");

        canvas.drawColor(builder.backgroundColor == 0 ? backgroundColor : builder.backgroundColor);
        if (builder.guides != null) {
            for (Guide guide : builder.guides) {
                guide.getHighlight().draw(canvas, mPaint, guide.getX(), guide.getY());
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void initData(Guider.Builder builder) {
        this.builder = builder;

        for (Guide guide : builder.guides) {
            addView(guide.getDescriptionView());
            KLog.e("initData");


        }


    }
}
