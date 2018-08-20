package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class SerieseBottomViewHolder extends RecyclerView.ViewHolder {

    LinearLayout bottom_button;
    TextView bottom_button_text;

    public SerieseBottomViewHolder(View itemView) {
        super(itemView);

        bottom_button = (LinearLayout) itemView.findViewById(R.id.bottom_button);
        bottom_button_text = (TextView) itemView.findViewById(R.id.bottom_button_text);

    }

    public LinearLayout getBottomButton(){return bottom_button;}
    public TextView getBottomButtonText(){return bottom_button_text;}


}
