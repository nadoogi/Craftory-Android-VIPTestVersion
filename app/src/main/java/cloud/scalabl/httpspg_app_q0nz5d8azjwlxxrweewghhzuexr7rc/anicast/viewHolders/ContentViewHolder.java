package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 5. 28..
 */

public class ContentViewHolder extends RecyclerView.ViewHolder{

    private ImageView card_image;
    private ImageView card_type;
    private LinearLayout category_layout;
    private LinearLayout channel_layout;
    private TextView category_text;
    private TextView channel_text;
    private LinearLayout price_layout;
    private TextView price_text;
    private TextView card_name;
    private Boolean purchase;
    private TextView like_count;
    private TextView comment_count;


    public ContentViewHolder(View cardView) {

        super(cardView);

        this.channel_text = (TextView) cardView.findViewById(R.id.channel_text);
        this.category_text = (TextView) cardView.findViewById(R.id.category_text);
        this.category_layout = (LinearLayout) cardView.findViewById(R.id.category_layout);
        this.channel_layout = (LinearLayout) cardView.findViewById(R.id.channel_layout);
        this.card_image = (ImageView) cardView.findViewById(R.id.card_image);
        this.card_type = (ImageView) cardView.findViewById(R.id.card_type);
        this.card_name = (TextView) cardView.findViewById(R.id.card_name);
        this.price_layout = (LinearLayout) cardView.findViewById(R.id.price_layout);
        this.price_text = (TextView) cardView.findViewById(R.id.price_text);
        this.purchase = false;
        this.like_count = (TextView) cardView.findViewById(R.id.like_count);
        this.comment_count = (TextView) cardView.findViewById(R.id.comment_count);

    }



    public ImageView getCardImage(){return card_image;}
    public ImageView getCardType(){return card_type;}
    public LinearLayout getCategoryLayout(){return category_layout;}
    public LinearLayout getChannelLayout(){return channel_layout;}
    public TextView getCategoryText(){return category_text;}
    public TextView getChannelText(){return channel_text;}
    public LinearLayout getPriceLayout(){return price_layout;}
    public TextView getPriceText(){return price_text;}
    public TextView getCardName(){return card_name;}
    public Boolean getPurchase(){return purchase;}
    public TextView getLikeCount(){return like_count;}
    public TextView getCommentCount(){return comment_count;}
    public int getViewType(){
        int viewType = 1;
        return viewType;}
}
