package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class EventViewHolder extends RecyclerView.ViewHolder {

    ImageView patron_image;
    TextView join_status;
    TextView dday_text;


    public EventViewHolder(View itemView) {
        super(itemView);

        patron_image = (ImageView) itemView.findViewById(R.id.patron_image);
        join_status = (TextView) itemView.findViewById(R.id.join_status);
        dday_text = (TextView) itemView.findViewById(R.id.dday_text);

    }

    public ImageView getPatronImage(){return patron_image;}
    public TextView getJoinStatus(){return join_status;}
    public TextView getDdayText(){return dday_text;}

}
