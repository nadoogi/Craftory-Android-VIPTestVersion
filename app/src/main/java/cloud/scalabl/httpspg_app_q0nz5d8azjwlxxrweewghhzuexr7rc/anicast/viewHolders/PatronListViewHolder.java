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

public class PatronListViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout patron_info_layout;
    private CircleImageView patron_image;
    private TextView patron_name;
    private TextView patron_point;

    public PatronListViewHolder(View itemView) {
        super(itemView);

        patron_info_layout = (LinearLayout) itemView.findViewById(R.id.patron_info_layout);
        patron_image = (CircleImageView) itemView.findViewById(R.id.patron_image);
        patron_name = (TextView) itemView.findViewById(R.id.patron_name);
        patron_point = (TextView) itemView.findViewById(R.id.patron_point);

    }

    public LinearLayout getPatronInfoLayout(){return patron_info_layout;}
    public CircleImageView getPatronImage(){return patron_image;}
    public TextView getPatronName(){return patron_name;}
    public TextView getPatronPoint(){return patron_point;}


}
