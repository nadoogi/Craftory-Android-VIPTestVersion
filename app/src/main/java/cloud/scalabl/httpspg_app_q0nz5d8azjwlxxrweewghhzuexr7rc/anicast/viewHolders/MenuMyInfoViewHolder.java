package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class MenuMyInfoViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView my_photo;
    private TextView username;
    private TextView email;
    private LinearLayout point_manage_button;
    private TextView current_point;
    private LinearLayout point_charge_button;

    private LinearLayout user_status_button;
    private TextView current_user_status;
    private LinearLayout myinfo_layout;


    public MenuMyInfoViewHolder(View itemView) {
        super(itemView);

        my_photo = (CircleImageView) itemView.findViewById(R.id.my_photo);
        username = (TextView) itemView.findViewById(R.id.username);
        email = (TextView) itemView.findViewById(R.id.email);
        point_manage_button = (LinearLayout) itemView.findViewById(R.id.point_manage_button);
        current_point = (TextView) itemView.findViewById(R.id.current_point);
        point_charge_button = (LinearLayout) itemView.findViewById(R.id.point_charge_button);
        user_status_button = (LinearLayout) itemView.findViewById(R.id.user_status_button);
        current_user_status = (TextView) itemView.findViewById(R.id.current_user_status);
        myinfo_layout = (LinearLayout) itemView.findViewById(R.id.myinfo_layout);

    }

    public CircleImageView getMyPhoto(){return my_photo;}
    public TextView getUsername(){return username;}
    public TextView getEmail(){return email;}
    public LinearLayout getPointManageButton(){return point_manage_button;}
    public LinearLayout getPointChargeButton(){return point_charge_button;}
    public TextView getCurrentPoint(){return current_point;}
    public LinearLayout getUserStatusButton(){return user_status_button;}
    public TextView getCurrentUserStatus(){return current_user_status;}
    public LinearLayout getMyInfoLayout(){return myinfo_layout;}


}
