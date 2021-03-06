package com.android.framework.demo;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.framework.AidlMainActivity;
import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.activity.AnimationListActivity;
import com.android.framework.demo.activity.BottomBarActivity;
import com.android.framework.demo.activity.BreatheActivity;
import com.android.framework.demo.activity.ClipChildrenActivity;
import com.android.framework.demo.activity.ConstraintLayoutActivity;
import com.android.framework.demo.activity.CustomDrawableActivity;
import com.android.framework.demo.activity.DecimalFormatActivity;
import com.android.framework.demo.activity.FragmentRepeatActivity;
import com.android.framework.demo.activity.HuaWeiNavBarActivity;
import com.android.framework.demo.activity.IncludeActivity;
import com.android.framework.demo.activity.LinkColorTextViewActivity;
import com.android.framework.demo.activity.RadarViewActivity;
import com.android.framework.demo.activity.span.SpanActivity;
import com.android.framework.demo.activity.canvas.CanvasSaveLayerActivity;
import com.android.framework.demo.activity.customshape.CustomShapeViewGroupActivity;
import com.android.framework.demo.activity.WrapHeightViewPagerActivity;
import com.android.framework.demo.activity.YouKuLinkActivity;
import com.android.framework.demo.activity.shareelement.Share1Activity;
import com.android.framework.demo.activity.viewdemo.DouYinSeekViewActivity;
import com.android.framework.demo.activity.GalleryRecyclerViewActivity;
import com.android.framework.demo.activity.LayoutAnimationActivity;
import com.android.framework.demo.activity.ListViewActivity;
import com.android.framework.demo.activity.OkHttpActivity;
import com.android.framework.demo.activity.PinnedHeaderExpandableListViewActivity;
import com.android.framework.demo.activity.PullDownDismissActivity;
import com.android.framework.demo.activity.SaveStateViewActivity;
import com.android.framework.demo.activity.ScreenLightSettingActivity;
import com.android.framework.demo.activity.ShortCutActivity;
import com.android.framework.demo.activity.StatusBarColorActivity;
import com.android.framework.demo.activity.TouchFloatActivity;
import com.android.framework.demo.activity.ViewDragHelperActivity;
import com.android.framework.demo.activity.ViewPageTransFormerActivity;
import com.android.framework.demo.activity.WrapTypeJsonActivity;
import com.android.framework.demo.activity.colormatrix.ColorMatrixActivity;
import com.android.framework.demo.activity.design.AnimatedVectorDrawableActivity;
import com.android.framework.demo.activity.design.AppBarLayoutActivity;
import com.android.framework.demo.activity.design.AppCompatTestActivity;
import com.android.framework.demo.activity.design.BottomSheetActivity;
import com.android.framework.demo.activity.design.CardViewActivity;
import com.android.framework.demo.activity.design.CoordinatorLayoutActivity;
import com.android.framework.demo.activity.design.FABBehaviorActivity;
import com.android.framework.demo.activity.design.FlexboxActivity;
import com.android.framework.demo.activity.design.SwipeDismissBehaviorActivity;
import com.android.framework.demo.activity.design.dragrecycler.DragRecyclerGridViewActivity;
import com.android.framework.demo.activity.fragmentT.AActivity;
import com.android.framework.demo.activity.glide.GlideActivity;
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
import com.android.framework.demo.activity.recyclerDecorat.RecyclerViewActivity;
import com.android.framework.demo.activity.shader.ShaderActivity;
import com.android.framework.demo.activity.themechange.ThemeChangeActivity;
import com.android.framework.demo.activity.utildemo.PermissionUtilDemoActivity;
import com.android.framework.demo.activity.viewdemo.BounceLoadingActivity;
import com.android.framework.demo.activity.viewdemo.CameraShootButtonActivity;
import com.android.framework.demo.activity.viewdemo.CircleLoadingActivity;
import com.android.framework.demo.activity.viewdemo.DragScoreViewActivity;
import com.android.framework.demo.activity.viewdemo.ExplosionActivity;
import com.android.framework.demo.activity.viewdemo.FloatingActionsMenuActivity;
import com.android.framework.demo.activity.viewdemo.FlowLayoutActivity;
import com.android.framework.demo.activity.viewdemo.FuseImageActivity;
import com.android.framework.demo.activity.viewdemo.HeadNewsRefreshViewActivity;
import com.android.framework.demo.activity.viewdemo.ImageOuterTextViewActivity;
import com.android.framework.demo.activity.viewdemo.InputLinearLayoutActivity;
import com.android.framework.demo.activity.viewdemo.LedTextViewActivity;
import com.android.framework.demo.activity.viewdemo.LevelImageViewActivity;
import com.android.framework.demo.activity.viewdemo.MarqueeViewActivity;
import com.android.framework.demo.activity.viewdemo.NumberSeekBarActivity;
import com.android.framework.demo.activity.viewdemo.PinnedHeaderListViewActivity;
import com.android.framework.demo.activity.viewdemo.PriceRangeActivity;
import com.android.framework.demo.activity.viewdemo.ProgressImageActivity;
import com.android.framework.demo.activity.viewdemo.RangeSeekBar2Activity;
import com.android.framework.demo.activity.viewdemo.RangeSeekBarActivity;
import com.android.framework.demo.activity.viewdemo.ScrollLayoutActivity;
import com.android.framework.demo.activity.viewdemo.SlideViewPagerActivity;
import com.android.framework.demo.activity.viewdemo.SmartGridViewActivity;
import com.android.framework.demo.activity.viewdemo.XFlowLayoutActivity;
import com.android.framework.demo.activity.viewdemo.measureExtend.MeasureExtendActivity;
import com.android.framework.demo.activity.webview.MikeWebViewActivity;
import com.android.framework.demo.media.MediaDemoMainActivity;
import com.android.framework.demo.requesttest.RequestTestActivity;
import com.android.framework.media.audiodemo.AudioDemoActivity;
import com.android.framework.websocket.WebSocketActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meikai on 15/10/14.
 */
