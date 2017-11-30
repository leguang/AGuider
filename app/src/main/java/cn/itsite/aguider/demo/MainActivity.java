package cn.itsite.aguider.demo;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.itsite.aguider.AGuiderListener;
import cn.itsite.aguider.Guide;
import cn.itsite.aguider.Guider;
import cn.itsite.aguider.demo.demo.NextActivity;
import cn.itsite.aguider.highlight.OvalHighlight;
import cn.itsite.aguider.highlight.RectHighlight;
import cn.itsite.aguider.position.Position;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btNext;
    private Button btTogether;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        btNext = (Button) findViewById(R.id.bt_next);
        btTogether = (Button) findViewById(R.id.bt_together);
    }

    private void initData() {
        btNext.setOnClickListener(this);
        btTogether.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                startActivity(new Intent(this, NextActivity.class));
                break;
            case R.id.bt_together:
//                startActivity(new Intent(this, DialogFragmentActivity.class));
                break;
            default:
        }
    }
}
