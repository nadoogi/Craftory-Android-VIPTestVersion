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

public class SerieseMGListViewHolder extends RecyclerView.ViewHolder {

    ImageView seriese_image;
    TextView post_body;
    TextView post_updated;
    TextView price;
    TextView type;
    TextView free_date;
    TextView order;
    TextView open_status;


    public SerieseMGListViewHolder(View itemView) {
        super(itemView);

        seriese_image = (ImageView) itemView.findViewById(R.id.patron_image);
        post_body = (TextView) itemView.findViewById(R.id.post_body);
        post_updated = (TextView) itemView.findViewById(R.id.post_updated);
        price = (TextView) itemView.findViewById(R.id.price);
        type = (TextView) itemView.findViewById(R.id.type);
        free_date = (TextView) itemView.findViewById(R.id.free_date);
        order = (TextView) itemView.findViewById(R.id.order);
        open_status = (TextView) itemView.findViewById(R.id.open_status);

    }

    public ImageView getSerieseImage(){return seriese_image;}
    public TextView getPostBody(){return post_body;}
    public TextView getUpdated(){return post_updated;}
    public TextView getPrice(){return price;}
    public TextView getType(){return type;}
    public TextView getFreeDate(){return free_date;}
    public TextView getOrder(){return order;}
    public TextView getOpenStatus(){return open_status;}

}
