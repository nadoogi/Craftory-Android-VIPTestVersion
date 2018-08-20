package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2016. 12. 21..
 */

public class GifNativeInitFragment extends Fragment {



    private String imageUrl;
    private String title;
    private String body;
    private String date;
    private String writter;
    private String writterImage;


    public GifNativeInitFragment() {

        // Required empty public constructor

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        imageUrl = getArguments().getString("image");
        title = getArguments().getString("title");
        body = getArguments().getString("body");
        date = getArguments().getString("date");
        writter = getArguments().getString("writter");
        writterImage = getArguments().getString("writter_image");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_card_contents_init, container, false);


        TextView content_name = (TextView) view.findViewById(R.id.content_name);
        TextView content_description = (TextView) view.findViewById(R.id.content_description);
        ImageView background_image = (ImageView) view.findViewById(R.id.background_image);
        ImageView represent_image = (ImageView) view.findViewById(R.id.represent_image);
        CircleImageView writter_image = (CircleImageView) view.findViewById(R.id.writter_photo);
        TextView writter_name = (TextView) view.findViewById(R.id.writter_name);
        TextView follow_text = (TextView) view.findViewById(R.id.follow_text);
        LinearLayout follow_button = (LinearLayout) view.findViewById(R.id.follow_button);
        TextView post_date = (TextView) view.findViewById(R.id.post_date);

        content_name.setText(title);
        content_description.setText(body);
        writter_name.setText(writter);
        post_date.setText(date);


        Glide.with(getActivity()).load(writterImage).into(writter_image);
        FunctionBase functionBase = new FunctionBase(getActivity());

        String backgroundPhoto = functionBase.imageUrlBase300Blur + imageUrl;
        String imageUrlPhoto = functionBase.imageUrlBase500 + imageUrl;

        Log.d("backgroundphoto", backgroundPhoto);
        Log.d("imageUrlPhoto", imageUrlPhoto);

        Glide.with(getActivity())
                .load(backgroundPhoto)
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
                .into(background_image);


        String[] imageUrlArrray = imageUrl.split("/");
        String imageFileName = imageUrlArrray[1];

        Glide.with(getActivity())
                .load(MediaManager.get().url().transformation(new Transformation().effect("blur:1000").quality("auto").fetchFormat("auto")).generate(imageFileName))
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
                .into(background_image);



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
                .into(represent_image);




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
