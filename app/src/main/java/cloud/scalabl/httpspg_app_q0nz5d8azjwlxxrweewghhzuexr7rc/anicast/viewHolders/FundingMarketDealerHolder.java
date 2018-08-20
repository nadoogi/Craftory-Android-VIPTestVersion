package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 5. 23..
 */

public class FundingMarketDealerHolder extends RecyclerView.ViewHolder {

    CircleImageView dealer_image;
    TextView dealer_name;
    LinearLayout dealer_info_layout;

    public FundingMarketDealerHolder(View itemView){

        super(itemView);

        this.dealer_image = (CircleImageView) itemView.findViewById(R.id.dealer_image);
        this.dealer_name = (TextView) itemView.findViewById(R.id.dealer_name);
        this.dealer_info_layout = (LinearLayout) itemView.findViewById(R.id.dealer_info_layout);
    }

    public CircleImageView getDealerImage(){return dealer_image;}
    public TextView getDealerName(){return dealer_name;}
    public LinearLayout getDealerInfoLayout(){return dealer_info_layout;}

}
