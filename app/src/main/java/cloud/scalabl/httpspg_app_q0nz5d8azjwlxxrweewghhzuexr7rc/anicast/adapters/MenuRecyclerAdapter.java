package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ChargeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.MyInfoActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.MenuMyInfoViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 4. 27..
 */

public class MenuRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();

        public void onLoaded(List<ParseObject> objects, Exception e);
    }


    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners =
            new ArrayList<>();


    int viewType;
    ArrayList<ParseObject> items = new ArrayList<>();
    Context context;
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;

    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private List<List<ParseObject>> objectPages = new ArrayList<>();
    private ParseUser currentUser;

    private RequestManager requestManager;
    private FunctionBase functionBase;



    public MenuRecyclerAdapter(Context context, RequestManager requestManager){

        this.context = context;
        this.requestManager = requestManager;
        this.currentUser = ParseUser.getCurrentUser();
        this.functionBase = new FunctionBase(context);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listView;

        switch (viewType){

            case 0:

                listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_myinfo, parent, false);

                return new MenuMyInfoViewHolder(listView);

            /*
            case 1:

                //listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu, parent, false);
                //MenuViewHolder type1Holder = new MenuViewHolder(listView);

                //return type1Holder;

                break;

            case 2:

                //listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_horizon_item, parent, false);
                //MainListHorizonViewHolder type2Holder = new MainListHorizonViewHolder(listView);

                //return type2Holder;

                break;

            */
            default:

                return null;

        }



    }



    @Override
    public int getItemViewType(int position) {

        return position;
    }


    private static CircleImageView my_profile;
    private static TextView username;
    private static TextView email;
    private static TextView current_point;
    private static LinearLayout point_charge_button ;
    private static LinearLayout myinfo_layout;

    String usernameString;



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject contentOb = getItem(position);

        switch (position){

            case 0:

                my_profile = ((MenuMyInfoViewHolder)holder).getMyPhoto();
                username = ((MenuMyInfoViewHolder)holder).getUsername();
                email = ((MenuMyInfoViewHolder)holder).getEmail();
                current_point = ((MenuMyInfoViewHolder)holder).getCurrentPoint();
                point_charge_button = ((MenuMyInfoViewHolder)holder).getPointChargeButton();
                LinearLayout user_status_button = ((MenuMyInfoViewHolder) holder).getUserStatusButton();
                TextView current_user_status = ((MenuMyInfoViewHolder) holder).getCurrentUserStatus();
                myinfo_layout = ((MenuMyInfoViewHolder)holder).getMyInfoLayout();

                String usernameString;


                myInfoUpdate(username, email,myinfo_layout, my_profile, current_point, point_charge_button);

                break;


            /*

            case 1:


                break;

            case 2:


                break;

            default:


                break;

            */

        }


    }

    private void myInfoUpdate(TextView username, TextView email, LinearLayout myinfo_layout, CircleImageView my_profile, final TextView current_point, LinearLayout point_charge_button){

        if(currentUser == null){

            username.setText("로그인이 필요 합니다.");
            email.setText("");

            myinfo_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        } else {


            String usernameString = "";

            if(currentUser.getString("name") == null){

                usernameString = "닉네임을 입력하세요";

            } else {

                usernameString = currentUser.getString("name");
            }

            username.setText(usernameString);
            email.setText(currentUser.getUsername());

            if(currentUser.get("thumnail") == null){

                my_profile.setImageResource(R.drawable.ic_action_circle_profile);

            } else {

                functionBase.glideFunction(context, currentUser.getParseFile("thumnail").getUrl(), my_profile, requestManager);

            }


            if(currentUser.get("point") != null){

                currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject pointOb, ParseException e) {

                        if(e==null){

                            current_point.setText( String.valueOf(pointOb.getInt("current_point")) );

                        } else {

                            e.printStackTrace();
                        }

                    }
                });

            }



            point_charge_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ChargeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });


            myinfo_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, MyInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });


        }

    }

    public void menuBoardResumeFunction(){

        myInfoUpdate(username, email,myinfo_layout, my_profile, current_point, point_charge_button);

    }


    @Override
    public void onClick(View view) {



    }


    @Override
    public int getItemCount() {

        return 1;

    }

    public ParseObject getItem(int position){

        return null;

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


                if ((e != null) &&
                        ((e.getCode() == ParseException.CONNECTION_FAILED) ||
                                (e.getCode() != ParseException.CACHE_MISS))) {
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
        }
        else {
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
        query.setLimit(this.objectsPerPage + 1);
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
