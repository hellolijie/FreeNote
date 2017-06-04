package cn.mune.jerry.abc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.mune.jerry.abc.R;
import cn.mune.jerry.abc.db.model.TagModel;

/**
 * Created by lijie on 17/4/16.
 */

public class TagAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private List<TagModel> tagList;

    private int selectPosition = 0;

    public TagAdapter(Context context, List<TagModel> tagList){
        this.tagList = tagList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TagHolder(inflater.inflate(R.layout.item_tag, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TagHolder tagHolder = (TagHolder) holder;
        TagModel tag = tagList.get(position);

        tagHolder.position = position;
        tagHolder.tagName.setText(tag.tagName);
        tagHolder.tagName.setSelected(position == selectPosition);
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    /**
     * 获取选中标签
     * @return
     */
    public TagModel getSelectTag(){
        return tagList.get(selectPosition);
    }

    class TagHolder extends RecyclerView.ViewHolder{
        int position;

        TextView tagName;

        public TagHolder(View itemView) {
            super(itemView);
            tagName = (TextView) itemView.findViewById(R.id.tv_tag_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPosition = position;
                    notifyDataSetChanged();
                }
            });
        }
    }
}
