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

public class ArtworkDashboardWebtoonRankingViewHolder extends RecyclerView.ViewHolder {

    ImageView seriese_image;
    TextView title;
    TextView nickname;
    LinearLayout profile_tag;
    LinearLayout post_tag_group;
    TagGroup tag_group;

    public ArtworkDashboardWebtoonRankingViewHolder(View itemView) {
        super(itemView);

        seriese_image = (ImageView) itemView.findViewById(R.id.seriese_image);
        title = (TextView) itemView.findViewById(R.id.title);
        nickname = (TextView) itemView.findViewById(R.id.nickname);
        profile_tag = (LinearLayout) itemView.findViewById(R.id.profile_tag);

        post_tag_group = (LinearLayout) itemView.findViewById(R.id.post_tag_layout);
        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);

    }

    public ImageView getSerieseImage(){return seriese_image;}
    public TextView getTitle(){return title;}
    public TextView getNickname(){return nickname;}
    public LinearLayout getProfileTag(){return profile_tag;}
    public LinearLayout getPostTagGroup(){return post_tag_group;}
    public TagGroup getTagGroup(){return tag_group;}

}
