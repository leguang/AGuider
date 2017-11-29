package cn.itsite.aguider.position;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/27 0027 19:10
 */
public class Position {

    public static IPosition center() {
        return new Center();
    }

    public static IPosition left() {
        return new Left();
    }

    public static IPosition right() {
        return new Right();
    }

    public static IPosition top() {
        return new Top();
    }

    public static IPosition bottom() {
        return new Bottom();
    }

    public static IPosition topRight() {
        return new TopRight();
    }

    public static IPosition topleft() {
        return new TopLeft();
    }

    public static IPosition bottomLeft() {
        return new BottomLeft();
    }

    public static IPosition bottomRight() {
        return new BottomRight();
    }
}
