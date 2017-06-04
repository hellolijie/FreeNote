package cn.mune.jerry.abc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.mune.jerry.abc.R;
import cn.mune.jerry.abc.db.dao.NewWordDao;
import cn.mune.jerry.abc.db.model.NoteModel;
import cn.mune.jerry.baselibrary.customWidget.fullyRecyclerViewManager.FullyLinearLayoutManager;

/**
 * Created by lijie on 17/3/19.
 */

public class NoteListAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<NoteModel> noteList;

    public NoteListAdapter(Context context, List<NoteModel> noteList){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.noteList = noteList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteListHolder(layoutInflater.inflate(R.layout.item_note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoteListHolder noteListHolder = (NoteListHolder) holder;
        NoteModel note = noteList.get(position);
        noteListHolder.position = position;
        noteListHolder.sentence.setText(note.sentence);
        noteListHolder.newWorldList
                .setAdapter(new NewWordAdapter(context, NewWordDao.getNewWordList(note.noteId)));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NoteListHolder extends RecyclerView.ViewHolder{
        int position;

        TextView sentence;
        RecyclerView newWorldList;

        public NoteListHolder(View itemView) {
            super(itemView);
            sentence = (TextView) itemView.findViewById(R.id.tv_sentence);
            newWorldList = (RecyclerView) itemView.findViewById(R.id.rcv_new_word);
            newWorldList.setLayoutManager(new FullyLinearLayoutManager(context));
        }
    }
}
