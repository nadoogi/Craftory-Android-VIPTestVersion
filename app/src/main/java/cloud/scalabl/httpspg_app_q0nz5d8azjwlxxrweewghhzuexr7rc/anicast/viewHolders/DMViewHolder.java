package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 5. 24..
 */

public class DMViewHolder extends RecyclerView.ViewHolder {

    LinearLayout send_layout;
    LinearLayout response_layout;
    CircleImageView user_image;
    TextView username;
    ImageView send_image;
    TextView send_text;

    ImageView response_image;
    TextView response_text;

    RelativeLayout send_image_layout;
    RelativeLayout response_image_layout;

    TextView send_date;
    TextView response_date;

    public DMViewHolder(View dmView) {

        super(dmView);

        this.send_layout = (LinearLayout) dmView.findViewById(R.id.send_layout);
        this.response_layout = (LinearLayout) dmView.findViewById(R.id.response_layout);
        this.user_image = (CircleImageView) dmView.findViewById(R.id.user_image);
        this.username = (TextView) dmView.findViewById(R.id.username);
        this.send_image = (ImageView) dmView.findViewById(R.id.send_image);
        this.send_text = (TextView) dmView.findViewById(R.id.send_text);
        this.response_image = (ImageView) dmView.findViewById(R.id.response_image);
        this.response_text = (TextView) dmView.findViewById(R.id.response_text);

        this.send_image_layout = (RelativeLayout) dmView.findViewById(R.id.send_image_layout);
        this.response_image_layout = (RelativeLayout) dmView.findViewById(R.id.response_image_layout);

        this.send_date = (TextView) dmView.findViewById(R.id.send_date);
        this.response_date = (TextView) dmView.findViewById(R.id.response_date);

    }

    public LinearLayout getSendLayout(){return send_layout;}
    public LinearLayout getResponseLayout(){return response_layout;}
    public CircleImageView getUserImage(){return user_image;}
    public TextView getUserName(){return username;}
    public ImageView getSendImage(){return send_image;}
    public TextView getSendText(){return send_text;}
    public ImageView getResponseImage(){return response_image;}
    public TextView getResponseText(){return response_text;}

    public RelativeLayout getSendImageLayout(){return send_image_layout;}
    public RelativeLayout getResponseImageLayout(){return response_image_layout;}

    public TextView getSendDate(){return send_date;}
    public TextView getResponseDate(){return response_date;}

}
