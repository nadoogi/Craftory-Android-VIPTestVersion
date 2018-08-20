package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class AddressListViewHolder extends RecyclerView.ViewHolder {

    private TextView postal_text;
    private TextView road_text;
    private TextView jibun_text;
    private LinearLayout item_layout;

    public AddressListViewHolder(View itemView) {
        super(itemView);

        this.postal_text = (TextView) itemView.findViewById(R.id.postal_text);
        this.road_text = (TextView) itemView.findViewById(R.id.road_text);
        this.jibun_text = (TextView) itemView.findViewById(R.id.jibun_text);
        this.item_layout = (LinearLayout) itemView.findViewById(R.id.item_layout);

    }

   public TextView getPostalText(){return postal_text;}
   public TextView getRoadText(){return road_text;}
   public TextView getJibunText(){return jibun_text;}
   public LinearLayout getItemLayout(){return item_layout;}

}
