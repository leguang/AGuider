package cn.itsite.aguider.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.itsite.aguider.demo.demo.AnywhereActivity;
import cn.itsite.aguider.demo.demo.CombinationActivity;
import cn.itsite.aguider.demo.demo.FragmentActivity;
import cn.itsite.aguider.demo.demo.ListActivity;
import cn.itsite.aguider.demo.demo.NextActivity;
import cn.itsite.aguider.demo.demo.SimpleActivity;
import cn.itsite.aguider.demo.demo.TogetherActivity;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btSimple;
    private Button btNext;
    private Button btTogether;
    private Button btList;
    private Button btFragment;
    private Button btAny;
    private Button btCombination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        btSimple = (Button) findViewById(R.id.bt_simple);
        btNext = (Button) findViewById(R.id.bt_next);
        btTogether = (Button) findViewById(R.id.bt_together);
        btList = (Button) findViewById(R.id.bt_list);
        btFragment = (Button) findViewById(R.id.bt_fragment);
        btAny = (Button) findViewById(R.id.bt_any);
        btCombination = (Button) findViewById(R.id.bt_combination);
    }

    private void initData() {
        btSimple.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btTogether.setOnClickListener(this);
        btList.setOnClickListener(this);
        btFragment.setOnClickListener(this);
        btAny.setOnClickListener(this);
        btCombination.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_simple:
                startActivity(new Intent(this, SimpleActivity.class));
                break;
            case R.id.bt_next:
                startActivity(new Intent(this, NextActivity.class));
                break;
            case R.id.bt_together:
                startActivity(new Intent(this, TogetherActivity.class));
                break;
            case R.id.bt_list:
                startActivity(new Intent(this, ListActivity.class));
                break;
            case R.id.bt_fragment:
                startActivity(new Intent(this, FragmentActivity.class));
                break;
            case R.id.bt_any:
                startActivity(new Intent(this, AnywhereActivity.class));
                break;
            case R.id.bt_combination:
                startActivity(new Intent(this, CombinationActivity.class));
                break;
            default:
        }
    }
}
