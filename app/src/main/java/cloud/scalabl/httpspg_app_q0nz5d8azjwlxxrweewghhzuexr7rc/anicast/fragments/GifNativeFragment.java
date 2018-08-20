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
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;


/**
 * Created by ssamkyu on 2016. 12. 21..
 */

public class GifNativeFragment extends Fragment {

    private int current_page_num;




    private String imageUrl;
    private String name;
    private String description;

    private String title;
    private String body;



    public GifNativeFragment() {

        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        imageUrl = getArguments().getString("image");
        String main_title = getArguments().getString("main_title");
        body = getArguments().getString("body");
        title = getArguments().getString("title");

        String objectId = getArguments().getString("objectId");
        FunctionBase functionBase = new FunctionBase(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_card_contents, container, false);


        TextView content_name = (TextView) view.findViewById(R.id.content_name);
        TextView content_description = (TextView) view.findViewById(R.id.content_description);
        PhotoView content_container = (PhotoView) view.findViewById(R.id.content_container);

        TextView total_page = (TextView) view.findViewById(R.id.total_page);
        TextView current_page = (TextView) view.findViewById(R.id.current_page);







        final PhotoViewAttacher attacher = new PhotoViewAttacher(content_container);

        if(title != null){

            content_name.setText(title);

        }

        if(body != null){

            content_description.setText(body);

        }


        int backgroundColor = Color.parseColor("#000000");
        content_container.setBackgroundColor(backgroundColor);


        String[] imageUrlArrray = imageUrl.split("/");
        String imageFileName = imageUrlArrray[1];

        Glide.with(getActivity())
                .load(MediaManager.get().url().transformation(new Transformation().quality("auto").fetchFormat("auto")).generate(imageFileName))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                //.transition(new DrawableTransitionOptions().crossFade())
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
