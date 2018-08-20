package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.RankingViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class RequestFollowAdapter extends RecyclerParseAdapter {

    private interface OnQueryLoadListener<ParseObject> {

        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);

    }

    private static ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private static List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private static List<List<ParseObject>> objectPages;
    private static ArrayList<ParseObject> items;
    private static int currentPage;
    private static ParseUser currentUser;
    private static String objectId;

    private static RequestManager requestManager;


    public RequestFollowAdapter(Context context, RequestManager requestManager, String objectId) {

        super(context);
        this.requestManager = requestManager;

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();
        this.currentUser = ParseUser.getCurrentUser();
        this.objectId = objectId;

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = currentUser.getRelation("following").getQuery();
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.include("my_info");
                query.orderByDescending("name");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rankingView;

        rankingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ranking, parent, false);

        return new RankingViewHolder(rankingView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject userOb = getItem(position);

        TextView ranking_text = ((RankingViewHolder)holder).getRankingText();
        CircleImageView ranker_photo = ((RankingViewHolder)holder).getRankerPhoto();
        TextView ranker_name = ((RankingViewHolder)holder).getRankingName();
        TextView gifted_point = ((RankingViewHolder)holder).getGiftedPoint();
        LinearLayout ranking_layout = ((RankingViewHolder)holder).getRankingLayout();

        //기능 추가
        ranking_text.setText(String.valueOf(position + 1));



        if(userOb.get("thumnail") != null){

            requestManager.load( userOb.getParseFile("thumnail").getUrl() ).into(ranker_photo);

        } else {

            requestManager.load( R.drawable.user_profile ).into(ranker_photo);

        }

        if(userOb.get("name") != null){

            ranker_name.setText( userOb.getString("name") );

        } else {

            String[] usernameSplit = userOb.getString("username").split("@");
            String username = usernameSplit[0];

            ranker_name.setText( username );

        }

        gifted_point.setText( String.valueOf( userOb.getParseObject("my_info").getInt("current_gifted_point") ) );

        ranking_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean wrapInScrollView = true;

                final MaterialDialog requestDialog = new MaterialDialog.Builder(context)
                        .customView(R.layout.request_dialog_item, wrapInScrollView)
                        .show();

                View dialogView = requestDialog.getCustomView();

                CircleImageView request_photo = (CircleImageView) dialogView.findViewById(R.id.request_photo);
                TextView request_name = (TextView) dialogView.findViewById(R.id.request_name);
                final TextView request_count = (TextView) dialogView.findViewById(R.id.request_count);
                final TextView request_point = (TextView) dialogView.findViewById(R.id.request_point);
                final TextView my_point = (TextView) dialogView.findViewById(R.id.my_point);
                final EditText gift_point = (EditText) dialogView.findViewById(R.id.gift_point);
                final Button request_button = (Button) dialogView.findViewById(R.id.request_button);
                request_button.setClickable(false);


                if(userOb.get("thumnail") != null){

                    requestManager.load( userOb.getParseFile("thumnail").getUrl() ).into(request_photo);

                } else {

                    requestManager.load( R.drawable.user_profile ).into(request_photo);

                }

                if(userOb.get("name") != null){

                    request_name.setText( userOb.getString("name") );

                } else {

                    String[] usernameSplit = userOb.getString("username").split("@");
                    String username = usernameSplit[0];

                    request_name.setText( username );

                }

                ParseQuery requestQuery = ParseQuery.getQuery("RequestVoice");
                requestQuery.whereEqualTo("target", userOb);
                requestQuery.whereEqualTo("warehouse_objectId", objectId);
                requestQuery.include("warehouse");
                requestQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(final ParseObject requestOb, ParseException e) {

                        request_button.setClickable(true);

                        if(e==null){


                            request_count.setText(String.valueOf( requestOb.getInt("request_count") ));
                            request_point.setText(String.valueOf(requestOb.getInt("request_point") ));
                            my_point.setText(String.valueOf(currentUser.getInt("current_point")));

                            //이미 요청한 경우와 그렇지 않은 경우 체크
                            ParseQuery myRequestQuery = requestOb.getRelation("request_user").getQuery();
                            myRequestQuery.getInBackground(currentUser.getObjectId(), new GetCallback<ParseObject>() {
                                @Override
                                public void done(final ParseObject userOb, ParseException e) {

                                    if(e==null){
                                        //이미 요청한 경우

                                        request_button.setText("요청 완료");
                                        request_button.setClickable(false);

                                        int textColor = Color.parseColor("#ff848484");
                                        request_button.setTextColor(textColor);

                                    } else {
                                        //요청하지 않은 경우
                                        request_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                final String my_gift_point = gift_point.getText().toString();

                                                ParseQuery warehouseQuery = ParseQuery.getQuery("Warehouse");
                                                warehouseQuery.getInBackground(objectId, new GetCallback<ParseObject>() {
                                                    @Override
                                                    public void done(final ParseObject warehouseOb, ParseException e) {

                                                        if(e==null){

                                                            try {

                                                                final int my_gift_point_int = Integer.parseInt(my_gift_point);

                                                                if( currentUser.getInt("current_point") < my_gift_point_int ){

                                                                    TastyToast.makeText(context, "내가 보유한 포인트 내에서 선물 가능 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                                } else if(my_gift_point.length() == 0 || my_gift_point.equals("0")) {

                                                                    final ParseObject pointManager = new ParseObject("PointManager");
                                                                    pointManager.put("from", "request");
                                                                    pointManager.put("user", currentUser);
                                                                    pointManager.put("status", true);
                                                                    pointManager.put("amount", 0);
                                                                    pointManager.put("type", "gift");
                                                                    pointManager.put("to", userOb);
                                                                    pointManager.saveInBackground(new SaveCallback() {
                                                                        @Override
                                                                        public void done(ParseException e) {

                                                                            if(e==null){

                                                                                requestOb.increment("request_count", 1);
                                                                                requestOb.put("warehouse", warehouseOb);
                                                                                requestOb.put("warehouse_objectId", objectId);
                                                                                requestOb.put("target", userOb);

                                                                                ParseRelation pmRelation = requestOb.getRelation("point_manager");
                                                                                pmRelation.add(pointManager);

                                                                                ParseRelation userRelation = requestOb.getRelation("request_user");
                                                                                userRelation.add(currentUser);

                                                                                requestOb.saveInBackground(new SaveCallback() {
                                                                                    @Override
                                                                                    public void done(ParseException e) {

                                                                                        if(e==null){

                                                                                            TastyToast.makeText(context, "요청이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                                            requestDialog.hide();

                                                                                        } else {

                                                                                            e.printStackTrace();

                                                                                        }

                                                                                    }
                                                                                });


                                                                            } else {

                                                                                e.printStackTrace();
                                                                            }


                                                                        }
                                                                    });



                                                                } else {

                                                                    final ParseObject pointManager = new ParseObject("PointManager");
                                                                    pointManager.put("from", "request");
                                                                    pointManager.put("user", currentUser);
                                                                    pointManager.put("status", true);
                                                                    pointManager.put("amount", my_gift_point_int);
                                                                    pointManager.put("type", "gift");
                                                                    pointManager.put("to", userOb);
                                                                    pointManager.saveInBackground(new SaveCallback() {
                                                                        @Override
                                                                        public void done(ParseException e) {

                                                                            if(e==null){

                                                                                requestOb.increment("request_count", 1);
                                                                                requestOb.increment("request_point", my_gift_point_int);
                                                                                requestOb.put("warehouse", warehouseOb);
                                                                                requestOb.put("warehouse_objectId", objectId);
                                                                                requestOb.put("target", userOb);

                                                                                ParseRelation pmRelation = requestOb.getRelation("point_manager");
                                                                                pmRelation.add(pointManager);

                                                                                ParseRelation userRelation = requestOb.getRelation("request_user");
                                                                                userRelation.add(currentUser);

                                                                                requestOb.saveInBackground(new SaveCallback() {
                                                                                    @Override
                                                                                    public void done(ParseException e) {

                                                                                        if(e==null){

                                                                                            currentUser.increment("current_point", -my_gift_point_int);
                                                                                            currentUser.increment("total_point", -my_gift_point_int);
                                                                                            currentUser.saveInBackground(new SaveCallback() {
                                                                                                @Override
                                                                                                public void done(ParseException e) {

                                                                                                    if(e==null){

                                                                                                        TastyToast.makeText(context, "요청이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                                                        requestDialog.hide();

                                                                                                    } else {

                                                                                                        e.printStackTrace();
                                                                                                    }

                                                                                                }
                                                                                            });


                                                                                        } else {

                                                                                            e.printStackTrace();

                                                                                        }

                                                                                    }
                                                                                });


                                                                            } else {

                                                                                e.printStackTrace();
                                                                            }


                                                                        }
                                                                    });

                                                                }

                                                            } catch (Error error){

                                                                TastyToast.makeText(context, "숫자를 입력하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                            }

                                                        } else {

                                                            e.printStackTrace();
                                                        }


                                                    }

                                                });


                                            }
                                        });

                                    }

                                }


                            });


                        } else {

                            e.printStackTrace();
                            request_count.setText(String.valueOf( 0 ));
                            request_point.setText(String.valueOf(0));

                            my_point.setText(String.valueOf(currentUser.getInt("current_point")));

                            //기존에 요청한 사람이 없는 경우
                            request_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    final String my_gift_point = gift_point.getText().toString();

                                    ParseQuery warehouseQuery = ParseQuery.getQuery("Warehouse");
                                    warehouseQuery.getInBackground(objectId, new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(final ParseObject warehouseOb, ParseException e) {

                                            if(e==null){

                                                try {

                                                    final int my_gift_point_int = Integer.parseInt(my_gift_point);

                                                    if( currentUser.getInt("current_point") < my_gift_point_int ){

                                                        TastyToast.makeText(context, "내가 보유한 포인트 내에서 선물 가능 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                    } else if(my_gift_point.length() == 0 || my_gift_point.equals("0")) {

                                                        final ParseObject pointManager = new ParseObject("PointManager");
                                                        pointManager.put("from", "request");
                                                        pointManager.put("user", currentUser);
                                                        pointManager.put("status", true);
                                                        pointManager.put("amount", 0);
                                                        pointManager.put("type", "gift");
                                                        pointManager.put("to", userOb);
                                                        pointManager.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(ParseException e) {

                                                                if(e==null){

                                                                    ParseObject newRequestOb = new ParseObject("RequestVoice");

                                                                    newRequestOb.increment("request_count", 1);
                                                                    newRequestOb.put("warehouse", warehouseOb);
                                                                    newRequestOb.put("warehouse_objectId", objectId);
                                                                    newRequestOb.put("target", userOb);

                                                                    ParseRelation pmRelation = newRequestOb.getRelation("point_manager");
                                                                    pmRelation.add(pointManager);

                                                                    newRequestOb.saveInBackground(new SaveCallback() {
                                                                        @Override
                                                                        public void done(ParseException e) {

                                                                            if(e==null){

                                                                                TastyToast.makeText(context, "요청이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                                            } else {

                                                                                e.printStackTrace();

                                                                            }

                                                                        }
                                                                    });


                                                                } else {

                                                                    e.printStackTrace();
                                                                }


                                                            }
                                                        });



                                                    } else {

                                                        final ParseObject pointManager = new ParseObject("PointManager");
                                                        pointManager.put("from", "request");
                                                        pointManager.put("user", currentUser);
                                                        pointManager.put("status", true);
                                                        pointManager.put("amount", my_gift_point_int);
                                                        pointManager.put("type", "gift");
                                                        pointManager.put("to", userOb);
                                                        pointManager.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(ParseException e) {

                                                                if(e==null){

                                                                    ParseObject newRequestOb = new ParseObject("RequestVoice");

                                                                    newRequestOb.increment("request_count", 1);
                                                                    newRequestOb.increment("request_point", my_gift_point_int);
                                                                    newRequestOb.put("warehouse", warehouseOb);
                                                                    newRequestOb.put("warehouse_objectId", objectId);
                                                                    newRequestOb.put("target", userOb);

                                                                    ParseRelation pmRelation = newRequestOb.getRelation("point_manager");
                                                                    pmRelation.add(pointManager);

                                                                    newRequestOb.saveInBackground(new SaveCallback() {
                                                                        @Override
                                                                        public void done(ParseException e) {

                                                                            if(e==null){

                                                                                currentUser.increment("current_point", -my_gift_point_int);
                                                                                currentUser.increment("total_point", -my_gift_point_int);
                                                                                currentUser.saveInBackground(new SaveCallback() {
                                                                                    @Override
                                                                                    public void done(ParseException e) {

                                                                                        if(e==null){

                                                                                            TastyToast.makeText(context, "요청이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                                                        } else {

                                                                                            e.printStackTrace();
                                                                                        }

                                                                                    }
                                                                                });


                                                                            } else {

                                                                                e.printStackTrace();

                                                                            }

                                                                        }
                                                                    });


                                                                } else {

                                                                    e.printStackTrace();
                                                                }


                                                            }
                                                        });

                                                    }

                                                } catch (Error error){

                                                    TastyToast.makeText(context, "숫자를 입력하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                }

                                            } else {

                                                e.printStackTrace();
                                            }


                                        }

                                    });


                                }
                            });

                        }

                    }

                });


            }
        });


    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ParseObject getItem(int position) {
        return items.get(position);
    }

    @Override
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
                    syncObjectsWithPages(items, objectPages);

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



    public void syncObjectsWithPages(ArrayList<ParseObject> items, List<List<ParseObject>> objectPages) {

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

    public void notifyOnLoadingListeners() {

        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {

            listener.onLoading();

        }

    }

    public void notifyOnLoadedListeners(List<ParseObject> objects, Exception e) {

        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {

            listener.onLoaded(objects, e);

        }

    }



}
