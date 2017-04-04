package thontepu.naveen.sampleproject.RecyclerViewAdapterViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import thontepu.naveen.sampleproject.R;
import thontepu.naveen.sampleproject.Pojo.StringCount;
import thontepu.naveen.sampleproject.Utils.Constants;

/**
 * Created by mac on 4/4/17
 */

public class WordCountAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private List<StringCount> data;

    public WordCountAdapter(Context context,List<StringCount> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<StringCount> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void clearList(){
        data = Collections.emptyList();
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == Constants.AdapterViewType.VIEW_ITEM) {
            View view = inflater.inflate(R.layout.item_view, parent, false);
            viewHolder = new WordCountItemViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.header_view, parent, false);
            viewHolder = new HeaderViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).isHeader()? Constants.AdapterViewType.VIEW_HEADER: Constants.AdapterViewType.VIEW_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StringCount stringCount = data.get(position);
        String showText = stringCount.getShowText();
        if (holder instanceof WordCountItemViewHolder){
            WordCountItemViewHolder wordCountItemViewHolder = (WordCountItemViewHolder) holder;
            wordCountItemViewHolder.wordCount.setText(showText);
        }else {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.headerText.setText(showText);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
