package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 5. 23..
 */

public class SocialMessageViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView title;
    LinearLayout cheer_layout;
    CircleImageView user_image;
    TextView user_name;
    TextView cheer_message;
    TextView cheer_point;
    LinearLayout poke_layout;
    ImageView poke_image;
    TextView target;
    TextView action;
    ImageView black_gradient;
    TextView social_message_type;
    LinearLayout function_button;
    TextView function_button_text;
    LinearLayout request_status_layout;
    TextView patron_count;
    TextView patron_point;
    LinearLayout allow_button;

    LinearLayout message_layout;
    TextView target_username;
    TextView point_amount;

    public SocialMessageViewHolder(View postView){

        super(postView);

        this.image = (ImageView) itemView.findViewById(R.id.patron_image);
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.cheer_layout = (LinearLayout) itemView.findViewById(R.id.cheer_layout);
        this.user_image = (CircleImageView) itemView.findViewById(R.id.user_image);
        this.user_name = (TextView) itemView.findViewById(R.id.user_name);
        this.cheer_message = (TextView) itemView.findViewById(R.id.cheer_message);
        this.cheer_point = (TextView) itemView.findViewById(R.id.cheer_point);
        this.poke_layout = (LinearLayout) itemView.findViewById(R.id.poke_layout);
        this.poke_image = (ImageView) itemView.findViewById(R.id.poke_image);
        this.target = (TextView) itemView.findViewById(R.id.target);
        this.action = (TextView) itemView.findViewById(R.id.action);
        this.black_gradient = (ImageView) itemView.findViewById(R.id.black_gradient);
        this.social_message_type = (TextView) itemView.findViewById(R.id.social_message_type);
        this.function_button = (LinearLayout) itemView.findViewById(R.id.function_button);
        this.function_button_text = (TextView) itemView.findViewById(R.id.function_button_text);
        this.request_status_layout = (LinearLayout) itemView.findViewById(R.id.request_status_layout);
        this.patron_count = (TextView) itemView.findViewById(R.id.patron_count);
        this.patron_point = (TextView) itemView.findViewById(R.id.patron_point);
        this.allow_button = (LinearLayout) itemView.findViewById(R.id.allow_button);

        this.message_layout = (LinearLayout) itemView.findViewById(R.id.message_layout);
        this.target_username = (TextView) itemView.findViewById(R.id.target_username);
        this.point_amount = (TextView) itemView.findViewById(R.id.point_amount);

    }

    public ImageView getImage(){return image;}
    public TextView getTitle(){return title;}
    public LinearLayout getCheerLayout(){return cheer_layout;}
    public CircleImageView getUserImage(){return user_image;}
    public TextView getUserName(){return user_name;}
    public TextView getCheerMessage(){return cheer_message;}
    public TextView getCheerPoint(){return cheer_point;}
    public LinearLayout getPokeLayout(){return poke_layout;}
    public ImageView getPokeImage(){return poke_image;}
    public TextView getTarget(){return target;}
    public TextView getAction(){return action;}
    public ImageView getBlackGradient(){return black_gradient;}
    public TextView getSocialMessageType(){return social_message_type;}
    public LinearLayout getFunctionButton(){return function_button;}
    public TextView getFunctionButtonText(){return function_button_text;}
    public LinearLayout getRequestStatusLayout(){return request_status_layout;}
    public TextView getPatronCount(){return patron_count;}
    public TextView getPatronPoint(){return patron_point;}
    public LinearLayout getAllowButton(){return allow_button;}

    public LinearLayout getMessageLayout(){return message_layout;}
    public TextView getTargetUsername(){return target_username;}
    public TextView getPointAmount(){return point_amount;}




}
