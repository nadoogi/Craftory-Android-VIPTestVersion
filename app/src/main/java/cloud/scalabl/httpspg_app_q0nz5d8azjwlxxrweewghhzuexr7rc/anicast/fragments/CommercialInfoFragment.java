package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommercialListAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class CommercialInfoFragment extends Fragment {

    private RecyclerView artwork_list;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private CommercialListAdapter commercialListAdapter;

    private String imagePath;

    private ArrayList<Image> images;
    private String finalImage;
    private RequestManager requestManager;

    private File tempFile;

    private ParseObject serieseOb;

    private String serieseId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        serieseId = getArguments().getString("serieseId");
        requestManager = Glide.with(getActivity());

        return inflater.inflate(R.layout.fragment_commercial_info, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
        final CircleImageView writter_photo = (CircleImageView) view.findViewById(R.id.writter_photo);
        final TextView writter_name = (TextView) view.findViewById(R.id.writter_name);
        final TagGroup tag_group = (TagGroup) view.findViewById(R.id.tag_group);
        final TextView description = (TextView) view.findViewById(R.id.description);

        final FunctionBase functionBase = new FunctionBase(getActivity());

        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.include("user");
        query.getInBackground(serieseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject serieseOb, ParseException e) {

                if(e==null){

                    ParseObject userOb = serieseOb.getParseObject("user");

                    functionBase.profileImageLoading(writter_photo, userOb, requestManager);
                    functionBase.profileNameLoading(writter_name, userOb);

                    String[] tagList = functionBase.jsonArrayToStringArray( serieseOb.getJSONArray("tag_array") );
                    tag_group.setTags(tagList);

                    if(serieseOb.get("body") != null){

                        description.setText(serieseOb.getString("body"));

                    } else {

                        description.setText("입력 안됨");

                    }




                } else {

                    e.printStackTrace();
                }

            }

        });





    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
