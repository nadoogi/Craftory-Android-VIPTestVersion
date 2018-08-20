package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class CreatorViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView writter_photo;
    private TextView writter_name;
    private LinearLayout follow_button;
    private TextView follow_button_text;
    private LinearLayout favor_list_layout;
    private TextView favor_msg;
    private ImageView favor_image_1;
    private ImageView favor_image_2;
    private ImageView favor_image_3;
    private ImageView favor_image_4;
    private ImageView favor_image_5;
    private ImageView favor_image_6;




    public CreatorViewHolder(View itemView) {
        super(itemView);

        this.writter_photo = (CircleImageView) itemView.findViewById(R.id.writter_photo);
        this.writter_name = (TextView) itemView.findViewById(R.id.writter_name);
        this.follow_button = (LinearLayout) itemView.findViewById(R.id.follow_button);
        this.follow_button_text = (TextView) itemView.findViewById(R.id.follow_button_text);
        this.favor_list_layout = (LinearLayout) itemView.findViewById(R.id.favor_list_layout);
        this.favor_msg = (TextView) itemView.findViewById(R.id.favor_msg);

        favor_image_1 = (ImageView) itemView.findViewById(R.id.favor_image_1);
        favor_image_2 = (ImageView) itemView.findViewById(R.id.favor_image_2);
        favor_image_3 = (ImageView) itemView.findViewById(R.id.favor_image_3);
        favor_image_4 = (ImageView) itemView.findViewById(R.id.favor_image_4);
        favor_image_5 = (ImageView) itemView.findViewById(R.id.favor_image_5);
        favor_image_6 = (ImageView) itemView.findViewById(R.id.favor_image_6);

    }

    public CircleImageView getWritterPhoto(){return writter_photo;}
    public TextView getWritterName(){return writter_name;}
    public LinearLayout getFollowButton(){return follow_button;}
    public TextView getFollowButtonText(){return follow_button_text;}
    public LinearLayout getFavorListLayout(){return favor_list_layout;}
    public TextView getFavorMsg(){return favor_msg;}
    public ImageView getFavorImage1(){return favor_image_1;}
    public ImageView getFavorImage2(){return favor_image_2;}
    public ImageView getFavorImage3(){return favor_image_3;}
    public ImageView getFavorImage4(){return favor_image_4;}
    public ImageView getFavorImage5(){return favor_image_5;}
    public ImageView getFavorImage6(){return favor_image_6;}


}
