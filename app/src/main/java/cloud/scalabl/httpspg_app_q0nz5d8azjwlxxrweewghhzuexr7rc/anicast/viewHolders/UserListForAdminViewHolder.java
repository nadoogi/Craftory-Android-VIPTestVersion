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

public class UserListForAdminViewHolder extends RecyclerView.ViewHolder {

    LinearLayout user_info_layout;
    CircleImageView user_image;
    TextView user_name;
    TextView user_email;
    TextView current_box;



    public UserListForAdminViewHolder(View itemView) {
        super(itemView);

        user_info_layout = (LinearLayout) itemView.findViewById(R.id.user_info_layout);
        user_image = (CircleImageView) itemView.findViewById(R.id.user_image);
        user_name = (TextView) itemView.findViewById(R.id.user_name);
        user_email = (TextView) itemView.findViewById(R.id.user_email);
        current_box = (TextView) itemView.findViewById(R.id.current_box);

    }

    public LinearLayout getUserInfoLayout(){return user_info_layout;}
    public CircleImageView getUserImage(){return user_image;}
    public TextView getUserName(){return user_name;}
    public TextView getUserEmail(){return user_email;}
    public TextView getCurrentBox(){return current_box;}



}
