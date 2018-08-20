package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 5. 24..
 */

public class TagItemMangerHolder extends RecyclerView.ViewHolder {

    ImageView tag_item_image;
    TextView tag_item_name;


    public TagItemMangerHolder(View tagView) {

        super(tagView);

        tag_item_image = (ImageView) tagView.findViewById(R.id.tag_item_image);
        tag_item_name = (TextView) tagView.findViewById(R.id.tag_item_name);

    }

    public ImageView getTagItemImage(){return tag_item_image;}
    public TextView getTagItemName(){return tag_item_name;}

}
