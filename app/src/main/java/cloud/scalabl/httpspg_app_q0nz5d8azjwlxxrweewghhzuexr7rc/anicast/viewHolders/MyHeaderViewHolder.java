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

public class MyHeaderViewHolder extends RecyclerView.ViewHolder {

    CircleImageView writter_photo;
    TextView writter_name;
    TextView writter_email;
    TextView writter_body;
    TextView post_count;
    TextView follower_count;
    TextView following_count;
    TextView gift_point;
    LinearLayout following_button;
    LinearLayout point_manage_button;
    LinearLayout myinfo_setting_button;
    TextView following_text;
    TextView like_count;
    TextView current_point;
    LinearLayout myinfo_setting;

    public MyHeaderViewHolder(View itemView) {
        super(itemView);

        writter_photo = (CircleImageView) itemView.findViewById(R.id.writter_photo);
        writter_name = (TextView) itemView.findViewById(R.id.writter_name);
        writter_email = (TextView) itemView.findViewById(R.id.writter_email);
        writter_body = (TextView) itemView.findViewById(R.id.writter_body);
        post_count = (TextView) itemView.findViewById(R.id.post_count);
        follower_count = (TextView) itemView.findViewById(R.id.follower_count);
        following_count = (TextView) itemView.findViewById(R.id.following_count);
        gift_point = (TextView) itemView.findViewById(R.id.gift_point);
        current_point = (TextView) itemView.findViewById(R.id.current_point);
        following_button = (LinearLayout) itemView.findViewById(R.id.following_button);
        point_manage_button = (LinearLayout) itemView.findViewById(R.id.point_manage_button);
        myinfo_setting_button = (LinearLayout) itemView.findViewById(R.id.myinfo_setting_button);
        following_text = (TextView) itemView.findViewById(R.id.following_text);
        like_count = (TextView) itemView.findViewById(R.id.like_count);
        myinfo_setting = (LinearLayout) itemView.findViewById(R.id.myinfo_setting);


    }

    public CircleImageView getWritterPhoto(){return writter_photo;}
    public TextView getWritterName(){return writter_name;}
    public TextView getWritterEmail(){return writter_email;}
    public TextView getWritterBody(){return writter_body;}
    public TextView getPostCount(){return post_count;}
    public TextView getFollowerCount(){return follower_count;}
    public TextView getFollowingCount(){return following_count;}
    public TextView getCurrentPoint(){return current_point;}
    public TextView getGiftPoint(){return gift_point;}
    public LinearLayout getFollowingButton(){return following_button;}
    public LinearLayout getPointManageButton(){return point_manage_button;}
    public LinearLayout getMyinfoSettingButton(){return myinfo_setting_button;}
    public TextView getFollowingText(){return following_text;}
    public TextView getLikeCount(){return like_count;}
    public LinearLayout getMyInfoSetting(){return myinfo_setting; }


}
