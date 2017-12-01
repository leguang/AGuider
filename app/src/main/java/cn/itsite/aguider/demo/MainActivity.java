package cn.itsite.aguider.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.itsite.aguider.demo.demo.ActivityActivity;
import cn.itsite.aguider.demo.demo.FragmentActivity;
import cn.itsite.aguider.demo.demo.NextActivity;
import cn.itsite.aguider.demo.demo.TogetherActivity;

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
    private Button btActivity;
    private Button btFragment;

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
        btActivity = (Button) findViewById(R.id.bt_activity);
        btFragment = (Button) findViewById(R.id.bt_fragment);
    }

    private void initData() {
        btNext.setOnClickListener(this);
        btTogether.setOnClickListener(this);
        btActivity.setOnClickListener(this);
        btFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                startActivity(new Intent(this, NextActivity.class));
                break;
            case R.id.bt_together:
                startActivity(new Intent(this, TogetherActivity.class));
                break;
            case R.id.bt_activity:
                startActivity(new Intent(this, ActivityActivity.class));
                break;
            case R.id.bt_fragment:
                startActivity(new Intent(this, FragmentActivity.class));
                break;
            default:
        }
    }
}
