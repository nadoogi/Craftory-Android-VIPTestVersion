package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class CreatorItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView listItemImage;

    public CreatorItemViewHolder(View itemView) {
        super(itemView);

        listItemImage = (ImageView) itemView.findViewById(R.id.patron_image);


    }

    public ImageView getListItemImage(){return listItemImage;}

}
