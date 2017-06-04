package cn.mune.jerry.abc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.mune.jerry.abc.R;
import cn.mune.jerry.abc.db.model.NewWordModel;

/**
 * Created by lijie on 17/3/19.
 */

public class NewWordAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<NewWordModel> newWordList;

    public NewWordAdapter(Context context, List<NewWordModel> newWordList){
        this.context = context;
        this.newWordList = newWordList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewWorldHolder(inflater.inflate(R.layout.item_new_world, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewWorldHolder newWorldHolder = (NewWorldHolder) holder;
        NewWordModel newWord = newWordList.get(position);
        newWorldHolder.position = position;

        newWorldHolder.word.setText(newWord.newWord);
        newWorldHolder.translate.setText(newWord.translate);
    }

    @Override
    public int getItemCount() {
        return newWordList.size();
    }

    class NewWorldHolder extends RecyclerView.ViewHolder{
        int position;

        TextView word;
        TextView translate;

        public NewWorldHolder(View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.tv_word);
            translate = (TextView) itemView.findViewById(R.id.tv_translate);
        }
    }
}
