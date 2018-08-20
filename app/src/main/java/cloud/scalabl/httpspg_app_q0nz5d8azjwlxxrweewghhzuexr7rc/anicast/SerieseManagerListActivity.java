package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;
import com.woxthebox.draglistview.DragListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SerieseArtworkAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SerieseItemAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FileOpenClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ImageInfoClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

public class SerieseManagerListActivity extends AppCompatActivity {

    private LinearLayout exist_button;
    private LinearLayout new_button;

    private DragListView artwork_list;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    final int REQ_CODE_SELECT_IMAGE=200;

    private SerieseItemAdapter serieseItemAdapter;

    private RequestManager requestManager;

    private ParseObject serieseOb;
    private FunctionBase functionBase;
    private ParseUser currentUser;

    private String serieseId;

    private ArrayList<Pair<Long, ParseObject>> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seriese_manager_list);

        Intent intent = getIntent();

        serieseId = intent.getExtras().getString("serieseId");


        artwork_list = (DragListView) findViewById(R.id.artwork_list);
        exist_button = (LinearLayout) findViewById(R.id.exist_button);
        new_button = (LinearLayout) findViewById(R.id.new_button);

        currentUser = ParseUser.getCurrentUser();

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(serieseOb != null){

                    listRefresh(serieseOb);

                }

                swipeLayout.setRefreshing(false);

            }

        });


        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(this, getApplicationContext());

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        back_button_text.setText("연재 작품 회차 관리");

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        artwork_list.setLayoutManager(layoutManager);


        ParseQuery<ArtistPost> artistQuery = ParseQuery.getQuery(ArtistPost.class);
        artistQuery.getInBackground(serieseId, new GetCallback<ArtistPost>() {
            @Override
            public void done(final ArtistPost object, ParseException e) {

                if(e==null){

                    serieseOb = object;

                    listRefresh(serieseOb);

                    exist_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplicationContext(), SerieseAddFromExistActivity.class);
                            intent.putExtra("serieseId", object.getObjectId());
                            startActivity(intent);

                        }
                    });

                    new_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String content_type = serieseOb.getString("content_type");
                            Log.d("content_type", content_type);

                            switch (content_type){

                                case "post":

                                    Intent intent1 = new Intent(getApplicationContext(), SerieseItemAddPostActivity.class);
                                    intent1.putExtra("serieseId", serieseId);
                                    startActivity(intent1);

                                    break;


                                case "webtoon":

                                    Intent intent2 = new Intent(getApplicationContext(), SerieseItemAddWebtoonActivity.class);
                                    intent2.putExtra("serieseId", serieseId);
                                    startActivity(intent2);

                                    break;

                                case "album":

                                    Intent intent3 = new Intent(getApplicationContext(), SerieseItemAddIllustActivity.class);
                                    intent3.putExtra("serieseId", serieseId);
                                    startActivity(intent3);

                                    break;

                                case "youtube":

                                    Intent intent4 = new Intent(getApplicationContext(), SerieseItemAddYoutubeActivity.class);
                                    intent4.putExtra("serieseId", serieseId);
                                    startActivity(intent4);

                                    break;


                                case "novel":

                                    Intent intent5 = new Intent(getApplicationContext(), SerieseItemAddNovelActivity.class);
                                    intent5.putExtra("serieseId", serieseId);
                                    startActivity(intent5);

                                    break;

                            }

                        }
                    });


                } else {

                    e.printStackTrace();
                }

            }
        });


    }

    private void listRefresh(final ParseObject serieseOb){

        ParseQuery itemQuery = serieseOb.getRelation("seriese_item").getQuery();
        itemQuery.orderByAscending("order");
        itemQuery.include("commercial");
        itemQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> serieseObs, ParseException e) {

                if(e==null){

                    itemList = new ArrayList<>();

                    int order = 0;

                    for(ParseObject itemOb:serieseObs){

                        Pair<Long, ParseObject> item = Pair.create(Long.parseLong(String.valueOf(order)), itemOb);

                        order += 1;

                        itemList.add(item);
                    }


                    serieseItemAdapter = new SerieseItemAdapter(getApplicationContext(), itemList , R.layout.list_item_commercial_edit_item , R.id.post_layout, true, serieseOb );
                    artwork_list.setAdapter(serieseItemAdapter, true);

                } else {

                    e.printStackTrace();
                }

            }

        });

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    protected void onPostResume() {

        if(serieseOb != null){

            listRefresh(serieseOb);

        }

        super.onPostResume();

    }
}
