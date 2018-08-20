package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 5. 24..
 */

public class CommercialPurchaseHolder extends RecyclerView.ViewHolder {

    TextView title;
    RelativeLayout purchase_button_layout;
    ImageView purchase_button;
    TextView purchase_price;

    public CommercialPurchaseHolder(View itemView) {

        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
        purchase_button = (ImageView) itemView.findViewById(R.id.purchase_button);
        purchase_button_layout = (RelativeLayout) itemView.findViewById(R.id.purchase_button_layout);
        purchase_price = (TextView) itemView.findViewById(R.id.purchase_price);

    }

    public TextView getTitle(){return title;}
    public RelativeLayout getPurchaseButtonLayout(){return purchase_button_layout;}
    public ImageView getPurchaseButton(){return purchase_button;}
    public TextView getPurchasePrice(){return purchase_price;}

}
