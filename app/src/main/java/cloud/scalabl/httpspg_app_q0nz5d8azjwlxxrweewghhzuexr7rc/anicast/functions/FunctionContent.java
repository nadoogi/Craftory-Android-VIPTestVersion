package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONException;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.ContentViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;


/**
 * Created by ssamkyu on 2017. 5. 29..
 */

public class FunctionContent {

    private Context context;
    private ParseUser currentUser;
    private RequestManager requestManager;
    private Drawable mDefaultBackground;
    private String youtube;
    private YouTubeBaseActivity activity;
    private FunctionBase functionBase;

    public FunctionContent(Context context) {

        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.mDefaultBackground = context.getResources().getDrawable(R.drawable.image_background4);
        this.functionBase = new FunctionBase(context);


    }

    public FunctionContent(YouTubeBaseActivity activity, Context context, String youtube) {

        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.mDefaultBackground = context.getResources().getDrawable(R.drawable.image_background4);
        this.youtube = youtube;
        this.activity = activity;
        this.functionBase = new FunctionBase(context);

    }


    public void ContentAdapterBuilder(ParseObject contentOb, RecyclerView.ViewHolder holder, String layoutType, RequestManager requestManager){

        this.requestManager = requestManager;

        TextView card_name = ((ContentViewHolder)holder).getCardName();
        ImageView card_image = ((ContentViewHolder)holder).getCardImage();

        final TextView category_text = ((ContentViewHolder)holder).getCategoryText();
        final TextView channel_text = ((ContentViewHolder)holder).getChannelText();

        LinearLayout category_layout = ((ContentViewHolder)holder).getCategoryLayout();
        LinearLayout channel_layout = ((ContentViewHolder)holder).getChannelLayout();

        ImageView card_type = ((ContentViewHolder)holder).getCardType();
        TextView price_text = ((ContentViewHolder)holder).getPriceText();
        LinearLayout price_layout = ((ContentViewHolder)holder).getPriceLayout();

        Boolean purchase = ((ContentViewHolder)holder).getPurchase();

        TextView like_count = ((ContentViewHolder)holder).getLikeCount();
        TextView comment_count = ((ContentViewHolder)holder).getCommentCount();

        if(currentUser != null){

            try {

                purchase = functionBase.doublePuchaseCheck(contentOb, currentUser);

                Log.d("purchase", String.valueOf(purchase));

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }


        //기능 추가

        if(layoutType.equals("big")){

            bindingDataToBigLayout(contentOb, card_name, card_image, category_text, category_layout, card_type, purchase, price_text, price_layout, like_count, comment_count);

        } else if(layoutType.equals("small")){

            bindingDataToSmallLayout(contentOb, card_name, card_image, category_text, channel_text , channel_layout, category_layout , card_type, purchase, price_text, price_layout, like_count, comment_count);

        } else if(layoutType.equals("channel")){

            bindingChannelToSmallLayout(contentOb, card_name, card_image, category_text, channel_text , channel_layout, category_layout , card_type, purchase, price_text, price_layout,  like_count, comment_count);

        }

    }


    private void bindingDataToSmallLayout(final ParseObject contentOb, TextView card_name, ImageView card_image, final TextView category_text, TextView channel_text , LinearLayout channel_layout , LinearLayout category_layout , ImageView card_type , final Boolean purchase, final TextView price_text, LinearLayout price_layout, TextView like_count, TextView comment_count){



        channel_text.setText(contentOb.getParseObject("channel").getString("name"));
        category_layout.setVisibility(View.GONE);

        like_count.setText(String.valueOf( contentOb.getInt("like_count") ));
        comment_count.setText(String.valueOf( contentOb.getInt("comment_count") ));

        functionBase.priceLayoutBuilder(contentOb, price_text, price_layout, purchase);

        if(contentOb.getString("type").equals("youtube")){

            String youtubeId = contentOb.getString("youtubeId");
            String youtubeImgUrl = "http://img.youtube.com/vi/"+ youtubeId +"/0.jpg";

            functionBase.glideFunction(context, youtubeImgUrl , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_action_play);

            card_name.setText( contentOb.getString("name"));


            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(youtube != null){

                        if(youtube.equals("youtube")){

                            functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context, activity);

                        } else {

                            functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                        }

                    } else {

                        functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                    }





                }
            });


        } else if(contentOb.getString("type").equals("photo")){

            String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");

            //requestManager.load(imageUrlPhoto).into(card_image);
            functionBase.glideFunction(context, imageUrlPhoto , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_action_picture);

            card_name.setText( contentOb.getString("name"));


            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });

        } else if(contentOb.getString("type").equals("gif")){

            String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");

            //requestManager.load(imageUrlPhoto).into(card_image);

            functionBase.glideFunction(context, imageUrlPhoto , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_action_gif);

            card_name.setText( contentOb.getString("name"));


            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });

        } else if(contentOb.getString("type").equals("webtoon")){

            String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");

            //requestManager.load(imageUrlPhoto).into(card_image);

            functionBase.glideFunction(context, imageUrlPhoto , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_webtoon);

            card_name.setText( "[" + contentOb.getParseObject("category").getString("name") + "] " + contentOb.getString("name"));


            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });

        }


    }

    private void bindingDataToBigLayout(final ParseObject contentOb, TextView card_name,  ImageView card_image, final TextView category_text, LinearLayout category_layout, ImageView card_type, final Boolean purchase , final TextView price_text, LinearLayout price_layout, TextView like_count, TextView comment_count){

        card_name.setText(contentOb.getString("name"));

        functionBase.priceLayoutBuilder(contentOb, price_text, price_layout, purchase);

        String htmlDelete = functionBase.html2text(contentOb.getString("description"));

        String youtubeDescription = "";

        if(htmlDelete.length() < 100){

            youtubeDescription = htmlDelete;

        } else {

            youtubeDescription = htmlDelete.substring(0,100) + "...";

        }




        if(contentOb.getString("type").equals("youtube")){

            String youtubeId = contentOb.getString("youtubeId");
            String youtubeImgUrl = "http://img.youtube.com/vi/"+ youtubeId +"/0.jpg";

            //requestManager.load(youtubeImgUrl).error(mDefaultBackground).into(card_image);

            functionBase.glideFunction(context, youtubeImgUrl , card_image, requestManager);

            contentOb.getParseObject("channel").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject channelOb, ParseException e) {

                    if(e==null){

                        category_text.setText(channelOb.getString("name"));

                    } else {

                        e.printStackTrace();
                    }

                }
            });

            card_type.setImageResource(R.drawable.ic_action_play);

            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });


        } else if(contentOb.getString("type").equals("photo")){

            String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");

            //requestManager.load(imageUrlPhoto).into(card_image);

            functionBase.glideFunction(context, imageUrlPhoto , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_action_picture);

            contentOb.getParseObject("channel").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject channelOb, ParseException e) {

                    if(e==null){

                        category_text.setText(channelOb.getString("name"));

                    } else {

                        e.printStackTrace();
                    }

                }
            });

            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });

        } else if(contentOb.getString("type").equals("gif")){

            String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");
            //requestManager.load(imageUrlPhoto).into(card_image);

            functionBase.glideFunction(context, imageUrlPhoto , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_action_gif);

            contentOb.getParseObject("channel").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject channelOb, ParseException e) {

                    if(e==null){

                        category_text.setText(channelOb.getString("name"));

                    } else {

                        e.printStackTrace();
                    }

                }
            });

            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });

        } else if(contentOb.getString("type").equals("webtoon")){

            Log.d("mgs", "webtoon" );

            String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");
            //requestManager.load(imageUrlPhoto).into(card_image);

            functionBase.glideFunction(context, imageUrlPhoto , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_webtoon);


            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });



        }

    }



    private void bindingChannelToSmallLayout(final ParseObject contentOb, TextView card_name, ImageView card_image, final TextView category_text, TextView channel_text , LinearLayout channel_layout , LinearLayout category_layout , ImageView card_type , final Boolean purchase, final TextView price_text, LinearLayout price_layout, TextView like_count, TextView comment_count){

        card_name.setText(contentOb.getString("name"));
        category_text.setText(contentOb.getParseObject("category").getString("name"));
        channel_layout.setVisibility(View.GONE);

        like_count.setText(String.valueOf( contentOb.getInt("like_count") ));
        comment_count.setText(String.valueOf( contentOb.getInt("comment_count") ));

        functionBase.priceLayoutBuilder(contentOb, price_text, price_layout, purchase);

        if(contentOb.getString("type").equals("youtube")){

            String youtubeId = contentOb.getString("youtubeId");
            String youtubeImgUrl = "http://img.youtube.com/vi/"+ youtubeId +"/0.jpg";

            //requestManager.load(youtubeImgUrl).error(mDefaultBackground).into(card_image);

            functionBase.glideFunction(context, youtubeImgUrl , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_action_play);

            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });


        } else if(contentOb.getString("type").equals("photo")){

            String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");

            //requestManager.load(imageUrlPhoto).into(card_image);

            functionBase.glideFunction(context, imageUrlPhoto , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_action_picture);

            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });

        } else if(contentOb.getString("type").equals("gif")){

            String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");

            //requestManager.load(imageUrlPhoto).into(card_image);

            functionBase.glideFunction(context, imageUrlPhoto , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_action_gif);

            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });

        } else if(contentOb.getString("type").equals("webtoon")){

            String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");

            //requestManager.load(imageUrlPhoto).into(card_image);

            functionBase.glideFunction(context, imageUrlPhoto , card_image, requestManager);

            card_type.setImageResource(R.drawable.ic_webtoon);

            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                }
            });

        }


    }




}
