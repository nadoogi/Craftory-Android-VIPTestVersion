package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 5. 24..
 */

public class ImmoticonViewHolder extends RecyclerView.ViewHolder {


    private ImageView immoticon;


    public ImmoticonViewHolder(View commentView) {

        super(commentView);
        this.immoticon = (ImageView) commentView.findViewById(R.id.immoticon_image);
    }

    public ImageView getImmoticon(){return immoticon;}

}
