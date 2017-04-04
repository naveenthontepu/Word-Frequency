package thontepu.naveen.sampleproject.RecyclerViewAdapterViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import thontepu.naveen.sampleproject.R;

/**
 * Created by mac on 4/4/17
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    TextView headerText;
    public HeaderViewHolder(View itemView) {
        super(itemView);
        headerText = (TextView)itemView.findViewById(R.id.headerText);
    }
}
