package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class FundingItemDetailHeaderViewHolder extends RecyclerView.ViewHolder {

    ImageView item_image;
    LinearLayout item_image_layout;
    TextView name;
    TextView price;

    LinearLayout tag_layout;
    TagGroup tag_group;

    RecyclerView item_detail_list;
    CircleImageView dealer_photo;
    TextView dealer_name;
    RecyclerView dealer_item_list;
    LinearLayout move_to_dealer;
    LinearLayout dealer_info_layout;

    LinearLayout funding_button;


    public FundingItemDetailHeaderViewHolder(View itemView) {
        super(itemView);

        item_image = (ImageView) itemView.findViewById(R.id.item_image);
        item_image_layout = (LinearLayout) itemView.findViewById(R.id.item_image_layout);
        name = (TextView) itemView.findViewById(R.id.name);
        price = (TextView) itemView.findViewById(R.id.price);

        tag_layout = (LinearLayout) itemView.findViewById(R.id.tag_layout);
        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);
        item_detail_list = (RecyclerView) itemView.findViewById(R.id.item_detail_list);

        dealer_photo = (CircleImageView) itemView.findViewById(R.id.dealer_photo);
        dealer_name = (TextView) itemView.findViewById(R.id.dealer_name);
        dealer_item_list = (RecyclerView) itemView.findViewById(R.id.dealer_item_list);
        move_to_dealer = (LinearLayout) itemView.findViewById(R.id.move_to_dealer);
        dealer_info_layout = (LinearLayout) itemView.findViewById(R.id.dealer_info_layout);

        funding_button = (LinearLayout) itemView.findViewById(R.id.funding_button);

    }


    public ImageView getItemImage(){return item_image;}
    public LinearLayout getItemImageLayout(){return item_image_layout;}
    public TextView getName(){return name;}
    public TextView getPrice(){return price;}
    public LinearLayout getTagLayout(){return tag_layout;}
    public TagGroup getTagGroup(){return tag_group;}
    public RecyclerView getItemDetailList(){return item_detail_list;}
    public CircleImageView getDealerPhoto(){return dealer_photo;}
    public TextView getDealerName(){return dealer_name;}
    public RecyclerView getDealerItemList(){return dealer_item_list;}
    public LinearLayout getMoveToDealer(){return move_to_dealer;}
    public LinearLayout getDealerInfoLayout(){return dealer_info_layout;}
    public LinearLayout getFundingButton(){return funding_button;}

}
