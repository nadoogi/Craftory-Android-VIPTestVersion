package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.ChargeHistoryViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class ChargeHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private ArrayList<ParseObject> items = new ArrayList<>();
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages = new ArrayList<>();;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    protected Context context;
    protected ParseUser currentUser;

    private FunctionBase functionBase;

    public ChargeHistoryAdapter(Context context, RequestManager requestManager) {

        RequestManager requestManager1 = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.functionBase = new FunctionBase(context);
        final ArrayList<String> queryArray = new ArrayList<>();
        queryArray.add("purchase");
        queryArray.add("revenue");

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("PointManager");
                query.whereEqualTo("user", currentUser);
                query.whereEqualTo("status", true);
                query.whereNotContainedIn("type",queryArray);
                query.include("user");
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View timelineView;

        timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_charge_history, parent, false);

        return new ChargeHistoryViewHolder(timelineView);



    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject pointManagerOb = getItem(position);

        TextView title = ((ChargeHistoryViewHolder)holder).getTitle();
        final TextView body = ((ChargeHistoryViewHolder)holder).getBody();
        TextView date = ((ChargeHistoryViewHolder)holder).getDate();
        TextView status = ((ChargeHistoryViewHolder)holder).getStatus();
        ImageView point_type_icon = ((ChargeHistoryViewHolder)holder).getPointTypeIcon();

        title.setTextColor(functionBase.likeColor);

        Log.d("type", pointManagerOb.getString("type"));

        if(pointManagerOb.getString("type").equals("paid")){

            point_type_icon.setImageResource(R.drawable.icon_point_pagelogo);

            String titleString = String.valueOf( pointManagerOb.getInt("amount") ) + "BOX 충전";

            title.setText(titleString);

            String bodyString = pointManagerOb.getString("pay_method");

            body.setText(bodyString);

            date.setText(functionBase.dateToStringForDisplay(pointManagerOb.getCreatedAt()));

            status.setText("결제 완료");

        } else if(pointManagerOb.getString("type").equals("reward") || pointManagerOb.getString("type").equals("ad")){

            String titleString = String.valueOf( pointManagerOb.getInt("amount") ) + "BOX 보상";

            title.setText(titleString);

            if(pointManagerOb.getString("from").equals("admob video reward")){

                point_type_icon.setImageResource(R.drawable.icon_reward_video);

                String bodyString = "동영상 보상 광고 시청";

                body.setText(bodyString);

            } else if(pointManagerOb.getString("from").equals("popcorn")){

                point_type_icon.setImageResource(R.drawable.icon_reward);

                String bodyString = "IGA 오퍼월 보상";

                body.setText(bodyString);

            }

            date.setText(functionBase.dateToStringForDisplay(pointManagerOb.getCreatedAt()));

            status.setText("보상 완료");

        } else if(pointManagerOb.getString("type").equals("free")){

            String titleString = String.valueOf( pointManagerOb.getInt("amount") ) + "BOX 무료 지급";

            title.setText(titleString);

            date.setText(functionBase.dateToStringForDisplay(pointManagerOb.getCreatedAt()));

            status.setText("지급 완료");

            if(pointManagerOb.getString("from").equals("daily_reward")){

                point_type_icon.setImageResource(R.drawable.icon_point_dailyreward);

                String bodyString = "일일 방문 무료 BOX";

                body.setText(bodyString);

            } else if(pointManagerOb.getString("from").equals("sign")){

                point_type_icon.setImageResource(R.drawable.icon_point_free_sign);

                String bodyString = "가입 무료 BOX";

                body.setText(bodyString);

            } else if(pointManagerOb.getString("from").equals("comment") || pointManagerOb.getString("from").equals("reply") || pointManagerOb.getString("from").equals("post") ){

                point_type_icon.setImageResource(R.drawable.icon_point_write_reward);

                String bodyString = "글쓰기&댓글&답글 참여 보상";

                body.setText(bodyString);

            }

        } else if(pointManagerOb.getString("type").equals("patron")){

            point_type_icon.setImageResource(R.drawable.icon_point_patron);

            String titleString = String.valueOf( "-" + pointManagerOb.getInt("amount") ) + "BOX 후원금 선물";

            title.setText(titleString);
            title.setTextColor(functionBase.likedColor);

            date.setText(functionBase.dateToStringForDisplay(pointManagerOb.getCreatedAt()));

            status.setText("발송 완료");


            ParseQuery toQuery = ParseQuery.getQuery("_User");
            toQuery.getInBackground(pointManagerOb.getString("to"), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {

                    if(e==null){

                        String bodyString = object.getString("name") + "에게 선물 지급";

                        body.setText(bodyString);

                    } else {

                        e.printStackTrace();
                    }


                }


            });



        } else if(pointManagerOb.getString("type").equals("cheer_accept")){

            point_type_icon.setImageResource(R.drawable.icon_point_cheer);

            String titleString = String.valueOf( pointManagerOb.getInt("amount") ) + "BOX 후원 받음";

            title.setText(titleString);

            date.setText(functionBase.dateToStringForDisplay(pointManagerOb.getCreatedAt()));

            status.setText("받음");


            ParseQuery toQuery = ParseQuery.getQuery("_User");
            toQuery.getInBackground(pointManagerOb.getString("to"), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {

                    if(e==null){

                        String bodyString = object.getString("name") + "에게 후원함";

                        body.setText(bodyString);

                    } else {

                        e.printStackTrace();
                    }


                }


            });

        } else if(pointManagerOb.getString("type").equals("cheer")){

            point_type_icon.setImageResource(R.drawable.icon_point_cheer);

            String titleString = String.valueOf( "-" + pointManagerOb.getInt("amount") ) + "BOX 후원";

            title.setText(titleString);
            title.setTextColor(functionBase.likedColor);

            date.setText(functionBase.dateToStringForDisplay(pointManagerOb.getCreatedAt()));

            status.setText("발송");


            ParseQuery toQuery = ParseQuery.getQuery("_User");
            toQuery.getInBackground(pointManagerOb.getString("to"), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {

                    if(e==null){

                        String bodyString = object.getString("name") + "에게 후원함";

                        body.setText(bodyString);

                    } else {

                        e.printStackTrace();
                    }


                }


            });

        } else if(pointManagerOb.getString("type").equals("patron_withdraw")){

            point_type_icon.setImageResource(R.drawable.icon_point_patron);

            String titleString = String.valueOf( pointManagerOb.getInt("amount") ) + "BOX 회수";

            title.setText(titleString);

            date.setText(functionBase.dateToStringForDisplay(pointManagerOb.getCreatedAt()));

            status.setText("회수 완료");


            ParseQuery postQuery = ParseQuery.getQuery("ArtistPost");
            postQuery.getInBackground(pointManagerOb.getParseObject("patron").getObjectId(), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {

                    if(e==null){

                        String bodyString = object.getString("title") + "에서 BOX를 회수 했습니다.";

                        body.setText(bodyString);

                    } else {

                        e.printStackTrace();
                    }


                }


            });

        } else if(pointManagerOb.getString("type").equals("admin_reward")){

            point_type_icon.setImageResource(R.drawable.icon_point_cheer);

            String titleString = String.valueOf( pointManagerOb.getInt("amount") ) + "BOX 받음";

            title.setText(titleString);

            date.setText(functionBase.dateToStringForDisplay(pointManagerOb.getCreatedAt()));

            String bodyString = "관리자 지급 무료 BOX";

            body.setText(bodyString);

            status.setText("지급 완료");

        }

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public ParseObject getItem(int position) {
        return items.get(position);
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
