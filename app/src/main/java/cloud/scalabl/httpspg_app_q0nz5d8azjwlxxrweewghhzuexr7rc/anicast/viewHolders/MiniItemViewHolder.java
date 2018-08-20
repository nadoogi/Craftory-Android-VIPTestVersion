package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;


/**
 * Created by ssamkyu on 2017. 6. 4..
 */

public class MiniItemViewHolder extends RecyclerView.ViewHolder{

    private ImageView image;

    public MiniItemViewHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.patron_image);

    }

    public ImageView getImage(){

        return image;
    }
}
