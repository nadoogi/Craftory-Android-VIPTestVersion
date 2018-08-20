package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.igaworks.adpopcorn.IgawAdpopcorn;
import com.igaworks.adpopcorn.interfaces.IAdPOPcornEventListener;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.support.v4.app.Fragment;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ChargeWebActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;


/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class ChargePointFragment extends Fragment implements BillingProcessor.IBillingHandler, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView current_coin;
    private TextView total_coin;


    private ParseUser currentUser;

    private RewardedVideoAd mAd;


    private TextView ad_status;

    private final int REQUEST = 112;

    // SAMPLE APP CONSTANTS
    private final String ACTIVITY_NUMBER = "activity_num";
    private final String LOG_TAG = "ChargeActivity";


    private BillingProcessor bp;
    private boolean readyToPurchase = false;
    private String uuid;

    private String purchaseType;
    private int selectedSalesItem = 0;

    RelativeLayout button_10000p;
    RelativeLayout button_30000p;
    RelativeLayout button_50000p;
    RelativeLayout button_100000p;

    private FunctionBase functionBase;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_charge_point, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_PHONE_STATE};
            if (!hasPermissions(getActivity(), PERMISSIONS)) {

                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST);

            } else {
                //Do here
                uuid = GetDevicesUUID(getActivity());

            }
        } else {
            //Do here
            uuid = GetDevicesUUID(getActivity());

        }

        if (!BillingProcessor.isIabServiceAvailable(getActivity())) {

            Toast.makeText(getActivity(), "In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16", Toast.LENGTH_SHORT).show();
            ;

        }


        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getActivity());

        current_coin = (TextView) view.findViewById(R.id.current_coin);
        total_coin = (TextView) view.findViewById(R.id.total_coin);

        ImageView charge_image2 = (ImageView) view.findViewById(R.id.charge_image2);
        TextView charge_title2 = (TextView) view.findViewById(R.id.charge_title2);
        TextView charge_description2 = (TextView) view.findViewById(R.id.charge_description2);
        LinearLayout charge_layout2 = (LinearLayout) view.findViewById(R.id.charge_layout2);

        ImageView charge_image3 = (ImageView) view.findViewById(R.id.charge_image3);

        RadioButton creditcard = (RadioButton) view.findViewById(R.id.creditcard);
        RadioButton phone = (RadioButton) view.findViewById(R.id.phone);

        RadioGroup purchase_method = (RadioGroup) view.findViewById(R.id.purchase_method);

        purchase_method.setOnCheckedChangeListener(this);

        LinearLayout purchase_button = (LinearLayout) view.findViewById(R.id.purchase_button);
        purchase_button.setOnClickListener(this);


        button_10000p = (RelativeLayout) view.findViewById(R.id.button_10000p);
        button_30000p = (RelativeLayout) view.findViewById(R.id.button_30000p);
        button_50000p = (RelativeLayout) view.findViewById(R.id.button_50000p);
        button_100000p = (RelativeLayout) view.findViewById(R.id.button_100000p);

        button_10000p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedSalesItem = 1000;

                button_10000p.setBackgroundResource(R.drawable.button_radius_5dp_point_button);
                button_30000p.setBackgroundColor(Color.TRANSPARENT);
                button_50000p.setBackgroundColor(Color.TRANSPARENT);
                button_100000p.setBackgroundColor(Color.TRANSPARENT);


            }
        });


        button_30000p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedSalesItem = 3000;

                button_30000p.setBackgroundResource(R.drawable.button_radius_5dp_point_button);
                button_10000p.setBackgroundColor(Color.TRANSPARENT);
                button_50000p.setBackgroundColor(Color.TRANSPARENT);
                button_100000p.setBackgroundColor(Color.TRANSPARENT);

            }
        });


        button_50000p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedSalesItem = 5000;

                button_50000p.setBackgroundResource(R.drawable.button_radius_5dp_point_button);
                button_30000p.setBackgroundColor(Color.TRANSPARENT);
                button_10000p.setBackgroundColor(Color.TRANSPARENT);
                button_100000p.setBackgroundColor(Color.TRANSPARENT);

            }
        });


        button_100000p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedSalesItem = 10000;

                button_100000p.setBackgroundResource(R.drawable.button_radius_5dp_point_button);
                button_30000p.setBackgroundColor(Color.TRANSPARENT);
                button_50000p.setBackgroundColor(Color.TRANSPARENT);
                button_10000p.setBackgroundColor(Color.TRANSPARENT);

            }
        });


        //클라우드 코드로 구현

        if(currentUser != null){

            HashMap<String, Object> params = new HashMap<>();

            params.put("key", currentUser.getSessionToken());

            ParseCloud.callFunctionInBackground("key_check", params, new FunctionCallback<Map<String, Object>>() {

                public void done(Map<String, Object> mapObject, ParseException e) {

                    if (e == null) {

                        Log.d("parse result", mapObject.toString());

                        if (mapObject.get("status").toString().equals("true")) {

                            String LICENSE_KEY = mapObject.get("license").toString();
                            String MERCHANT_ID = mapObject.get("merchant").toString();

                            Log.d("l", LICENSE_KEY);
                            Log.d("m", MERCHANT_ID);

                            bp = new BillingProcessor(getActivity(), LICENSE_KEY, MERCHANT_ID, ChargePointFragment.this);
                            bp.initialize();


                        } else {


                        }

                    } else {
                        Log.d("error", "error");
                        e.printStackTrace();
                    }
                }
            });

        }


        charge_title2.setText("IGA 보상 광고 충전");
        charge_description2.setText("앱다운로드, 미션수행을 하고 BOX 충전하기");


        charge_layout2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //오퍼월을 노출합니다.
                IgawAdpopcorn.openOfferWall(getActivity());

            }
        });

        IgawAdpopcorn.setEventListener(getActivity(), new IAdPOPcornEventListener() {
            @Override
            public void OnClosedOfferWallPage() {

                myStatusUpdate(currentUser);

                //TastyToast.makeText(getApplicationContext(), String.valueOf(updatedAmount) + "코인이 적립 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            }
        });

        myStatusUpdate(currentUser);


    }


    @Override
    public void onResume() {
        super.onResume();

        if (currentUser != null) {

            myStatusUpdate(currentUser);

        }

    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Do here
                    uuid = GetDevicesUUID(getActivity());

                } else {
                    Toast.makeText(getActivity(), "The app was not allowed to write to your storage.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }







    private void myStatusUpdate(ParseUser user) {

        try {

            ParseObject pointObject = user.getParseObject("point").fetch();

            int currentCoin = pointObject.getInt("current_point");
            int totalCoin = pointObject.getInt("total_point");

            current_coin.setText(functionBase.makeComma(currentCoin));
            total_coin.setText(functionBase.makeComma(totalCoin));

        } catch (ParseException e) {

            e.printStackTrace();

        }


    }



    @Override
    public void onProductPurchased(final String productId, TransactionDetails details) {

        Log.d("hello1", details.toString());
        Log.d("hello2", productId);

        if (bp.isValidTransactionDetails(details)) {


            if (bp.isPurchased(productId)) {

                //데모용 실제 코드는 노드에서 구현
                switch (productId) {

                    case "point_purchase_10000":

                        ParseObject pointMOb = new ParseObject("PointManager");
                        pointMOb.put("from", "purchase");
                        pointMOb.put("type", "google_purchase");
                        pointMOb.put("amount", 10000);
                        pointMOb.put("user", currentUser);
                        pointMOb.put("status", true);
                        pointMOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {

                                    currentUser.increment("current_point", 10000);
                                    currentUser.increment("total_point", 10000);
                                    currentUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            bp.consumePurchase(productId);
                                            TastyToast.makeText(getActivity(), "10,000P가 충전되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                        }
                                    });

                                } else {

                                    e.printStackTrace();
                                }

                            }
                        });

                        break;

                    case "point_purchase_30000":

                        ParseObject pointMOb2 = new ParseObject("PointManager");
                        pointMOb2.put("from", "purchase");
                        pointMOb2.put("type", "google_purchase");
                        pointMOb2.put("amount", 30000);
                        pointMOb2.put("user", currentUser);
                        pointMOb2.put("status", true);
                        pointMOb2.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {

                                    currentUser.increment("current_point", 30000);
                                    currentUser.increment("total_point", 30000);
                                    currentUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            bp.consumePurchase(productId);
                                            TastyToast.makeText(getActivity(), "30,000P가 충전되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                        }
                                    });

                                } else {

                                    e.printStackTrace();
                                }

                            }
                        });

                        break;


                    case "point_purchase_50000":

                        ParseObject pointMOb3 = new ParseObject("PointManager");
                        pointMOb3.put("from", "purchase");
                        pointMOb3.put("type", "google_purchase");
                        pointMOb3.put("amount", 50000);
                        pointMOb3.put("user", currentUser);
                        pointMOb3.put("status", true);
                        pointMOb3.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {

                                    currentUser.increment("current_point", 50000);
                                    currentUser.increment("total_point", 50000);
                                    currentUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            bp.consumePurchase(productId);
                                            TastyToast.makeText(getActivity(), "50,000P가 충전되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                        }
                                    });

                                } else {

                                    e.printStackTrace();
                                }

                            }
                        });


                        break;

                }

            }


        } else {

            Toast.makeText(getActivity(), "승인되지 않은 구매 입니다. 불법적인 구매시도는 법적으로 처벌 받을 수 있습니다. 절대 시도하지 않으시길 부탁 드립니다.", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onPurchaseHistoryRestored() {

        Log.d("hello2", "hello google in app billing");

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

        Log.d("hello3", "hello google in app billing");

    }

    @Override
    public void onBillingInitialized() {

        Log.d("hello4", "hello google in app billing");
        SkuDetails item = bp.getPurchaseListingDetails("point_purchase");
        Log.d("item", item.priceText);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    @Override
    public void onDestroy() {

        if (bp != null) {

            bp.release();

        }


        super.onDestroy();
    }

    private String GetDevicesUUID(Context mContext) {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());

            return deviceUuid.toString();

        }

        return null;

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

        switch (i){

            case R.id.creditcard:

                Toast.makeText(getActivity(), "신용카드", Toast.LENGTH_SHORT).show();
                purchaseType = "creditcard";

                break;

            case R.id.phone:

                Toast.makeText(getActivity(), "휴대전화", Toast.LENGTH_SHORT).show();
                purchaseType = "phone";

                break;

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.purchase_button:

                //결제
                if(purchaseType == null){

                    TastyToast.makeText(getActivity(), "결제 방식을 선택하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else if(selectedSalesItem == 0){

                    TastyToast.makeText(getActivity(), "충전 할 포인트를 선택하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else {

                    Intent intent = new Intent(getActivity(), ChargeWebActivity.class);
                    intent.putExtra("amount", String.valueOf(selectedSalesItem));
                    intent.putExtra("type", purchaseType);
                    startActivity(intent);

                }

                break;
        }

    }

}
