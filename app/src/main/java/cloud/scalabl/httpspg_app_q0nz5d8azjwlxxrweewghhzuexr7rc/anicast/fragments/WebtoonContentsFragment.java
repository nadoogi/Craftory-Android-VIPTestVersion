package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.ParseSessionSyncJsInterface;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionLikeDislike;
import im.delight.android.webview.AdvancedWebView;


/**
 * Created by ssamkyu on 2017. 3. 21..
 */

public class WebtoonContentsFragment extends Fragment implements AdvancedWebView.Listener {


    private String contentId;

    private AdvancedWebView content_container;
    private ParseObject contentOb;

    private TextView comment_count;
    private TextView like_count;

    private LinearLayout like_button;

    private LinearLayout option_button;

    private ImageView like_icon;

    private LinearLayout tap_navigation;
    private LinearLayout top_navigation;

    private TextView back_button_text;

    private LinearLayout navigation_button;

    private ParseUser currentUser;
    private FunctionBase functionBase;
    private ParseObject parentOb;

    private Boolean navigationStatus;
    private Boolean bottomStatus;

    private int currentOrder;

    private ParseObject preItem;
    private ParseObject nextItem;
    private ImageView pv_icon;
    private TextView pv_count;

    public WebtoonContentsFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contentId = getArguments().getString("cardId");
        String type = getArguments().getString("type");

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase( (AppCompatActivity) getActivity() ,getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_webtoon_contents, container, false);

        pv_count = (TextView) view.findViewById(R.id.pv_count);
        pv_icon = (ImageView) view.findViewById(R.id.pv_icon);
        pv_icon.setColorFilter(functionBase.whiteColor);


        like_button = (LinearLayout) view.findViewById(R.id.like_button);
        LinearLayout comment_button = (LinearLayout) view.findViewById(R.id.comment_button);

        comment_count = (TextView) view.findViewById(R.id.comment_count);
        like_count = (TextView) view.findViewById(R.id.like_count);

        like_icon = (ImageView) view.findViewById(R.id.like_icon);
        ImageView comment_icon = (ImageView) view.findViewById(R.id.comment_icon);

        LinearLayout patron_button = (LinearLayout) view.findViewById(R.id.patron_button);
        final LinearLayout share_button = (LinearLayout) view.findViewById(R.id.share_button);
        option_button = (LinearLayout) view.findViewById(R.id.option_button);

        ImageView patron_icon = (ImageView) view.findViewById(R.id.patron_icon);
        ImageView share_icon = (ImageView) view.findViewById(R.id.share_icon);
        ImageView option_icon = (ImageView) view.findViewById(R.id.option_icon);

        final LinearLayout pre_layout = (LinearLayout) view.findViewById(R.id.pre_layout);
        ImageView pre = (ImageView) view.findViewById(R.id.pre);

        final LinearLayout next_layout = (LinearLayout) view.findViewById(R.id.next_layout);
        ImageView next = (ImageView) view.findViewById(R.id.next);

        final TextView current_order = (TextView) view.findViewById(R.id.current_order);

        LinearLayout back_button = (LinearLayout) view.findViewById(R.id.back_button);
        back_button_text = (TextView) view.findViewById(R.id.back_button_text);
        ImageView back_button_icon = (ImageView) view.findViewById(R.id.back_icon);

        top_navigation = (LinearLayout) view.findViewById(R.id.top_navigation);
        tap_navigation = (LinearLayout) view.findViewById(R.id.tap_navigation);

        navigation_button = (LinearLayout) view.findViewById(R.id.navigation_button);
        navigation_button.setVisibility(View.GONE);

        patron_button.setVisibility(View.GONE);

        navigationStatus = true;

        int defaultColor = Color.parseColor("#ffffff");

