package wangdaye.com.geometricweather.UserInterface;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import wangdaye.com.geometricweather.R;

/**
 * Show application's details.
 * */

public class AboutAppActivity extends AppCompatActivity {
    // TAG
//    private final String TAG = "AboutAppActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarTransParent();

        setContentView(R.layout.activity_about);

        FrameLayout statusBar = (FrameLayout) findViewById(R.id.activity_about_status_bar);
        this.setStatusBar(statusBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_info_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        setWindowTopColor();
        MainActivity.initNavigationBar(this, getWindow());

        ImageView titleImage = (ImageView) findViewById(R.id.app_info_title);
        if (MainActivity.isDay) {
            titleImage.setImageBitmap(MainActivity.readBitMap(this, R.drawable.about_title_day));
        } else {
            titleImage.setImageBitmap(MainActivity.readBitMap(this, R.drawable.about_title_night));
        }

        ImageView iconImage = (ImageView) findViewById(R.id.about_app_icon);
        iconImage.setImageBitmap(MainActivity.readBitMap(this, R.drawable.ic_launcher));

        TextView[] textView = new TextView[4];
        textView[0] = (TextView) findViewById(R.id.app_info_name_text);
        textView[1] = (TextView) findViewById(R.id.app_info_tech_text);
        textView[2] = (TextView) findViewById(R.id.app_info_thank_text);
        textView[3] = (TextView) findViewById(R.id.app_info_author_text);
        if (MainActivity.isDay) {
            for (int i = 0; i < 4; i ++) {
                textView[i].setTextColor(ContextCompat.getColor(this, R.color.lightPrimary_3));
            }
        } else {
            for (int i = 0; i < 4; i ++) {
                textView[i].setTextColor(ContextCompat.getColor(this, R.color.darkPrimary_1));
            }
        }

        RelativeLayout introductionContainer = (RelativeLayout) findViewById(R.id.about_app_ic_app_introduce_container);
        introductionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutAppActivity.this, IntroduceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setStatusBarTransParent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowTopColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription taskDescription;
            Bitmap topIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            if (MainActivity.isDay) {
                taskDescription = new ActivityManager.TaskDescription(
                        getString(R.string.app_name),
                        topIcon,
                        ContextCompat.getColor(this, R.color.lightPrimary_5));
            } else {
                taskDescription = new ActivityManager.TaskDescription(
                        getString(R.string.app_name),
                        topIcon,
                        ContextCompat.getColor(this, R.color.darkPrimary_5));
            }
            setTaskDescription(taskDescription);
            topIcon.recycle();
        }
    }

    private void setStatusBar(FrameLayout statusBar) {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        statusBar.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        statusBarHeight
                )
        );
        if (MainActivity.isDay) {
            statusBar.setBackgroundColor(ContextCompat.getColor(this, R.color.lightPrimary_5));
        } else {
            statusBar.setBackgroundColor(ContextCompat.getColor(this, R.color.darkPrimary_5));
        }
    }
}