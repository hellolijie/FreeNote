package cn.mune.jerry.abc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.mune.jerry.abc.R;
import cn.mune.jerry.abc.adapter.TagAdapter;
import cn.mune.jerry.abc.adapter.WordTranslateAdapter;
import cn.mune.jerry.abc.db.dao.NoteDao;
import cn.mune.jerry.abc.db.dao.TagDao;
import cn.mune.jerry.abc.entity.NewWordEntity;
import cn.mune.jerry.abc.helper.SplitWorldHelper;
import cn.mune.jerry.baselibrary.utils.ToastUtil;

/**
 * Created by lijie on 17/3/25.
 */

public class AddNoteActivity extends BaseActivity {
    private SplitWorldHelper splitWorldHelper;
    private TagFlowLayout worldsLayout;
    private EditText textContent;
    private boolean editing;

    private RecyclerView wordTranslateRcv;
    private HashMap<String, NewWordEntity> newWordMap;
    private List<NewWordEntity> newWordList;

    private RecyclerView tagRcv;
    private TagAdapter tagAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        init();
    }

    private void init() {
        initToolBar("添加笔记");

        newWordMap = new HashMap<>();
        wordTranslateRcv = (RecyclerView) findViewById(R.id.rcv_word_translate);
        wordTranslateRcv.setLayoutManager(new LinearLayoutManager(this));

        textContent = (EditText) findViewById(R.id.et_text);
        worldsLayout = (TagFlowLayout) findViewById(R.id.tfl_worlds);
        splitWorldHelper = new SplitWorldHelper(worldsLayout) {
            @Override
            public View getButton(String word, FlowLayout parent) {
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_edit_word, parent, false);
                textView.setText(word);
                return textView;
            }

            @Override
            public void onSelect(List<String> selectWords) {
                newWordList = new ArrayList<>();
                for (String word : selectWords){
                    if (newWordMap.get(word) == null) {
                        NewWordEntity newWordEntity = new NewWordEntity();
                        newWordEntity.word = word;
                        newWordMap.put(word, newWordEntity);
                    }
                    newWordList.add(newWordMap.get(word));
                }

                wordTranslateRcv.setAdapter(new WordTranslateAdapter(AddNoteActivity.this, newWordList));
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        initTag();
    }

    /**
     * 初始化标签信息
     */
    private void initTag(){
        tagRcv = (RecyclerView) findViewById(R.id.rcv_tag);
        tagRcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tagRcv.setAdapter(tagAdapter = new TagAdapter(this, TagDao.getAllTag()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_confirm){
            if (saveNote()){
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 存储笔记
     */
    private boolean saveNote(){
        String sentence = ((TextView)findViewById(R.id.et_text)).getText().toString();
        if (TextUtils.isEmpty(sentence)){
            ToastUtil.show(this, "请输入笔记");
            return false;
        }

        String wordsIndexes = getWordsIndexes();
        String tagId = tagAdapter.getSelectTag().tagId;

        return NoteDao.saveNote(this, sentence, wordsIndexes, tagId, newWordList) != null;
    }

    /**
     * 获取生词位置
     * @return
     */
    private String getWordsIndexes(){
        Set<Integer> selectPosSet = splitWorldHelper.getSelectPosSet();
        String wordsIndexes = "";
        if (selectPosSet != null){
            Iterator<Integer> iterator = selectPosSet.iterator();
            while (iterator.hasNext()) {
                if (!TextUtils.isEmpty(wordsIndexes)){
                    wordsIndexes += ",";
                }
                wordsIndexes += iterator.next();
            }
        }

        return wordsIndexes;
    }

    /**
     * 切换编辑生词页面
     *
     * @param edit
     */
    private void switchEditNewWordLayout(boolean edit) {
        if (edit) {
            textContent.setVisibility(View.GONE);
            worldsLayout.setVisibility(View.VISIBLE);

        } else {
            textContent.setVisibility(View.VISIBLE);
            worldsLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 编辑生词
     *
     * @param view
     */
    public void onEditNewWord(View view) {
        if (!editing) {
            //编辑生词
            String inputText = textContent.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                ToastUtil.show(AddNoteActivity.this, "请输入文字");
                return;
            }

            splitWorldHelper.show(inputText);
            editing = true;
            switchEditNewWordLayout(editing);

            ((FloatingActionButton) view).setImageResource(R.mipmap.ic_close);

            hideSoftKeyboard();
        } else {
            editing = false;
            switchEditNewWordLayout(editing);
            ((FloatingActionButton) view).setImageResource(R.mipmap.ic_mode_edit);
        }
    }

    /**
     * 添加标签
     * @param view
     */
    public void addTag(View view){
        Intent intent = new Intent(this, AddTagActivity.class);
        startActivity(intent);
    }
}
