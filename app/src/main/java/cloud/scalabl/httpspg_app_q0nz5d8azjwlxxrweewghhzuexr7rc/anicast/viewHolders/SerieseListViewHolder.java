package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class SerieseListViewHolder extends RecyclerView.ViewHolder {

    ImageView seriese_image;
    TextView post_body;
    ImageView post_type_image;
    LinearLayout post_tag_layout;
    TagGroup tag_group;
    LinearLayout delete_button;


    public SerieseListViewHolder(View itemView) {
        super(itemView);

        seriese_image = (ImageView) itemView.findViewById(R.id.patron_image);
        post_body = (TextView) itemView.findViewById(R.id.post_body);
        post_type_image = (ImageView) itemView.findViewById(R.id.post_type_image);

        post_tag_layout = (LinearLayout) itemView.findViewById(R.id.post_tag_layout);
        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);
        delete_button = (LinearLayout) itemView.findViewById(R.id.delete_button);


    }

    public ImageView getSerieseImage(){return seriese_image;}
    public TextView getPostBody(){return post_body;}
    public ImageView getPostTypeImage(){return post_type_image;}
    public LinearLayout getPostTagLayout(){return post_tag_layout;}
    public TagGroup getTagGroup(){return tag_group;}
    public LinearLayout getDeleteButton(){return delete_button;}

}
