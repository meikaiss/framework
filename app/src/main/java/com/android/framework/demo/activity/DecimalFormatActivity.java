package com.android.framework.demo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 2017/12/04.
 */
public class DecimalFormatActivity extends BaseCompactActivity {

    private RecyclerView recyclerView;
    private DecimalAdapter adapter;
    private List<String> dataList;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_decimal_format;
    }

    @Override
    public void findViews() {
        recyclerView = findViewById(R.id.recycler_view);

        dataList = new ArrayList<>();

        double money = 43.72;
        dataList.add("DecimalFormat 中 0 和 # 的不同作用");

        dataList.add("");
        dataList.add("以0作为占位符");
        dataList.add("原始 double 数据=" + money);
        dataList.add(getResult("00.00", money));
        dataList.add(getResult("00.000", money));
        dataList.add(getResult("00.0", money));
        dataList.add(getResult("000.00", money));
        dataList.add(getResult("0.00", money));

        dataList.add("");
        double money2 = 43.77;
        dataList.add("原始 double 数据=" + money2 + " , 验证 四舍五入");
        dataList.add(getResult("00.00", money2));
        dataList.add(getResult("00.000", money2));
        dataList.add(getResult("00.0", money2));
        dataList.add(getResult("000.00", money2));
        dataList.add(getResult("0.00", money2));

        dataList.add("");
        int money3 = 43;
        dataList.add("原始 int 数据=" + money3);
        dataList.add(getResult("00.00", money3));
        dataList.add(getResult("00.000", money3));
        dataList.add(getResult("00.0", money3));
        dataList.add(getResult("000.00", money3));
        dataList.add(getResult("0.00", money3));

        dataList.add("");
        double money4 = 0.72;
        dataList.add("原始 double 数据=" + money4);
        dataList.add(getResult("00.00", money4));
        dataList.add(getResult("00.000", money4));
        dataList.add(getResult("00.0", money4));
        dataList.add(getResult("000.00", money4));
        dataList.add(getResult("0.00", money4));

        dataList.add("");
        dataList.add("结论：0格式化时，特征如下。\na:如果你的数字少了，就会补0，小数和整数都会补；\nb:如果数字多了，就切掉，但只切小数的末尾，整数不能切;"
                + "\nc:同时被切掉的小数位会进行四舍五入处理");
        dataList.add("");

        dataList.add("");
        dataList.add("-------------------------------------------------------");
        dataList.add("----------------------- 0 和 # 的分割线----------------------");
        dataList.add("-------------------------------------------------------");
        dataList.add("以#作为占位符");
        dataList.add("原始 double 数据=" + money);
        dataList.add(getResult("##.##", money));
        dataList.add(getResult("##.###", money));
        dataList.add(getResult("##.#", money));
        dataList.add(getResult("###.##", money));
        dataList.add(getResult("#.##", money));

        dataList.add("");
        dataList.add("原始 double 数据=" + money2);
        dataList.add(getResult("##.##", money2));
        dataList.add(getResult("##.###", money2));
        dataList.add(getResult("##.#", money2));
        dataList.add(getResult("###.##", money2));
        dataList.add(getResult("#.##", money2));

        dataList.add("");
        dataList.add("原始 double 数据=" + money3);
        dataList.add(getResult("##.##", money3));
        dataList.add(getResult("##.###", money3));
        dataList.add(getResult("##.#", money3));
        dataList.add(getResult("###.##", money3));
        dataList.add(getResult("#.##", money3));

        dataList.add("");
        dataList.add("原始 double 数据=" + money4);
        dataList.add(getResult("##.##", money4));
        dataList.add(getResult("##.###", money4));
        dataList.add(getResult("##.#", money4));
        dataList.add(getResult("###.##", money4));
        dataList.add(getResult("#.##", money4));

        double d0 = 0;
        dataList.add("");
        dataList.add("原始 定义的 double d0 = 0 的 数据=" + d0 + "，转成String后，默认为0.0");
        dataList.add(getResult("#.00", d0));

        dataList.add("");
        dataList.add("原始 定义的 double d0 = 0 的 数据=" + d0 + "，转成String后，默认为0.0");
        dataList.add(getResult("#0.00", d0));

        double d1 = 0.0;
        dataList.add("");
        dataList.add("原始 定义的 double d1 = 0.0 的 数据=" + d1 + "，转成String后，默认为0.0");
        dataList.add(getResult("#.00", d1));

        dataList.add("");

        dataList.add("结论：#格式化时，特征如下。\na:如果你的数字少了，则不处理，不会补0，也不会补#；\nb:如果数字多了，就切掉，但只切小数的末尾，整数不能切;"
                + "\nc:同时被切掉的小数位会进行四舍五入处理");
        dataList.add("");
        dataList.add("总结:\na:0强制按格式对齐，#最充足的情况以这样的格式对齐\nb:# "
                + "适用的场景是:当小数位超过两位时，只显示两位，但若只有一位或没有，则不需要补0\nc:整数位用多个#是没有意义的\nd:对于固定保留两位小数的格式化设为 #0.00 是最合适的");
        dataList.add("");

        adapter = new DecimalAdapter(dataList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private String getResult(String pattern, double value) {
        String result = new DecimalFormat(pattern).format(value);
        return pattern + "_____" + result;
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }


    private static class DecimalViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public DecimalViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView;
        }
    }

    private static class DecimalAdapter extends RecyclerView.Adapter<DecimalViewHolder> {

        private List<String> dataList;

        public DecimalAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        @Override
        public DecimalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            TextView textView = new TextView(viewGroup.getContext());
            textView.setTextColor(Color.BLACK);

            return new DecimalViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(DecimalViewHolder decimalViewHolder, int i) {
            decimalViewHolder.textView.setText(dataList.get(i));

            if (dataList.get(i).startsWith("结论") || dataList.get(i).startsWith("总结")) {
                decimalViewHolder.textView.setTextColor(Color.RED);
            } else {
                decimalViewHolder.textView.setTextColor(Color.BLACK);
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

}
