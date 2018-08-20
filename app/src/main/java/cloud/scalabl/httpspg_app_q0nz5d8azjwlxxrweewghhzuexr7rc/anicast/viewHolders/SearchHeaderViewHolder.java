package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 5. 28..
 */

public class SearchHeaderViewHolder extends RecyclerView.ViewHolder{

    TextView filter_type;
    TagGroup tag_group;
    NestedScrollView scroll_view;


    public SearchHeaderViewHolder(View cardView) {

        super(cardView);

        this.filter_type = (TextView) cardView.findViewById(R.id.filter_type);
        this.tag_group = (TagGroup) cardView.findViewById(R.id.tag_group);
        this.scroll_view = (NestedScrollView) cardView.findViewById(R.id.scroll_view);

    }

    public TextView getFilterType(){return filter_type;}
    public TagGroup getTagGroup(){return tag_group;}
    public NestedScrollView getScrollView(){return scroll_view;}



}
