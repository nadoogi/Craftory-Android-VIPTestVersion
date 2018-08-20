package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class MyContentAdViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView profile;
    private TextView username;

    private ImageView post_image;
    private TextView post_body;

    public MyContentAdViewHolder(View itemView) {
        super(itemView);

        profile = (CircleImageView) itemView.findViewById(R.id.profile);
        username = (TextView) itemView.findViewById(R.id.username);

        post_image = (ImageView) itemView.findViewById(R.id.post_image);
        post_body = (TextView) itemView.findViewById(R.id.post_body);


    }

    public CircleImageView getProfile(){ return profile;}
    public TextView getUsername() { return username; }
    public ImageView getPostImage(){return post_image;}
    public TextView getPostBody(){return post_body;}

}
