package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;


/**
 * Created by ssamkyu on 2017. 6. 4..
 */

public class PokeListItemViewHolder extends RecyclerView.ViewHolder{

    ImageView image;
    TextView title;

    public PokeListItemViewHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.patron_image);
        title = (TextView) itemView.findViewById(R.id.title);

    }

    public ImageView getImage(){return image;}
    public TextView getTitle(){return title;}


}
