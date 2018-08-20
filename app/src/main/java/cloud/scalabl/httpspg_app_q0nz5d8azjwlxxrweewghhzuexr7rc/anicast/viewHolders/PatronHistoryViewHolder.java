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

public class PatronHistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView order_num;
    private TextView to_name;
    private TextView address;
    private TextView phone;
    private TextView message;
    private TextView post_title;

    private LinearLayout button_layout;
    private LinearLayout edit_button;
    private LinearLayout cancel_button;

    private ImageView image;

    public PatronHistoryViewHolder(View itemView){

        super(itemView);

        this.order_num = (TextView) itemView.findViewById(R.id.order_num);
        this.to_name = (TextView) itemView.findViewById(R.id.to_name);
        this.address = (TextView) itemView.findViewById(R.id.address);
        this.phone = (TextView) itemView.findViewById(R.id.phone);
        this.message = (TextView) itemView.findViewById(R.id.message);
        this.post_title = (TextView) itemView.findViewById(R.id.post_title);

        this.button_layout = (LinearLayout) itemView.findViewById(R.id.button_layout);
        this.edit_button = (LinearLayout) itemView.findViewById(R.id.edit_button);
        this.cancel_button = (LinearLayout) itemView.findViewById(R.id.cancel_button);
        this.image = (ImageView) itemView.findViewById(R.id.image);

    }

    public TextView getOrderNum(){return order_num;}
    public TextView getToName(){return to_name;}
    public TextView getAddress(){return address;}
    public TextView getPhone(){return phone;}
    public TextView getMessage(){return message;}
    public TextView getPostTitle(){return post_title;}

    public LinearLayout getButtonLayout(){return button_layout;}
    public LinearLayout getEditButton(){return edit_button;}
    public LinearLayout getCancelButton(){return cancel_button;}
    public ImageView getImage(){return image;}

}
