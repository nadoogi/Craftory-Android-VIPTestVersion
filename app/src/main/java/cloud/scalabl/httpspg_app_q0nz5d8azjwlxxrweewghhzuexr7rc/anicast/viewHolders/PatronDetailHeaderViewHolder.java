package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.bumptech.glide.RequestManager;
import com.parse.ParseObject;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class PatronDetailHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView current_point;
    TextView open_type;
    TextView max_point_onprogress;
    RoundCornerProgressBar progressbar;
    TextView total_profit_share;
    TextView profit_share_ratio;
    TextView current_point_oninfo;
    TextView current_point_onprogress;
    LinearLayout patron_manager_layout;

    TextView patron_type;
    TextView min_box;
    LinearLayout user_info_layout;

    CircleImageView user_image_1;
    CircleImageView user_image_2;
    CircleImageView user_image_3;

    TextView user_name_1;
    TextView user_name_2;
    TextView user_name_3;

    TextView post_date_1;
    TextView post_date_2;
    TextView post_date_3;

    TextView comment_body_1;
    TextView comment_body_2;
    TextView comment_body_3;


    LinearLayout comment_1;
    LinearLayout comment_2;
    LinearLayout comment_3;

    LinearLayout no_comment;

    LinearLayout comment_layout;

    EditText comment_input;

    LinearLayout patron_stop;
    LinearLayout patron_result_summit;
    LinearLayout patron_withdraw;
    LinearLayout patron_cancel;
    LinearLayout patron_user_button;

    TextView patron_result_summit_text;
    TextView patron_withdraw_text;

    //header
    TextView patron_title;
    ImageView patron_image;
    TextView patron_body;
    TextView max_point;

    CircleImageView writter_photo;
    TextView writter_name;
    TextView patron_updated;
    LinearLayout like_button;
    ImageView like_icon;
    TextView like_count;
    ImageView comment_icon;
    TextView comment_count;
    ImageView share_icon;

    TagGroup tag_group;

    TextView achieve_ratio;
    TextView patron_count;


    TextView patron_detail_info;
    LinearLayout patron_detail_info_edit;

    LinearLayout share_button;
    TextView share_count;
    LinearLayout patron_tag_layout;

    TextView reward_detail;
    TextView price;

    ImageView item_image;
    TextView item_name;

    RecyclerView detail_info_list;

    LinearLayout join_button;
    TextView current_box;

    ImageView immoticon1;
    ImageView immoticon2;
    ImageView immoticon3;

    TextView pv_count;

    public PatronDetailHeaderViewHolder(View itemView) {
        super(itemView);

        min_box = (TextView) itemView.findViewById(R.id.min_box);
        patron_type = (TextView) itemView.findViewById(R.id.patron_type);
        open_type = (TextView) itemView.findViewById(R.id.open_type);
        patron_user_button = (LinearLayout) itemView.findViewById(R.id.patron_user_button);

        patron_title = (TextView) itemView.findViewById(R.id.patron_title);
        patron_image = (ImageView) itemView.findViewById(R.id.patron_image);
        patron_body = (TextView) itemView.findViewById(R.id.patron_body);
        max_point = (TextView) itemView.findViewById(R.id.max_point);
        max_point_onprogress = (TextView) itemView.findViewById(R.id.max_point_onprogress);
        writter_photo = (CircleImageView) itemView.findViewById(R.id.writter_photo);
        writter_name = (TextView) itemView.findViewById(R.id.writter_name);
        patron_updated = (TextView) itemView.findViewById(R.id.patron_updated);
        like_button = (LinearLayout) itemView.findViewById(R.id.like_button);
        like_icon = (ImageView) itemView.findViewById(R.id.like_icon);
        like_count = (TextView) itemView.findViewById(R.id.like_count);
        comment_icon = (ImageView) itemView.findViewById(R.id.comment_icon);
        comment_count = (TextView) itemView.findViewById(R.id.comment_count);
        share_button = (LinearLayout) itemView.findViewById(R.id.share_button);
        share_count = (TextView) itemView.findViewById(R.id.share_count);
        share_icon = (ImageView) itemView.findViewById(R.id.share_icon);

        patron_tag_layout = (LinearLayout) itemView.findViewById(R.id.patron_tag_layout);
        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);
        achieve_ratio = (TextView) itemView.findViewById(R.id.achieve_ratio_oninfo);
        patron_count = (TextView) itemView.findViewById(R.id.patron_count_oninfo);
        current_point_oninfo = (TextView) itemView.findViewById(R.id.current_point_oninfo);
        current_point_onprogress = (TextView) itemView.findViewById(R.id.current_point_onprogress);

        patron_detail_info = (TextView) itemView.findViewById(R.id.patron_detail_info);
        patron_detail_info_edit = (LinearLayout) itemView.findViewById(R.id.patron_detail_info_edit);
        patron_manager_layout = (LinearLayout) itemView.findViewById(R.id.patron_manager_layout);

        progressbar = (RoundCornerProgressBar) itemView.findViewById(R.id.progressbar);

        user_info_layout = (LinearLayout) itemView.findViewById(R.id.user_info_layout);

        user_image_1 = (CircleImageView) itemView.findViewById(R.id.user_image_1);
        user_image_2 = (CircleImageView) itemView.findViewById(R.id.user_image_2);
        user_image_3 = (CircleImageView) itemView.findViewById(R.id.user_image_3);

        user_name_1 = (TextView) itemView.findViewById(R.id.user_name_1);
        user_name_2 = (TextView) itemView.findViewById(R.id.user_name_2);
        user_name_3 = (TextView) itemView.findViewById(R.id.user_name_3);

        post_date_1 = (TextView) itemView.findViewById(R.id.post_date_1);
        post_date_2 = (TextView) itemView.findViewById(R.id.post_date_2);
        post_date_3 = (TextView) itemView.findViewById(R.id.post_date_3);

        comment_body_1 = (TextView) itemView.findViewById(R.id.comment_body_1);
        comment_body_2 = (TextView) itemView.findViewById(R.id.comment_body_2);
        comment_body_3 = (TextView) itemView.findViewById(R.id.comment_body_3);

        comment_1 = (LinearLayout) itemView.findViewById(R.id.comment_1);
        comment_2 = (LinearLayout) itemView.findViewById(R.id.comment_2);
        comment_3 = (LinearLayout) itemView.findViewById(R.id.comment_3);

        no_comment = (LinearLayout) itemView.findViewById(R.id.no_comment);

        comment_layout = (LinearLayout) itemView.findViewById(R.id.comment_layout);
        comment_input = (EditText) itemView.findViewById(R.id.comment_input);

        patron_stop = (LinearLayout) itemView.findViewById(R.id.patron_stop);
        patron_result_summit = (LinearLayout) itemView.findViewById(R.id.patron_result_summit);
        patron_result_summit_text =(TextView) itemView.findViewById(R.id.patron_result_summit_text);
        patron_cancel = (LinearLayout) itemView.findViewById(R.id.patron_cancel);
        patron_withdraw = (LinearLayout) itemView.findViewById(R.id.patron_withdraw);
        patron_withdraw_text =(TextView) itemView.findViewById(R.id.patron_withdraw_text);
        price = (TextView) itemView.findViewById(R.id.price);

        item_image = (ImageView) itemView.findViewById(R.id.item_image);
        item_name = (TextView) itemView.findViewById(R.id.item_name);

        detail_info_list = (RecyclerView) itemView.findViewById(R.id.detail_info_list);

        join_button = (LinearLayout) itemView.findViewById(R.id.join_button);
        current_box = (TextView) itemView.findViewById(R.id.current_box);

        immoticon1 = (ImageView) itemView.findViewById(R.id.immoticon1);
        immoticon2 = (ImageView) itemView.findViewById(R.id.immoticon2);
        immoticon3 = (ImageView) itemView.findViewById(R.id.immoticon3);

        pv_count = (TextView) itemView.findViewById(R.id.pv_count);

    }

    public LinearLayout getJoinButton(){return join_button;}
    public TextView getCurrentBox(){return current_box;}

    public TextView getPatronTitle(){return patron_title;}
    public ImageView getPatronImage(){return patron_image;}
    public TextView getPatronBody(){return patron_body;}
    public TextView getMaxPoint(){return max_point;}
    public CircleImageView getWritterPhoto(){return writter_photo;}
    public TextView getWritterName(){return writter_name;}
    public TextView getPatronUpdated(){return patron_updated;}
    public LinearLayout getLikeButton(){return like_button;}
    public ImageView getLikeIcon(){return like_icon;}
    public TextView getLikeCount(){return like_count;}
    public ImageView getCommentIcon(){return comment_icon;}
    public TextView getCommentCount(){return comment_count;}
    public LinearLayout getShareButton(){return share_button;}
    public ImageView getShareIcon(){return share_icon;}
    public LinearLayout getPatronTagLayout(){return patron_tag_layout;}
    public TagGroup getTagGroup(){return tag_group;}
    public TextView getTotalProfitShare(){return total_profit_share;}
    public TextView getProfitShareRatio(){return profit_share_ratio;}
    public TextView getAchieveRatio(){return achieve_ratio;}
    public TextView getPatronCount(){return patron_count;}
    public TextView getCurrentPoint(){return current_point;}
    public TextView getPartronDetailInfo(){return patron_detail_info;}
    public LinearLayout getPatronDetailInfoEdit(){return patron_detail_info_edit;}

    public TextView getOpenType(){return open_type;}
    public TextView getMaxPointOnprogress(){return max_point_onprogress;}
    public RoundCornerProgressBar getProgressbar(){return progressbar;}
    public TextView getCurrentPointOnInfo(){return current_point_oninfo;}
    public TextView getCurrentPointOnProgress(){return current_point_onprogress;}
    public LinearLayout getPatronManagerLayout(){return patron_manager_layout;}
    public TextView getPatronType(){return patron_type;}
    public TextView getMinBox(){return min_box;}
    public LinearLayout getUserInfoLayout(){return user_info_layout;}
    public CircleImageView getUserImage1(){return user_image_1;}
    public CircleImageView getUserImage2(){return user_image_2;}
    public CircleImageView getUserImage3(){return user_image_3;}
    public TextView getUserName1(){return user_name_1;}
    public TextView getUserName2(){return user_name_2;}
    public TextView getUserName3(){return user_name_3;}
    public TextView getPostDate1(){return post_date_1;}
    public TextView getPostDate2(){return post_date_2;}
    public TextView getPostDate3(){return post_date_3;}
    public TextView getCommentBody1(){return comment_body_1;}
    public TextView getCommentBody2(){return comment_body_2;}
    public TextView getCommentBody3(){return comment_body_3;}
    public LinearLayout getComment1(){return comment_1;}
    public LinearLayout getComment2(){return comment_2;}
    public LinearLayout getComment3(){return comment_3;}
    public LinearLayout getNoComment(){return no_comment;}
    public LinearLayout getCommentLayout(){return comment_layout;}
    public EditText getCommentInput(){return comment_input;}
    public LinearLayout getPatronStop(){return patron_stop;}
    public LinearLayout getPatronResultSummit(){return patron_result_summit;}
    public LinearLayout getPatronWithdraw(){return patron_withdraw;}
    public LinearLayout getPatronCancel(){return patron_cancel;}
    public LinearLayout getPatronUserButton(){return patron_user_button;}
    public TextView getPatronResultSummitText(){return patron_result_summit_text;}
    public TextView getPatronWithdrawText(){return patron_withdraw_text;}

    public TextView getPrice(){return price;}
    public ImageView getItemImage(){return item_image;}
    public TextView getItemName(){return item_name;}

    public RecyclerView getDetailInfoList(){return detail_info_list;}

    public TextView getShareCount(){return share_count;}

    public ImageView getImmoticon1(){return immoticon1;}
    public ImageView getImmoticon2(){return immoticon2;}
    public ImageView getImmoticon3(){return immoticon3;}

    public TextView getPvCount(){return pv_count;}

}
