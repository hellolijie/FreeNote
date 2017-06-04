package cn.mune.jerry.abc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.iflytek.cloud.thirdparty.V;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.mune.jerry.abc.R;
import cn.mune.jerry.abc.adapter.NoteListAdapter;
import cn.mune.jerry.abc.db.dao.NoteDao;
import cn.mune.jerry.abc.db.model.NoteModel;
import cn.mune.jerry.abc.play.PlayData;
import cn.mune.jerry.abc.play.PlayService;
import cn.mune.jerry.baselibrary.customWidget.RecyclerRefreshHelper;
import cn.mune.jerry.baselibrary.utils.PermissionUtils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PAGE_SIZE = 10;

    private RecyclerView noteListRcv;
    private List<NoteModel> noteList;
    private NoteListAdapter noteListAdapter;
    private int page;

    private FloatingActionButton addButton;
    private PermissionUtils.PermissionGrant permissionGrant;

    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayData playData) {
        if (playData != null){
            if (playData.type == PlayData.TYPE_SENTENCE){
                isPlaying = true;
                addButton.setImageResource(R.mipmap.ic_pause);

                ((TextView)findViewById(R.id.tv_cur_sentence)).setText(playData.content);
                ((TextView)findViewById(R.id.tv_cur_word)).setText("");
                findViewById(R.id.btn_get).setVisibility(View.VISIBLE);
            }
            else {
                ((TextView)findViewById(R.id.tv_cur_word)).setText("-" + playData.content);
            }

        }
    }

    private void init() {
        initToolBar("", R.mipmap.ic_menu);

        addButton = (FloatingActionButton) findViewById(R.id.fab_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    isPlaying = false;
                    stopService(new Intent(MainActivity.this, PlayService.class));
                    addButton.setImageResource(R.mipmap.ic_play_arrow_white);
                    findViewById(R.id.btn_get).setVisibility(View.GONE);
                }
                else {
                    startService(new Intent(MainActivity.this, PlayService.class));
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initNoteList();


        PermissionUtils.requestPermission(this,
                PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,
                permissionGrant = new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode) {

            }
        });
    }

    /**
     * 初始化列表相关
     */
    private void initNoteList() {
        noteListRcv = (RecyclerView) findViewById(R.id.rcv_note_list);
        noteListRcv.setLayoutManager(new LinearLayoutManager(this));

        noteList = NoteDao.getNotePage(page, PAGE_SIZE);
        noteListRcv.setAdapter(noteListAdapter = new NoteListAdapter(this, noteList));

        noteListRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Logger.d("state: " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Logger.d("dy: " + dy);
                showAddButton(dy <= 0);
            }
        });

        new RecyclerRefreshHelper(noteListRcv, null)
                .setOnRefreshAndLoadMoreListener(new RecyclerRefreshHelper.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page ++;
                noteList.addAll(NoteDao.getNotePage(page, PAGE_SIZE));

                noteListAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 显示和隐藏添加按钮
     *
     * @param show
     */
    private void showAddButton(boolean show) {
        if ((addButton.getVisibility() == View.VISIBLE) ^ show) {
            addButton.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 添加
     */
    private void add() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            add();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, permissionGrant);
    }
}
