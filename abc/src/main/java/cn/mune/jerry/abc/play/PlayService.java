package cn.mune.jerry.abc.play;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lijie on 17/4/22.
 */

public class PlayService extends Service {

    private Player player;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new Player(this);
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        player.startPlay();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stopPlay();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
