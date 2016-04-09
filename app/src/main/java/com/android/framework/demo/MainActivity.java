package com.android.framework.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.framework.AidlMainActivity;
import com.android.framework.demo.activity.BottomBarActivity;
import com.android.framework.demo.activity.BounceLoadingActivity;
import com.android.framework.demo.activity.CircleLoadingActivity;
import com.android.framework.demo.activity.ExplosionActivity;
import com.android.framework.demo.activity.FlowLayoutActivity;
import com.android.framework.demo.activity.FuseImageActivity;
import com.android.framework.demo.activity.InputLinearLayoutActivity;
import com.android.framework.demo.activity.LevelImageViewActivity;
import com.android.framework.demo.activity.NumberSeekBarActivity;
import com.android.framework.demo.activity.OkHttpActivity;
import com.android.framework.demo.activity.PinnedHeaderExpandableListViewActivity;
import com.android.framework.demo.activity.SaveStateViewActivity;
import com.android.framework.demo.activity.ScrollLayoutActivity;
import com.android.framework.demo.activity.SlideViewPagerActivity;
import com.android.framework.demo.activity.StatusBarColorActivity;
import com.android.framework.demo.activity.colormatrix.ColorMatrixActivity;
import com.android.framework.demo.activity.design.AnimatedVectorDrawableActivity;
import com.android.framework.demo.activity.design.AppBarLayoutActivity;
import com.android.framework.demo.activity.design.AppCompatTestActivity;
import com.android.framework.demo.activity.design.BottomSheetActivity;
import com.android.framework.demo.activity.design.CardViewActivity;
import com.android.framework.demo.activity.design.CoordinatorLayoutActivity;
import com.android.framework.demo.activity.design.FABBehaviorActivity;
import com.android.framework.demo.activity.design.SwipeDismissBehaviorActivity;
import com.android.framework.demo.activity.ndk.NDKTestActivity;
import com.android.framework.demo.activity.nolib.AlertDialogActivity;
import com.android.framework.demo.activity.nolib.AnimatorActivity;
import com.android.framework.demo.activity.nolib.HandlerThreadActivity;
import com.android.framework.demo.activity.nolib.InsetClipActivity;
import com.android.framework.demo.activity.nolib.IntentServiceActivity;
import com.android.framework.demo.activity.nolib.LayoutWeightActivity;
import com.android.framework.demo.activity.nolib.NotificationActivity;
import com.android.framework.demo.activity.nolib.RippleActivity;
import com.android.framework.demo.activity.nolib.TabLayoutActivity;
import com.android.framework.demo.activity.shader.ShaderActivity;
import com.android.framework.demo.activity.utildemo.PermissionUtilDemoActivity;
import com.android.framework.demo.activity.viewdemo.DragScoreViewActivity;
import com.android.framework.demo.activity.viewdemo.ImageOuterTextViewActivity;
import com.android.framework.demo.activity.viewdemo.PriceRangeActivity;
import com.android.framework.media.demo.MediaDemoActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meikai on 15/10/14.
 */
public class MainActivity extends Activity {

    private ListView listView;
    private Class<?>[] classes = {
            FABBehaviorActivity.class,
            NDKTestActivity.class,
            AidlMainActivity.class,
            AnimatorActivity.class,
            PermissionUtilDemoActivity.class,
            MediaDemoActivity.class,
            BottomSheetActivity.class,
            ImageOuterTextViewActivity.class,
            DragScoreViewActivity.class,
            PriceRangeActivity.class,
            AnimatedVectorDrawableActivity.class,
            SwipeDismissBehaviorActivity.class,
            CoordinatorLayoutActivity.class,
            AppBarLayoutActivity.class,
            InsetClipActivity.class,
            FuseImageActivity.class,
            LevelImageViewActivity.class,
            ColorMatrixActivity.class,
            ShaderActivity.class,
            AlertDialogActivity.class,
            OkHttpActivity.class,
            SaveStateViewActivity.class,
            PinnedHeaderExpandableListViewActivity.class,
            RippleActivity.class,
            CardViewActivity.class,
            InputLinearLayoutActivity.class,
            SlideViewPagerActivity.class,
            TabLayoutActivity.class,
            BottomBarActivity.class,
            FlowLayoutActivity.class,
            ScrollLayoutActivity.class,
            ExplosionActivity.class,
            NumberSeekBarActivity.class,
            AppCompatTestActivity.class,
            StatusBarColorActivity.class,
            NotificationActivity.class,
            BounceLoadingActivity.class,
            CircleLoadingActivity.class,
            HandlerThreadActivity.class,
            IntentServiceActivity.class,
            LayoutWeightActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addContentView(listView, lp);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_demo, R.id.tv_item_name, getArray(classes));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);

    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Class<?> className = (Class<?>) parent.getAdapter().getItem(position);

            Class<?> className = classes[position];
            MainActivity.this.startActivity(new Intent(MainActivity.this, className));
        }
    };

    private String[] getArray(Class<?>[] classes) {
        List<Class<?>> classList = Arrays.asList(classes);
        List<String> stringList = new ArrayList<>();

        for (int i = 0; i < classList.size(); i++) {
            stringList.add(classList.get(i).getSimpleName());
        }

        return stringList.toArray(new String[stringList.size()]);
    }

}
