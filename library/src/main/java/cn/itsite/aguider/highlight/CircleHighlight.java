package cn.itsite.aguider.highlight;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class CircleHighlight implements IHighlight {
    public int width;
    public int height;
    public int padding;
    public int radius;

    public CircleHighlight() {
    }

    public CircleHighlight(int width, int height) {
        this(width, height, 0);
    }

    public CircleHighlight(int width, int height, int padding) {
        this.width = width;
        this.height = height;
        this.padding = padding;
        init();
    }

    @Override
    public void init() {
        this.radius = ((int) Math.hypot((double) width, (double) height) / 2) + padding;
        this.width = radius * 2;
        this.height = radius * 2;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y) {
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y, int value) {
        if (value >= radius) {
            value = radius;
        }
        canvas.drawCircle(x, y, value, paint);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getMax() {
        return Math.max(width, height);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
}
