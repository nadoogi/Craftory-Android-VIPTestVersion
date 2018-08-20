package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 5. 28..
 */

public class SearchMainViewHolder extends RecyclerView.ViewHolder{

    TextView search_query;
    TextView search_date;
    LinearLayout search_item_layout;


    public SearchMainViewHolder(View cardView) {

        super(cardView);

        this.search_query = (TextView) cardView.findViewById(R.id.search_query);
        this.search_date = (TextView) cardView.findViewById(R.id.search_date);
        this.search_item_layout = (LinearLayout) cardView.findViewById(R.id.search_item_layout);

    }

    public TextView getSearchQuery(){return search_query;}
    public TextView getSearchDate(){return search_date;}
    public LinearLayout getSearchItemLayout(){return search_item_layout;}

}
