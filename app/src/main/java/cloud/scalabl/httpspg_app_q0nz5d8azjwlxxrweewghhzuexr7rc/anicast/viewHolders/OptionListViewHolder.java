package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 5. 24..
 */

public class OptionListViewHolder extends RecyclerView.ViewHolder {

    TextView option_name;
    EditText price_input;

    public OptionListViewHolder(View optionView) {

        super(optionView);

        option_name = optionView.findViewById(R.id.option_name);
        price_input = optionView.findViewById(R.id.price_input);

    }

    public TextView getOption_name() {
        return option_name;
    }

    public EditText getPrice_input() {
        return price_input;
    }
}
