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

public class TimelineWebtoonHeaderViewHolder extends RecyclerView.ViewHolder {

    LinearLayout ilsang_button;
    ImageView ilsang_icon;
    TextView ilsang_text;

    LinearLayout all_button;
    ImageView all_icon;
    TextView all_text;

    LinearLayout soonjung_button;
    ImageView soonjung_icon;
    TextView soonjung_text;

    LinearLayout gag_button;
    ImageView gag_icon;
    TextView gag_text;

    LinearLayout action_button;
    ImageView action_icon;
    TextView action_text;

    LinearLayout romance_button;
    ImageView romance_icon;
    TextView romance_text;

    LinearLayout thriller_button;
    ImageView thriller_icon;
    TextView thriller_text;

    LinearLayout fantasy_button;
    ImageView fantasy_icon;
    TextView fantasy_text;

    LinearLayout sports_button;
    ImageView sports_icon;
    TextView sports_text;

    LinearLayout hackwon_button;
    ImageView hackwon_icon;
    TextView hackwon_text;

    LinearLayout top_layout;


    public TimelineWebtoonHeaderViewHolder(View itemView) {
        super(itemView);

        top_layout = (LinearLayout) itemView.findViewById(R.id.top_layout);

        all_button = (LinearLayout) itemView.findViewById(R.id.all_button);
        all_icon = (ImageView) itemView.findViewById(R.id.all_icon);
        all_text = (TextView) itemView.findViewById(R.id.all_text);

        ilsang_button = (LinearLayout) itemView.findViewById(R.id.ilsang_button);
        ilsang_icon = (ImageView) itemView.findViewById(R.id.ilsang_icon);
        ilsang_text = (TextView) itemView.findViewById(R.id.ilsang_text);

        soonjung_button = (LinearLayout) itemView.findViewById(R.id.soonjung_button);
        soonjung_icon = (ImageView) itemView.findViewById(R.id.soonjung_icon);
        soonjung_text = (TextView) itemView.findViewById(R.id.soonjung_text);

        gag_button = (LinearLayout) itemView.findViewById(R.id.gag_button);
        gag_icon = (ImageView) itemView.findViewById(R.id.gag_icon);
        gag_text = (TextView) itemView.findViewById(R.id.gag_text);

        action_button = (LinearLayout) itemView.findViewById(R.id.action_button);
        action_icon = (ImageView) itemView.findViewById(R.id.action_icon);
        action_text = (TextView) itemView.findViewById(R.id.action_text);

        romance_button = (LinearLayout) itemView.findViewById(R.id.romance_button);
        romance_icon = (ImageView) itemView.findViewById(R.id.romance_icon);
        romance_text = (TextView) itemView.findViewById(R.id.romance_text);

        thriller_button = (LinearLayout) itemView.findViewById(R.id.thriller_button);
        thriller_icon = (ImageView) itemView.findViewById(R.id.thriller_icon);
        thriller_text = (TextView) itemView.findViewById(R.id.thriller_text);

        fantasy_button = (LinearLayout) itemView.findViewById(R.id.fantasy_button);
        fantasy_icon = (ImageView) itemView.findViewById(R.id.fantasy_icon);
        fantasy_text = (TextView) itemView.findViewById(R.id.fantasy_text);

        sports_button = (LinearLayout) itemView.findViewById(R.id.sports_button);
        sports_icon = (ImageView) itemView.findViewById(R.id.sports_icon);
        sports_text = (TextView) itemView.findViewById(R.id.sports_text);

        hackwon_button = (LinearLayout) itemView.findViewById(R.id.hackwon_button);
        hackwon_icon = (ImageView) itemView.findViewById(R.id.hackwon_icon);
        hackwon_text = (TextView) itemView.findViewById(R.id.hackwon_text);


    }

    public LinearLayout getAllButton(){return all_button;}
    public ImageView getAllIcon() { return all_icon; }
    public TextView getAllText(){return all_text;}

    public LinearLayout getIlSangButton(){return ilsang_button;}
    public ImageView getIlSangIcon() { return ilsang_icon; }
    public TextView getIlSangText(){return ilsang_text;}

    public LinearLayout getSoonJungButton(){return soonjung_button;}
    public ImageView getSoonJungIcon() { return soonjung_icon; }
    public TextView getSoonJungText(){return soonjung_text;}

    public LinearLayout getGagButton(){return gag_button;}
    public ImageView getGagIcon() { return gag_icon; }
    public TextView getGagText(){return gag_text;}

    public LinearLayout getActionButton(){return action_button;}
    public ImageView getActionIcon() { return action_icon; }
    public TextView getActionText(){return action_text;}

    public LinearLayout getRomanceButton(){return romance_button;}
    public ImageView getRomanceIcon() { return romance_icon; }
    public TextView getRomanceText(){return romance_text;}

    public LinearLayout getThrillerButton(){return thriller_button;}
    public ImageView getThrillerIcon() { return thriller_icon; }
    public TextView getThrillerText(){return thriller_text;}

    public LinearLayout getFantasyButton(){return fantasy_button;}
    public ImageView getFantasyIcon() { return fantasy_icon; }
    public TextView getFantasyText(){return fantasy_text;}

    public LinearLayout getSportsButton(){return sports_button;}
    public ImageView getSportsIcon() { return sports_icon; }
    public TextView getSportsText(){return sports_text;}

    public LinearLayout getHackwonButton(){return hackwon_button;}
    public ImageView getHackwonIcon() { return hackwon_icon; }
    public TextView getHackwonText(){return hackwon_text;}

    public LinearLayout getTopLayout(){return top_layout;}

}
