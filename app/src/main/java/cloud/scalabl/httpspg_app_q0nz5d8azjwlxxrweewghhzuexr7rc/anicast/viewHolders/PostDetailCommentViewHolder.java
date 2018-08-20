package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class PostDetailCommentViewHolder extends RecyclerView.ViewHolder {

    CircleImageView user_image_1;
    CircleImageView user_image_2;
    CircleImageView user_image_3;

    TextView user_name_1;
    TextView user_name_2;
    TextView user_name_3;

    TextView post_date_1;
    TextView post_date_2;
    TextView post_date_3;

    TextView comment_body_1;
    TextView comment_body_2;
    TextView comment_body_3;


    LinearLayout comment_1;
    LinearLayout comment_2;
    LinearLayout comment_3;
    LinearLayout input;

    LinearLayout no_comment;

    LinearLayout comment_layout;
    EditText comment_input;

    ImageView immoticon1;
    ImageView immoticon2;
    ImageView immoticon3;

    public PostDetailCommentViewHolder(View itemView) {
        super(itemView);

        user_image_1 = (CircleImageView) itemView.findViewById(R.id.user_image_1);
        user_image_2 = (CircleImageView) itemView.findViewById(R.id.user_image_2);
        user_image_3 = (CircleImageView) itemView.findViewById(R.id.user_image_3);

        user_name_1 = (TextView) itemView.findViewById(R.id.user_name_1);
        user_name_2 = (TextView) itemView.findViewById(R.id.user_name_2);
        user_name_3 = (TextView) itemView.findViewById(R.id.user_name_3);

        post_date_1 = (TextView) itemView.findViewById(R.id.post_date_1);
        post_date_2 = (TextView) itemView.findViewById(R.id.post_date_2);
        post_date_3 = (TextView) itemView.findViewById(R.id.post_date_3);

        comment_body_1 = (TextView) itemView.findViewById(R.id.comment_body_1);
        comment_body_2 = (TextView) itemView.findViewById(R.id.comment_body_2);
        comment_body_3 = (TextView) itemView.findViewById(R.id.comment_body_3);


        comment_1 = (LinearLayout) itemView.findViewById(R.id.comment_1);
        comment_2 = (LinearLayout) itemView.findViewById(R.id.comment_2);
        comment_3 = (LinearLayout) itemView.findViewById(R.id.comment_3);

        input = (LinearLayout) itemView.findViewById(R.id.input);

        comment_input = (EditText) itemView.findViewById(R.id.comment_input);



        no_comment = (LinearLayout) itemView.findViewById(R.id.no_comment);
        comment_layout = (LinearLayout) itemView.findViewById(R.id.comment_layout);

        immoticon1 = (ImageView) itemView.findViewById(R.id.immoticon1);
        immoticon2 = (ImageView) itemView.findViewById(R.id.immoticon2);
        immoticon3 = (ImageView) itemView.findViewById(R.id.immoticon3);

    }

    public LinearLayout getInput(){return input;}
    public LinearLayout getComment1(){return comment_1;}
    public LinearLayout getComment2(){return comment_2;}
    public LinearLayout getComment3(){return comment_3;}
    public TextView getCommentBody1(){return comment_body_1;}
    public TextView getCommentBody2(){return comment_body_2;}
    public TextView getCommentBody3(){return comment_body_3;}
    public TextView getPostDate1(){return post_date_1;}
    public TextView getPostDate2(){return post_date_2;}
    public TextView getPostDate3(){return post_date_3;}
    public CircleImageView getUserImage1(){return user_image_1;}
    public CircleImageView getUserImage2(){return user_image_2;}
    public CircleImageView getUserImage3(){return user_image_3;}
    public TextView getUserName1(){return user_name_1;}
    public TextView getUserName2(){return user_name_2;}
    public TextView getUserName3(){return user_name_3;}
    public LinearLayout getNoComment(){return no_comment;}
    public LinearLayout getCommentLayout(){return comment_layout;}
    public EditText getCommentInput(){return comment_input;}

    public ImageView getImmoticon1(){return immoticon1;}
    public ImageView getImmoticon2(){return immoticon2;}
    public ImageView getImmoticon3(){return immoticon3;}


}
