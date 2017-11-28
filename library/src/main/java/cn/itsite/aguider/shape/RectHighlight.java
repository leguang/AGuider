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
public class RectHighlight implements Highlight {
    public int width;
    public int height;
    public int padding;

    public RectHighlight(int width, int height) {
        this(width, height, 0);
    }

    public RectHighlight(int width, int height, int padding) {
        this.width = width;
        this.height = height;
        this.padding = padding;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y) {
        final int left = x - width / 2 - padding;
        final int top = y - height / 2 - padding;
        final int right = x + width / 2 + padding;
        final int bottom = y + height / 2 + padding;
        final RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawRect(rectF, paint);
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
}