package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.MainBoardActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class GuidStep4Fragment extends Fragment{

    LinearLayout skip_button;
    ParseInstallation currentInstallation;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentInstallation = ParseInstallation.getCurrentInstallation();

        return inflater.inflate(R.layout.fragment_guide_fourth_step, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        skip_button = (LinearLayout) view.findViewById(R.id.skip_button);
        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                currentInstallation.put("guide_check", true);
                currentInstallation.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e==null){

                            Intent intent = new Intent(getActivity(), MainBoardActivity.class);
                            startActivity(intent);

                            getActivity().finish();

                        } else {

                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }



    @Override
    public void onResume() {
        super.onResume();


    }

}
