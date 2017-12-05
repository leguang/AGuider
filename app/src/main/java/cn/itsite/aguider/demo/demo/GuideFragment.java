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

import com.socks.library.KLog;

import cn.itsite.aguider.AGuiderListener;
import cn.itsite.aguider.BaseViewHolder;
import cn.itsite.aguider.Guide;
import cn.itsite.aguider.Guider;
import cn.itsite.aguider.GuiderView;
import cn.itsite.aguider.demo.R;
import cn.itsite.aguider.highlight.CircleHighlight;
import cn.itsite.aguider.highlight.RectHighlight;
import cn.itsite.aguider.position.Position;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/12/1 0001 10:14
 */
public class GuideFragment extends Fragment {
    private static final String TAG = "GuideFragment";
    private TextView textView;
    private ImageView imageView;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_next, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        textView = ((TextView) view.findViewById(R.id.textView));
        imageView = ((ImageView) view.findViewById(R.id.imageView));
        button = ((Button) view.findViewById(R.id.button));
    }

    private void initData() {
        simple();
    }

    public void simple() {

        Guide guide0 = new Guide.Builder()
                .setPoint(textView)
                .setPosition(Position.bottomLeft())
//                .setHighlight(Highlight.oval())
                .setView(R.layout.guide_0)
                .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                    @Override
                    public void convert(BaseViewHolder holder, GuiderView guiderView) {
                        holder.setText(R.id.tv_des, "骚年，没错，就是这里……");
                    }
                })
                .setOnGuideListener(new AGuiderListener.OnGuideListener() {
                    @Override
                    public void onStart(Guide guide) {
                        KLog.e(TAG, "Guide--000--onStart…………");

                    }

                    @Override
                    public void onStop(Guide guide) {
                        KLog.e(TAG, "Guide--000--onStop…………");

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
                .setOnGuideListener(new AGuiderListener.OnGuideListener() {
                    @Override
                    public void onStart(Guide guide) {
                        KLog.e(TAG, "Guide--111--onStart…………");

                    }

                    @Override
                    public void onStop(Guide guide) {
                        KLog.e(TAG, "Guide--111--onStop…………");

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
                })
                .setOnGuideListener(new AGuiderListener.OnGuideListener() {
                    @Override
                    public void onStart(Guide guide) {
                        KLog.e(TAG, "Guide--222--onStart…………");

                    }

                    @Override
                    public void onStop(Guide guide) {
                        KLog.e(TAG, "Guide--222--onStop…………");

                    }
                })
                .build();

        new Guider.Builder()
                .setAnchor(this)
                .addGuides(guide0, guide1, guide2)
                .setMode(Guider.MODE_NEXT)//MODE_NEXT：一个接着一个显示。MODE_TOGETHER：一起显示。
                .setOnGuidertStartListener(new AGuiderListener.OnGuidertStartListener() {
                    @Override
                    public void onStart() {
                        KLog.e(TAG, "onStart…………");
                    }
                })
                .setOnGuidertStopListener(new AGuiderListener.OnGuidertStopListener() {
                    @Override
                    public void onStop() {
                        KLog.e(TAG, "onStop…………");
                    }
                })
                .show();
    }

}
