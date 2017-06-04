package cn.mune.jerry.abc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cn.mune.jerry.abc.R;
import cn.mune.jerry.abc.db.DBUtil;
import cn.mune.jerry.abc.db.dao.TagDao;
import cn.mune.jerry.abc.db.model.TagModel;
import cn.mune.jerry.baselibrary.utils.ToastUtil;

/**
 * Created by lijie on 17/4/16.
 */

public class AddTagActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        initToolBar("添加标签");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_confirm){
            String tagName = ((TextView)findViewById(R.id.et_tag)).getText().toString();
            if (TextUtils.isEmpty(tagName)){
                ToastUtil.show(this, "请输入标签名");
                return true;
            }

            TagDao.saveTag(this, tagName);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