        like_icon.setColorFilter(defaultColor);
        comment_icon.setColorFilter(defaultColor);
        patron_icon.setColorFilter(defaultColor);
        share_icon.setColorFilter(defaultColor);
        option_icon.setColorFilter(defaultColor);
        pre.setColorFilter(defaultColor);
        next.setColorFilter(defaultColor);
        back_button_icon.setColorFilter(defaultColor);



        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent commentIntent = new Intent(getActivity(), CommentActivity.class);
                commentIntent.putExtra("postId", contentId);
                commentIntent.putExtra("type", "webtoon");
                getActivity().startActivity(commentIntent);

            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();

            }
        });

        Boolean likeFlag = false;
        //like_icon.setColorFilter(likeColor);
        //like_count.setTextColor(likeColor);

        content_container = (AdvancedWebView) view.findViewById(R.id.content_container);
        content_container.setListener(getActivity(), this);
        content_container.setLayerType(View.LAYER_TYPE_HARDWARE, null);



        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        ParseQuery cardQuery = ParseQuery.getQuery("ArtistPost");
        cardQuery.include("seriese_parent");
        cardQuery.getInBackground(contentId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e==null){

                    contentOb = object;

                    pv_count.setText(functionBase.makeComma(contentOb.getInt("pv_count")));

                    share_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());

                            builder.title("확인");
                            builder.content("내 타임라인에 공유하시겠습니까?");
                            builder.positiveText("예");
                            builder.negativeText("아니요");
                            builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            });

                            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    HashMap<String, Object> params = new HashMap<>();

                                    params.put("key", currentUser.getSessionToken());
                                    params.put("shareObId", contentOb.getObjectId() );

                                    Date uniqueIdDate = new Date();
                                    String uniqueId = uniqueIdDate.toString();

                                    params.put("uid", uniqueId);

                                    ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                        public void done(Map<String, Object> mapObject, ParseException e) {

                                            if (e == null) {

                                                if(mapObject.get("status").toString().equals("true")){

                                                    TastyToast.makeText(getActivity(), "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                } else {

                                                    TastyToast.makeText(getActivity(), "실패 했어요 ㅜㅜ", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                }

                                            } else {

                                                e.printStackTrace();


                                            }
                                        }
                                    });

                                }
                            });
                            builder.show();

                        }
                    });



                    if(contentOb.getBoolean("seriese_in")){

                        navigation_button.setVisibility(View.VISIBLE);

                    } else {

                        navigation_button.setVisibility(View.GONE);

                    }

                    //네비게이션 강제 제거.. 기능 개발 후 공개
                    //navigation_button.setVisibility(View.GONE);

                    currentOrder = contentOb.getInt("order");
                    current_order.setText(String.valueOf(currentOrder));

                    parentOb = contentOb.getParseObject("seriese_parent");

                    if(parentOb == null){

                        navigation_button.setVisibility(View.GONE);

                    } else {

                        ParseQuery query = parentOb.getRelation("seriese_item").getQuery();
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {

                                if(e==null){

                                    preItem = null;
                                    nextItem = null;

                                    int preInt = currentOrder -1;
                                    int nextInt = currentOrder + 1;

                                    for(int i=0;objects.size() > i;i++){

                                        ParseObject itemOb = objects.get(i);

                                        if( itemOb.getInt("order") == preInt ){

                                            preItem = itemOb;

                                        }

                                        if(itemOb.getInt("order") == nextInt){

                                            nextItem = itemOb;

                                        }

                                    }

                                    if(preItem == null){

                                        pre_layout.setVisibility(View.GONE);

                                    } else {

                                        functionBase.chargeFollowPatronCheckAndFinish(preItem, pre_layout);

                                    }

                                    if(nextItem == null){

                                        next_layout.setVisibility(View.GONE);

                                    } else {

                                        functionBase.chargeFollowPatronCheckAndFinish(nextItem, next_layout);

                                    }

                                    if(nextItem == null && preItem == null){

                                        navigation_button.setVisibility(View.GONE);

                                    }

                                } else {

                                    e.printStackTrace();
                                }

                            }

                        });

                    }


                    if(currentUser != null){

                        option_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                functionBase.OptionButtonFunction(getActivity(), option_button, currentUser, contentOb );

                            }
                        });

                    } else {

                        TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();

                    }

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(getActivity(), contentOb, like_button, like_icon, like_count);
                    likeFunction.likeButtonFunctionViewerStatusCheck();

                    like_count.setText(String.valueOf(contentOb.getInt("like_count")));
                    comment_count.setText(String.valueOf(contentOb.getInt("comment_count")));

                    like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(getActivity(), contentOb, like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunctionViewer();

                        }
                    });

                    if(contentOb.get("title")!= null){

                        back_button_text.setText(contentOb.getString("title"));

                    }

                    content_container.setPadding(0,0,0,0);
                    content_container.setInitialScale(getScale(600));
                    content_container.setVerticalScrollBarEnabled(true);
                    content_container.setHorizontalScrollBarEnabled(false);
                    //content_container.setOverScrollMode(View.OVER_SCROLL_NEVER);
                    content_container.setBackgroundColor(0);


                    ParseSessionSyncJsInterface.applyInterface(content_container, getString(R.string.parse_id), getString(R.string.parse_js_key) );

                    WebSettings webSettings = content_container.getSettings();
                    webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

                    //webSettings.setNeedInitialFocus(true);
                    //webSettings.setJavaScriptEnabled(true);
                    //webSettings.setDomStorageEnabled(true);
                    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                    //webSettings.setLoadsImagesAutomatically(true);
                    //webSettings.setAppCacheEnabled(true);
                    //webSettings.setAllowContentAccess(true);
                    //webSettings.setAllowFileAccess(true);
                    //webSettings.setUseWideViewPort(true);
                    //webSettings.setLoadWithOverviewMode(true);

                    //webSettings.setDisplayZoomControls(true);
                    //webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        //webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

                    }


                    //webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

                    content_container.setWebViewClient(new WebViewClient(){


                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);

                            Log.d("msg", "start");

                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);


                        }
                    });

                    content_container.setWebChromeClient(new WebChromeClient(){

                        @Override
                        public void onProgressChanged(WebView view, int newProgress) {
                            super.onProgressChanged(view, newProgress);



                        }
                    });

                    String url = "https://pg-app-q0nz5d8azjwlxxrweewghhzuexr7rc.scalabl.cloud/webtoonmanager?objectid=" + contentOb.getObjectId();
                    Log.d("url", url);
                    content_container.loadUrl( url , true);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        content_container.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                            @Override
                            public void onScrollChange(View view, int i, int currentY, int i2, int oldY) {

                                if(currentY > oldY){

                                    navigationStatus = false;

                                    top_navigation.setVisibility(View.GONE);
                                    tap_navigation.setVisibility(View.GONE);

                                } else if(currentY == 0) {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            navigationStatus = true;

                                            top_navigation.setVisibility(View.VISIBLE);
                                            tap_navigation.setVisibility(View.VISIBLE);

                                            //여기에 딜레이 후 시작할 작업들을 입력
                                        }
                                    }, 500);



                                } else {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            navigationStatus = true;

                                            top_navigation.setVisibility(View.VISIBLE);
                                            tap_navigation.setVisibility(View.VISIBLE);

                                            //여기에 딜레이 후 시작할 작업들을 입력
                                        }
                                    }, 500);

                                }

                            }
                        });




                    }

                } else {

                    Log.d("query error", "error");
                    e.printStackTrace();

                }



            }

        });


        return view;

    }

    private int getScale(int PIC_WIDTH){

        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = display.getWidth();

        Double val = Double.parseDouble(String.valueOf(width))/Double.parseDouble(String.valueOf(PIC_WIDTH));

        val = val * 100;

        return val.intValue();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        content_container.onResume();


    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        content_container.onPause();
        // ...
        super.onPause();
    }

    @Override
    public void onDestroy() {
        content_container.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        content_container.onActivityResult(requestCode, resultCode, intent);
        // ...
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }


}
