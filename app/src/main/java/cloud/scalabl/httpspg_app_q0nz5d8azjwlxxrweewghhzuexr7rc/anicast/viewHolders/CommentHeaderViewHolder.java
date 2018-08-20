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

public class CommentHeaderViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout comment_layout;
    private CircleImageView comment_photo;
    private TextView comment_name;
    private TextView comment_updated;
    private TextView comment_body;
    private LinearLayout comment_button;
    private ImageView comment_icon;
    private TextView comment_text;
    private TextView comment_count;

    private LinearLayout like_button;
    private ImageView like_icon;
    private TextView like_text;
    private TextView like_count;

    private LinearLayout dislike_button;
    private ImageView dislike_icon;
    private TextView dislike_text;
    private TextView dislike_count;

    LinearLayout best_layout;

    LinearLayout post_image_layout;
    ImageView comment_image;

    public CommentHeaderViewHolder(View itemView) {
        super(itemView);

        comment_layout = (LinearLayout) itemView.findViewById(R.id.addtional_button);
        comment_photo = (CircleImageView) itemView.findViewById(R.id.comment_photo);
        comment_name = (TextView) itemView.findViewById(R.id.comment_name);
        comment_updated = (TextView) itemView.findViewById(R.id.comment_updated);
        comment_body = (TextView) itemView.findViewById(R.id.comment_body);
        comment_button = (LinearLayout) itemView.findViewById(R.id.comment_reply);
        comment_icon = (ImageView) itemView.findViewById(R.id.comment_icon);
        comment_count = (TextView) itemView.findViewById(R.id.comment_replyCount);
        like_button = (LinearLayout) itemView.findViewById(R.id.comment_like);
        like_icon = (ImageView) itemView.findViewById(R.id.comment_like_icon);
        like_count = (TextView) itemView.findViewById(R.id.comment_likeCount);
        best_layout = (LinearLayout) itemView.findViewById(R.id.best_layout);

        post_image_layout = (LinearLayout) itemView.findViewById(R.id.post_image_layout);
        comment_image = (ImageView) itemView.findViewById(R.id.comment_image);

    }

    public LinearLayout getPostImageLayout(){return post_image_layout;}
    public ImageView getCommentImage(){return comment_image;}

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
    public LinearLayout getCommentButton(){

        return comment_button;
    }
    public ImageView getCommentIcon(){

        return comment_icon;
    }
    public TextView getCommentCount(){

        return comment_count;
    }
    public LinearLayout getLikeButton(){

        return like_button;
    }
    public ImageView getLikeIcon(){
        return like_icon;
    }
    public TextView getLikeCount(){
        return like_count;
    }
    public LinearLayout getBestLayout(){return best_layout;}


}
