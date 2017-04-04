package thontepu.naveen.sampleproject.RecyclerViewAdapterViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import thontepu.naveen.sampleproject.R;

/**
 * Created by mac on 4/4/17
 */

class WordCountItemViewHolder extends RecyclerView.ViewHolder{

    TextView wordCount;
    WordCountItemViewHolder(View itemView) {
        super(itemView);
        wordCount = (TextView) itemView.findViewById(R.id.wordCount);
    }
}
