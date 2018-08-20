package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;


/**
 * Created by ssamkyu on 2016. 12. 21..
 */

public class CardContentsFragment extends Fragment {


    private String imageUrl;
    private String name;
    private String description;

    private String contentId;

    private TextView content_name;
    private TextView content_description;
    //private ParseImageView content_container;
    private PhotoView content_container;
    private FunctionBase functionBase;

    public CardContentsFragment() {

        // Required empty public constructor

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contentId = getArguments().getString("contentId");
        String cardId = getArguments().getString("cardId");
        functionBase = new FunctionBase(getActivity());



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_card_contents, container, false);


        content_name = (TextView) view.findViewById(R.id.content_name);
        content_description = (TextView) view.findViewById(R.id.content_description);
        content_container = (PhotoView) view.findViewById(R.id.content_container);


        final PhotoViewAttacher attacher = new PhotoViewAttacher(content_container);

        ParseQuery imageQuery = ParseQuery.getQuery("Image");
        imageQuery.getInBackground(contentId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject imageOb, ParseException e) {

                if(e==null){

                    content_name.setText(imageOb.getString("name"));
                    content_description.setText(imageOb.getString("description"));

                    int backgroundColor = Color.parseColor("#000000");
                    content_container.setBackgroundColor(backgroundColor);

                    String imageUrlPhoto = functionBase.imageUrlBase600 + imageOb.getString("image_cdn");

                    Glide.with(getActivity())
                            .load(imageUrlPhoto)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(content_container);



                } else {

                    e.printStackTrace();

                }

            }

        });



        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        super.onResume();



    }


}
