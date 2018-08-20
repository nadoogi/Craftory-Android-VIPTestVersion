package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronUserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionLikeDislike;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.PatronDetailHeaderViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelinePatronViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class PatronManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private ArrayList<ParseObject> items;
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages;
    private int objectsPerPage = 50;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;
    private FunctionBase functionBase;

    private int HeaderViewType = 0;
    private int ContentViewType = 1;
    private int ListViewType = 2;
    private ParseObject parentObject;

    public PatronManagerAdapter(AppCompatActivity activity, Context context, RequestManager requestManager, final ParseObject parentOb ) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.parentObject = parentOb;
        this.items = new ArrayList<>();
        this.objectPages =  new ArrayList<>();
        this.functionBase = new FunctionBase(activity, context);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View headerView;

        if(parentObject.getString("content_type").equals("goods")){

            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patron_manager_header_goods, parent, false);

            return new PatronDetailHeaderViewHolder(headerView);

        } else {

            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patron_manager_header, parent, false);

            return new PatronDetailHeaderViewHolder(headerView);

        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TextView current_point = ((PatronDetailHeaderViewHolder)holder).getCurrentPoint();
        TextView open_type = ((PatronDetailHeaderViewHolder)holder).getOpenType();
        TextView max_point_onprogress = ((PatronDetailHeaderViewHolder)holder).getMaxPointOnprogress();
        RoundCornerProgressBar progressbar= ((PatronDetailHeaderViewHolder)holder).getProgressbar();
        TextView total_profit_share= ((PatronDetailHeaderViewHolder)holder).getTotalProfitShare();
        TextView profit_share_ratio= ((PatronDetailHeaderViewHolder)holder).getProfitShareRatio();
        //TextView current_point_oninfo= ((PatronDetailHeaderViewHolder)holder).getCurrentPointOnInfo();
        TextView current_point_onprogress= ((PatronDetailHeaderViewHolder)holder).getMaxPointOnprogress();
        LinearLayout patron_manager_layout= ((PatronDetailHeaderViewHolder)holder).getPatronManagerLayout();

        TextView patron_type= ((PatronDetailHeaderViewHolder)holder).getPatronType();
        TextView min_box= ((PatronDetailHeaderViewHolder)holder).getMinBox();
        LinearLayout user_info_layout= ((PatronDetailHeaderViewHolder)holder).getUserInfoLayout();

        LinearLayout patron_stop= ((PatronDetailHeaderViewHolder)holder).getPatronStop();
        LinearLayout patron_result_summit= ((PatronDetailHeaderViewHolder)holder).getPatronResultSummit();
        LinearLayout patron_withdraw= ((PatronDetailHeaderViewHolder)holder).getPatronWithdraw();
        LinearLayout patron_cancel= ((PatronDetailHeaderViewHolder)holder).getPatronCancel();
        LinearLayout patron_user_button= ((PatronDetailHeaderViewHolder)holder).getPatronUserButton();

        TextView patron_result_summit_text= ((PatronDetailHeaderViewHolder)holder).getPatronResultSummitText();
        TextView patron_withdraw_text= ((PatronDetailHeaderViewHolder)holder).getPatronWithdrawText();

        //header
        TextView patron_title= ((PatronDetailHeaderViewHolder)holder).getPatronTitle();
        ImageView patron_image= ((PatronDetailHeaderViewHolder)holder).getPatronImage();
        TextView patron_body= ((PatronDetailHeaderViewHolder)holder).getPatronBody();
        TextView max_point= ((PatronDetailHeaderViewHolder)holder).getMaxPoint();

        CircleImageView writter_photo= ((PatronDetailHeaderViewHolder)holder).getWritterPhoto();
        TextView writter_name= ((PatronDetailHeaderViewHolder)holder).getWritterName();
        TextView patron_updated= ((PatronDetailHeaderViewHolder)holder).getPatronUpdated();

        TagGroup tag_group= ((PatronDetailHeaderViewHolder)holder).getTagGroup();

        TextView achieve_ratio= ((PatronDetailHeaderViewHolder)holder).getAchieveRatio();
        TextView patron_count= ((PatronDetailHeaderViewHolder)holder).getPatronCount();


        TextView patron_detail_info= ((PatronDetailHeaderViewHolder)holder).getPartronDetailInfo();
        LinearLayout patron_detail_info_edit= ((PatronDetailHeaderViewHolder)holder).getPatronDetailInfoEdit();

        LinearLayout patron_tag_layout= ((PatronDetailHeaderViewHolder)holder).getPatronTagLayout();
        TextView current_point_oninfo = ((PatronDetailHeaderViewHolder)holder).getCurrentPointOnInfo();

        TextView price = ((PatronDetailHeaderViewHolder)holder).getPrice();

        ImageView item_image = ((PatronDetailHeaderViewHolder)holder).getItemImage();
        TextView item_name = ((PatronDetailHeaderViewHolder)holder).getItemName();


        if(parentObject.getString("content_type").equals("goods")){

            ParseObject itemOb = parentObject.getParseObject("item");

            functionBase.generalImageLoading(item_image, itemOb, requestManager);
            item_name.setText( itemOb.getString("name") );

            price.setText(functionBase.makeComma( parentObject.getInt("price") ));

            //current_point_oninfo.setText( functionBase.makeComma( parentObject.getInt("patron_count") ) + " 명" );
            current_point_onprogress.setText(functionBase.makeComma( parentObject.getInt("patron_count") ) + " 명");
            max_point.setText(functionBase.makeComma(parentObject.getInt("target_amount")) + " 명");
            max_point_onprogress.setText(functionBase.makeComma(parentObject.getInt("target_amount")) + " 명");

            if(parentObject.getInt("target_amount") == 0){

                achieve_ratio.setText( String.valueOf( 0 ) + "%" );

            } else {

                achieve_ratio.setText( String.valueOf( parentObject.getInt("patron_count") / parentObject.getInt("target_amount") * 100 ) + "%" );

            }

            int currentProgress = functionBase.progressMaker( parentObject.getInt("patron_count"), parentObject.getInt("target_amount"));

            int progressBarInt = currentProgress;

            if(currentProgress > 100){

                progressBarInt = 100;

            }

            progressbar.setProgress(progressBarInt);
            achieve_ratio.setText(String.valueOf(currentProgress) + "%");


        } else {

            if(open_type != null){

                if(parentObject.get("patron_flag") != null){

                    if(parentObject.getBoolean("patron_flag")){

                        open_type.setText("후원자 독점");

                    } else {

                        open_type.setText("전체 공개");
                    }


                } else {

                    if(parentObject.get("open_type") != null){

                        switch (parentObject.getString("open_type")){

                            case "openToPatron":

                                open_type.setText("후원자 공개");

                                break;

                            case "openToAll":

                                open_type.setText("전체 공개");

                                break;

                        }

                    }

                }

            }


            if(min_box != null){

                min_box.setText(functionBase.makeComma(parentObject.getInt("min_box")));

            }


            if(patron_detail_info != null){

                if(parentObject.get("reward_exist") != null){

                    if(parentObject.getBoolean("reward_exist")){

                        patron_detail_info.setText("있음");

                    } else {

                        patron_detail_info.setText("없음");

                    }

                }

            }

            current_point_oninfo.setText( functionBase.makeComma( parentObject.getInt("achieve_amount") ) + " BOX" );
            current_point_onprogress.setText(functionBase.makeComma( parentObject.getInt("achieve_amount") ) + " BOX");
            max_point.setText(functionBase.makeComma(parentObject.getInt("target_amount")) + " BOX");
            max_point_onprogress.setText(functionBase.makeComma(parentObject.getInt("target_amount")) + " BOX");

            if(profit_share_ratio != null){

                profit_share_ratio.setText(String.valueOf(parentObject.getInt("profit_share_ratio")) + "%");

            }

            if(parentObject.getInt("target_amount") == 0){

                achieve_ratio.setText( String.valueOf( 0 ) + "%" );

            } else {

                achieve_ratio.setText( String.valueOf( parentObject.getInt("achieve_amount") / parentObject.getInt("target_amount") * 100 ) + "%" );

            }

            int currentProgress = functionBase.progressMaker( parentObject.getInt("achieve_amount"), parentObject.getInt("target_amount"));

            int progressBarInt = currentProgress;

            if(currentProgress > 100){

                progressBarInt = 100;

            }

            progressbar.setProgress(progressBarInt);
            achieve_ratio.setText(String.valueOf(currentProgress) + "%");


        }

        patron_count.setText( functionBase.makeComma( parentObject.getInt("patron_count") ) + "명" );


        patron_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PatronUserActivity.class);
                intent.putExtra("patronId", parentObject.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });




        if(patron_type != null){

            if(parentObject.get("content_type") != null){

                if(parentObject.getString("content_type").equals("commission")){

                    patron_type.setText( "커미션" );

                } else if(parentObject.getString("content_type").equals("goods")){

                    patron_type.setText( "굿즈제작" );

                } else if(parentObject.getString("content_type").equals("extra")){

                    patron_type.setText( "기타" );

                } else if(parentObject.getString("content_type").equals("post")){

                    patron_type.setText( "포스트" );

                } else if(parentObject.getString("content_type").equals("webtoon")){

                    patron_type.setText( "웹툰" );

                } else if(parentObject.getString("content_type").equals("album")){

                    patron_type.setText( "사진첩" );

                } else if(parentObject.getString("content_type").equals("novel")){

                    patron_type.setText( "웹소설" );

                } else if(parentObject.getString("content_type").equals("youtube")){

                    patron_type.setText( "영상" );

                }



            }

        }

        functionBase.patronManagerButtonSetOnclickFunction(patron_detail_info_edit, patron_stop, patron_cancel, patron_result_summit, patron_result_summit_text , patron_withdraw, patron_withdraw_text , parentObject);

        patron_title.setText(parentObject.getString("title"));
        patron_body.setText(parentObject.getString("body"));

        functionBase.generalImageLoading(patron_image, parentObject, requestManager);

        if(parentObject.get("patron_detail_info") == null){

            patron_detail_info.setText(R.string.patron_detail_default_text);

        } else {

            patron_detail_info.setText(parentObject.getString("patron_detail_info"));

        }

        if(currentUser == null){

            patron_manager_layout.setVisibility(View.GONE);

        } else {

            if(currentUser.getObjectId().equals(parentObject.getParseObject("user").getObjectId()) ){

                patron_manager_layout.setVisibility(View.VISIBLE);

            } else {

                patron_manager_layout.setVisibility(View.GONE);
            }

        }

        tag_group.setTags( functionBase.jsonArrayToStringArray(parentObject.getJSONArray("tag_array")) );



        if( parentObject.get("user") != null){

            final ParseObject userOb = parentObject.getParseObject("user");

            functionBase.profileImageLoading(writter_photo, userOb, requestManager);
            functionBase.profileNameLoading(writter_name, userOb);

            patron_updated.setText(functionBase.dateToString(userOb.getCreatedAt()));

            if(total_profit_share != null){

                total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + " BOX");

            }

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });

        }



    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {

        //return items.size() + 2;

        return 1;

    }


    public void loadObjects(final int page) {

        final ParseQuery<ParseObject> query = this.queryFactory.create();

        if (this.objectsPerPage > 0 && this.paginationEnabled) {
            this.setPageOnQuery(page, query);
        }

        this.notifyOnLoadingListeners();

        if (page >= objectPages.size()) {
            objectPages.add(page, new ArrayList<ParseObject>());
        }


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> foundObjects, ParseException e) {

                if ((e != null) && ((e.getCode() == ParseException.CONNECTION_FAILED) || (e.getCode() != ParseException.CACHE_MISS))) {

                    hasNextPage = true;

                } else if (foundObjects != null) {

                    // Only advance the page, this prevents second call back from CACHE_THEN_NETWORK to
                    // reset the page.
                    if (page >= currentPage) {
                        currentPage = page;

                        // since we set limit == objectsPerPage + 1
                        hasNextPage = (foundObjects.size() > objectsPerPage);
                    }

                    if (paginationEnabled && foundObjects.size() > objectsPerPage) {
                        // Remove the last object, fetched in order to tell us whether there was a "next page"
                        foundObjects.remove(objectsPerPage);
                    }

                    List<ParseObject> currentPage = objectPages.get(page);
                    currentPage.clear();
                    currentPage.addAll(foundObjects);

                    syncObjectsWithPages();

                    // executes on the UI thread
                    notifyDataSetChanged();
                }

                notifyOnLoadedListeners(foundObjects, e);

            }
        });
    }


    public void loadNextPage() {


        if (items.size() == 0) {

            loadObjects(0);

        } else {

            loadObjects(currentPage + 1);

        }
    }


    public void setObjectsPerPage(int objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }

    private void syncObjectsWithPages() {
        items.clear();
        for (List<ParseObject> pageOfObjects : objectPages) {
            items.addAll(pageOfObjects);
        }
    }



    protected void setPageOnQuery(int page, ParseQuery<ParseObject> query) {
        query.setLimit(this.objectsPerPage);
        query.setSkip(page * this.objectsPerPage);
    }

    public void addOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {
        this.onQueryLoadListeners.add(listener);
    }

    public void removeOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {
        this.onQueryLoadListeners.remove(listener);
    }

    private void notifyOnLoadingListeners() {
        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {
            listener.onLoading();
        }
    }

    private void notifyOnLoadedListeners(List<ParseObject> objects, Exception e) {
        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {
            listener.onLoaded(objects, e);
        }
    }




}
