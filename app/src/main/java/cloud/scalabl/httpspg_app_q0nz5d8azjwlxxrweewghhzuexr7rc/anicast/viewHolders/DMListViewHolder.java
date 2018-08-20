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

public class DMListViewHolder extends RecyclerView.ViewHolder {

    CircleImageView user_image;
    TextView nick_name;
    TextView message;
    LinearLayout dm_layout;
    TextView message_count;

    public DMListViewHolder(View dmView) {

        super(dmView);

        this.user_image = (CircleImageView) dmView.findViewById(R.id.user_image);
        this.nick_name = (TextView) dmView.findViewById(R.id.nick_name);
        this.message = (TextView) dmView.findViewById(R.id.message);
        this.dm_layout = (LinearLayout) dmView.findViewById(R.id.dm_layout);
        this.message_count = (TextView) dmView.findViewById(R.id.message_count);

    }

    public CircleImageView getUserImage(){return user_image;}
    public TextView getNickName(){return nick_name;}
    public TextView getMessage(){return message;}
    public LinearLayout getDMLayout(){return dm_layout;}
    public TextView getMessageCount(){return message_count;}

}
