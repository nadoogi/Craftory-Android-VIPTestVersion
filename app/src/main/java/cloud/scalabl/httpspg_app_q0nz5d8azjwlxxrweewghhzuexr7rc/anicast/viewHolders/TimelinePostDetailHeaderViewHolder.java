package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class TimelinePostDetailHeaderViewHolder extends RecyclerView.ViewHolder {

    CircleImageView writter_photo;
    TextView writter_name;
    TextView post_updated;
    TextView post_body;
    ImageView post_image;
    LinearLayout post_image_layout;


    LinearLayout post_like_button;
    ImageView like_icon;
    TextView like_count;

    LinearLayout share_button;
    ImageView share_icon;
    TextView share_count;

    LinearLayout user_info_layout;

    ImageView play_button;



    LinearLayout post_tag_layout;
    TagGroup tag_group;

    ImageView post_type_image;
    LinearLayout post_type_image_layout;

    LinearLayout follow_button;
    TextView follow_button_text;
    RecyclerView creator_artwork_list;
    LinearLayout move_to_creator;

    LinearLayout comment_layout;
    TextView pv_count;



    //patron

    private TextView title;


    public TimelinePostDetailHeaderViewHolder(View itemView) {
        super(itemView);

        writter_photo = (CircleImageView) itemView.findViewById(R.id.writter_photo);
        writter_name = (TextView) itemView.findViewById(R.id.writter_name);
        post_updated = (TextView) itemView.findViewById(R.id.post_updated);
        post_body = (TextView) itemView.findViewById(R.id.post_body);
        post_image = (ImageView) itemView.findViewById(R.id.patron_image);
        post_image_layout= (LinearLayout) itemView.findViewById(R.id.post_image_layout);


        post_like_button = (LinearLayout) itemView.findViewById(R.id.post_like_button);
        like_icon = (ImageView) itemView.findViewById(R.id.like_icon);
        like_count = (TextView) itemView.findViewById(R.id.like_count);

        share_button = (LinearLayout) itemView.findViewById(R.id.share_button);
        share_icon = (ImageView) itemView.findViewById(R.id.share_icon);
        share_count = (TextView) itemView.findViewById(R.id.share_count);


        user_info_layout = (LinearLayout) itemView.findViewById(R.id.user_info_layout);



        post_tag_layout = (LinearLayout) itemView.findViewById(R.id.post_tag_layout);
        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);

        post_type_image = (ImageView) itemView.findViewById(R.id.post_type_image);
        post_type_image_layout = (LinearLayout) itemView.findViewById(R.id.post_type_image_layout);

        follow_button = (LinearLayout) itemView.findViewById(R.id.follow_button);
        follow_button_text = (TextView) itemView.findViewById(R.id.follow_button_text);
        creator_artwork_list = (RecyclerView) itemView.findViewById(R.id.creator_artwork_list);
        move_to_creator = (LinearLayout) itemView.findViewById(R.id.move_to_creator);

        title = (TextView) itemView.findViewById(R.id.title);

        comment_layout = (LinearLayout) itemView.findViewById(R.id.comment_layout);

        pv_count = (TextView) itemView.findViewById(R.id.pv_count);




    }

    public CircleImageView getWritterPhoto(){return writter_photo;}
    public TextView getWritterName(){return writter_name;}
    public TextView getPostUpdated(){return post_updated;}
    public TextView getPostBody(){return post_body;}
    public ImageView getPostImage(){return post_image;}
    public LinearLayout getPostImageLayout(){return post_image_layout;}

    public LinearLayout getPostLikeButton(){return post_like_button; }
    public ImageView getLikeIcon(){return like_icon;}
    public TextView getLikeCount(){return like_count;}

    public LinearLayout getShareButton(){return share_button;}
    public ImageView getShareIcon(){return share_icon;}
    public TextView getShareCount(){return share_count;}


    public LinearLayout getUserInfoLayout(){return user_info_layout;}
    public ImageView getPlayButton(){return play_button;}
    public LinearLayout getPostTagLayout(){return post_tag_layout;}
    public TagGroup getTagGroup(){return tag_group;}
    public ImageView getPostTypeImage(){return post_type_image;}
    public LinearLayout getPostTypeImageLayout(){return post_type_image_layout;}
    public TextView getTitle(){return title;}

    public LinearLayout getFollowButton(){return follow_button;}
    public TextView getFollowButtonText(){return follow_button_text;}
    public RecyclerView getCreatorArtworkList(){return creator_artwork_list; }
    public LinearLayout getMoveToCreator(){return move_to_creator;}

    public LinearLayout getCommentLayout(){return comment_layout;}

    public TextView getPvCount(){return pv_count;}



}
