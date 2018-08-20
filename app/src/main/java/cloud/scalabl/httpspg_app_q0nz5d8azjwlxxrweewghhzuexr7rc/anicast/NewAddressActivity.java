package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Function;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyTimelineAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.AddressHistoryFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.DealerTimelineFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryNewFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryPriceFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SearchAddressFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.AddressDataFromFragmentListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.AddressDataFromHistoryFragmentListener;
import cz.msebera.android.httpclient.Header;

public class NewAddressActivity extends AppCompatActivity implements AddressDataFromFragmentListener, AddressDataFromHistoryFragmentListener{

    private TabLayout address_tabs;
    private ViewPager address_container;
    private FragmentPagerAdapter pageAdapter;

    private LinearLayout back_button;
    private TextView back_button_text;

    private String patronId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);

        Intent intent = getIntent();

        patronId = intent.getExtras().getString("patronId");

        address_tabs = (TabLayout) findViewById(R.id.address_tabs);
        address_container = (ViewPager) findViewById(R.id.address_container);

        back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("주소 검색");


        pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private final String[] menuFragmentNames = new String[]{

                    "신규주소",
                    "히스토리"

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        SearchAddressFragment searchAddressFragment = new SearchAddressFragment();
                        searchAddressFragment.setAddressDataFromFragmentListener(NewAddressActivity.this);

                        Bundle addressBundle = new Bundle();
                        addressBundle.putInt("page", position);
                        searchAddressFragment.setArguments(addressBundle);


                        return searchAddressFragment;

                    case 1:

                        AddressHistoryFragment addressHistoryFragment = new AddressHistoryFragment();
                        addressHistoryFragment.setAddressDataFromHistoryFragmentListener(NewAddressActivity.this);

                        Bundle historyBundle = new Bundle();
                        historyBundle.putInt("page", position);
                        addressHistoryFragment.setArguments(historyBundle);

                        return addressHistoryFragment;

                    default:

                        return null;

                }

            }



            @Override
            public int getCount() {
                return menuFragmentNames.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return menuFragmentNames[position];
            }

        };

        address_container.setAdapter(pageAdapter);
        address_tabs.setupWithViewPager(address_container);


    }


    @Override
    public void onAddressClicked(JSONObject data) {

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

            Log.d("roadAddr", roadAddr);
            Log.d("roadAddr1", roadAddrPart1);

            Intent returnIntent = new Intent();

            returnIntent.putExtra("roadAddr", roadAddr);
            returnIntent.putExtra("roadAddrPart1", roadAddrPart1);
            returnIntent.putExtra("roadAddrPart2", roadAddrPart2);
            returnIntent.putExtra("jibunAddr", jibunAddr);
            returnIntent.putExtra("engAddr", engAddr);
            returnIntent.putExtra("zipNo", zipNo);
            returnIntent.putExtra("admCd", admCd);
            returnIntent.putExtra("rnMgtSn", rnMgtSn);
            returnIntent.putExtra("bdMgtSn", bdMgtSn);
            returnIntent.putExtra("detBdNmList", detBdNmList);
            returnIntent.putExtra("bdNm" , bdNm);
            returnIntent.putExtra("bdKdcd", bdKdcd);
            returnIntent.putExtra("siNm", siNm);
            returnIntent.putExtra("sggNm", sggNm);
            returnIntent.putExtra("emdNm", emdNm);
            returnIntent.putExtra("liNm", liNm);
            returnIntent.putExtra("rn", rn);
            returnIntent.putExtra("udrtYn", udrtYn);
            returnIntent.putExtra("buldMnnm", buldMnnm);
            returnIntent.putExtra("buldSlno", buldSlno);
            returnIntent.putExtra("mtYn", mtYn);
            returnIntent.putExtra("lnbrMnnm", lnbrMnnm);
            returnIntent.putExtra("lnbrSlno", lnbrSlno);
            returnIntent.putExtra("emdNo", emdNo);

            setResult(RESULT_OK, returnIntent);

            finish();

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void onAddressClicked(ParseObject data) {

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

        Log.d("roadAddr", roadAddr);
        Log.d("roadAddr1", roadAddrPart1);

        Intent returnIntent = new Intent();

        returnIntent.putExtra("roadAddr", roadAddr);
        returnIntent.putExtra("roadAddrPart1", roadAddrPart1);
        returnIntent.putExtra("roadAddrPart2", roadAddrPart2);
        returnIntent.putExtra("jibunAddr", jibunAddr);
        returnIntent.putExtra("engAddr", engAddr);
        returnIntent.putExtra("zipNo", zipNo);
        returnIntent.putExtra("admCd", admCd);
        returnIntent.putExtra("rnMgtSn", rnMgtSn);
        returnIntent.putExtra("bdMgtSn", bdMgtSn);
        returnIntent.putExtra("detBdNmList", detBdNmList);
        returnIntent.putExtra("bdNm" , bdNm);
        returnIntent.putExtra("bdKdcd", bdKdcd);
        returnIntent.putExtra("siNm", siNm);
        returnIntent.putExtra("sggNm", sggNm);
        returnIntent.putExtra("emdNm", emdNm);
        returnIntent.putExtra("liNm", liNm);
        returnIntent.putExtra("rn", rn);
        returnIntent.putExtra("udrtYn", udrtYn);
        returnIntent.putExtra("buldMnnm", buldMnnm);
        returnIntent.putExtra("buldSlno", buldSlno);
        returnIntent.putExtra("mtYn", mtYn);
        returnIntent.putExtra("lnbrMnnm", lnbrMnnm);
        returnIntent.putExtra("lnbrSlno", lnbrSlno);
        returnIntent.putExtra("emdNo", emdNo);

        setResult(RESULT_OK, returnIntent);

        finish();


    }
}
