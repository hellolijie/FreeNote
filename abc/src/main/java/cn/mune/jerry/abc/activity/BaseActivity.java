package cn.mune.jerry.abc.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import cn.mune.jerry.abc.R;
import cn.mune.jerry.baselibrary.customWidget.StatusBarCompat;

/**
 * Created by lijie on 17/3/19.
 */

public class BaseActivity extends cn.mune.jerry.baselibrary.activity.BaseActivity {
    protected Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        StatusBarCompat.compat(this, getResources().getColor(R.color.white));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this, getResources().getColor(R.color.white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化toobar
     * @param title
     * @param navigationIcon
     */
    protected void initToolBar(String title, int navigationIcon){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(navigationIcon);
        setSupportActionBar(toolbar);

        TextView titleView = (TextView) findViewById(R.id.tv_title);
        if (titleView != null){
            titleView.setText(title);
        }
    }

    /**
     * 默认使用返回icon
     * @param title
     */
    protected void initToolBar(String title){
        initToolBar(title, R.mipmap.ic_arrow_back);
    }
}
