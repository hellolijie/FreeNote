package cn.mune.jerry.abc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.mune.jerry.abc.Constans;
import cn.mune.jerry.abc.R;
import cn.mune.jerry.abc.Utils.GeneralUtils;
import cn.mune.jerry.abc.activity.MainActivity;
import cn.mune.jerry.abc.entity.NewWordEntity;
import cn.mune.jerry.abc.net.NetConstans;
import cn.mune.jerry.abc.net.TranslateApiWrapper;
import cn.mune.jerry.abc.net.model.BaiduTranslateModel;
import cn.mune.jerry.baselibrary.net.ApiCreator;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lijie on 17/4/15.
 */

public class WordTranslateAdapter extends RecyclerView.Adapter{
    private LayoutInflater inflater;
    private List<NewWordEntity> wordList;

    public WordTranslateAdapter(Context context, List<NewWordEntity> wordList){
        this.wordList = wordList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WordTranslateHolder(inflater.inflate(R.layout.item_word_translate, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WordTranslateHolder wordTranslateHolder = (WordTranslateHolder) holder;
        NewWordEntity newWord = wordList.get(position);

        wordTranslateHolder.word.setText(newWord.word);
        if (TextUtils.isEmpty(newWord.translate)){
            //如果为空使用百度翻译获取翻译
            translate(newWord, ((WordTranslateHolder) holder).translate);
        }
        else {
            wordTranslateHolder.translate.setText(newWord.translate);
        }
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    /**
     * 翻译
     * @param word
     * @param translateView
     */
    private void translate(final NewWordEntity word, final TextView translateView){
        int salt = (int) System.currentTimeMillis();
        TranslateApiWrapper apiWrapper = ApiCreator.create(TranslateApiWrapper.class, NetConstans.BAIDU_TRANSLATE_HOST);
        apiWrapper
            .baiduTranslate(
                    word.word,
                    "en",
                    "zh",
                    Constans.BAIDU_TRANSLATE_APPID,
                    String.valueOf(salt),
                    GeneralUtils.createBaiduTranslateSign(salt, word.word))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Response<String>>() {
                           @Override
                           public void call(Response<String> stringResponse) {
                               try {
                                   Logger.d(stringResponse.body());
                                   Gson gson = new Gson();
                                   BaiduTranslateModel translateModel
                                           = gson.fromJson(stringResponse.body(), BaiduTranslateModel.class);
                                   String translateStr = "";
                                   for (BaiduTranslateModel.TranslateResult translateResult : translateModel.transResult){
                                       if (!TextUtils.isEmpty(translateStr))
                                           translateStr += ",";
                                       translateStr += translateResult.dst;
                                   }

                                   word.translate = translateStr;
                                   translateView.setText(translateStr);
                               }catch (Exception e){
                                   e.printStackTrace();
                               }

                           }
                       },
                    new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.getMessage());
                        }
                    });
    }

    class WordTranslateHolder extends RecyclerView.ViewHolder{
        TextView word;
        TextView translate;

        public WordTranslateHolder(View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.tv_word);
            translate = (TextView) itemView.findViewById(R.id.tv_translate);
        }
    }
}
