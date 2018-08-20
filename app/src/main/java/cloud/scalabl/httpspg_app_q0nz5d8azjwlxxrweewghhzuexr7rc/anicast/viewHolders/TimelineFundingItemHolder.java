package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.awesomeimagepicker.models.Image;

/**
 * Created by ssamkyu on 2017. 5. 24..
 */

public class TimelineFundingItemHolder extends RecyclerView.ViewHolder {


    ImageView image;

    public TimelineFundingItemHolder(View itemView) {

        super(itemView);

        this.image = (ImageView) itemView.findViewById(R.id.image);

    }

    public ImageView getImage(){return image;}

}
