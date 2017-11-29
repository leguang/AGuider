package cn.itsite.aguider.highlight;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class OvalHighlight implements IHighlight {
    public int width;
    public int height;
    public int padding;

    public OvalHighlight(int width, int height) {
        this(width, height, 0);
    }

    public OvalHighlight(int width, int height, int padding) {
        this.width = width + padding * 2;
        this.height = height + padding * 2;
        this.padding = padding;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y) {
        final int left = x - width / 2;
        final int top = y - height / 2;
        final int right = x + width / 2;
        final int bottom = y + height / 2;
        final RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawOval(rectF, paint);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y, int value) {
        int left = x - value;
        int top = y - value;
        int right = x + value;
        int bottom = y + value;
        if (left < x - width / 2) {
            left = x - width / 2;
        }
        if (top < y - height / 2) {
            top = y - height / 2;
        }
        if (right > x + width / 2) {
            right = x + width / 2;
        }
        if (bottom > y + height / 2) {
            bottom = y + height / 2;
        }
        final RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawOval(rectF, paint);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getMax() {
        return Math.max(width, height);
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
}
