package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 9. 11..
 */

public class NotiAlertViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView noti_image;
    public TextView action;
    public TextView message;
    public TextView noti_time;
    public LinearLayout noti_layout;


    public NotiAlertViewHolder(View cardView) {

        super(cardView);

        noti_image = (CircleImageView) cardView.findViewById(R.id.noti_image);
        action = (TextView) cardView.findViewById(R.id.action);
        message = (TextView) cardView.findViewById(R.id.message);
        noti_time = (TextView) cardView.findViewById(R.id.noti_time);
        noti_layout = (LinearLayout) cardView.findViewById(R.id.noti_layout);

    }

    public CircleImageView getNotiImage(){return noti_image;}
    public TextView getAction(){return action;}
    public TextView getMessage(){return message;}
    public TextView getNotiTime(){return noti_time;}
    public LinearLayout getNotiLayout(){return noti_layout;}



}
