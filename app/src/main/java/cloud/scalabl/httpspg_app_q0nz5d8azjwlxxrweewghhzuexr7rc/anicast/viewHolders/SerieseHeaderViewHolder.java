package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class SerieseHeaderViewHolder extends RecyclerView.ViewHolder {

    ImageView seriese_image;
    LinearLayout upload_image;
    EditText title_text;
    TagGroup tag_group;
    LinearLayout save_button;
    LinearLayout commercial_button;
    TextView commercial_button_text;
    LinearLayout mon;
    LinearLayout tue;
    LinearLayout wen;
    LinearLayout thu;
    LinearLayout fri;
    LinearLayout sat;
    LinearLayout sun;
    ImageView title_reset;


    public SerieseHeaderViewHolder(View itemView) {
        super(itemView);

        seriese_image = (ImageView) itemView.findViewById(R.id.patron_image);
        upload_image = (LinearLayout) itemView.findViewById(R.id.upload_image);
        title_text = (EditText) itemView.findViewById(R.id.title_text);
        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);
        save_button = (LinearLayout) itemView.findViewById(R.id.save_button);
        commercial_button = (LinearLayout) itemView.findViewById(R.id.commercial_button);
        commercial_button_text = (TextView) itemView.findViewById(R.id.commercial_button_text);

        mon = (LinearLayout) itemView.findViewById(R.id.mon);
        tue = (LinearLayout) itemView.findViewById(R.id.tue);
        wen = (LinearLayout) itemView.findViewById(R.id.wen);
        thu = (LinearLayout) itemView.findViewById(R.id.thu);
        fri = (LinearLayout) itemView.findViewById(R.id.fri);
        sat = (LinearLayout) itemView.findViewById(R.id.sat);
        sun = (LinearLayout) itemView.findViewById(R.id.sun);

        title_reset = (ImageView) itemView.findViewById(R.id.title_reset);

    }

    public ImageView getSerieseImage(){return seriese_image;}
    public LinearLayout getUploadImage(){return upload_image;}
    public EditText getTitleText(){return title_text;}
    public TagGroup getTagGroup(){return tag_group;}
    public LinearLayout getSaveButton(){return save_button;}
    public LinearLayout getCommercialButton(){return commercial_button;}
    public TextView getCommercialButtonText(){return commercial_button_text;}
    public LinearLayout getMon(){return mon;}
    public LinearLayout getTue(){return tue;}
    public LinearLayout getWen(){return wen;}
    public LinearLayout getThu(){return thu;}
    public LinearLayout getFri(){return fri;}
    public LinearLayout getSat(){return sat;}
    public LinearLayout getSun(){return sun;}
    public ImageView getTitleReset(){return title_reset;}


}
