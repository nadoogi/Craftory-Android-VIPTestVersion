package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.github.chrisbanes.photoview.PhotoView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class OriginalIllustActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_illust);

        Intent intent = getIntent();

        String imageUrl = intent.getExtras().getString("image_url");

        Log.d("imageUrl", imageUrl);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        FunctionBase functionBase = new FunctionBase(getApplicationContext());

        PhotoView original_illust = (PhotoView) findViewById(R.id.original_image);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button_text.setText("고해상도 보기");

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        String[] imageUrlArrray = imageUrl.split("/");
        String imageFileName = imageUrlArrray[1];


        Glide.with(getApplicationContext())
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
                .into(original_illust);







    }
}
