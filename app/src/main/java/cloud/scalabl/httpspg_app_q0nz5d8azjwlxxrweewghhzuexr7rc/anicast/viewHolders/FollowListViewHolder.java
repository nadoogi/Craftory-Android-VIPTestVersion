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

public class FollowListViewHolder extends RecyclerView.ViewHolder {

    LinearLayout follow_info_layout;
    CircleImageView follow_image;
    TextView follow_name;
    LinearLayout follow_button_layout;
    LinearLayout follow_button;
    TextView follow_button_text;



    public FollowListViewHolder(View itemView) {
        super(itemView);

        follow_info_layout = (LinearLayout) itemView.findViewById(R.id.follow_info_layout);
        follow_image = (CircleImageView) itemView.findViewById(R.id.patron_image);
        follow_name = (TextView) itemView.findViewById(R.id.follow_name);
        follow_button_layout = (LinearLayout) itemView.findViewById(R.id.follow_button_layout);
        follow_button = (LinearLayout) itemView.findViewById(R.id.follow_button);
        follow_button_text = (TextView) itemView.findViewById(R.id.follow_button_text);

    }

    public LinearLayout getFollowInfoLayout(){return follow_info_layout;}
    public CircleImageView getFollowImage(){return follow_image;}
    public TextView getFollowName(){return follow_name;}
    public LinearLayout getFollowButtonLayout(){return follow_button_layout;}
    public LinearLayout getFollowButton(){return follow_button;}
    public TextView getFollowButtonText(){return follow_button_text;}



}
