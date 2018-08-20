package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class TimelineItemShareViewHolder extends RecyclerView.ViewHolder {

    CircleImageView writter_photo;
    TextView writter_name;
    TextView post_updated;
    TextView post_body;
    ImageView post_image;
    LinearLayout post_image_layout;

    LinearLayout post_comment_button;
    ImageView comment_icon;
    TextView comment_count;

    LinearLayout post_like_button;
    ImageView like_icon;
    TextView like_count;

    LinearLayout record_button;
    ImageView record_icon;
    TextView record_count;

    LinearLayout request_button;
    ImageView request_icon;

    LinearLayout function_button;
    LinearLayout user_info_layout;


    LinearLayout share_button;

    ImageView share_icon;
    TextView share_count;
    LinearLayout comment_sub_layout;
    TextView target_user_name;

    private LinearLayout post_tag_layout;
    private TagGroup tag_group;

    private ImageView post_type_image;
    private LinearLayout post_type_image_layout;
    private LinearLayout option_button;
    private ImageView option_icon;

    LinearLayout follow_button;
    TextView follow_button_text;
    RecyclerView create_artwork_list;
    LinearLayout move_to_creator;
    //patron

    private TextView total_profit_share;
    private TextView dday_text;
    private TextView current_point;
    private TextView max_point;
    private RoundCornerProgressBar progressbar;
    private TextView title;

    LinearLayout delete_button;
    ImageView delete_icon;

    LinearLayout seriese_icon_layout;
    ImageView seriese_icon;
    TextView seriese_text;

    TextView post_detail;
    TextView last_post;

    TextView share_user_name;
    LinearLayout ad_badge_layout;

    LinearLayout share_layout;

    TextView target_post;

    public TimelineItemShareViewHolder(View itemView) {
        super(itemView);

        writter_photo = (CircleImageView) itemView.findViewById(R.id.writter_photo);
        writter_name = (TextView) itemView.findViewById(R.id.writter_name);
        post_updated = (TextView) itemView.findViewById(R.id.post_updated);
        post_body = (TextView) itemView.findViewById(R.id.post_body);
        post_image = (ImageView) itemView.findViewById(R.id.patron_image);
        post_image_layout= (LinearLayout) itemView.findViewById(R.id.post_image_layout);

        post_comment_button = (LinearLayout) itemView.findViewById(R.id.post_comment_button);
        comment_icon = (ImageView) itemView.findViewById(R.id.comment_icon);
        comment_count = (TextView) itemView.findViewById(R.id.comment_count);

        post_like_button = (LinearLayout) itemView.findViewById(R.id.post_like_button);
        like_icon = (ImageView) itemView.findViewById(R.id.like_icon);
        like_count = (TextView) itemView.findViewById(R.id.like_count);


        user_info_layout = (LinearLayout) itemView.findViewById(R.id.user_info_layout);


        share_button = (LinearLayout) itemView.findViewById(R.id.share_button);
        share_icon = (ImageView) itemView.findViewById(R.id.share_icon);
        share_count = (TextView) itemView.findViewById(R.id.share_count);
        comment_sub_layout = (LinearLayout) itemView.findViewById(R.id.comment_sub_layout);
        post_tag_layout = (LinearLayout) itemView.findViewById(R.id.post_tag_layout);
        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);

        post_type_image = (ImageView) itemView.findViewById(R.id.post_type_image);
        post_type_image_layout = (LinearLayout) itemView.findViewById(R.id.post_type_image_layout);
        seriese_icon_layout = (LinearLayout) itemView.findViewById(R.id.seriese_icon_layout);
        seriese_icon = (ImageView) itemView.findViewById(R.id.seriese_icon);
        seriese_text = (TextView) itemView.findViewById(R.id.seriese_text);

        option_button = (LinearLayout) itemView.findViewById(R.id.option_button);
        option_icon = (ImageView) itemView.findViewById(R.id.option_icon);

        total_profit_share = (TextView) itemView.findViewById(R.id.total_profit_share);
        dday_text = (TextView) itemView.findViewById(R.id.dday_text);
        current_point = (TextView) itemView.findViewById(R.id.current_point);
        max_point = (TextView) itemView.findViewById(R.id.max_point);
        progressbar = (RoundCornerProgressBar) itemView.findViewById(R.id.progressbar);
        title = (TextView) itemView.findViewById(R.id.title);

        delete_button = (LinearLayout) itemView.findViewById(R.id.delete_button);
        delete_icon = (ImageView) itemView.findViewById(R.id.delete_icon);

        post_detail = (TextView) itemView.findViewById(R.id.post_detail);
        last_post = (TextView) itemView.findViewById(R.id.last_post);

        share_user_name = (TextView) itemView.findViewById(R.id.share_user_name);
        ad_badge_layout = (LinearLayout) itemView.findViewById(R.id.ad_badge_layout);

        share_layout = (LinearLayout) itemView.findViewById(R.id.share_layout);
        target_user_name = (TextView) itemView.findViewById(R.id.target_user_name);
        target_post = (TextView) itemView.findViewById(R.id.target_post);

    }

    public LinearLayout getDeleteButton(){return delete_button;}
    public ImageView getDeleteIcon(){return delete_icon;}
    public CircleImageView getWritterPhoto(){return writter_photo;}
    public TextView getWritterName(){return writter_name;}
    public TextView getPostUpdated(){return post_updated;}
    public TextView getPostBody(){return post_body;}
    public ImageView getPostImage(){return post_image;}
    public LinearLayout getPostImageLayout(){return post_image_layout;}
    public LinearLayout getPostCommentButton(){return post_comment_button;}
    public ImageView getCommentIcon(){return comment_icon;}
    public TextView getCommentCount(){return comment_count;}
    public LinearLayout getPostLikeButton(){return post_like_button; }
    public ImageView getLikeIcon(){return like_icon;}
    public TextView getLikeCount(){return like_count;}
    public LinearLayout getRecordButton(){return record_button;}
    public ImageView getRecordIcon(){return record_icon;}
    public TextView getRecordCount(){return record_count;}
    public LinearLayout getRequestButton(){return request_button;}
    public ImageView getRequestIcon(){return request_icon;}
    public LinearLayout getUserInfoLayout(){return user_info_layout;}
    public LinearLayout getFunctionButton(){return function_button;}
    public LinearLayout getShareButton(){return share_button;}
    public ImageView getShareIcon(){return share_icon;}
    public TextView getShareCount(){return share_count;}
    public LinearLayout getCommentSubLayout(){return comment_sub_layout;}
    public LinearLayout getPostTagLayout(){return post_tag_layout;}
    public TagGroup getTagGroup(){return tag_group;}
    public ImageView getPostTypeImage(){return post_type_image;}
    public LinearLayout getPostTypeImageLayout(){return post_type_image_layout;}
    public LinearLayout getOptionButton(){return option_button;}
    public ImageView getOptionIcon(){return option_icon;}
    public TextView getTotalProfitShare(){return total_profit_share;}
    public TextView getDdayText(){return dday_text;}
    public TextView getCurrentPoint(){return current_point;}
    public TextView getMaxPoint(){return max_point;}
    public RoundCornerProgressBar getProgressbar(){return progressbar;}
    public TextView getTitle(){return title;}

    public LinearLayout getSerieseIconLayout(){return seriese_icon_layout;}
    public TextView getSerieseText(){return seriese_text;}
    public ImageView getSerieseIcon(){return seriese_icon;}

    public TextView getPostDetail(){return post_detail;}
    public TextView getLastPost(){return last_post;}
    public TextView getShareUserName(){return share_user_name;}
    public LinearLayout getAdBadgeLayout(){return ad_badge_layout;}

    public LinearLayout getShareLayout(){return share_layout;}
    public TextView getTargetUserName(){return target_user_name;}
    public TextView getTargetPost(){return target_post;}

}
