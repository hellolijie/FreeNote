package cn.mune.jerry.abc.play;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by lijie on 17/4/22.
 */

public class Player {
    private boolean isPlaying;
    private Speaker speaker;
    private PlayDataController playDataController;

    public Player(Context context){

        speaker = new Speaker(context) {
            @Override
            public void onSpeakFinish() {
                playNote();
            }
        };

        isPlaying = false;

        playDataController = new PlayDataController();
    }


    /**
     * 播放语句
     */
    private void playNote(){
        PlayData playData = playDataController.getPlayData();
        speaker.speak(playData.content);

        EventBus.getDefault().post(playData);
    }

    /**
     * 开始收听
     */
    public void startPlay(){
        if (isPlaying)
            stopPlay();

        isPlaying = true;
        playNote();
    }

    /**
     * 停止收听
     */
    public void stopPlay(){
        isPlaying = false;
        speaker.stop();
    }

    /**
     * 是否在收听中
     * @return
     */
    public boolean isPlaying(){
        return isPlaying;
    }
}
