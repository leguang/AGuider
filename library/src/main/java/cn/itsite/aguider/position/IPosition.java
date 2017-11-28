package cn.itsite.aguider.position;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/27 0027 18:41
 */
public interface IPosition {

    int left(int x, int widthRadius, int width);

    int top(int y, int heightRadius, int height);
}
