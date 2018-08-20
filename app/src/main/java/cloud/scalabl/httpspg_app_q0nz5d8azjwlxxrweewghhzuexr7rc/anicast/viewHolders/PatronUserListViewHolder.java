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

public class PatronUserListViewHolder extends RecyclerView.ViewHolder {

    LinearLayout patron_info_layout;
    CircleImageView patron_image;
    TextView patron_name;
    LinearLayout patron_button_layout;
    LinearLayout patron_button;
    TextView patron_button_text;
    TextView patron_box_amount;



    public PatronUserListViewHolder(View itemView) {
        super(itemView);

        patron_info_layout = (LinearLayout) itemView.findViewById(R.id.patron_info_layout);
        patron_image = (CircleImageView) itemView.findViewById(R.id.patron_image);
        patron_name = (TextView) itemView.findViewById(R.id.patron_name);
        patron_button_layout = (LinearLayout) itemView.findViewById(R.id.patron_button_layout);
        patron_button = (LinearLayout) itemView.findViewById(R.id.patron_button);
        patron_button_text = (TextView) itemView.findViewById(R.id.patron_button_text);
        patron_box_amount = (TextView) itemView.findViewById(R.id.patron_box_amount);

    }

    public LinearLayout getPatronInfoLayout(){return patron_info_layout;}
    public CircleImageView getPatronImage(){return patron_image;}
    public TextView getPatronName(){return patron_name;}
    public LinearLayout getPatronButtonLayout(){return patron_button_layout;}
    public LinearLayout getPatronButton(){return patron_button;}
    public TextView getPatronButtonText(){return patron_button_text;}
    public TextView getPatronBoxAmount(){return patron_box_amount;}



}
