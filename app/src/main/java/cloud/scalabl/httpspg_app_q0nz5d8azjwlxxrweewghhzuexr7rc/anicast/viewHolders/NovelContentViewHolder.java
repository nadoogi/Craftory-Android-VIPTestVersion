package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class NovelContentViewHolder extends RecyclerView.ViewHolder {

    private TextView novel_string;
    private ImageView novel_image;
    private LinearLayout novel_content_layout;


    public NovelContentViewHolder(View itemView) {
        super(itemView);

        novel_string = (TextView) itemView.findViewById(R.id.novel_string);
        novel_image = (ImageView) itemView.findViewById(R.id.novel_image);
        novel_content_layout = (LinearLayout) itemView.findViewById(R.id.novel_content_layout);


    }

    public TextView getNovelString(){return novel_string;}
    public ImageView getNovelImage(){return novel_image;}
    public LinearLayout getNovelContentLayout(){return novel_content_layout;}


}
