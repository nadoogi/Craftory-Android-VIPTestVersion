package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class SearchUserViewHolder extends RecyclerView.ViewHolder {

    CircleImageView user_photo;
    TextView user_name;
    TextView gifted_point;
    LinearLayout user_layout;

    public SearchUserViewHolder(View itemView) {

        super(itemView);

        user_photo = (CircleImageView) itemView.findViewById(R.id.user_photo);
        user_name = (TextView) itemView.findViewById(R.id.user_name);
        gifted_point = (TextView) itemView.findViewById(R.id.gifted_point);

        user_layout = (LinearLayout) itemView.findViewById(R.id.user_layout);

    }

    public CircleImageView getUserPhoto(){return user_photo;}
    public TextView getUserName(){return user_name;}
    public TextView getGiftedPoint(){return gifted_point;}
    public LinearLayout getUserLayout(){return user_layout;}


}
