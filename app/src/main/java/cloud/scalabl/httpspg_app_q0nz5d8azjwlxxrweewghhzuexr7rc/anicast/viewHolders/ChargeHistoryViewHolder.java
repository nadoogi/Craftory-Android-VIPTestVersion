package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class ChargeHistoryViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView body;
    TextView date;
    TextView status;
    ImageView point_type_icon;

    public ChargeHistoryViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
        body = (TextView) itemView.findViewById(R.id.body);
        date = (TextView) itemView.findViewById(R.id.date);
        status = (TextView) itemView.findViewById(R.id.status);
        point_type_icon = (ImageView) itemView.findViewById(R.id.point_type_icon);

    }

    public TextView getTitle(){return title;}
    public TextView getBody(){return body;}
    public TextView getDate(){return date;}
    public TextView getStatus(){return status;}
    public ImageView getPointTypeIcon(){return point_type_icon;}


}
