package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class UserHeaderViewHolder extends RecyclerView.ViewHolder {

    CircleImageView writter_photo;
    TextView writter_name;
    TextView writter_body;
    TextView post_count;
    TextView follower_count;
    TextView following_count;
    TextView creator_talk;
    LinearLayout follow_button;
    TextView follow_text;
    LinearLayout request_button;
    LinearLayout cheer_button;
    LinearLayout poke_button;
    TextView poke_text;
    LinearLayout social_buttons;
    LinearLayout dm_button;

    public UserHeaderViewHolder(View itemView) {
        super(itemView);

        writter_photo = (CircleImageView) itemView.findViewById(R.id.writter_photo);
        writter_name = (TextView) itemView.findViewById(R.id.writter_name);
        writter_body = (TextView) itemView.findViewById(R.id.writter_body);
        post_count = (TextView) itemView.findViewById(R.id.post_count);
        follower_count = (TextView) itemView.findViewById(R.id.follower_count);
        following_count = (TextView) itemView.findViewById(R.id.following_count);
        creator_talk = (TextView) itemView.findViewById(R.id.creator_talk);
        follow_button = (LinearLayout) itemView.findViewById(R.id.follow_button);
        follow_text = (TextView) itemView.findViewById(R.id.follow_text);
        request_button = (LinearLayout) itemView.findViewById(R.id.request_button);
        cheer_button = (LinearLayout) itemView.findViewById(R.id.cheer_button);
        poke_button = (LinearLayout) itemView.findViewById(R.id.poke_button);
        poke_text = (TextView) itemView.findViewById(R.id.poke_text);
        social_buttons = (LinearLayout) itemView.findViewById(R.id.social_button);
        dm_button = (LinearLayout) itemView.findViewById(R.id.dm_button);

    }

    public CircleImageView getWritterPhoto(){return writter_photo;}
    public TextView getWritterName(){return writter_name;}
    public TextView getWritterBody(){return writter_body;}
    public TextView getPostCount(){return post_count;}
    public TextView getFollowerCount(){return follower_count;}
    public TextView getFollowingCount(){return following_count;}
    public TextView getCreatorTalk(){return creator_talk;}
    public LinearLayout getFollowButton(){return follow_button;}
    public TextView getFollowText(){return follow_text;}
    public LinearLayout getRequestButton(){return request_button;}
    public LinearLayout getCheerButton(){return cheer_button;}
    public LinearLayout getPokeButton(){return poke_button;}
    public TextView getPokeText(){return poke_text;}
    public LinearLayout getSocialButtons(){return social_buttons;}
    public LinearLayout getDMButton(){return dm_button;}


}
