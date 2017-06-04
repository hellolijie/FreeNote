package cn.mune.jerry.abc.play;

import com.activeandroid.query.Select;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import cn.mune.jerry.abc.db.dao.NewWordDao;
import cn.mune.jerry.abc.db.model.NewWordModel;
import cn.mune.jerry.abc.db.model.NoteModel;

/**
 * Created by lijie on 17/4/22.
 */

public class PlayDataController {
    private LinkedBlockingQueue<PlayData> dataQueue;
    public PlayDataController(){
        dataQueue = new LinkedBlockingQueue<>();
    }

    /**
     * 从本地数据库获取笔记数据
     * @return
     */
    private NoteModel getNote(){
        return new Select().from(NoteModel.class).executeSingle();
    }

    /**
     * 向队列中添加数据
     */
    private void offerPlayData(){
        NoteModel note = getNote();
        List<NewWordModel> newWordList = NewWordDao.getNewWordList(note.noteId);

        PlayData sentence = new PlayData();
        sentence.type = PlayData.TYPE_SENTENCE;
        sentence.content = note.sentence += ";";
        dataQueue.offer(sentence);

        for (NewWordModel newWord : newWordList){
            String wordSpeech = "";
            wordSpeech += newWord.newWord;
            wordSpeech += "; ";
            for (int i = 0; i < newWord.newWord.length(); i++){
                wordSpeech += newWord.newWord.substring(i, i+1);
                wordSpeech += " ";
            }
            wordSpeech += newWord.translate;
            wordSpeech += ";";

            PlayData word = new PlayData();
            word.type = PlayData.TYPE_WORD;
            word.content = wordSpeech;
            dataQueue.offer(word);
        }

    }

    /**
     * 获取好播放的数据
     * @return
     */
    public PlayData getPlayData(){
        PlayData data = dataQueue.poll();
        if (data == null){
            //没有数据了
            offerPlayData();
            data = dataQueue.poll();
        }

        return data;
    }

}
