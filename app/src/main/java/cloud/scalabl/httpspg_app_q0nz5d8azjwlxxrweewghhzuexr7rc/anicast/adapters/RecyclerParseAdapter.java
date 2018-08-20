package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by ssamkyu on 2017. 5. 28..
 */

public class RecyclerParseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static Context context;
    protected static ParseUser currentUser;
    protected static int objectsPerPage;
    protected boolean paginationEnabled;
    protected boolean hasNextPage;
    private static int currentPage;

    public RecyclerParseAdapter(Context context) {

        this.context = context;

        this.objectsPerPage = 25;
        this.paginationEnabled = true;
        this.hasNextPage = true;
        this.currentUser = ParseUser.getCurrentUser();

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public ParseObject getItem(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void loadObjects(final int page) {


    }


    public void setObjectsPerPage(int objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }


}
