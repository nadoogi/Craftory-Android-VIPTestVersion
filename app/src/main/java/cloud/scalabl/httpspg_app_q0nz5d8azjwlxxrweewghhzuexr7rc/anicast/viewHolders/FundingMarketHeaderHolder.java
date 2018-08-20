package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import in.myinnos.awesomeimagepicker.models.Image;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by ssamkyu on 2017. 5. 23..
 */

public class FundingMarketHeaderHolder extends RecyclerView.ViewHolder {

    BannerSlider banner_slider;

    LinearLayout total_button;
    ImageView total_icon;
    TextView total_text;

    LinearLayout books_button;
    ImageView books_icon;
    TextView books_text;

    LinearLayout fashion_button;
    ImageView fashion_icon;
    TextView fashion_text;

    LinearLayout stationery_button;
    ImageView stationery_icon;
    TextView stationery_text;

    LinearLayout souvenir_button;
    ImageView souvenir_icon;
    TextView souvenir_text;

    LinearLayout badge_button;
    ImageView badge_icon;
    TextView badge_text;

    LinearLayout cushion_button;
    ImageView cushion_icon;
    TextView cushion_text;

    LinearLayout event_button;
    ImageView event_icon;
    TextView event_text;

    public FundingMarketHeaderHolder(View itemView){

        super(itemView);

        banner_slider = (BannerSlider) itemView.findViewById(R.id.banner_slider);

        total_button = (LinearLayout) itemView.findViewById(R.id.total_button);
        total_icon = (ImageView) itemView.findViewById(R.id.total_icon);
        total_text = (TextView) itemView.findViewById(R.id.total_text);

        books_button = (LinearLayout) itemView.findViewById(R.id.books_button);
        books_icon = (ImageView) itemView.findViewById(R.id.books_icon);
        books_text = (TextView) itemView.findViewById(R.id.books_text);

        fashion_button = (LinearLayout) itemView.findViewById(R.id.fashion_button);
        fashion_icon = (ImageView) itemView.findViewById(R.id.fashion_icon);
        fashion_text = (TextView) itemView.findViewById(R.id.fashion_text);

        stationery_button = (LinearLayout) itemView.findViewById(R.id.stationery_button);
        stationery_icon = (ImageView) itemView.findViewById(R.id.stationery_icon);
        stationery_text = (TextView) itemView.findViewById(R.id.stationery_text);

        souvenir_button = (LinearLayout) itemView.findViewById(R.id.souvenir_button);
        souvenir_icon = (ImageView) itemView.findViewById(R.id.souvenir_icon);
        souvenir_text = (TextView) itemView.findViewById(R.id.souvenir_text);

        badge_button = (LinearLayout) itemView.findViewById(R.id.badge_button);
        badge_icon = (ImageView) itemView.findViewById(R.id.badge_icon);
        badge_text = (TextView) itemView.findViewById(R.id.badge_text);

        cushion_button = (LinearLayout) itemView.findViewById(R.id.cushion_button);
        cushion_icon = (ImageView) itemView.findViewById(R.id.cushion_icon);
        cushion_text = (TextView) itemView.findViewById(R.id.cushion_text);

        event_button = (LinearLayout) itemView.findViewById(R.id.event_button);
        event_icon = (ImageView) itemView.findViewById(R.id.event_icon);
        event_text = (TextView) itemView.findViewById(R.id.event_text);

    }

    public BannerSlider getBannerSlider(){return banner_slider;}

    public LinearLayout getTotalButton(){return total_button;}
    public ImageView getTotalIcon(){return total_icon;}
    public TextView getTotalText(){return total_text;}

    public LinearLayout getBooksButton(){return books_button;}
    public ImageView getBooksIcon(){return books_icon;}
    public TextView getBooksText(){return books_text;}

    public LinearLayout getFashionButton(){return fashion_button;}
    public ImageView getFashionIcon(){return fashion_icon;}
    public TextView getFashionText(){return fashion_text;}

    public LinearLayout getStationeryButton(){return stationery_button;}
    public ImageView getStationeryIcon(){return stationery_icon;}
    public TextView getStationeryText(){return stationery_text;}

    public LinearLayout getSouvenirButton(){return souvenir_button;}
    public ImageView getSouvenirIcon(){return souvenir_icon;}
    public TextView getSouvenirText(){return souvenir_text;}

    public LinearLayout getBadgeButton(){return badge_button;}
    public ImageView getBadgeIcon(){return badge_icon;}
    public TextView getBadgeText(){return badge_text;}

    public LinearLayout getCushionButton(){return cushion_button;}
    public ImageView getCushionIcon(){return cushion_icon;}
    public TextView getCushionText(){return cushion_text;}

    public LinearLayout getEventButton(){return event_button;}
    public ImageView getEventIcon(){return event_icon;}
    public TextView getEventText(){return event_text;}


}
