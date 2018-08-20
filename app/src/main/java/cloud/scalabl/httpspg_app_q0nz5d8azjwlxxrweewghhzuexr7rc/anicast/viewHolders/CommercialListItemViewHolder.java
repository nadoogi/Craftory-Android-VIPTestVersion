package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class CommercialListItemViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView title;
    TextView post_updated;
    LinearLayout purchase;
    TextView price;
    LinearLayout charge_status_layout;
    ImageView commercial_status;
    TextView dday;

    LinearLayout price_layout;
    LinearLayout dday_layout;
    LinearLayout update_layout;

    LinearLayout current_page_layout;


    public CommercialListItemViewHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.patron_image);
        post_updated = (TextView) itemView.findViewById(R.id.post_updated);
        price = (TextView) itemView.findViewById(R.id.price);
        title = (TextView) itemView.findViewById(R.id.title);
        purchase = (LinearLayout) itemView.findViewById(R.id.purchase);
        charge_status_layout = (LinearLayout) itemView.findViewById(R.id.charge_status_layout);
        commercial_status = (ImageView) itemView.findViewById(R.id.commercial_status);
        dday = (TextView) itemView.findViewById(R.id.dday);

        price_layout = (LinearLayout) itemView.findViewById(R.id.price_layout);
        dday_layout = (LinearLayout) itemView.findViewById(R.id.dday_layout);
        update_layout = (LinearLayout) itemView.findViewById(R.id.update_layout);

        current_page_layout = (LinearLayout) itemView.findViewById(R.id.current_page_layout);

    }

    public ImageView getSerieseImage(){return image;}
    public TextView getUpdated(){return post_updated;}
    public TextView getPrice(){return price;}
    public TextView getTitle(){return title;}
    public LinearLayout getPurchase(){return purchase;}
    public LinearLayout getChargeStatusLayout(){return charge_status_layout;}
    public ImageView getCommercialStatus(){return commercial_status;}
    public TextView getDday(){return dday;}

    public LinearLayout getPriceLayout(){return price_layout;}
    public LinearLayout getDdayLayout(){return dday_layout;}
    public LinearLayout getUpdateLayout(){return update_layout;}
    public LinearLayout getCurrentPageLayout(){return current_page_layout;}


}
