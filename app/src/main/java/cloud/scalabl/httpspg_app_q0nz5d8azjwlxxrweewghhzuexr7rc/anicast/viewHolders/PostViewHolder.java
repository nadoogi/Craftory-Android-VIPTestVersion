package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseImageView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 5. 23..
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    private ParseImageView writter_photo;
    private TextView writter_name;
    private TextView channel_owner;
    private TextView post_updated;
    private TextView post_body;
    private TextView comment_count;
    private TextView like_count;
    private TextView dislike_count;
    private ImageView comment_icon;
    private ImageView like_icon;
    private ImageView dislike_icon;

    private TextView comment_text;
    private TextView like_text;
    private TextView dislike_text;

    private LinearLayout post_comment_button;
    private LinearLayout post_like_button;
    private LinearLayout post_dislike_button;

    private ParseImageView post_image;
    private LinearLayout post_image_layout;

    private LinearLayout comment_sub_layout;
    private LinearLayout post_tag_layout;
    private TagGroup tag_group;

    public PostViewHolder(View postView){

        super(postView);

        this.writter_photo = (ParseImageView) postView.findViewById(R.id.writter_photo);
        this.writter_name = (TextView) postView.findViewById(R.id.writter_name);
        this.channel_owner = (TextView) postView.findViewById(R.id.channel_owner);
        this.post_updated = (TextView) postView.findViewById(R.id.post_updated);
        this.post_body = (TextView) postView.findViewById(R.id.post_body);
        this.comment_count = (TextView) postView.findViewById(R.id.comment_count);
        this.like_count = (TextView) postView.findViewById(R.id.like_count);
        this.dislike_count = (TextView) postView.findViewById(R.id.dislike_count);
        this.comment_icon = (ImageView) postView.findViewById(R.id.comment_icon);
        this.like_icon = (ImageView) postView.findViewById(R.id.like_icon);
        this.dislike_icon = (ImageView) postView.findViewById(R.id.dislike_icon);

        this.post_comment_button = (LinearLayout) postView.findViewById(R.id.post_comment_button);
        this.post_like_button = (LinearLayout) postView.findViewById(R.id.post_like_button);
        this.post_dislike_button = (LinearLayout) postView.findViewById(R.id.post_dislike_button);

        this.comment_text = (TextView) postView.findViewById(R.id.comment_text);
        this.like_text = (TextView) postView.findViewById(R.id.like_text);
        this.dislike_text = (TextView) postView.findViewById(R.id.dislike_text);

        this.post_image = (ParseImageView) postView.findViewById(R.id.patron_image);
        this.post_image_layout = (LinearLayout) postView.findViewById(R.id.post_image_layout);
        this.post_tag_layout = (LinearLayout) postView.findViewById(R.id.post_tag_layout);
        this.tag_group = (TagGroup) postView.findViewById(R.id.tag_group);

    }

    public ParseImageView getWritterPhoto(){

        return writter_photo;

    }

    public TextView getWritterName(){

        return writter_name;
    }

    public TextView getChannelOwner(){

        return channel_owner;
    }

    public TextView getPostUpdated(){

        return post_updated;

    }
    public TextView getPostBody(){

        return post_body;
    }
    public TextView getCommentCount(){

        return comment_count;
    }

    public TextView getLikeCount(){

        return like_count;
    }
    public TextView getDislikeCount(){

        return dislike_count;
    }
    public ImageView getCommentIcon(){

        return comment_icon;

    }
    public ImageView getLikeIcon(){

        return like_icon;
    }

    public ImageView getDislikeIcon(){

        return dislike_icon;
    }

    public LinearLayout getPostCommentButton(){


        return post_comment_button;
    }

    public LinearLayout getPostLikeButton(){

        return post_like_button;
    }

    public LinearLayout getPostDislikeButton(){

        return post_dislike_button;

    }

    public ParseImageView getPostImage(){

        return post_image;
    }
    public LinearLayout getPostImageLayout(){

        return post_image_layout;
    }

    public TextView getCommentText(){

        return comment_text;
    }

    public TextView getLikeText(){

        return like_text;
    }

    public TextView getDislikeText(){

        return dislike_text;
    }

    public LinearLayout getCommentSubLayout(){return comment_sub_layout;}
    public LinearLayout getPostTagLayout(){return post_tag_layout;}
    public TagGroup getTagGroup(){return tag_group;}

}
