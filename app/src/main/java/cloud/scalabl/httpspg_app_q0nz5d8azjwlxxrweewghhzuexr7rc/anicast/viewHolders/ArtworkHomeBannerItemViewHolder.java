package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 8. 25..
 */

public class ArtworkHomeBannerItemViewHolder extends RecyclerView.ViewHolder {

    ImageView banner_image;
    TextView title;
    TextView body;

    public ArtworkHomeBannerItemViewHolder(View itemView) {
        super(itemView);

        banner_image = (ImageView) itemView.findViewById(R.id.banner_image);
        title = (TextView) itemView.findViewById(R.id.title);
        body = (TextView) itemView.findViewById(R.id.post_body);

    }

    public ImageView getBannerImage(){return banner_image;}
    public TextView getTitle(){return title;}
    public TextView getBody(){return body;}

}
