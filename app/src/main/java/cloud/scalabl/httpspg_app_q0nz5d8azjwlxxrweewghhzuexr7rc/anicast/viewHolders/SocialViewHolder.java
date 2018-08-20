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

public class SocialViewHolder extends RecyclerView.ViewHolder {

    //cheer
    TextView username_cheer;
    TextView username_cheer_2;
    TextView body_cheer;
    TextView send_box_amount;
    CircleImageView user_image_cheer;
    LinearLayout cheer_layout;
    LinearLayout accept_cheer_button;
    TextView accept_cheer_button_text;

    LinearLayout comment_cheer_button;
    TextView comment_cheer_count;

    //creator_comment
    LinearLayout creator_comment_layout;
    TextView creator_comment_text;

    //poke
    TextView username_poke;
    CircleImageView user_image_poke;
    LinearLayout poke_layout;
    ImageView image_poke;
    TextView action_poke;
    LinearLayout poke_button;
    LinearLayout comment_poke_button;
    TextView comment_poke_count;
    TextView poke_button_text;

    //request
    TextView username_request;
    CircleImageView user_image_request;
    LinearLayout request_layout;
    ImageView image_request;
    TextView title_request;
    TextView body_request;
    LinearLayout request_detail_button;
    LinearLayout request_accept_button;
    LinearLayout comment_request_button;
    TextView comment_request_count;
    LinearLayout request_button_layout;

    //message
    TextView username_message;
    CircleImageView user_image_message;
    LinearLayout message_layout;
    ImageView image_message;
    TextView message_text;



    public SocialViewHolder(View socialView){

        super(socialView);

        username_cheer = (TextView) socialView.findViewById(R.id.username_cheer);
        username_cheer_2 = (TextView) socialView.findViewById(R.id.username_cheer_2);
        body_cheer = (TextView) socialView.findViewById(R.id.body_cheer);
        send_box_amount = (TextView) socialView.findViewById(R.id.send_box_amount);
        user_image_cheer = (CircleImageView) socialView.findViewById(R.id.user_image_cheer);
        cheer_layout = (LinearLayout) socialView.findViewById(R.id.cheer_layout);
        accept_cheer_button = (LinearLayout) socialView.findViewById(R.id.accept_cheer_button);
        accept_cheer_button_text = (TextView) socialView.findViewById(R.id.accept_cheer_button_text);
        comment_cheer_button = (LinearLayout) socialView.findViewById(R.id.comment_cheer_button);
        comment_cheer_count = (TextView) socialView.findViewById(R.id.comment_cheer_count);


        creator_comment_layout = (LinearLayout) socialView.findViewById(R.id.creator_comment_layout);
        creator_comment_text = (TextView) socialView.findViewById(R.id.creator_comment_text);


        username_poke = (TextView) socialView.findViewById(R.id.username_poke);
        user_image_poke = (CircleImageView) socialView.findViewById(R.id.user_image_poke);
        poke_layout = (LinearLayout) socialView.findViewById(R.id.poke_layout);
        image_poke = (ImageView) socialView.findViewById(R.id.image_poke);
        action_poke = (TextView) socialView.findViewById(R.id.action_poke);
        poke_button = (LinearLayout) socialView.findViewById(R.id.poke_button);
        comment_poke_button = (LinearLayout) socialView.findViewById(R.id.comment_poke_button);
        comment_poke_count = (TextView) socialView.findViewById(R.id.comment_poke_count);
        poke_button_text = (TextView) socialView.findViewById(R.id.poke_button_text);

        username_request = (TextView) socialView.findViewById(R.id.username_request);
        user_image_request = (CircleImageView) socialView.findViewById(R.id.user_image_request);
        request_layout = (LinearLayout) socialView.findViewById(R.id.request_layout);
        image_request = (ImageView) socialView.findViewById(R.id.image_request);
        title_request = (TextView) socialView.findViewById(R.id.title_request);
        body_request = (TextView) socialView.findViewById(R.id.body_request);
        request_detail_button = (LinearLayout) socialView.findViewById(R.id.request_detail_button);
        request_accept_button = (LinearLayout) socialView.findViewById(R.id.request_accept_button);
        comment_request_button = (LinearLayout) socialView.findViewById(R.id.comment_request_button);
        comment_request_count = (TextView) socialView.findViewById(R.id.comment_request_count);
        request_button_layout = (LinearLayout) socialView.findViewById(R.id.request_button_layout);

        username_message = (TextView) socialView.findViewById(R.id.username_message);
        user_image_message = (CircleImageView) socialView.findViewById(R.id.user_image_message);
        message_layout = (LinearLayout) socialView.findViewById(R.id.message_layout);
        image_message = (ImageView) socialView.findViewById(R.id.image_message);
        message_text = (TextView) socialView.findViewById(R.id.message_text);


    }

    public TextView getUsernameCheer(){return username_cheer;}
    public TextView getUsernameCheer2(){return username_cheer_2;}
    public TextView getBodyCheer(){return body_cheer;}
    public TextView getSendBoXAmount(){return send_box_amount;}
    public CircleImageView getUserImageCheer(){return user_image_cheer;}
    public LinearLayout getCheerLayout(){return cheer_layout;}
    public LinearLayout getAcceptCheerButton(){return accept_cheer_button;}
    public TextView getAcceptCheerButtonText(){return accept_cheer_button_text;}
    public LinearLayout getCommentCheerButton(){return comment_cheer_button;}
    public TextView getCommentCheerCount(){return comment_cheer_count;}
    public LinearLayout getCreatorCommentLayout(){return creator_comment_layout;}
    public TextView getCreatorCommentText(){return creator_comment_text;}
    public TextView getUsernamePoke(){return username_poke;}
    public CircleImageView getUserImagePoke(){return user_image_poke;}
    public LinearLayout getPokeLayout(){return poke_layout;}
    public ImageView getImagePoke(){return image_poke;}
    public TextView getActionPoke(){return action_poke;}
    public LinearLayout getPokeButton(){return poke_button;}
    public LinearLayout getCommentPokeButton(){return comment_poke_button;}
    public TextView getCommentPokeCount(){return comment_poke_count;}
    public TextView getUsernameRequest(){return username_request;}
    public CircleImageView getUserImageRequest(){return user_image_request;}
    public LinearLayout getRequestLayout(){return request_layout;}
    public ImageView getImageRequest(){return image_request;}
    public TextView getTitleRequest(){return title_request;}
    public TextView getBodyRequest(){return body_request;}
    public LinearLayout getRequestDetailButton(){return request_detail_button;}
    public LinearLayout getRequestAcceptButton(){return request_accept_button;}
    public LinearLayout getCommentRequestButton(){return comment_request_button;}
    public TextView getCommentRequestCount(){return comment_request_count;}
    public LinearLayout getRequestButtonLayout(){return request_button_layout;}
    public TextView getUsernameMessage(){return username_message;}
    public CircleImageView getUserImageMessage(){return user_image_message;}
    public LinearLayout getMessageLayout(){return message_layout;}
    public ImageView getImageMessage(){return image_message;}
    public TextView getMessageText(){return message_text;}
    public TextView getPokeButtonText(){return poke_button_text;}


}
