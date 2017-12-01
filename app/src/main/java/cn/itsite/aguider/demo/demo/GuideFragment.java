package cn.itsite.aguider.demo.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.itsite.aguider.demo.R;

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
//        simple();
    }

}
