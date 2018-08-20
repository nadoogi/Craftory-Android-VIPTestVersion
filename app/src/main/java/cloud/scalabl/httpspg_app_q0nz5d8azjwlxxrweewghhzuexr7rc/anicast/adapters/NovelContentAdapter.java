package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.RequestManager;
import com.parse.ParseUser;

import java.util.ArrayList;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FileOpenClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ImageInfoClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.NovelContent;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.NovelContentViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class NovelContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<NovelContent> items;
    //private static ImageView preview;

    private RequestManager requestManager;

    public void setFileOpenClickListener(FileOpenClickListener fileOpenClickListener){

        FileOpenClickListener fileOpenClickListener1 = fileOpenClickListener;

    }

    public void setImageInfoClickListener(ImageInfoClickListener imageInfoClickListener){

        ImageInfoClickListener imageInfoClickListener1 = imageInfoClickListener;

    }

    private FunctionBase functionBase;


    public NovelContentAdapter(Context context, ArrayList<NovelContent> novels, RequestManager requestManager) {

        this.context = context;
        this.requestManager = requestManager;
        ParseUser currentUser = ParseUser.getCurrentUser();
        this.items = novels;
        this.functionBase = new FunctionBase(context);

        //this.preview = preview;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contentView;

        contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_novel_content, parent, false);

        return new NovelContentViewHolder(contentView);



    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        NovelContent item = getItem(position);

        TextView novel_string = ((NovelContentViewHolder)holder).getNovelString();
        ImageView novel_image = ((NovelContentViewHolder)holder).getNovelImage();
        final LinearLayout novel_content_layout = ((NovelContentViewHolder)holder).getNovelContentLayout();

        Log.d("content", item.getContent());

        if(item.getType().equals("String")){

            novel_string.setVisibility(View.VISIBLE);
            novel_image.setVisibility(View.GONE);

            novel_string.setText( item.getContent() );

        } else {

            novel_string.setVisibility(View.GONE);
            novel_image.setVisibility(View.VISIBLE);

            requestManager
                    .load(functionBase.imageUrlBase300 + item.getContent())
                    .into(novel_image);

        }

        novel_content_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean wrapInScrollView = true;

                final MaterialDialog imageDialog = new MaterialDialog.Builder(context)
                        .title("이미지 정보")
                        .customView(R.layout.layout_image_info, wrapInScrollView)
                        .show();


                ImageView currentImage = (ImageView) imageDialog.getCustomView().findViewById(R.id.current_image);
                BootstrapButton deleteButton = (BootstrapButton) imageDialog.getCustomView().findViewById(R.id.delete_button);
                BootstrapButton saveButton = (BootstrapButton) imageDialog.getCustomView().findViewById(R.id.save_button);
                EditText title = (EditText) imageDialog.getCustomView().findViewById(R.id.title);
                EditText body = (EditText) imageDialog.getCustomView().findViewById(R.id.post_body);

                final String titleString = title.getText().toString();
                final String bodyString = body.getText().toString();


                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });

            }
        });



    }







    @Override
    public int getItemCount() {

        return items.size();

    }


    public NovelContent getItem(int position) {

        return items.get(position);

    }





}
