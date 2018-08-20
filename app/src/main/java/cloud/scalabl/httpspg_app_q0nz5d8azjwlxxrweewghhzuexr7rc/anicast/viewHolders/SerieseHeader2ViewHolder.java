package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class SerieseHeader2ViewHolder extends RecyclerView.ViewHolder {

    ImageView seriese_image;
    TagGroup tag_group;
    TextView title;
    TextView body;
    CircleImageView writter_photo;
    TextView writter_name;
    LinearLayout follow_button;
    TextView follow_button_text;
    LinearLayout subscribe_button;
    LinearLayout init_artwork;
    TextView subscribe_text;
    TextView subs_count;
    ImageView subs_count_icon;

    public SerieseHeader2ViewHolder(View itemView) {
        super(itemView);

        seriese_image = (ImageView) itemView.findViewById(R.id.patron_image);
        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);
        title = (TextView) itemView.findViewById(R.id.title);
        writter_photo = (CircleImageView) itemView.findViewById(R.id.writter_photo);
        writter_name = (TextView) itemView.findViewById(R.id.writter_name);
        follow_button = (LinearLayout) itemView.findViewById(R.id.follow_button);
        follow_button_text = (TextView) itemView.findViewById(R.id.follow_button_text);
        subscribe_button = (LinearLayout) itemView.findViewById(R.id.subscribe_button);
        init_artwork = (LinearLayout) itemView.findViewById(R.id.init_artwork);
        subscribe_text = (TextView) itemView.findViewById(R.id.subscribe_text);

        subs_count = (TextView) itemView.findViewById(R.id.subs_count);
        subs_count_icon = (ImageView) itemView.findViewById(R.id.subs_count_icon);

    }

    public ImageView getSerieseImage(){return seriese_image;}
    public TagGroup getTagGroup(){return tag_group;}
    public TextView getTitle(){return title;}
    public CircleImageView getWritterPhoto(){return writter_photo;}
    public TextView getWritterName(){return writter_name;}
    public LinearLayout getFollowButton(){return follow_button;}
    public TextView getFollowButtonText(){return follow_button_text;}
    public LinearLayout getSubscribeButton(){return subscribe_button;}
    public LinearLayout getInitArtwork(){return init_artwork;}
    public TextView getSubscribeText(){return subscribe_text;}

    public TextView getSubsCount() { return subs_count; }
    public ImageView getSubsCountIcon(){ return subs_count_icon;}
}
