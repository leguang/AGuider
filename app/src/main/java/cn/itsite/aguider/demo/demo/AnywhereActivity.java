package cn.itsite.aguider.demo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cn.itsite.aguider.AGuiderListener;
import cn.itsite.aguider.BaseViewHolder;
import cn.itsite.aguider.Guide;
import cn.itsite.aguider.Guider;
import cn.itsite.aguider.GuiderView;
import cn.itsite.aguider.demo.R;
import cn.itsite.aguider.demo.Utils;
import cn.itsite.aguider.highlight.Highlight;
import cn.itsite.aguider.position.Position;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/12/1 0001 10:14
 */
public class AnywhereActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = AnywhereActivity.class.getSimpleName();
    private TextView textView;
    private Guider guider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anywhere);
        initView();
        textView.post(new Runnable() {
            @Override
            public void run() {
                initGuider();
            }
        });
    }

    private void initView() {
        textView = ((TextView) findViewById(R.id.tv));
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        initGuider();
    }

    public void initGuider() {
        //随便给你要设置高亮的位置和形状。
        int x = Utils.getScreenWidth(this) / 2;
        int y = Utils.getScreenHeight(this) / 2;

        Guide guide0 = new Guide.Builder()
                /**
                 * 设置想要添加高亮的View。
                 */
                .setPoint(x, y)
                /**
                 * 设置指引View的相对位置，有上下左右中等多个位置，可实现IPosition接口自定义位置。
                 */
                .setPosition(Position.top())
                /**
                 * 设置高亮，有多种形状可选择，可实现IHighlight接口自定义位置。
                 */
                .setHighlight(Highlight.circle(Utils.dp2px(this, 100), Utils.dp2px(this, 100)))
                /**
                 * 设置指引View，可以是View也可以是Layout。
                 */
                .setView(R.layout.guide_2)
                /**
                 * 将设置的指引View或者layout通过ViewHolder的形式通过这个接口暴露。
                 */
                .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                    @Override
                    public void convert(BaseViewHolder holder, GuiderView guiderView) {
                        holder.setText(R.id.tv_des, "骚年，没错，就是这里……");
                    }
                })
                .build();

        guider = new Guider.Builder()
                /**
                 * 设置锚点，可以是Activity、Fragment和任意View，最终都会找到其相应的Activity然后找到DecorView或者window对象，然后添加上去。
                 */
                .setAnchor(this)
                /**
                 * 如果是NEXT模式的话，先添加的先显示。还有addGuide()函数也可以添加Guide。
                 */
                .addGuide(guide0)
                /**
                 * Guider.MODE_NEXT表示一个接着一个显示，one by one.默认值。
                 * Guider.MODE_TOGETHER表示多个Guide一起显示。
                 */
                .setMode(Guider.MODE_NEXT)
                .show();
    }

    @Override
    public void onBackPressed() {
        if (guider.isVisible()) {
            guider.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
