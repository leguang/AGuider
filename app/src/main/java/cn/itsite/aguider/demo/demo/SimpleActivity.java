package cn.itsite.aguider.demo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
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
import cn.itsite.aguider.highlight.Highlight;
import cn.itsite.aguider.position.Position;

public class SimpleActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SimpleActivity.class.getSimpleName();
    private TextView textView;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        initView();
        initGuider();
    }

    private void initView() {
        textView = ((TextView) findViewById(R.id.textView));
        imageView = ((ImageView) findViewById(R.id.imageView));
        button = ((Button) findViewById(R.id.button));
        textView.setOnClickListener(this);
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    private void initGuider() {
        new Guider.Builder()
                .setAnchor(this)
                .addGuide(new Guide.Builder()
                        .setPoint(textView)
                        .setPosition(Position.bottomLeft())
                        .setView(R.layout.guide_0)
                        .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                            @Override
                            public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                holder.setText(R.id.tv_des, "骚年，没错，就是这里……");
                            }
                        }).build())
                .addGuide(new Guide.Builder()
                        .setPoint(imageView)
                        .setPosition(Position.bottomRight())
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
                        }).build())
                .addGuide(new Guide.Builder()
                        .setPoint(button)
                        .setPosition(Position.top())
                        .setHighlight(Highlight.oval())
                        .setView(R.layout.guide_1)
                        .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                            @Override
                            public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                holder.setText(R.id.tv_des, "大爷，你终于来了……");
                                ImageView imageView = holder.getView(R.id.iv_arrow);
                                imageView.setImageResource(R.drawable.arrow_bottom);
                            }
                        }).build())
                .show();
    }

    @Override
    public void onClick(View v) {
        initGuider();
    }
}
