package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseImageView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 5. 23..
 */

public class FundingMarketItemHolder extends RecyclerView.ViewHolder {

    private ImageView sales_item_image;
    private TextView title;
    private TextView price;
    private TextView dealer;

    private LinearLayout post_image_layout;

    public FundingMarketItemHolder(View itemView){

        super(itemView);

        this.sales_item_image = (ImageView) itemView.findViewById(R.id.sales_item_image);
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.price = (TextView) itemView.findViewById(R.id.price);
        this.dealer = (TextView) itemView.findViewById(R.id.dealer);
        this.post_image_layout = (LinearLayout) itemView.findViewById(R.id.post_image_layout);

    }

    public ImageView getSalesItemImage(){return sales_item_image;}
    public TextView getTitle(){return title;}
    public TextView getPrice(){return price;}
    public TextView getDealer(){return dealer;}
    public LinearLayout getPostImageLayout(){return post_image_layout;}


}
