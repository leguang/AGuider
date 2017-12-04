package cn.itsite.aguider.highlight;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/29 0029 14:21
 */
public class Highlight {

    public static CircleHighlight circle() {
        return new CircleHighlight();
    }

    public static CircleHighlight circle(int width, int height) {
        return new CircleHighlight(width, height);
    }

    public static CircleHighlight circle(int width, int height, int padding) {
        return new CircleHighlight(width, height, padding);
    }

    public static OvalHighlight oval() {
        return new OvalHighlight();
    }

    public static OvalHighlight oval(int width, int height) {
        return new OvalHighlight(width, height);
    }

    public static OvalHighlight oval(int width, int height, int padding) {
        return new OvalHighlight(width, height, padding);
    }

    public static RectHighlight rect() {
        return new RectHighlight();
    }

    public static RectHighlight rect(int width, int height) {
        return new RectHighlight(width, height);
    }

    public static RectHighlight rect(int width, int height, int padding) {
        return new RectHighlight(width, height, padding);
    }

    public static RoundRectHighlight roundRect() {
        return new RoundRectHighlight();
    }

    public static RoundRectHighlight roundRect(int width, int height) {
        return new RoundRectHighlight(width, height);
    }

    public static RoundRectHighlight roundRect(int width, int height, int padding) {
        return new RoundRectHighlight(width, height, padding);
    }
}
