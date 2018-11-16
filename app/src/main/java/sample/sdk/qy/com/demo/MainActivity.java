package sample.sdk.qy.com.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<View> pages;
    private FrameLayout layout;
    private ViewPager pager;
    private ViewPagerIndicator mIndicatorCircleLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pages=getPages();
        pager=(ViewPager) findViewById(R.id.gallery);
        PagerAdapter adapter=new ViewAdapter(pages);
        pager.setAdapter(adapter);
        pager.setPageMargin(20);
        pager.setOffscreenPageLimit(3);
        pager.setPageTransformer(true, new GalleryPageTransformer());

        layout=(FrameLayout) findViewById(R.id.frame_layout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return pager.dispatchTouchEvent(event);
            }
        });

        mIndicatorCircleLine = (ViewPagerIndicator) findViewById(R.id.indicator_circle_line);
        mIndicatorCircleLine.setViewPager(pager,9);
    }

    private List<View> getPages() {
        List<View> pages=new ArrayList<>();
        Field[] fields=R.drawable.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.getName().startsWith("page")) {
                    ImageView view = new ImageView(this);
                    view.setImageBitmap(ImageUtils.getReverseBitmapById(this, field.getInt(null), 0.5f));
                    pages.add(view);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return pages;
    }
}