package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class MyRecentFollowViewHolder extends RecyclerView.ViewHolder {

    private BootstrapCircleThumbnail follow_image;
    private TextView follow_name;
    private LinearLayout user_info_layout;


    public MyRecentFollowViewHolder(View itemView) {
        super(itemView);

        follow_image = (BootstrapCircleThumbnail) itemView.findViewById(R.id.patron_image);
        follow_name = (TextView) itemView.findViewById(R.id.follow_name);
        user_info_layout = (LinearLayout) itemView.findViewById(R.id.user_info_layout);

    }

    public BootstrapCircleThumbnail getFollowImage(){return follow_image;}
    public TextView getFollowName(){return follow_name;}
    public LinearLayout getUserInfoLayout(){return user_info_layout;}

}
