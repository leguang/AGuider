package cn.itsite.aguider.demo.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.itsite.aguider.AGuiderListener;
import cn.itsite.aguider.BaseViewHolder;
import cn.itsite.aguider.Guide;
import cn.itsite.aguider.Guider;
import cn.itsite.aguider.GuiderView;
import cn.itsite.aguider.demo.R;
import cn.itsite.aguider.highlight.CircleHighlight;
import cn.itsite.aguider.highlight.Highlight;
import cn.itsite.aguider.highlight.RectHighlight;
import cn.itsite.aguider.position.Position;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/12/1 0001 10:14
 */
public class GuideFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "GuideFragment";
    private TextView textView;
    private ImageView imageView;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_next, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initGuider();
    }

    private void initView(View view) {
        textView = ((TextView) view.findViewById(R.id.textView));
        imageView = ((ImageView) view.findViewById(R.id.imageView));
        button = ((Button) view.findViewById(R.id.button));
        textView.setOnClickListener(this);
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        initGuider();
    }

    public void initGuider() {
        Guide guide0 = new Guide.Builder()
                /**
                 * 设置想要添加高亮的View。
                 */
                .setPoint(textView)
                /**
                 * 设置指引View的相对位置，有上下左右中等多个位置，可实现IPosition接口自定义位置。
                 */
                .setPosition(Position.bottomLeft())
                /**
                 * 设置高亮，有多种形状可选择，可实现IHighlight接口自定义位置。
                 */
                .setHighlight(Highlight.oval())
                /**
                 * 设置指引View，可以是View也可以是Layout。
                 */
                .setView(R.layout.guide_0)
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

        Guide guide1 = new Guide.Builder()
                .setPoint(imageView)
                .setPosition(Position.bottomRight())
                .setHighlight(new CircleHighlight(imageView.getWidth(), imageView.getHeight()))
                .setView(R.layout.guide_0)
                .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                    @Override
                    public void convert(BaseViewHolder holder, GuiderView guiderView) {
                        holder.setText(R.id.tv_des, "对对对，你说的都对……");
                        ImageView imageView = holder.getView(R.id.iv_arrow);
                        imageView.setImageResource(R.drawable.arrow_topleft);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                        layoutParams.gravity = Gravity.LEFT;
                        imageView.setLayoutParams(layoutParams);
                    }
                })
                .setBackground(0x5000FF00)
                .build();

        Guide guide2 = new Guide.Builder()
                .setPoint(button)
                .setPosition(Position.top())
                .setHighlight(new RectHighlight(button.getWidth(), button.getHeight()))
                .setView(R.layout.guide_1)
                .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                    @Override
                    public void convert(BaseViewHolder holder, GuiderView guiderView) {
                        holder.setText(R.id.tv_des, "大爷，你终于来了……");
                        ImageView imageView = holder.getView(R.id.iv_arrow);
                        imageView.setImageResource(R.drawable.arrow_bottom);
                    }
                }).build();

        new Guider.Builder()
                /**
                 * 设置锚点，可以是Activity、Fragment和任意View，最终都会找到其相应的Activity然后找到DecorView或者window对象，然后添加上去。
                 */
                .setAnchor(this)
                /**
                 * 如果是NEXT模式的话，先添加的先显示。还有addGuide()函数也可以添加Guide。
                 */
                .addGuides(guide0, guide1, guide2)
                /**
                 * Guider.MODE_NEXT表示一个接着一个显示，one by one.默认值。
                 * Guider.MODE_TOGETHER表示多个Guide一起显示。
                 */
                .setMode(Guider.MODE_NEXT)
                .show();
    }
}
