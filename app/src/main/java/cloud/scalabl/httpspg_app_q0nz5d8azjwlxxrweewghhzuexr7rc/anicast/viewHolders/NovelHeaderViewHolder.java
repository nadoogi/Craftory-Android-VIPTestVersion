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

public class NovelHeaderViewHolder extends RecyclerView.ViewHolder {

    ImageView represent_image;
    TextView title;
    TextView body;
    LinearLayout comment_button;
    LinearLayout playlist_button;


    public NovelHeaderViewHolder(View itemView) {
        super(itemView);

        represent_image = (ImageView) itemView.findViewById(R.id.represent_image);
        title = (TextView) itemView.findViewById(R.id.title);
        body = (TextView) itemView.findViewById(R.id.post_body);
        comment_button = (LinearLayout) itemView.findViewById(R.id.comment_button);
        playlist_button = (LinearLayout) itemView.findViewById(R.id.playlist_button);

    }

    public ImageView getRepresentImage(){return represent_image;}
    public TextView getTitle(){return title;}
    public TextView getBody(){return body;}
    public LinearLayout getCommentButton(){return comment_button;}
    public LinearLayout getPlaylistButton(){return playlist_button;}



}
