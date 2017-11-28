package cn.itsite.aguider.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class RoundRectHighlight implements Highlight {
    public int width;
    public int height;
    public int padding;
    public int radius;

    public RoundRectHighlight(int width, int height) {
        this(width, height, 0);
    }

    public RoundRectHighlight(int width, int height, int padding) {
        this(width, height, padding, 0);
    }

    public RoundRectHighlight(int width, int height, int padding, int radius) {
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y) {
        final int left = x - width / 2 - padding;
        final int top = y - height / 2 - padding;
        final int right = x + width / 2 + padding;
        final int bottom = y + height / 2 + padding;
        final RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectF, radius, radius, paint);
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

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}