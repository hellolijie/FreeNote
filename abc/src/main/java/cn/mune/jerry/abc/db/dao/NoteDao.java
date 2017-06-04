package cn.mune.jerry.abc.db.dao;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.List;

import cn.mune.jerry.abc.db.DBUtil;
import cn.mune.jerry.abc.db.model.NewWordModel;
import cn.mune.jerry.abc.db.model.NoteModel;
import cn.mune.jerry.abc.db.model.NoteNewWordModel;
import cn.mune.jerry.abc.entity.NewWordEntity;

/**
 * Created by lijie on 17/4/16.
 */

public class NoteDao {
    /**
     * 存储笔记
     * @param context
     * @param sentence
     * @param wordsIndexes
     * @param tagId
     * @param newWordList
     * @return
     */
    public static NoteModel saveNote(Context context
            , String sentence
            , String wordsIndexes
            , String tagId
            , List<NewWordEntity> newWordList){
        ActiveAndroid.beginTransaction();
        try {
            //存储笔记数据
            NoteModel noteModel = new NoteModel();
            noteModel.sentence = sentence;
            noteModel.wordsIndexes = wordsIndexes;
            noteModel.tagId = tagId;
            noteModel.createTime = System.currentTimeMillis();
            noteModel.updateTime = noteModel.createTime;
            noteModel.noteId = DBUtil.createID(context, "note");
            noteModel.save();

            for (NewWordEntity newWord : newWordList){
                //存储新词数据
                NewWordModel newWordModel = NewWordDao.getNewWordByWord(newWord.word);
                if (newWordModel == null)
                    newWordModel = NewWordDao.saveNewWord(context, newWord.word, newWord.translate);

                //存储笔记新词关联数据
                NoteNewWordModel noteNewWordModel = new NoteNewWordModel();
                noteNewWordModel.noteNewWordId = DBUtil.createID(context, "noteNewWord");
                noteNewWordModel.noteId = noteModel.noteId;
                noteNewWordModel.newWordId = newWordModel.newWordId;
                noteNewWordModel.createTime = noteModel.createTime;
                noteNewWordModel.updateTime = noteModel.updateTime;
                noteNewWordModel.save();
            }

            ActiveAndroid.setTransactionSuccessful();

            return noteModel;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            ActiveAndroid.endTransaction();
        }

        return null;
    }

    /**
     * 获取分页笔记
     * @param page
     * @param pageSize
     * @return
     */
    public static List<NoteModel> getNotePage(int page, int pageSize){
        return new Select()
                .from(NoteModel.class)
                .limit(pageSize)
                .offset(page * pageSize)
                .execute();
    }
}
