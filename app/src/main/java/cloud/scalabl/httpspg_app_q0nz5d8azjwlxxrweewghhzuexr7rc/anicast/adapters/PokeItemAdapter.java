package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.PokeItemSelectListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.PokeListItemViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class PokeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    public ArrayList<ParseObject> items;
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages;
    private int objectsPerPage = 25;
    private boolean paginationEnabled;
    private boolean hasNextPage;
    private int currentPage;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;

    private final int patronType = 0;
    private final int postType = 1;

    private ImageView preview_image;
    private TextView action;
    private TextView target;
    private FunctionBase functionBase;

    private PokeItemSelectListener pokeItemSelectListener;

    public void setPokeItemSelectListener(PokeItemSelectListener pokeItemSelectListener){

        this.pokeItemSelectListener = pokeItemSelectListener;

    }


    public PokeItemAdapter(Context context, RequestManager requestManager) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.items = new ArrayList<>();
        this.objectPages = new ArrayList<>();
        this.currentPage = 0;
        this.paginationEnabled = true;
        this.hasNextPage = true;
        this.onQueryLoadListeners = new ArrayList<>();
        this.functionBase = new FunctionBase(context);

        String adminUser = "IbovvfkgaZ";
        final ArrayList<String> queryArray = new ArrayList<>();
        queryArray.add(adminUser);

        if(currentUser != null){

            queryArray.add(currentUser.getObjectId());

        }

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("PokeItem");
                query.whereEqualTo("status", true);
                query.whereContainedIn("userId", queryArray);
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View pokeItemView;

        pokeItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_poke_list, parent, false);

        return new PokeListItemViewHolder(pokeItemView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject pokeItemOb = getItem(position);

        ImageView image = ((PokeListItemViewHolder)holder).getImage();
        TextView title = ((PokeListItemViewHolder)holder).getTitle();

        if(pokeItemOb.get("image") != null){

            requestManager
                    .load( pokeItemOb.getParseFile("image").getUrl() )
                    .into(image);

        } else if(pokeItemOb.get("image_cdn") != null){

            requestManager
                    .load(functionBase.imageUrlBase200 + pokeItemOb.getString("image_cdn"))
                    .into(image);

        } else {

            image.setImageResource(R.drawable.image_background);

        }


        title.setText( pokeItemOb.getString("title") );


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pokeItemSelectListener.onImageSelected(pokeItemOb);

            }
        });


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
