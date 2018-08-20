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

public class TimelinePatronViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView writter_photo;
    private TextView writter_name;
    private TextView post_updated;
    private ImageView post_image;
    private LinearLayout user_info_layout;
    private LinearLayout post_tag_layout;
    private TagGroup tag_group;
    private TextView max_point;
    private TextView current_point;
    private RoundCornerProgressBar progressbar;
    private TextView title;
    private TextView body;

    private LinearLayout post_comment_button;
    private ImageView comment_icon;
    private TextView comment_count;

    private LinearLayout post_like_button;
    private ImageView like_icon;
    private TextView like_count;

    private LinearLayout share_button;
    private ImageView share_icon;
    private TextView share_count;
    private LinearLayout comment_sub_layout;
    private LinearLayout content_layout;
    private LinearLayout content_layout_border;

    private LinearLayout post_type_image_layout;
    private ImageView post_type_image;
    private TextView profit_share;
    private TextView dday_text;
    TextView total_profit_share;

    TextView achieve_ratio;
    TextView patron_count;
    TextView open_type;
    TextView reward_detail;

    LinearLayout seriese_icon_layout;
    ImageView seriese_icon;
    TextView seriese_text;

    TextView min_box;
    TextView patron_type;
    LinearLayout patron_user_button;

    LinearLayout delete_button;
    ImageView delete_icon;

    RecyclerView image_list;
    private TextView price;

    LinearLayout post_button;
    LinearLayout edit_button;
    LinearLayout manage_button;

    public TimelinePatronViewHolder(View itemView) {
        super(itemView);

        writter_photo = (CircleImageView) itemView.findViewById(R.id.writter_photo);
        writter_name = (TextView) itemView.findViewById(R.id.writter_name);
        post_updated = (TextView) itemView.findViewById(R.id.post_updated);
        post_image = (ImageView) itemView.findViewById(R.id.patron_image);
        user_info_layout = (LinearLayout) itemView.findViewById(R.id.user_info_layout);
        post_tag_layout = (LinearLayout) itemView.findViewById(R.id.post_tag_layout);
        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);
        max_point = (TextView) itemView.findViewById(R.id.max_point);
        progressbar = (RoundCornerProgressBar) itemView.findViewById(R.id.progressbar);
        title = (TextView) itemView.findViewById(R.id.title);
        body = (TextView) itemView.findViewById(R.id.post_body);

        post_comment_button = (LinearLayout) itemView.findViewById(R.id.post_comment_button);
        comment_icon = (ImageView) itemView.findViewById(R.id.comment_icon);
        comment_count = (TextView) itemView.findViewById(R.id.comment_count);

        post_like_button = (LinearLayout) itemView.findViewById(R.id.post_like_button);
        like_icon = (ImageView) itemView.findViewById(R.id.like_icon);
        like_count = (TextView) itemView.findViewById(R.id.like_count);

        share_button = (LinearLayout) itemView.findViewById(R.id.share_button);
        share_icon = (ImageView) itemView.findViewById(R.id.share_icon);
        share_count = (TextView) itemView.findViewById(R.id.share_count);
        comment_sub_layout = (LinearLayout) itemView.findViewById(R.id.comment_sub_layout);
        content_layout = (LinearLayout) itemView.findViewById(R.id.content_layout);

        post_type_image_layout = (LinearLayout) itemView.findViewById(R.id.post_type_image_layout);
        post_type_image = (ImageView) itemView.findViewById(R.id.post_type_image);
        //profit_share = (TextView) itemView.findViewById(R.id.profit_share);
        dday_text = (TextView) itemView.findViewById(R.id.dday_text);
        current_point = (TextView) itemView.findViewById(R.id.current_point);
        total_profit_share = (TextView) itemView.findViewById(R.id.total_profit_share);

        open_type = (TextView) itemView.findViewById(R.id.open_type);
        achieve_ratio = (TextView) itemView.findViewById(R.id.achieve_ratio);
        patron_count = (TextView) itemView.findViewById(R.id.patron_count);
        //reward_detail = (TextView) itemView.findViewById(R.id.reward_detail);

        seriese_icon_layout = (LinearLayout) itemView.findViewById(R.id.seriese_icon_layout);
        seriese_icon = (ImageView) itemView.findViewById(R.id.seriese_icon);
        seriese_text = (TextView) itemView.findViewById(R.id.seriese_text);
        min_box = (TextView) itemView.findViewById(R.id.min_box);
        patron_type = (TextView) itemView.findViewById(R.id.patron_type);
        patron_user_button = (LinearLayout) itemView.findViewById(R.id.patron_user_button);

        delete_button = (LinearLayout) itemView.findViewById(R.id.delete_button);
        delete_icon = (ImageView) itemView.findViewById(R.id.delete_icon);

        image_list = (RecyclerView) itemView.findViewById(R.id.image_list);

        price = (TextView) itemView.findViewById(R.id.price);

        post_button = (LinearLayout) itemView.findViewById(R.id.post_button);
        edit_button = (LinearLayout) itemView.findViewById(R.id.edit_button);
        manage_button = (LinearLayout) itemView.findViewById(R.id.manage_button);

    }

    public LinearLayout getPostTypeImageLayout(){return post_type_image_layout;}
    public ImageView getPostTypeImage(){return post_type_image;}
    public TextView getProfitShare(){return profit_share;}
    public TextView getDdayText(){return dday_text;}
    public TextView getCurrentPoint(){return current_point;}
    public TextView getTotalProfitShare(){return total_profit_share;}

    public CircleImageView getWritterPhoto(){return writter_photo;}
    public TextView getWritterName(){return writter_name;}
    public TextView getPostUpdated(){return post_updated;}
    public ImageView getPostImage(){return post_image;}
    public LinearLayout getUserInfoLayout(){return user_info_layout;}
    public LinearLayout getPostTagLayout(){return post_tag_layout;}
    public TagGroup getTagGroup(){return tag_group;}
    public TextView getMaxPoint(){return max_point;}
    public RoundCornerProgressBar getProgressbar(){return progressbar;}
    public TextView getTitle(){return title;}
    public TextView getBody(){return body;}

    public LinearLayout getPostCommentButton(){return post_comment_button;}
    public ImageView getCommentIcon(){return comment_icon;}
    public TextView getCommentCount(){return comment_count;}
    public LinearLayout getPostLikeButton(){return post_like_button; }
    public ImageView getLikeIcon(){return like_icon;}
    public TextView getLikeCount(){return like_count;}

    public LinearLayout getShareButton(){return share_button;}
    public ImageView getShareIcon(){return share_icon;}
    public TextView getShareCount(){return share_count;}
    public LinearLayout getCommentSubLayout(){return comment_sub_layout;}
    public LinearLayout getContentLayout(){return content_layout;}
    public LinearLayout getContentLayoutBorder(){return content_layout_border;}

    public TextView getOpenType(){return open_type;}
    public TextView getAchieveRatio(){return achieve_ratio;}
    public TextView getPatronCount(){return patron_count;}
    public TextView getRewardDetail(){return reward_detail;}

    public LinearLayout getSerieseIconLayout(){return seriese_icon_layout;}
    public ImageView getSerieseIcon(){return seriese_icon;}
    public TextView getSerieseText(){return seriese_text;}
    public TextView getMinBox(){return min_box;}
    public TextView getPatronType(){return patron_type;}
    public LinearLayout getPatronUserButton(){return patron_user_button;}

    public LinearLayout getDeleteButton(){return delete_button;}
    public ImageView getDeleteIcon(){return delete_icon;}

    public RecyclerView getImageList(){return image_list;}
    public TextView getPrice(){return price;}

    public LinearLayout getPostButton(){return post_button;}
    public LinearLayout getEditButton(){return edit_button;}
    public LinearLayout getManageButton(){return manage_button;}

}
