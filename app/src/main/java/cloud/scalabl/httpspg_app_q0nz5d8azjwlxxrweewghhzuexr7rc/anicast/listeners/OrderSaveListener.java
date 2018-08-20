package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners;

import android.support.v4.util.Pair;

import com.parse.ParseObject;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ssamkyu on 2017. 8. 28..
 */

public interface OrderSaveListener {

    public void onCurrentOrderSave(ArrayList<Pair<Long, ParseObject>> item_array);

}
