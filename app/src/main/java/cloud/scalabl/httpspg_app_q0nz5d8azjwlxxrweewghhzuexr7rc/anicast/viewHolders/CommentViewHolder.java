package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 5. 24..
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView comment_photo;
    private TextView comment_name;
    private TextView comment_updated;
    private TextView comment_body;
    private LinearLayout comment_body_layout;
    private LinearLayout thumbnail_layout;

    private LinearLayout comment_like;
    private TextView comment_likeCount;

    private LinearLayout comment_dislike;
    private TextView comment_dislikeCount;
    private Boolean likeButtonStatus;
    private Boolean dislikeButtonStatus;
    private LinearLayout comment_reply;
    private TextView comment_replyCount;

    private ImageView comment_icon;
    private ImageView comment_like_icon;
    private ImageView comment_dislike_icon;

    private LinearLayout delete_button;

    private LinearLayout comment_layout;

    private LinearLayout best_layout;
    private ImageView immoticon;


    public CommentViewHolder(View commentView) {

        super(commentView);

        this.comment_photo = (CircleImageView) commentView.findViewById(R.id.comment_photo);
        this.comment_name = (TextView) commentView.findViewById(R.id.comment_name);
        this.comment_updated = (TextView) commentView.findViewById(R.id.comment_updated);
        this.comment_body = (TextView) commentView.findViewById(R.id.comment_body);
        this.comment_like = (LinearLayout) commentView.findViewById(R.id.comment_like);
        this.comment_likeCount = (TextView) commentView.findViewById(R.id.comment_likeCount);
        this.comment_dislike = (LinearLayout) commentView.findViewById(R.id.comment_dislike);
        this.comment_dislikeCount = (TextView) commentView.findViewById(R.id.comment_dislikeCount);

        this.comment_reply = (LinearLayout) commentView.findViewById(R.id.comment_reply);
        this.comment_replyCount = (TextView) commentView.findViewById(R.id.comment_replyCount);

        this.comment_icon = (ImageView) commentView.findViewById(R.id.comment_icon);
        this.comment_like_icon = (ImageView) commentView.findViewById(R.id.comment_like_icon);
        this.comment_dislike_icon = (ImageView) commentView.findViewById(R.id.comment_dislike_icon);

        this.comment_body_layout = (LinearLayout) commentView.findViewById(R.id.comment_body_layout);

        this.likeButtonStatus = false;
        this.dislikeButtonStatus = false;

        this.delete_button = (LinearLayout) commentView.findViewById(R.id.delete_button);
        this.comment_layout = (LinearLayout) commentView.findViewById(R.id.addtional_button);

        this.best_layout = (LinearLayout) commentView.findViewById(R.id.best_layout);

        this.thumbnail_layout = (LinearLayout) commentView.findViewById(R.id.thumbnail_layout);
        this.immoticon = (ImageView) commentView.findViewById(R.id.immoticon);
    }

    public ImageView getImmoticon(){return immoticon;}
    public LinearLayout getThumbnailLayout(){return thumbnail_layout;}

    public LinearLayout getCommentLayout(){

        return comment_layout;
    }

    public CircleImageView getCommentPhoto(){

        return comment_photo;
    }

    public TextView getCommentName(){

        return comment_name;
    }
    public TextView getCommentUpdated(){

        return comment_updated;
    }
    public TextView getCommentBody(){

        return comment_body;
    }
    public LinearLayout getCommentBodyLayout(){

        return comment_body_layout;
    }
    public LinearLayout getCommentLike(){

        return comment_like;
    }
    public TextView getCommentLikeCount(){

        return comment_likeCount;
    }
    public LinearLayout getCommentDislike(){
        return comment_dislike;
    }
    public TextView getCommentDislikeCount(){

        return comment_dislikeCount;
    }
    public Boolean getLikeButtonStatus(){

        return likeButtonStatus;
    }
    public Boolean getDislikeButtonStatus(){

        return dislikeButtonStatus;
    }
    public LinearLayout getCommentReply(){

        return comment_reply;
    }
    public TextView getCommentReplyCount(){
        return comment_replyCount;
    }
    public ImageView getCommentIcon(){

        return comment_icon;
    }
    public ImageView getCommentLikeIcon(){

        return comment_like_icon;
    }
    public ImageView getCommentDislikeIcon(){
        return comment_dislike_icon;
    }
    public LinearLayout getDeleteButton(){
        return delete_button;
    }
    public LinearLayout getBestLayout(){return best_layout;}
}
