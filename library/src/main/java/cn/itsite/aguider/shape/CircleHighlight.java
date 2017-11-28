package cn.itsite.aguider.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class CircleHighlight implements Highlight {
    public int width;
    public int height;
    public int padding;
    public int radius;

    public CircleHighlight(int width, int height) {
        this(width, height, 0);
    }

    public CircleHighlight(int width, int height, int padding) {
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.radius = (int) Math.hypot((double) width, (double) height) / 2;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y) {
        canvas.drawCircle(x, y, radius + padding, paint);
    }

    @Override
    public int getWidth() {
        return radius + padding;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getHeight() {
        return radius + padding;
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
