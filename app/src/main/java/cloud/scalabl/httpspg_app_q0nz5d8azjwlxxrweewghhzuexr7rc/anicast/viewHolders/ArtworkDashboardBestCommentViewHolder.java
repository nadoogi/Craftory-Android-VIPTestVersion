package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class ArtworkDashboardBestCommentViewHolder extends RecyclerView.ViewHolder {

    ImageView seriese_image;
    TextView title;
    TextView comment;
    TextView nickname;
    LinearLayout profile_tag;
    CircleImageView profile_image;
    TextView writter_name;
    ImageView like_icon;
    TextView like_count;


    public ArtworkDashboardBestCommentViewHolder(View itemView) {
        super(itemView);

        seriese_image = (ImageView) itemView.findViewById(R.id.seriese_image);
        title = (TextView) itemView.findViewById(R.id.title);
        comment = (TextView) itemView.findViewById(R.id.comment);
        nickname = (TextView) itemView.findViewById(R.id.nickname);
        profile_tag = (LinearLayout) itemView.findViewById(R.id.profile_tag);

        profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
        writter_name = (TextView) itemView.findViewById(R.id.writter_name);

        like_icon = (ImageView) itemView.findViewById(R.id.like_icon);
        like_count = (TextView) itemView.findViewById(R.id.like_count);

    }

    public ImageView getSerieseImage(){return seriese_image;}
    public TextView getTitle(){return title;}
    public TextView getComment(){return comment;}
    public TextView getNickname(){return nickname;}
    public LinearLayout getProfileTag(){return profile_tag;}
    public CircleImageView getProfileImage(){return profile_image;}
    public TextView getWritterName(){return writter_name;}
    public ImageView getLikeIcon(){return like_icon;}
    public TextView getLikeCount(){return like_count;}

}
