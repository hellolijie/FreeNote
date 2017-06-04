package cn.mune.jerry.abc.db.dao;

import android.content.Context;

import com.activeandroid.query.Select;

import java.util.List;

import cn.mune.jerry.abc.db.DBUtil;
import cn.mune.jerry.abc.db.model.NewWordModel;
import cn.mune.jerry.abc.db.model.NoteNewWordModel;

/**
 * Created by lijie on 17/4/16.
 */

public class NewWordDao {

    /**
     * 根据单词查询
     * @param word
     * @return
     */
    public static NewWordModel getNewWordByWord(String word){
        return new Select()
                .from(NewWordModel.class)
                .where("new_word=?", word)
                .executeSingle();
    }

    /**
     * 存储新词
     * @param context
     * @param newWord
     * @param translate
     * @return
     */
    public static NewWordModel saveNewWord(Context context
            , String newWord
            , String translate){
        NewWordModel newWordModel = new NewWordModel();
        newWordModel.newWord = newWord;
        newWordModel.translate = translate;
        newWordModel.newWordId = DBUtil.createID(context, "newWord");
        newWordModel.createTime = System.currentTimeMillis();
        newWordModel.updateTime = newWordModel.createTime;
        newWordModel.save();

        return newWordModel;
    }

    /**
     * 获取新词
     * @param noteId
     * @return
     */
    public static List<NewWordModel> getNewWordList(String noteId){
        return new Select()
                .from(NewWordModel.class)
                .as("a")
                .innerJoin(NoteNewWordModel.class)
                .as("b")
                .on("a.new_word_id=b.new_word_id")
                .where("b.note_id=?", noteId)
                .execute();
    }
}
