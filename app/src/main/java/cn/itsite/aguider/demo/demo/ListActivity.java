package cn.itsite.aguider.demo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.aguider.AGuiderListener;
import cn.itsite.aguider.BaseViewHolder;
import cn.itsite.aguider.Guide;
import cn.itsite.aguider.Guider;
import cn.itsite.aguider.GuiderView;
import cn.itsite.aguider.demo.R;
import cn.itsite.aguider.highlight.Highlight;
import cn.itsite.aguider.position.Position;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2016/11/24 0024 9:08
 */
public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Guider guider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                initGuider(recyclerView.getChildAt(0));
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        final List<String> mData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mData.add("第" + i + "个");
        }
        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView textView = new TextView(ListActivity.this);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setPadding(50, 50, 50, 50);
                return new ViewHolder(textView) {
                };
            }

            @Override
            public void onBindViewHolder(final ViewHolder holder, final int position) {
                ((TextView) holder.itemView).setText(mData.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initGuider(holder.itemView);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }

    public void initGuider(View position) {
        Guide guide0 = new Guide.Builder()
                /**
                 * 设置想要添加高亮的View。
                 */
                .setPoint(position)
                /**
                 * 设置指引View的相对位置，有上下左右中等多个位置，可实现IPosition接口自定义位置。
                 */
                .setPosition(Position.top())
                /**
                 * 设置高亮，有多种形状可选择，可实现IHighlight接口自定义位置。
                 */
                .setHighlight(Highlight.roundRect())
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
