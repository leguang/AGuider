package cn.itsite.aguider.position;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/27 0027 18:56
 */
public class BottomRight implements IPosition {

    @Override
    public int left(int x, int widthRadius, int width) {
        return x + widthRadius;
    }

    @Override
    public int top(int y, int heightRadius, int height) {
        return y + heightRadius;
    }
}