public class MainActivity extends BaseCompactActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ListView listView;

    private Class<?>[] classes = {
            AppBarLayoutActivity.class,
            BreatheActivity.class,
            ProgressImageActivity.class,
            SpanActivity.class,
            Share1Activity.class,
            LinkColorTextViewActivity.class,
            RadarViewActivity.class,
            FragmentRepeatActivity.class,
            MeasureExtendActivity.class,
            CanvasSaveLayerActivity.class,
            StatusBarColorActivity.class,
            CustomShapeViewGroupActivity.class,
            IncludeActivity.class,
            WrapHeightViewPagerActivity.class,
            ClipChildrenActivity.class,
            HuaWeiNavBarActivity.class,
            YouKuLinkActivity.class,
            DouYinSeekViewActivity.class,
            CameraShootButtonActivity.class,
            RangeSeekBar2Activity.class,
            RangeSeekBarActivity.class,
            CustomDrawableActivity.class,
            DecimalFormatActivity.class,
            ConstraintLayoutActivity.class,
            GlideActivity.class,
            RecyclerViewActivity.class,
            AActivity.class,
            ViewDragHelperActivity.class,
            GalleryRecyclerViewActivity.class,
            RequestTestActivity.class,
            ListViewActivity.class,
            ShortCutActivity.class,
            TouchFloatActivity.class,
            PullDownDismissActivity.class,
            ViewPageTransFormerActivity.class,
            WrapTypeJsonActivity.class,
            XFlowLayoutActivity.class,
            FloatingActionsMenuActivity.class,
            LedTextViewActivity.class,
            MarqueeViewActivity.class,
            MikeWebViewActivity.class,
            ScreenLightSettingActivity.class,
            LayoutAnimationActivity.class,
            AnimationListActivity.class,
            HeadNewsRefreshViewActivity.class,
            DragRecyclerGridViewActivity.class,
            FlexboxActivity.class,
            MediaDemoMainActivity.class,
            SmartGridViewActivity.class,
            PinnedHeaderListViewActivity.class,
            WebSocketActivity.class,
            ThemeChangeActivity.class,
            FABBehaviorActivity.class,
            NDKTestActivity.class,
            AidlMainActivity.class,
            AnimatorActivity.class,
            PermissionUtilDemoActivity.class,
            AudioDemoActivity.class,
            BottomSheetActivity.class,
            ImageOuterTextViewActivity.class,
            DragScoreViewActivity.class,
            PriceRangeActivity.class,
            AnimatedVectorDrawableActivity.class,
            SwipeDismissBehaviorActivity.class,
            CoordinatorLayoutActivity.class,
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
            NotificationActivity.class,
            BounceLoadingActivity.class,
            CircleLoadingActivity.class,
            HandlerThreadActivity.class,
            IntentServiceActivity.class,
            LayoutWeightActivity.class
    };

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void findViews() {

        drawerLayout = f(R.id.root_drawer_layout);

        toolbar = f(R.id.toolbar);

        listView = f(R.id.demo_list_view);

    }

    @Override
    public void setListeners() {

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Class<?> className = classes[position];
            MainActivity.this.startActivity(new Intent(MainActivity.this, className));
        });

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.fuse1_default);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_demo, R.id.tv_item_name,
                getArray(classes));
        listView.setAdapter(adapter);

    }

    @Override
    public void onThemeChange() {
        this.recreate();
    }

    private String[] getArray(Class<?>[] classes) {
        List<Class<?>> classList = Arrays.asList(classes);
        List<String> stringList = new ArrayList<>();

        for (int i = 0; i < classList.size(); i++) {
            stringList.add(classList.get(i).getSimpleName());
        }

        return stringList.toArray(new String[stringList.size()]);
    }

}
