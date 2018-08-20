package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.AddressClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.AddressListViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemShareViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class AddressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage;

    private ArrayList<JSONObject> items;

    private AddressClickListener addressClickListener;

    public void setAddressClickListener(AddressClickListener addressClickListener){

        this.addressClickListener = addressClickListener;

    }

    public AddressListAdapter(Context context, ArrayList<JSONObject> listArray) {

        Context context1 = context;
        ParseUser currentUser = ParseUser.getCurrentUser();
        this.items = new ArrayList<>();
        this.objectPages = new ArrayList<>();
        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.items = listArray;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listView;

        listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_address_search, parent, false);

        return new AddressListViewHolder(listView);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final JSONObject data = getItem(position);


        TextView postal_text =((AddressListViewHolder)holder).getPostalText();
        TextView road_text =((AddressListViewHolder)holder).getRoadText();
        TextView jibun_text = ((AddressListViewHolder)holder).getJibunText();
        LinearLayout item_layout = ((AddressListViewHolder)holder).getItemLayout();

        try {
            //도로명주소 전체
            String roadAddr = data.getString("roadAddr");
            //도로명주소
            String roadAddrPart1 = data.getString("roadAddrPart1");
            //참고주소
            String roadAddrPart2 = data.getString("roadAddrPart2");
            //지번주소
            String jibunAddr = data.getString("jibunAddr");
            //영문도로명주소
            String engAddr = data.getString("engAddr");
            //우편번호
            String zipNo = data.getString("zipNo");
            //행정구역코드
            String admCd = data.getString("admCd");
            //도로명 코드
            String rnMgtSn = data.getString("rnMgtSn");
            //건물관리번호
            String bdMgtSn = data.getString("bdMgtSn");
            //상세건물명
            String detBdNmList = data.getString("detBdNmList");
            //건물명
            String bdNm = data.getString("bdNm");
            //공동주택여부
            String bdKdcd = data.getString("bdKdcd");
            //시도명
            String siNm = data.getString("siNm");
            //시군구명
            String sggNm = data.getString("sggNm");
            //읍면동면
            String emdNm = data.getString("emdNm");
            //법정리명
            String liNm = data.getString("liNm");
            //도로명
            String rn = data.getString("rn");
            //지하여부
            String udrtYn =data.getString("udrtYn");
            //건물본번
            String buldMnnm = data.getString("buldMnnm");
            //건물부번
            String buldSlno = data.getString("buldSlno");
            //산여부
            String mtYn = data.getString("mtYn");
            //지번본번
            String lnbrMnnm = data.getString("lnbrMnnm");
            //지번부번
            String lnbrSlno = data.getString("lnbrSlno");
            //읍면동일련번호
            String emdNo = data.getString("emdNo");

            postal_text.setText(zipNo);
            road_text.setText(roadAddr);
            jibun_text.setText(jibunAddr);

            item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addressClickListener.onAddressClicked(data);

                }
            });

            Log.d("postal", zipNo);

        } catch (JSONException e) {
            e.printStackTrace();
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


    public JSONObject getItem(int position) {
        return items.get(position);
    }

}
