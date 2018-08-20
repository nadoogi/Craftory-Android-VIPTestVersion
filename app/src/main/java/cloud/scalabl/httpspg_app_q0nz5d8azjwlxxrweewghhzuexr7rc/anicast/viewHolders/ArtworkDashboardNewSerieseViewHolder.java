package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class ArtworkDashboardNewSerieseViewHolder extends RecyclerView.ViewHolder {

    ImageView seriese_image;
    TextView title;
    TextView description;
    TextView nickname;
    LinearLayout profile_tag;


    public ArtworkDashboardNewSerieseViewHolder(View itemView) {
        super(itemView);

        seriese_image = (ImageView) itemView.findViewById(R.id.seriese_image);
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
        nickname = (TextView) itemView.findViewById(R.id.nickname);
        profile_tag = (LinearLayout) itemView.findViewById(R.id.profile_tag);

    }

    public ImageView getSerieseImage(){return seriese_image;}
    public TextView getTitle(){return title;}
    public TextView getDescription(){return description;}
    public TextView getNickname(){return nickname;}
    public LinearLayout getProfileTag(){return profile_tag;}

}
