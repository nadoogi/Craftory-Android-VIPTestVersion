package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FileOpenClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ImageInfoClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.MiniItem2ViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.MiniItemViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class ImageUploadSelectorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<HashMap<String, String>> items;
    //private static ImageView preview;

    private RequestManager requestManager;

    private FileOpenClickListener fileOpenClickListener;
    private ImageInfoClickListener imageInfoClickListener;

    public void setFileOpenClickListener(FileOpenClickListener fileOpenClickListener){

        this.fileOpenClickListener = fileOpenClickListener;

    }

    public void setImageInfoClickListener(ImageInfoClickListener imageInfoClickListener){

        this.imageInfoClickListener = imageInfoClickListener;

    }

    private FunctionBase functionBase;
    private TextView status_text;

    public ImageUploadSelectorAdapter(Context context, ArrayList<HashMap<String, String>> images, RequestManager requestManager, TextView status_text) {

        Context context1 = context;
        this.requestManager = requestManager;
        ParseUser currentUser = ParseUser.getCurrentUser();
        this.items = images;
        this.functionBase = new FunctionBase(context);
        this.status_text = status_text;
        //this.preview = preview;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View photoView;

        if(getItemCount() == 1){

            Log.d("viewType", String.valueOf(viewType) + ": 1 ");

            photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mini_image_add, parent, false);

            return new MiniItem2ViewHolder(photoView);

        } else {



            if(viewType == items.size()){

                Log.d("viewType", String.valueOf(viewType) + ": 2 ");

                photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mini_image_add, parent, false);

                return new MiniItem2ViewHolder(photoView);

            } else {

                Log.d("viewType", String.valueOf(viewType) + ": 3 ");

                photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mini_preview, parent, false);

                return new MiniItemViewHolder(photoView);

            }



        }



    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.d("position" , String.valueOf(position));
        Log.d("getItemCount" , String.valueOf(getItemCount()));
        Log.d("item.size", String.valueOf(items.size()));



        if( getItemCount() == 1){

            ImageView upload_image = ((MiniItem2ViewHolder)holder).getImage();

            upload_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(fileOpenClickListener != null){

                        fileOpenClickListener.onFileOpenClicked();

                    }

                }
            });

        } else {

            if(position == items.size()){


                ImageView upload_image = ((MiniItem2ViewHolder)holder).getImage();

                upload_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(fileOpenClickListener != null){

                            fileOpenClickListener.onFileOpenClicked();

                        }

                    }
                });

            } else {

                //final String photoPath = getItem(position);
                final HashMap<String, String> data = getItem(position);

                String photoPath = functionBase.imageUrlBase200 + data.get("url");

                Log.d("image", photoPath);

                final ImageView image = ((MiniItemViewHolder)holder).getImage();

                requestManager
                        .load(photoPath)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                status_text.setText("업로드 완료 || 이미지 미리보기 실패");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                status_text.setText("업로드 완료 || 이미지 미리보기 완료");
                                return false;
                            }
                        })
                        .into(image);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        imageInfoClickListener.onImageInfoOpened(data);

                    }
                });

            }



        }


    }







    @Override
    public int getItemCount() {

        return items.size()+1;

    }


    public HashMap<String, String> getItem(int position) {

        Log.d("position", String.valueOf(position));

        if(items.size() == 0){

            return null;

        } else if(position == items.size()){

            return null;

        } else {

            return items.get(position);
        }


    }





}
