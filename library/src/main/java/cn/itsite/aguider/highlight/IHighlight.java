package cn.itsite.aguider.highlight;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 * <p>
 * Specifies a shape of the target (e.g circle, rectangle).
 * Implementations of this interface will be responsible to draw the shape
 * at specified center point (x, y).
 */
public interface IHighlight {

    void draw(Canvas canvas, Paint paint, int x, int y);

    void draw(Canvas canvas, Paint paint, int x, int y, int value);

    int getWidth();

    int getHeight();

    int getMax();

    void setWidth(int width);

    void setHeight(int height);

    void init();
}
