package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommercialActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.GifNativeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.GuideActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.IgaDAActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.InterstitialAdActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.MainBoardActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.NovelActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.OriginalIllustActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronDescriptionEditorActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronDetailEditorActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PhotoContentsActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostEditActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostEditIllustActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostEditNovelActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostEditWebtoonActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostEditYoutubeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostingDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.RecommendCreatorActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.RecommendIllustActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.RewardVideoActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.WebtoonContentActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.YoutubeActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.awesomeimagepicker.models.Image;

/**
 * Created by ssamkyu on 2016. 11. 4..
 */

public class FunctionBase {

    private Context context;
    private AppCompatActivity activity;

    public final String daumKey = "1a6077cbd87d880a241da54b9f9e6731";
    public final String DEVELOPER_KEY = "AIzaSyBI_4ULpg5_AAfC9CA-naH1D23J8ayylhA";
    public final String new_image_msg = "새로운 짤방이 등록되었습니다.";

    public final String cloudinaryBaseUrl = "http://res.cloudinary.com/dqn5e8b6u/image/upload";
    public final String cloudinarySize100 = "/c_scale,w_100/";
    public final String cloudinarySize200 = "/c_scale,w_200/";
    public final String cloudinarySize300 = "/c_scale,w_600/";
    public final String cloudinarySize400 = "/c_scale,w_800/";
    public final String cloudinarySize500 = "/c_scale,w_1000/";
    public final String cloudinarySize600 = "/c_scale,w_1200/";
    public final String cloudinarySize1800 = "/c_scale,w_1800/";
    public final String cloudinarySize300Blur = "/c_scale,e_blur:1000,w_300/";

    public String imageUrlBase100 = cloudinaryBaseUrl + cloudinarySize100;
    public String imageUrlBase200 = cloudinaryBaseUrl + cloudinarySize200;
    public String imageUrlBase300 = cloudinaryBaseUrl + cloudinarySize300;
    public String imageUrlBase500 = cloudinaryBaseUrl + cloudinarySize500;
    public String imageUrlBase1800 = cloudinaryBaseUrl + cloudinarySize1800;
    public String imageUrlBase300Blur = cloudinaryBaseUrl + cloudinarySize300Blur;

    public String imageUrlBase600 = cloudinaryBaseUrl + cloudinarySize600;

    public int viewerLikeColor = Color.parseColor("#ffffff");
    public int whiteColor = Color.parseColor("#ffffff");
    public int likeColor = Color.parseColor("#2C1A4E");
    public int likedColor = Color.parseColor("#ea5882");
    public int filterDefaultColor = Color.parseColor("#b2b2b2");
    public int mainColor = Color.parseColor("#FF2C1A4E");
    public int mainColor2 = Color.parseColor("#FF88739A");

    public int likePostColor = Color.parseColor("#2C1A4E");
    public int likedPostColor =  Color.parseColor("#ea5882");

    public int notSelectedColor = Color.parseColor("#b2b2b2");

    public int timelineBackgroundColor = Color.parseColor("#f0f0f0");

    public final String recentFilter = "update_time";
    public final String favorFilter = "total_count";
    public final String createFilter = "createdAt";
    public final String commentFavorFilter = "like_count";
    private ParseUser currentUser;

    public int REQUEST_CODE = 100;
    public int DETAILINFO_REQUEST_CODE = 300;

    public int IMAGE_REQUEST_CODE = 200;
    public int REPRESENT_CODE = 1000;


    public FunctionBase(Context context){

        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();

    }

    public FunctionBase( AppCompatActivity activity, Context context) {

        this.activity = activity;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();

    }

    public void enteranceCheckFunction(AppCompatActivity activity, ParseUser currentUser){

        if(currentUser.get("tag_check") != null){

            Log.d("check", "2");

            if(currentUser.getBoolean("tag_check")){

                Log.d("check", "3");

                if(currentUser.get("creator_check") != null){

                    Log.d("check", "4");

                    if(currentUser.getBoolean("creator_check")){

                        Log.d("check", "5");

                        Intent intent = new Intent(activity, MainBoardActivity.class);
                        activity.startActivity(intent);

                        activity.finish();

                    } else {

                        Intent intent = new Intent(activity, RecommendIllustActivity.class);
                        activity.startActivity(intent);

                        activity.finish();

                    }

                } else {

                    Intent intent = new Intent(activity, RecommendCreatorActivity.class);
                    activity.startActivity(intent);

                    activity.finish();

                }

            } else {

                Intent intent = new Intent(activity, RecommendIllustActivity.class);
                activity.startActivity(intent);

                activity.finish();

            }


        } else {

            Intent intent = new Intent(activity, RecommendIllustActivity.class);
            activity.startActivity(intent);

            activity.finish();

        }

    }

    public void chargeFollowPatronCheck(final ParseObject targetOb, final RelativeLayout target_image){


        if(currentUser != null){


            if(targetOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){
                //본인

                artistPostSetOnClickFunction(targetOb, target_image);

            } else {
                //본인 아님
                targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject sellerOb, ParseException e) {

                        if(e==null){

                            if(targetOb.getBoolean("follow_flag")){
                                //팔로워만
                                if(parseArrayCheck(sellerOb, "follower_array", currentUser.getObjectId())){
                                    //팔로워임 그냥 고~
                                    if(targetOb.getBoolean("charge_flag")){
                                        //결제 필요 확인
                                        Log.d("location", "charge_flag");
                                        currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject fechedPointOb, ParseException e) {

                                                ParseObject commercialOb = targetOb.getParseObject("commercial");
                                                String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                                if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                    //결제 완료

                                                    artistPostSetOnClickFunction(targetOb, target_image);

                                                } else {

                                                    //결제 안됨. 결제 시작
                                                    target_image.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            target_image.setClickable(false);

                                                            Log.d("chargeFollowPatronCheck", String.valueOf(40));
                                                            purchaseItemFunction(targetOb, target_image);

                                                        }
                                                    });

                                                }


                                            }
                                        });

                                    } else if(targetOb.getBoolean("ad_flag")){
                                        //광고보고 보기
                                        Log.d("location", "ad_flag");
                                        target_image.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                adClickFunction(targetOb);

                                            }
                                        });


                                    } else {
                                        //무료
                                        Log.d("location", "free");
                                        Log.d("adFlag", String.valueOf(targetOb.getBoolean("ad_flag")));
                                        artistPostSetOnClickFunction(targetOb, target_image);

                                    }


                                } else {
                                    //암됨
                                    target_image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            TastyToast.makeText(context, "팔로워만 볼수 있어요! 팔로우 해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        }
                                    });

                                }

                            } else if(targetOb.getBoolean("patron_flag")){

                                Log.d("location", "patron_flag");
                                if(targetOb.getString("post_type").equals("patron")){

                                    artistPostSetOnClickFunction(targetOb, target_image);

                                } else {

                                    targetOb.getParseObject("patron").fetchInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject fetchedOb, ParseException e) {

                                            //후원자만
                                            if(parseArrayCheck(fetchedOb, "patron_array", currentUser.getObjectId())){

                                                artistPostSetOnClickFunction(targetOb, target_image);

                                            } else {

                                                TastyToast.makeText(context, "후원자만 볼수 있어요! 후원해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                            }


                                        }
                                    });

                                }

                            } else {

                                //전체
                                if(targetOb.getBoolean("charge_flag")){
                                    //결제 필요 확인
                                    Log.d("adFlag pass", String.valueOf(1));

                                    currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject fechedPointOb, ParseException e) {

                                            ParseObject commercialOb = targetOb.getParseObject("commercial");
                                            String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                            if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                //결제 완료
                                                artistPostSetOnClickFunction(targetOb, target_image);

                                            } else {

                                                //결제 안됨. 결제 시작
                                                target_image.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        Log.d("chargeFollowPatronCheck", String.valueOf(999));

                                                        target_image.setClickable(false);

                                                        purchaseItemFunction(targetOb, target_image);

                                                    }
                                                });

                                            }

                                        }
                                    });



                                } else if (targetOb.getBoolean("ad_flag")){
                                    //광고보고 보기
                                    Log.d("adFlag pass", String.valueOf(2));
                                    target_image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            adClickFunction(targetOb);

                                        }
                                    });


                                } else {
                                    //무료
                                    Log.d("adFlag pass", String.valueOf(3));
                                    Log.d("location", "free2");
                                    artistPostSetOnClickFunction(targetOb, target_image);

                                }

                            }

                        } else {

                            e.printStackTrace();
                        }

                    }
                });

            }

        } else {

            targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject sellerOb, ParseException e) {

                    if(e==null){

                        if(targetOb.getBoolean("follow_flag")){
                            //팔로워만
                            Log.d("location", "follow_flag");
                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    TastyToast.makeText(context, "팔로워만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });



                        } else if(targetOb.getBoolean("patron_flag")){

                            Log.d("location", "patron_flag");
                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    TastyToast.makeText(context, "후원자만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });



                        } else {

                            //전체
                            if(targetOb.getBoolean("charge_flag")){
                                //결제 필요 확인

                                Log.d("location", "charge_flag");
                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        TastyToast.makeText(context, "결제가 필요합니다! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        Intent intent = new Intent(context, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                    }
                                });




                            } else if(targetOb.getBoolean("ad_flag")){
                                //광고보고 보기
                                Log.d("location", "ad_flag");
                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        adClickFunction(targetOb);

                                    }
                                });


                            } else {
                                //무료
                                Log.d("adFlag", String.valueOf(targetOb.getBoolean("ad_flag")));
                                Log.d("location", "free3");
                                artistPostSetOnClickFunction(targetOb, target_image);

                            }

                        }

                    } else {

                        e.printStackTrace();
                    }

                }
            });


        }

    }

    public void chargeFollowPatronCheck(final ParseObject targetOb, final TextView target_image){


        if(currentUser != null){


            if(targetOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){
                //본인

                artistPostSetOnClickFunction(targetOb, target_image);

            } else {
                //본인 아님
                targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject sellerOb, ParseException e) {

                        if(e==null){

                            if(targetOb.getBoolean("follow_flag")){
                                //팔로워만
                                if(parseArrayCheck(sellerOb, "follower_array", currentUser.getObjectId())){
                                    //팔로워임 그냥 고~
                                    if(targetOb.getBoolean("charge_flag")){
                                        //결제 필요 확인
                                        Log.d("location", "charge_flag");
                                        currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject fechedPointOb, ParseException e) {

                                                ParseObject commercialOb = targetOb.getParseObject("commercial");
                                                String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                                if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                    //결제 완료

                                                    artistPostSetOnClickFunction(targetOb, target_image);

                                                } else {

                                                    //결제 안됨. 결제 시작
                                                    target_image.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            target_image.setClickable(false);

                                                            Log.d("chargeFollowPatronCheck", String.valueOf(40));
                                                            purchaseItemFunction(targetOb, target_image);

                                                        }
                                                    });

                                                }


                                            }
                                        });

                                    } else if(targetOb.getBoolean("ad_flag")){
                                        //광고보고 보기
                                        Log.d("location", "ad_flag");
                                        target_image.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                adClickFunction(targetOb);

                                            }
                                        });


                                    } else {
                                        //무료
                                        Log.d("location", "free");
                                        Log.d("adFlag", String.valueOf(targetOb.getBoolean("ad_flag")));
                                        artistPostSetOnClickFunction(targetOb, target_image);

                                    }


                                } else {
                                    //암됨
                                    target_image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            TastyToast.makeText(context, "팔로워만 볼수 있어요! 팔로우 해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        }
                                    });

                                }

                            } else if(targetOb.getBoolean("patron_flag")){

                                Log.d("location", "patron_flag");
                                if(targetOb.getString("post_type").equals("patron")){

                                    artistPostSetOnClickFunction(targetOb, target_image);

                                } else {

                                    targetOb.getParseObject("patron").fetchInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject fetchedOb, ParseException e) {

                                            //후원자만
                                            if(parseArrayCheck(fetchedOb, "patron_array", currentUser.getObjectId())){

                                                artistPostSetOnClickFunction(targetOb, target_image);

                                            } else {

                                                TastyToast.makeText(context, "후원자만 볼수 있어요! 후원해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                            }


                                        }
                                    });

                                }

                            } else {

                                //전체
                                if(targetOb.getBoolean("charge_flag")){
                                    //결제 필요 확인
                                    Log.d("adFlag pass", String.valueOf(1));

                                    currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject fechedPointOb, ParseException e) {

                                            ParseObject commercialOb = targetOb.getParseObject("commercial");
                                            String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                            if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                //결제 완료
                                                artistPostSetOnClickFunction(targetOb, target_image);

                                            } else {

                                                //결제 안됨. 결제 시작
                                                target_image.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        Log.d("chargeFollowPatronCheck", String.valueOf(999));

                                                        target_image.setClickable(false);

                                                        purchaseItemFunction(targetOb, target_image);

                                                    }
                                                });

                                            }

                                        }
                                    });



                                } else if (targetOb.getBoolean("ad_flag")){
                                    //광고보고 보기
                                    Log.d("adFlag pass", String.valueOf(2));
                                    target_image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            adClickFunction(targetOb);

                                        }
                                    });


                                } else {
                                    //무료
                                    Log.d("adFlag pass", String.valueOf(3));
                                    Log.d("location", "free2");
                                    artistPostSetOnClickFunction(targetOb, target_image);

                                }

                            }

                        } else {

                            e.printStackTrace();
                        }

                    }
                });

            }

        } else {

            targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject sellerOb, ParseException e) {

                    if(e==null){

                        if(targetOb.getBoolean("follow_flag")){
                            //팔로워만
                            Log.d("location", "follow_flag");
                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    TastyToast.makeText(context, "팔로워만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });



                        } else if(targetOb.getBoolean("patron_flag")){

                            Log.d("location", "patron_flag");
                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    TastyToast.makeText(context, "후원자만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });



                        } else {

                            //전체
                            if(targetOb.getBoolean("charge_flag")){
                                //결제 필요 확인

                                Log.d("location", "charge_flag");
                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        TastyToast.makeText(context, "결제가 필요합니다! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        Intent intent = new Intent(context, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                    }
                                });




                            } else if(targetOb.getBoolean("ad_flag")){
                                //광고보고 보기
                                Log.d("location", "ad_flag");
                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        adClickFunction(targetOb);

                                    }
                                });


                            } else {
                                //무료
                                Log.d("adFlag", String.valueOf(targetOb.getBoolean("ad_flag")));
                                Log.d("location", "free3");
                                artistPostSetOnClickFunction(targetOb, target_image);

                            }

                        }

                    } else {

                        e.printStackTrace();
                    }

                }
            });


        }

    }

    public void chargeFollowPatronCheck(final ParseObject targetOb, final ImageView target_image){


        if(currentUser != null){


            if(targetOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){
                //본인

                artistPostSetOnClickFunction(targetOb, target_image);

            } else {
                //본인 아님
                targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject sellerOb, ParseException e) {

                        if(e==null){

                            if(targetOb.getBoolean("follow_flag")){
                                //팔로워만
                                if(parseArrayCheck(sellerOb, "follower_array", currentUser.getObjectId())){
                                    //팔로워임 그냥 고~
                                    if(targetOb.getBoolean("charge_flag")){
                                        //결제 필요 확인
                                        Log.d("location", "charge_flag");
                                        currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject fechedPointOb, ParseException e) {

                                                ParseObject commercialOb = targetOb.getParseObject("commercial");
                                                String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                                if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                    //결제 완료

                                                    artistPostSetOnClickFunction(targetOb, target_image);

                                                } else {

                                                    //결제 안됨. 결제 시작
                                                    target_image.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            target_image.setClickable(false);

                                                            Log.d("chargeFollowPatronCheck", String.valueOf(40));
                                                            purchaseItemFunction(targetOb, target_image);

                                                        }
                                                    });

                                                }


                                            }
                                        });

                                    } else if(targetOb.getBoolean("ad_flag")){
                                        //광고보고 보기
                                        Log.d("location", "ad_flag");
                                        target_image.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                adClickFunction(targetOb);

                                            }
                                        });


                                    } else {
                                        //무료
                                        Log.d("location", "free");
                                        Log.d("adFlag", String.valueOf(targetOb.getBoolean("ad_flag")));
                                        artistPostSetOnClickFunction(targetOb, target_image);

                                    }


                                } else {
                                    //암됨
                                    target_image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            TastyToast.makeText(context, "팔로워만 볼수 있어요! 팔로우 해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        }
                                    });

                                }

                            } else if(targetOb.getBoolean("patron_flag")){

                                Log.d("location", "patron_flag");
                                if(targetOb.getString("post_type").equals("patron")){

                                    artistPostSetOnClickFunction(targetOb, target_image);

                                } else {

                                    targetOb.getParseObject("patron").fetchInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject fetchedOb, ParseException e) {

                                            //후원자만
                                            if(parseArrayCheck(fetchedOb, "patron_array", currentUser.getObjectId())){

                                                artistPostSetOnClickFunction(targetOb, target_image);

                                            } else {

                                                TastyToast.makeText(context, "후원자만 볼수 있어요! 후원해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                            }


                                        }
                                    });

                                }

                            } else {

                                //전체
                                if(targetOb.getBoolean("charge_flag")){
                                    //결제 필요 확인
                                    Log.d("adFlag pass", String.valueOf(1));

                                    currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject fechedPointOb, ParseException e) {

                                            ParseObject commercialOb = targetOb.getParseObject("commercial");
                                            String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                            if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                //결제 완료
                                                artistPostSetOnClickFunction(targetOb, target_image);

                                            } else {

                                                //결제 안됨. 결제 시작
                                                target_image.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        Log.d("chargeFollowPatronCheck", String.valueOf(999));

                                                        target_image.setClickable(false);

                                                        purchaseItemFunction(targetOb, target_image);

                                                    }
                                                });

                                            }

                                        }
                                    });



                                } else if (targetOb.getBoolean("ad_flag")){
                                    //광고보고 보기
                                    Log.d("adFlag pass", String.valueOf(2));
                                    target_image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            adClickFunction(targetOb);

                                        }
                                    });


                                } else {
                                    //무료
                                    Log.d("adFlag pass", String.valueOf(3));
                                    Log.d("location", "free2");
                                    artistPostSetOnClickFunction(targetOb, target_image);

                                }

                            }

                        } else {

                            e.printStackTrace();
                        }

                    }
                });

            }

        } else {

            targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject sellerOb, ParseException e) {

                    if(e==null){

                        if(targetOb.getBoolean("follow_flag")){
                            //팔로워만
                            Log.d("location", "follow_flag");
                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    TastyToast.makeText(context, "팔로워만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });



                        } else if(targetOb.getBoolean("patron_flag")){

                            Log.d("location", "patron_flag");
                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    TastyToast.makeText(context, "후원자만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });



                        } else {

                            //전체
                            if(targetOb.getBoolean("charge_flag")){
                                //결제 필요 확인

                                Log.d("location", "charge_flag");
                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        TastyToast.makeText(context, "결제가 필요합니다! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        Intent intent = new Intent(context, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                    }
                                });




                            } else if(targetOb.getBoolean("ad_flag")){
                                //광고보고 보기
                                Log.d("location", "ad_flag");
                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        adClickFunction(targetOb);

                                    }
                                });


                            } else {
                                //무료
                                Log.d("adFlag", String.valueOf(targetOb.getBoolean("ad_flag")));
                                Log.d("location", "free3");
                                artistPostSetOnClickFunction(targetOb, target_image);

                            }

                        }

                    } else {

                        e.printStackTrace();
                    }

                }
            });


        }

    }

    public void chargeFollowPatronCheck(final ParseObject targetOb, final LinearLayout target_image){



        if(currentUser != null){

            if(targetOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                Log.d("chargeFollowPatronCheck", String.valueOf(1));

                artistPostSetOnClickFunction(targetOb, target_image);

            } else {

                Log.d("chargeFollowPatronCheck", String.valueOf(2));

                targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject sellerOb, ParseException e) {

                        if(e==null){

                            Log.d("chargeFollowPatronCheck", String.valueOf(3));

                            if(targetOb.getBoolean("follow_flag")){
                                //팔로워만
                                Log.d("chargeFollowPatronCheck", String.valueOf(4));

                                if(parseArrayCheck(sellerOb, "follower_array", currentUser.getObjectId())){
                                    //팔로워임 그냥 고~
                                    Log.d("chargeFollowPatronCheck", String.valueOf(5));

                                    if(targetOb.getBoolean("charge_flag")){
                                        //결제 필요 확인

                                        Log.d("chargeFollowPatronCheck", String.valueOf(6));

                                        currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject fechedPointOb, ParseException e) {

                                                ParseObject commercialOb = targetOb.getParseObject("commercial");
                                                String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                                if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                    //결제 완료

                                                    artistPostSetOnClickFunction(targetOb, target_image);

                                                } else {

                                                    //결제 안됨. 결제 시작
                                                    target_image.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            target_image.setClickable(false);

                                                            Log.d("chargeFollowPatronCheck", String.valueOf(40));
                                                            purchaseItemFunction(targetOb, target_image);

                                                        }
                                                    });

                                                }


                                            }
                                        });

                                    } else if(targetOb.getBoolean("ad_flag")){
                                        //광고보고 보기
                                        Log.d("location", "ad_flag");
                                        target_image.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                adClickFunction(targetOb);

                                            }
                                        });


                                    } else {
                                        //무료
                                        Log.d("adFlag", String.valueOf(targetOb.getBoolean("ad_flag")));
                                        Log.d("chargeFollowPatronCheck", String.valueOf(12));
                                        artistPostSetOnClickFunction(targetOb, target_image);

                                    }


                                } else {
                                    //암됨
                                    Log.d("chargeFollowPatronCheck", String.valueOf(13));
                                    TastyToast.makeText(context, "팔로워만 볼수 있어요! 팔로우 해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                }

                            } else if(targetOb.getBoolean("patron_flag")){

                                Log.d("chargeFollowPatronCheck", String.valueOf(14));
                                targetOb.getParseObject("patron").fetchInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject fetchedOb, ParseException e) {

                                        //후원자만
                                        if(parseArrayCheck(fetchedOb, "patron_array", currentUser.getObjectId())){

                                            Log.d("chargeFollowPatronCheck", String.valueOf(15));
                                            artistPostSetOnClickFunction(targetOb, target_image);

                                        } else {

                                            Log.d("chargeFollowPatronCheck", String.valueOf(16));
                                            TastyToast.makeText(context, "후원자만 볼수 있어요! 후원해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        }


                                    }
                                });

                            } else {

                                Log.d("chargeFollowPatronCheck", String.valueOf(17));
                                //전체
                                if(targetOb.getBoolean("charge_flag")){
                                    //결제 필요 확인

                                    currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject fechedPointOb, ParseException e) {

                                            ParseObject commercialOb = targetOb.getParseObject("commercial");
                                            String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                            if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                //결제 완료
                                                artistPostSetOnClickFunction(targetOb, target_image);

                                            } else {

                                                //결제 안됨. 결제 시작
                                                target_image.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        Log.d("chargeFollowPatronCheck", String.valueOf(999));

                                                        target_image.setClickable(false);

                                                        purchaseItemFunction(targetOb, target_image);

                                                    }
                                                });

                                            }

                                        }
                                    });



                                } else if(targetOb.getBoolean("ad_flag")){
                                    //광고보고 보기
                                    Log.d("location", "ad_flag");
                                    target_image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            adClickFunction(targetOb);

                                        }
                                    });


                                } else {
                                    //무료

                                    Log.d("chargeFollowPatronCheck", String.valueOf(23));
                                    artistPostSetOnClickFunction(targetOb, target_image);

                                }

                            }

                        } else {

                            e.printStackTrace();
                        }

                    }
                });

            }

        } else {

            Log.d("chargeFollowPatronCheck", String.valueOf(24));
            targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject sellerOb, ParseException e) {

                    if(e==null){

                        Log.d("chargeFollowPatronCheck", String.valueOf(25));
                        if(targetOb.getBoolean("follow_flag")){
                            //팔로워만

                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Log.d("chargeFollowPatronCheck", String.valueOf(26));
                                    TastyToast.makeText(context, "팔로워만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });



                        } else if(targetOb.getBoolean("patron_flag")){

                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Log.d("chargeFollowPatronCheck", String.valueOf(27));

                                    TastyToast.makeText(context, "후원자만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });



                        } else {

                            Log.d("chargeFollowPatronCheck", String.valueOf(28));
                            //전체
                            if(targetOb.getBoolean("charge_flag")){
                                //결제 필요 확인

                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Log.d("chargeFollowPatronCheck", String.valueOf(29));
                                        TastyToast.makeText(context, "결제가 필요합니다! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        Intent intent = new Intent(context, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                    }
                                });




                            } else if(targetOb.getBoolean("ad_flag")){
                                //광고보고 보기
                                Log.d("location", "ad_flag");
                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        adClickFunction(targetOb);

                                    }
                                });


                            } else {
                                //무료
                                Log.d("chargeFollowPatronCheck", String.valueOf(30));
                                artistPostSetOnClickFunction(targetOb, target_image);

                            }

                        }

                    } else {

                        Log.d("chargeFollowPatronCheck", String.valueOf(31));
                        e.printStackTrace();
                    }

                }
            });


        }




    }


    public void chargeFollowPatronCheckAndFinish(final ParseObject targetOb, final LinearLayout target_image){


        if(currentUser != null){

            if(targetOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                Log.d("chargeFollowPatronCheck", String.valueOf(1));

                artistPostSetOnClickFunctionAndFinish(targetOb, target_image);

            } else {

                Log.d("chargeFollowPatronCheck", String.valueOf(2));

                targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject sellerOb, ParseException e) {

                        if(e==null){

                            Log.d("chargeFollowPatronCheck", String.valueOf(3));

                            if(targetOb.getBoolean("follow_flag")){
                                //팔로워만
                                Log.d("chargeFollowPatronCheck", String.valueOf(4));

                                if(parseArrayCheck(sellerOb, "follower_array", currentUser.getObjectId())){
                                    //팔로워임 그냥 고~
                                    Log.d("chargeFollowPatronCheck", String.valueOf(5));

                                    if(targetOb.getBoolean("charge_flag")){
                                        //결제 필요 확인

                                        Log.d("chargeFollowPatronCheck", String.valueOf(6));

                                        currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject fechedPointOb, ParseException e) {

                                                ParseObject commercialOb = targetOb.getParseObject("commercial");
                                                String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                                if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                    //결제 완료

                                                    artistPostSetOnClickFunctionAndFinish(targetOb, target_image);

                                                } else {

                                                    //결제 안됨. 결제 시작
                                                    target_image.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            target_image.setClickable(false);

                                                            Log.d("chargeFollowPatronCheck", String.valueOf(40));
                                                            purchaseItemFunctionAndFinish(targetOb, target_image);

                                                        }
                                                    });

                                                }


                                            }
                                        });

                                    } else if(targetOb.getBoolean("ad_flag")){
                                        //광고보고 보기
                                        Log.d("location", "ad_flag");
                                        target_image.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                adClickFunction(targetOb);

                                                activity.finish();

                                            }
                                        });


                                    } else {
                                        //무료
                                        Log.d("adFlag", String.valueOf(targetOb.getBoolean("ad_flag")));
                                        Log.d("chargeFollowPatronCheck", String.valueOf(12));
                                        artistPostSetOnClickFunctionAndFinish(targetOb, target_image);

                                    }


                                } else {
                                    //암됨
                                    Log.d("chargeFollowPatronCheck", String.valueOf(13));
                                    TastyToast.makeText(context, "팔로워만 볼수 있어요! 팔로우 해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                }

                            } else if(targetOb.getBoolean("patron_flag")){

                                Log.d("chargeFollowPatronCheck", String.valueOf(14));
                                targetOb.getParseObject("patron").fetchInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject fetchedOb, ParseException e) {

                                        //후원자만
                                        if(parseArrayCheck(fetchedOb, "patron_array", currentUser.getObjectId())){

                                            Log.d("chargeFollowPatronCheck", String.valueOf(15));
                                            artistPostSetOnClickFunctionAndFinish(targetOb, target_image);

                                        } else {

                                            Log.d("chargeFollowPatronCheck", String.valueOf(16));
                                            TastyToast.makeText(context, "후원자만 볼수 있어요! 후원해주세요!", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        }


                                    }
                                });

                            } else {

                                Log.d("chargeFollowPatronCheck", String.valueOf(17));
                                //전체
                                if(targetOb.getBoolean("charge_flag")){
                                    //결제 필요 확인

                                    currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject fechedPointOb, ParseException e) {

                                            ParseObject commercialOb = targetOb.getParseObject("commercial");
                                            String commercialId = targetOb.getParseObject("commercial").getObjectId();

                                            if(purchaseCheck( fechedPointOb, "purchase_array" , commercialId )){
                                                //결제 완료
                                                artistPostSetOnClickFunctionAndFinish(targetOb, target_image);

                                            } else {

                                                //결제 안됨. 결제 시작
                                                target_image.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        Log.d("chargeFollowPatronCheck", String.valueOf(999));

                                                        target_image.setClickable(false);

                                                        purchaseItemFunctionAndFinish(targetOb, target_image);

                                                    }
                                                });

                                            }

                                        }
                                    });



                                } else if(targetOb.getBoolean("ad_flag")){
                                    //광고보고 보기
                                    Log.d("location", "ad_flag");
                                    target_image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            adClickFunction(targetOb);

                                            activity.finish();

                                        }
                                    });


                                } else {
                                    //무료

                                    Log.d("chargeFollowPatronCheck", String.valueOf(23));
                                    artistPostSetOnClickFunctionAndFinish(targetOb, target_image);

                                }

                            }

                        } else {

                            e.printStackTrace();
                        }

                    }
                });

            }

        } else {

            Log.d("chargeFollowPatronCheck", String.valueOf(24));
            targetOb.getParseObject("user").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject sellerOb, ParseException e) {

                    if(e==null){

                        Log.d("chargeFollowPatronCheck", String.valueOf(25));
                        if(targetOb.getBoolean("follow_flag")){
                            //팔로워만

                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Log.d("chargeFollowPatronCheck", String.valueOf(26));
                                    TastyToast.makeText(context, "팔로워만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                    activity.finish();

                                }
                            });



                        } else if(targetOb.getBoolean("patron_flag")){

                            target_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Log.d("chargeFollowPatronCheck", String.valueOf(27));

                                    TastyToast.makeText(context, "후원자만 볼수 있어요! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                    activity.finish();

                                }
                            });



                        } else {

                            Log.d("chargeFollowPatronCheck", String.valueOf(28));
                            //전체
                            if(targetOb.getBoolean("charge_flag")){
                                //결제 필요 확인

                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Log.d("chargeFollowPatronCheck", String.valueOf(29));
                                        TastyToast.makeText(context, "결제가 필요합니다! 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        Intent intent = new Intent(context, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                        activity.finish();

                                    }
                                });




                            } else if(targetOb.getBoolean("ad_flag")){
                                //광고보고 보기
                                Log.d("location", "ad_flag");
                                target_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        adClickFunction(targetOb);

                                        activity.finish();

                                    }
                                });


                            } else {
                                //무료
                                Log.d("chargeFollowPatronCheck", String.valueOf(30));
                                artistPostSetOnClickFunctionAndFinish(targetOb, target_image);

                            }

                        }

                    } else {

                        Log.d("chargeFollowPatronCheck", String.valueOf(31));
                        e.printStackTrace();
                    }

                }
            });


        }




    }


    public void artistPostSetOnClickFunction( final ParseObject targetOb, TextView target_layout){

        if(targetOb.getString("post_type").equals("post")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("webtoon")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, WebtoonContentActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());

                    if(targetOb.get("seriese_in") == null){

                        intent.putExtra("seriese_in", false);

                    } else {

                        if(targetOb.getBoolean("seriese_in")){

                            intent.putExtra("seriese_in", true);

                        } else {

                            intent.putExtra("seriese_in", false);

                        }

                    }

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("novel")){

            if(targetOb.getString("progress").equals("start")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 집필을 시작하셨어요. 오픈 될 때까지 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            } else if(targetOb.getString("progress").equals("open")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            } else if(targetOb.getString("progress").equals("finish")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("seriese")){

            if(targetOb.getInt("seriese_count")==0){


                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 연재를 시작하셨어요. 첫 작품이 시작될 때까지 많은 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    }
                });

            } else {


                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, CommercialActivity.class);
                        intent.putExtra("serieseId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("youtube")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, YoutubeActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });

        } else if(targetOb.getString("post_type").equals("patron")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("album")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, GifNativeActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("notice") || targetOb.getString("post_type").equals("event")){


            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });

        }



    }


    public void artistPostSetOnClickFunction( final ParseObject targetOb, LinearLayout target_layout){

        if(targetOb.getString("post_type").equals("post")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("webtoon")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, WebtoonContentActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());

                    if(targetOb.get("seriese_in") == null){

                        intent.putExtra("seriese_in", false);

                    } else {

                        if(targetOb.getBoolean("seriese_in")){

                            intent.putExtra("seriese_in", true);

                        } else {

                            intent.putExtra("seriese_in", false);

                        }

                    }

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("novel")){

            if(targetOb.getString("progress").equals("start")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 집필을 시작하셨어요. 오픈 될 때까지 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            } else if(targetOb.getString("progress").equals("open")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            } else if(targetOb.getString("progress").equals("finish")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("seriese")){

            if(targetOb.getInt("seriese_count")==0){


                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 연재를 시작하셨어요. 첫 작품이 시작될 때까지 많은 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    }
                });

            } else {


                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, CommercialActivity.class);
                        intent.putExtra("serieseId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("youtube")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, YoutubeActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });

        } else if(targetOb.getString("post_type").equals("patron")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("album")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, GifNativeActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("notice") || targetOb.getString("post_type").equals("event")){


            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });

        }



    }


    public void artistPostSetOnClickFunctionAndFinish( final ParseObject targetOb, LinearLayout target_layout){

        if(targetOb.getString("post_type").equals("post")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                    activity.finish();

                }
            });


        } else if(targetOb.getString("post_type").equals("webtoon")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, WebtoonContentActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());

                    if(targetOb.get("seriese_in") == null){

                        intent.putExtra("seriese_in", false);

                    } else {

                        if(targetOb.getBoolean("seriese_in")){

                            intent.putExtra("seriese_in", true);

                        } else {

                            intent.putExtra("seriese_in", false);

                        }

                    }

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                    activity.finish();

                }
            });


        } else if(targetOb.getString("post_type").equals("novel")){

            if(targetOb.getString("progress").equals("start")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 집필을 시작하셨어요. 오픈 될 때까지 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                        activity.finish();

                    }
                });

            } else if(targetOb.getString("progress").equals("open")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                        activity.finish();

                    }
                });

            } else if(targetOb.getString("progress").equals("finish")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                        activity.finish();

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("seriese")){

            if(targetOb.getInt("seriese_count")==0){


                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 연재를 시작하셨어요. 첫 작품이 시작될 때까지 많은 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    }
                });

            } else {


                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, CommercialActivity.class);
                        intent.putExtra("serieseId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                        activity.finish();

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("youtube")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, YoutubeActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                    activity.finish();

                }
            });

        } else if(targetOb.getString("post_type").equals("patron")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                    activity.finish();

                }
            });


        } else if(targetOb.getString("post_type").equals("album")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, GifNativeActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                    activity.finish();

                }
            });


        } else if(targetOb.getString("post_type").equals("notice") || targetOb.getString("post_type").equals("event")){


            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                    activity.finish();

                }
            });

        }



    }


    public void artistPostSetOnClickFunction( final ParseObject targetOb, RelativeLayout target_layout){

        if(targetOb.getString("post_type").equals("post")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("webtoon")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, WebtoonContentActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());

                    if(targetOb.get("seriese_in") == null){

                        intent.putExtra("seriese_in", false);

                    } else {

                        if(targetOb.getBoolean("seriese_in")){

                            intent.putExtra("seriese_in", true);

                        } else {

                            intent.putExtra("seriese_in", false);

                        }

                    }

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("novel")){

            if(targetOb.getString("progress").equals("start")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 집필을 시작하셨어요. 오픈 될 때까지 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            } else if(targetOb.getString("progress").equals("open")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            } else if(targetOb.getString("progress").equals("finish")){

                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("seriese")){

            if(targetOb.getInt("seriese_count")==0){


                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 연재를 시작하셨어요. 첫 작품이 시작될 때까지 많은 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    }
                });

            } else {


                target_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, CommercialActivity.class);
                        intent.putExtra("serieseId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("youtube")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, YoutubeActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });

        } else if(targetOb.getString("post_type").equals("patron")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("album")){

            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, GifNativeActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("notice") || targetOb.getString("post_type").equals("event")){


            target_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });

        }



    }


    public void artistPostSetOnClickFunction(final ParseObject targetOb, final ImageView target_image){

        if(targetOb.getString("post_type").equals("post")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("webtoon")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, WebtoonContentActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());

                    if(targetOb.get("seriese_in") == null){

                        intent.putExtra("seriese_in", false);

                    } else {

                        if(targetOb.getBoolean("seriese_in")){

                            intent.putExtra("seriese_in", true);

                        } else {

                            intent.putExtra("seriese_in", false);

                        }

                    }

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("novel")){

            if(targetOb.getString("progress").equals("start")){

                target_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 집필을 시작하셨어요. 오픈 될 때까지 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            } else if(targetOb.getString("progress").equals("open")){

                target_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            } else if(targetOb.getString("progress").equals("finish")){

                target_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, NovelActivity.class);
                        intent.putExtra("postId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("seriese")){

            if(targetOb.getInt("seriese_count")==0){


                target_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 연재를 시작하셨어요. 첫 작품이 시작될 때까지 많은 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    }
                });

            } else {


                target_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, CommercialActivity.class);
                        intent.putExtra("serieseId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        onClickUniqueCounter(targetOb.getObjectId());

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("youtube")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, YoutubeActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });

        } else if(targetOb.getString("post_type").equals("patron")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("album")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, GifNativeActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });


        } else if(targetOb.getString("post_type").equals("notice") || targetOb.getString("post_type").equals("event")){


            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    onClickUniqueCounter(targetOb.getObjectId());

                }
            });

        }



    }

    public void onClickUniqueCounter(String postId){

        if(currentUser != null){

            HashMap<String, Object> params = new HashMap<>();

            params.put("key", currentUser.getSessionToken());

            Date uniqueIdDate = new Date();
            String uniqueId = uniqueIdDate.toString();

            params.put("uid", uniqueId);
            params.put("postId", postId);

            ParseCloud.callFunctionInBackground("uv_count", params, new FunctionCallback<Map<String, Object>>() {

                public void done(Map<String, Object> mapObject, ParseException e) {

                    if (e == null) {

                        Log.d("tag", mapObject.toString());

                        if(mapObject.get("status").toString().equals("true")){

                            Log.d("uv count", "success");
                            Log.d("uv count", mapObject.get("message").toString());

                        } else {

                            Log.d("uv count", "fail");
                            Log.d("uv count", mapObject.get("message").toString());

                        }

                    } else {

                        Log.d("uv count", "error");
                        e.printStackTrace();

                    }
                }
            });

        } else {

            HashMap<String, Object> params = new HashMap<>();

            Date uniqueIdDate = new Date();
            String uniqueId = uniqueIdDate.toString();

            params.put("uid", uniqueId);
            params.put("postId", postId);

            ParseCloud.callFunctionInBackground("pv_count", params, new FunctionCallback<Map<String, Object>>() {

                public void done(Map<String, Object> mapObject, ParseException e) {

                    if (e == null) {

                        Log.d("tag", mapObject.toString());

                        if(mapObject.get("status").toString().equals("true")){

                            Log.d("pv count", "success");
                            Log.d("pv count", mapObject.get("message").toString());

                        } else {

                            Log.d("pv count", "fail");
                            Log.d("pv count", mapObject.get("message").toString());

                        }

                    } else {

                        Log.d("pv count", "error");
                        e.printStackTrace();

                    }
                }
            });

        }

    }

    private void adClickFunction(ParseObject targetOb){

        //Intent intent = new Intent(context, RewardVideoActivity.class);
        //Intent intent = new Intent(context, InterstitialAdActivity.class);
        Intent intent = new Intent(context, IgaDAActivity.class);
        intent.putExtra("postId", targetOb.getObjectId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        onClickUniqueCounter(targetOb.getObjectId());

    }

    public String makeCategory(String category){

        String result;

        switch (category){

            case "books":

                return "Books";

            case "fashion":

                return "패션용품";

            case "souvenir":

                return "기념품";

            case "badge":

                return "뱃지류";

            case "cushion":

                return "쿠션류";

            case "stationery":

                return "문구/펜시";

            case "event":

                return "이벤트";


        }

        return null;

    }

    public void makeCategoryImageDisplay(String category , ImageView targetImage){

        String result;

        switch (category){

            case "books":

                targetImage.setImageResource(R.drawable.store_book);

                break;

            case "fashion":

                targetImage.setImageResource(R.drawable.store_fashion);

                break;

            case "souvenir":

                targetImage.setImageResource(R.drawable.store_souvinior);

                break;

            case "badge":

                targetImage.setImageResource(R.drawable.store_badge);

                break;

            case "cushion":

                targetImage.setImageResource(R.drawable.store_coushion);

                break;

            case "stationery":

                targetImage.setImageResource(R.drawable.store_stationary);

                break;

            case "event":

                targetImage.setImageResource(R.drawable.store_events);

                break;


        }



    }


    public void purchaseItemFunction(final ParseObject targetOb, final ImageView imageView){

        MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

        builder.title("확인");
        builder.content("구매 하시겠습니까?");
        builder.positiveText("예");
        builder.negativeText("아니요");
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                imageView.setClickable(true);

            }
        });

        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                ParseObject commercialOb = targetOb.getParseObject("commercial");

                commercialOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedCommercialOb, ParseException e) {

                        if(e==null){

                            TastyToast.makeText(context, "구매 중 입니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);



                            HashMap<String, Object> params = new HashMap<>();

                            params.put("key", currentUser.getSessionToken());
                            params.put("price", fetchedCommercialOb.getInt("price") );
                            params.put("product", fetchedCommercialOb.getObjectId());

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("purchase_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            Log.d("result", mapObject.toString());

                                            TastyToast.makeText(context, "구매가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            artistPostIntentFunction(context, targetOb);

                                            imageView.setClickable(true);

                                        } else {

                                            if(mapObject.get("message").toString().contains("no money")){

                                                TastyToast.makeText(context, "보유한 BOX가 부족합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                imageView.setClickable(true);

                                            } else {

                                                TastyToast.makeText(context, mapObject.get("message").toString(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                imageView.setClickable(true);

                                            }


                                        }

                                    } else {

                                        e.printStackTrace();
                                        imageView.setClickable(true);

                                    }
                                }
                            });


                        } else {

                            imageView.setClickable(true);
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
        builder.show();

    }

    public void purchaseItemFunction(final ParseObject targetOb, final TextView imageView){

        MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

        builder.title("확인");
        builder.content("구매 하시겠습니까?");
        builder.positiveText("예");
        builder.negativeText("아니요");
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                imageView.setClickable(true);

            }
        });

        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                ParseObject commercialOb = targetOb.getParseObject("commercial");

                commercialOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedCommercialOb, ParseException e) {

                        if(e==null){

                            TastyToast.makeText(context, "구매 중 입니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);



                            HashMap<String, Object> params = new HashMap<>();

                            params.put("key", currentUser.getSessionToken());
                            params.put("price", fetchedCommercialOb.getInt("price") );
                            params.put("product", fetchedCommercialOb.getObjectId());

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("purchase_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            Log.d("result", mapObject.toString());

                                            TastyToast.makeText(context, "구매가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            artistPostIntentFunction(context, targetOb);

                                            imageView.setClickable(true);

                                        } else {

                                            if(mapObject.get("message").toString().contains("no money")){

                                                TastyToast.makeText(context, "보유한 BOX가 부족합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                imageView.setClickable(true);

                                            } else {

                                                TastyToast.makeText(context, mapObject.get("message").toString(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                imageView.setClickable(true);

                                            }


                                        }

                                    } else {

                                        e.printStackTrace();
                                        imageView.setClickable(true);

                                    }
                                }
                            });


                        } else {

                            imageView.setClickable(true);
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
        builder.show();

    }

    public void purchaseItemFunction(final ParseObject targetOb, final LinearLayout layoutView){

        MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

        builder.title("확인");
        builder.content("구매 하시겠습니까?");
        builder.positiveText("예");
        builder.negativeText("아니요");
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                layoutView.setClickable(true);

            }
        });

        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                ParseObject commercialOb = targetOb.getParseObject("commercial");

                commercialOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedCommercialOb, ParseException e) {

                        if(e==null){

                            TastyToast.makeText(context, "구매 중 입니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            HashMap<String, Object> params = new HashMap<>();

                            params.put("key", currentUser.getSessionToken());
                            params.put("price", fetchedCommercialOb.getInt("price") );
                            params.put("product", fetchedCommercialOb.getObjectId());

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("purchase_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            Log.d("result", mapObject.toString());

                                            TastyToast.makeText(context, "구매가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            artistPostIntentFunction(context, targetOb);

                                            layoutView.setClickable(true);

                                        } else {

                                            if(mapObject.get("message").toString().contains("no money")){

                                                TastyToast.makeText(context, "보유한 BOX가 부족합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                layoutView.setClickable(true);

                                            } else {

                                                TastyToast.makeText(context, mapObject.get("message").toString(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                layoutView.setClickable(true);

                                            }


                                        }

                                    } else {

                                        e.printStackTrace();
                                        layoutView.setClickable(true);

                                    }
                                }
                            });


                        } else {

                            layoutView.setClickable(true);
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
        builder.show();


    }


    public void purchaseItemFunctionAndFinish(final ParseObject targetOb, final LinearLayout layoutView){

        MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

        builder.title("확인");
        builder.content("구매 하시겠습니까?");
        builder.positiveText("예");
        builder.negativeText("아니요");
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                layoutView.setClickable(true);

            }
        });

        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                ParseObject commercialOb = targetOb.getParseObject("commercial");

                commercialOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedCommercialOb, ParseException e) {

                        if(e==null){

                            TastyToast.makeText(context, "구매 중 입니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            HashMap<String, Object> params = new HashMap<>();

                            params.put("key", currentUser.getSessionToken());
                            params.put("price", fetchedCommercialOb.getInt("price") );
                            params.put("product", fetchedCommercialOb.getObjectId());

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("purchase_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            Log.d("result", mapObject.toString());

                                            TastyToast.makeText(context, "구매가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            artistPostIntentFunctionAndFinish(context, targetOb);

                                            layoutView.setClickable(true);

                                        } else {

                                            if(mapObject.get("message").toString().contains("no money")){

                                                TastyToast.makeText(context, "보유한 BOX가 부족합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                layoutView.setClickable(true);

                                            } else {

                                                TastyToast.makeText(context, mapObject.get("message").toString(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                layoutView.setClickable(true);

                                            }


                                        }

                                    } else {

                                        e.printStackTrace();
                                        layoutView.setClickable(true);

                                    }
                                }
                            });


                        } else {

                            layoutView.setClickable(true);
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
        builder.show();


    }


    public void purchaseItemFunction(final ParseObject targetOb, final RelativeLayout layoutView){

        MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

        builder.title("확인");
        builder.content("구매 하시겠습니까?");
        builder.positiveText("예");
        builder.negativeText("아니요");
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                layoutView.setClickable(true);

            }
        });

        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                ParseObject commercialOb = targetOb.getParseObject("commercial");

                commercialOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedCommercialOb, ParseException e) {

                        if(e==null){

                            TastyToast.makeText(context, "구매 중 입니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            HashMap<String, Object> params = new HashMap<>();

                            params.put("key", currentUser.getSessionToken());
                            params.put("price", fetchedCommercialOb.getInt("price") );
                            params.put("product", fetchedCommercialOb.getObjectId());

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("purchase_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            Log.d("result", mapObject.toString());

                                            TastyToast.makeText(context, "구매가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            artistPostIntentFunction(context, targetOb);

                                            layoutView.setClickable(true);

                                        } else {

                                            if(mapObject.get("message").toString().contains("no money")){

                                                TastyToast.makeText(context, "보유한 BOX가 부족합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                layoutView.setClickable(true);

                                            } else {

                                                TastyToast.makeText(context, mapObject.get("message").toString(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                layoutView.setClickable(true);

                                            }


                                        }

                                    } else {

                                        e.printStackTrace();
                                        layoutView.setClickable(true);

                                    }
                                }
                            });


                        } else {

                            layoutView.setClickable(true);
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
        builder.show();


    }


    public void artistPostIntentFunction(final Context context, final ParseObject targetOb){

        if(targetOb.getString("post_type").equals("post")){

            Intent intent = new Intent(context, PostingDetailActivity.class);
            intent.putExtra("postId", targetOb.getObjectId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());


        } else if(targetOb.getString("post_type").equals("webtoon")){

            Intent intent = new Intent(context, WebtoonContentActivity.class);
            intent.putExtra("cardId", targetOb.getObjectId());

            if(targetOb.get("seriese_in") == null){

                intent.putExtra("seriese_in", false);

            } else {

                if(targetOb.getBoolean("seriese_in")){

                    intent.putExtra("seriese_in", true);

                } else {

                    intent.putExtra("seriese_in", false);

                }

            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());


        } else if(targetOb.getString("post_type").equals("novel")){

            if(targetOb.getString("progress").equals("start")){

                TastyToast.makeText(context, "작가님이 막 집필을 시작하셨어요. 오픈 될 때까지 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                Intent intent = new Intent(context, NovelActivity.class);
                intent.putExtra("postId", targetOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                onClickUniqueCounter(targetOb.getObjectId());

            } else if(targetOb.getString("progress").equals("open")){

                Intent intent = new Intent(context, NovelActivity.class);
                intent.putExtra("postId", targetOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                onClickUniqueCounter(targetOb.getObjectId());

            } else if(targetOb.getString("progress").equals("finish")){

                Intent intent = new Intent(context, NovelActivity.class);
                intent.putExtra("postId", targetOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                onClickUniqueCounter(targetOb.getObjectId());

            }


        } else if(targetOb.getString("post_type").equals("seriese")){

            if(targetOb.getInt("seriese_count")==0){

                TastyToast.makeText(context, "작가님이 막 연재를 시작하셨어요. 첫 작품이 시작될 때까지 많은 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

            } else {

                Intent intent = new Intent(context, CommercialActivity.class);
                intent.putExtra("serieseId", targetOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                onClickUniqueCounter(targetOb.getObjectId());

            }


        } else if(targetOb.getString("post_type").equals("youtube")){

            Intent intent = new Intent(context, YoutubeActivity.class);
            intent.putExtra("cardId", targetOb.getObjectId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());

        } else if(targetOb.getString("post_type").equals("patron")){

            Intent intent = new Intent(context, PatronDetailActivity.class);
            intent.putExtra("postId", targetOb.getObjectId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());


        } else if(targetOb.getString("post_type").equals("album")){

            Intent intent = new Intent(context, GifNativeActivity.class);
            intent.putExtra("postId", targetOb.getObjectId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());

        }



    }

    public void artistPostIntentFunctionAndFinish(final Context context, final ParseObject targetOb){

        if(targetOb.getString("post_type").equals("post")){

            Intent intent = new Intent(context, PostingDetailActivity.class);
            intent.putExtra("postId", targetOb.getObjectId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());

            activity.finish();

        } else if(targetOb.getString("post_type").equals("webtoon")){

            Intent intent = new Intent(context, WebtoonContentActivity.class);
            intent.putExtra("cardId", targetOb.getObjectId());

            if(targetOb.get("seriese_in") == null){

                intent.putExtra("seriese_in", false);

            } else {

                if(targetOb.getBoolean("seriese_in")){

                    intent.putExtra("seriese_in", true);

                } else {

                    intent.putExtra("seriese_in", false);

                }

            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());

            activity.finish();

        } else if(targetOb.getString("post_type").equals("novel")){

            if(targetOb.getString("progress").equals("start")){

                TastyToast.makeText(context, "작가님이 막 집필을 시작하셨어요. 오픈 될 때까지 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                Intent intent = new Intent(context, NovelActivity.class);
                intent.putExtra("postId", targetOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                onClickUniqueCounter(targetOb.getObjectId());

                activity.finish();

            } else if(targetOb.getString("progress").equals("open")){

                Intent intent = new Intent(context, NovelActivity.class);
                intent.putExtra("postId", targetOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                onClickUniqueCounter(targetOb.getObjectId());

                activity.finish();

            } else if(targetOb.getString("progress").equals("finish")){

                Intent intent = new Intent(context, NovelActivity.class);
                intent.putExtra("postId", targetOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                onClickUniqueCounter(targetOb.getObjectId());

                activity.finish();

            }


        } else if(targetOb.getString("post_type").equals("seriese")){

            if(targetOb.getInt("seriese_count")==0){

                TastyToast.makeText(context, "작가님이 막 연재를 시작하셨어요. 첫 작품이 시작될 때까지 많은 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

            } else {

                Intent intent = new Intent(context, CommercialActivity.class);
                intent.putExtra("serieseId", targetOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                onClickUniqueCounter(targetOb.getObjectId());

                activity.finish();

            }


        } else if(targetOb.getString("post_type").equals("youtube")){

            Intent intent = new Intent(context, YoutubeActivity.class);
            intent.putExtra("cardId", targetOb.getObjectId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());

            activity.finish();

        } else if(targetOb.getString("post_type").equals("patron")){

            Intent intent = new Intent(context, PatronDetailActivity.class);
            intent.putExtra("postId", targetOb.getObjectId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());

            activity.finish();

        } else if(targetOb.getString("post_type").equals("album")){

            Intent intent = new Intent(context, GifNativeActivity.class);
            intent.putExtra("postId", targetOb.getObjectId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            onClickUniqueCounter(targetOb.getObjectId());

            activity.finish();

        }



    }


    public void artistPostEditSetOnClickFunction(final Context context, final ParseObject targetOb, ImageView target_image){

        Log.d("onclick", "onclickfunction alright");

        if(targetOb.getString("post_type").equals("post")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostEditActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });


        } else if(targetOb.getString("post_type").equals("webtoon")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostEditWebtoonActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());

                    if(targetOb.get("seriese_in") == null){

                        intent.putExtra("seriese_in", false);

                    } else {

                        if(targetOb.getBoolean("seriese_in")){

                            intent.putExtra("seriese_in", true);

                        } else {

                            intent.putExtra("seriese_in", false);

                        }

                    }

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });


        } else if(targetOb.getString("post_type").equals("novel")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TastyToast.makeText(context, "작가님이 막 집필을 시작하셨어요. 오픈 될 때까지 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    Intent intent = new Intent(context, PostEditNovelActivity .class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });


        } else if(targetOb.getString("post_type").equals("seriese")){

            if(targetOb.getInt("seriese_count")==0){


                target_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TastyToast.makeText(context, "작가님이 막 연재를 시작하셨어요. 첫 작품이 시작될 때까지 많은 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    }
                });

            } else {


                target_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, CommercialActivity.class);
                        intent.putExtra("serieseId", targetOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        } else if(targetOb.getString("post_type").equals("youtube")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostEditYoutubeActivity.class);
                    intent.putExtra("cardId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        } else if(targetOb.getString("post_type").equals("patron")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronDetailActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });


        } else if(targetOb.getString("post_type").equals("album")){

            target_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostEditIllustActivity.class);
                    intent.putExtra("postId", targetOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });


        }



    }

    public void artistPostBadgeBuilder(final ParseObject targetOb, LinearLayout post_layout , ImageView post_icon, LinearLayout seriese_layout, ImageView seriese_icon, TextView seriese_text ){

        if(targetOb.getString("post_type").equals("post")){

            if(post_layout != null){

                post_layout.setVisibility(View.VISIBLE);
                post_icon.setImageResource(R.drawable.icon_post);

            }

            if(seriese_layout != null){

                seriese_layout.setVisibility(View.GONE);

            }


        } else if(targetOb.getString("post_type").equals("webtoon")){

            if(post_layout != null){

                post_layout.setVisibility(View.VISIBLE);
                post_icon.setImageResource(R.drawable.icon_webtoon);

            }

            if(seriese_layout != null){

                seriese_layout.setVisibility(View.GONE);

            }


        } else if(targetOb.getString("post_type").equals("novel")){

            if(post_layout != null){

                post_layout.setVisibility(View.VISIBLE);
                post_icon.setImageResource(R.drawable.icon_novel);

            }

            if(seriese_layout != null){

                seriese_layout.setVisibility(View.GONE);

            }


        } else if(targetOb.getString("post_type").equals("seriese")){

            if(post_layout != null){

                post_layout.setVisibility(View.GONE);

            }

            if(seriese_layout != null){

                seriese_layout.setVisibility(View.VISIBLE);

                seriese_text.setText("작품 연재");

                if(targetOb.getString("content_type").equals("post")){

                    seriese_icon.setImageResource(R.drawable.icon_post);

                } else if(targetOb.getString("content_type").equals("webtoon")){

                    seriese_icon.setImageResource(R.drawable.icon_webtoon);

                } else if(targetOb.getString("content_type").equals("novel")){

                    seriese_icon.setImageResource(R.drawable.icon_novel);

                } else if(targetOb.getString("content_type").equals("album")){

                    seriese_icon.setImageResource(R.drawable.icon_album);

                } else if(targetOb.getString("content_type").equals("youtube")){

                    seriese_icon.setImageResource(R.drawable.icon_youtube);

                }

            }

        } else if(targetOb.getString("post_type").equals("youtube")){

            if(post_layout != null){

                post_layout.setVisibility(View.VISIBLE);

            }

            if(post_icon != null){

                post_icon.setImageResource(R.drawable.icon_youtube);

            }

            if(seriese_layout != null){

                seriese_layout.setVisibility(View.GONE);

            }

        } else if(targetOb.getString("post_type").equals("patron")){

            if(post_layout != null){

                post_layout.setVisibility(View.GONE);

            }

            if(seriese_layout != null){

                seriese_layout.setVisibility(View.VISIBLE);

                seriese_text.setText("작품 후원");

                if(targetOb.getString("content_type").equals("post")){

                    seriese_icon.setImageResource(R.drawable.icon_post);

                } else if(targetOb.getString("content_type").equals("webtoon")){

                    seriese_icon.setImageResource(R.drawable.icon_webtoon);

                } else if(targetOb.getString("content_type").equals("novel")){

                    seriese_icon.setImageResource(R.drawable.icon_novel);

                } else if(targetOb.getString("content_type").equals("album")){

                    seriese_icon.setImageResource(R.drawable.icon_album);

                } else if(targetOb.getString("content_type").equals("youtube")){

                    seriese_icon.setImageResource(R.drawable.icon_youtube);

                } else {

                    if(post_layout != null){

                        post_layout.setVisibility(View.GONE);

                    }

                    if(seriese_layout != null){

                        seriese_layout.setVisibility(View.GONE);

                    }
                }

            }


        } else if(targetOb.getString("post_type").equals("album")){

            if(post_layout != null){

                post_layout.setVisibility(View.VISIBLE);
                post_icon.setImageResource(R.drawable.icon_album);

            }

            if(seriese_layout != null){

                seriese_layout.setVisibility(View.GONE);

            }


        } else if(targetOb.getString("post_type").equals("event") || targetOb.getString("post_type").equals("notice")){

            if(post_layout != null){

                post_layout.setVisibility(View.GONE);

            }

            if(seriese_layout != null){

                seriese_layout.setVisibility(View.GONE);

            }

        }



    }


    public String[] tagMaker(List<ParseObject> tagObs){

        ArrayList<String> stringArray = new ArrayList<>();
        ArrayList<Map<String, String>> finalDataArray = new ArrayList<>();

        for(int i=0;tagObs.size()>i;i++){

            String tag = tagObs.get(i).getString("tag");

            if(!stringArray.contains(tag)){

                stringArray.add(tag);

            }

        }

        for(int j=0;stringArray.size()>j;j++){

            int counter = 0;

            for(int k=0;tagObs.size()>k;k++){

                String checkTag = tagObs.get(k).getString("tag");

                if(checkTag.equals(stringArray.get(j))){

                    counter += 1;

                }


            }

            Map<String, String> map = new HashMap<>();
            map.put("tag", stringArray.get(j) );
            map.put("count", String.valueOf(counter));

            finalDataArray.add(map);

        }



        //sort 출연순위 높은 순으로 정렬 빠짐

        String[] tagArray = new String[stringArray.size()];
        tagArray = stringArray.toArray(tagArray);

        return Arrays.copyOfRange(tagArray, 0, 10);
    }

    public void enteranceWithGuideCheckFunction(AppCompatActivity activity, ParseUser currentUser, ParseInstallation currentInstallation){

        if(currentUser.get("tag_check") != null){

            Log.d("check", "2");

            if(currentUser.getBoolean("tag_check")){

                Log.d("check", "3");

                if(currentUser.get("creator_check") != null){

                    Log.d("check", "4");

                    if(currentUser.getBoolean("creator_check")){

                        
                        if(currentInstallation.get("guide_check") != null){

                            if(currentInstallation.getBoolean("guide_check")){

                                Intent intent = new Intent(activity, MainBoardActivity.class);
                                activity.startActivity(intent);

                                activity.finish();

                            } else {

                                Intent intent = new Intent(activity, GuideActivity.class);
                                activity.startActivity(intent);

                                activity.finish();

                            }


                        } else {

                            Intent intent = new Intent(activity, GuideActivity.class);
                            activity.startActivity(intent);

                            activity.finish();

                        }

                    } else {

                        Intent intent = new Intent(activity, RecommendCreatorActivity.class);
                        activity.startActivity(intent);

                        activity.finish();

                    }

                } else {

                    Intent intent = new Intent(activity, RecommendCreatorActivity.class);
                    activity.startActivity(intent);

                    activity.finish();

                }

            } else {

                Intent intent = new Intent(activity, RecommendIllustActivity.class);
                activity.startActivity(intent);

                activity.finish();

            }


        } else {

            Intent intent = new Intent(activity, RecommendIllustActivity.class);
            activity.startActivity(intent);

            activity.finish();

        }

    }

    public void profileNameLoading(TextView name, ParseObject user){

        if(user.get("name") != null){

            name.setText(user.getString("name"));

        } else if(user.get("username") != null){

            name.setText(user.getString("username"));

        }

    }


    public void profileImageLoading(CircleImageView circleImageView, final ParseObject userObject, RequestManager requestManager){

        if(circleImageView != null){



            if(userObject.get("image_cdn") != null){

                String imageUrl = userObject.getString("image_cdn");
                String[] imageUrlArrray = imageUrl.split("/");
                String imageFileName = imageUrlArrray[1];

                requestManager
                        .load(MediaManager.get().url().transformation(new Transformation().width("80").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName))
                        //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .thumbnail(0.3f)
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(circleImageView);

            } else if(userObject.get("pic_url") != null){

                requestManager
                        .load(userObject.getString("pic_url"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(circleImageView);

            } else if(userObject.get("thumnail") != null){

                requestManager
                        .load(userObject.getParseFile("thumnail").getUrl())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(circleImageView);

            } else {

                circleImageView.setImageResource(R.drawable.icon_profile_default);

            }

            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userObject.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });

        }

    };

    public void profileImageLoading(CircleImageView circleImageView, final ParseObject userObject, RequestManager requestManager, String from){

        if(circleImageView != null){

            if(userObject.get("image_cdn") != null){

                final String imageUrl = userObject.getString("image_cdn");
                String[] imageUrlArrray = imageUrl.split("/");
                String imageFileName = imageUrlArrray[1];

                requestManager
                        .load(MediaManager.get().url().transformation(new Transformation().width("80").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName))
                        //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .thumbnail(0.3f)
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(circleImageView);

                circleImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, OriginalIllustActivity.class);
                        intent.putExtra("image_url",  imageUrl);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);

                    }
                });

            } else if(userObject.get("pic_url") != null){

                requestManager
                        .load(userObject.getString("pic_url"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(circleImageView);

            } else if(userObject.get("thumnail") != null){

                requestManager
                        .load(userObject.getParseFile("thumnail").getUrl())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(circleImageView);

            } else {

                circleImageView.setImageResource(R.drawable.icon_profile_default);

            }

        }

    };



    public void generalImageLoading(ImageView imageView, ParseObject object, RequestManager requestManager){



        if(imageView != null){

            if(object.get("image_cdn") != null){

                String imageUrl = object.getString("image_cdn");
                String[] imageUrlArrray = imageUrl.split("/");
                String imageFileName = imageUrlArrray[1];

                requestManager
                        .load(MediaManager.get().url().transformation(new Transformation().width("300").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName))
                        //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .thumbnail(0.3f)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView);




            } else if(object.get("image_youtube") != null){

                requestManager
                        .load( object.getString("image_youtube"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView);

            } else if(object.get("thumbnail") != null){

                if(gifChecker(imageUrlBase300 + object.getString("thumbnail"))){

                    requestManager
                            .load(imageUrlBase300 + object.getString("thumbnail"))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            //.transition(new DrawableTransitionOptions().crossFade())
                            .into(imageView);

                } else {

                    requestManager
                            .load( imageUrlBase300 + object.getString("thumbnail"))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            //.transition(new DrawableTransitionOptions().crossFade())
                            .into(imageView);

                }

            } else if(object.get("image") != null){

                requestManager
                        .load(object.getParseFile("image").getUrl())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView);

            } else {

                imageView.setVisibility(View.GONE);

            }

        }

    }

    public Date yesterday(Date today){

        Calendar todayCal = Calendar.getInstance();
        todayCal.setTime(today);
        todayCal.add(Calendar.DATE, -1);

        return todayCal.getTime();
    }


    public Date tommorow(Date today){

        Calendar todayCal = Calendar.getInstance();
        todayCal.setTime(today);
        todayCal.add(Calendar.DATE, +1);

        return todayCal.getTime();
    }


    public void generalImageLoadingForPost(ImageView imageView, ParseObject object, RequestManager requestManager){



        if(imageView != null){

            if(object.get("image_cdn") != null){

                String imageUrl = object.getString("image_cdn");
                String[] imageUrlArrray = imageUrl.split("/");
                String imageFileName = imageUrlArrray[1];

                requestManager
                        .load(MediaManager.get().url().transformation(new Transformation().width("500").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName))
                        //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .thumbnail(0.3f)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView);




            } else if(object.get("image_youtube") != null){

                requestManager
                        .load( object.getString("image_youtube"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView);

            } else if(object.get("thumbnail") != null){

                if(gifChecker(imageUrlBase300 + object.getString("thumbnail"))){

                    requestManager
                            .load(imageUrlBase300 + object.getString("thumbnail"))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            //.transition(new DrawableTransitionOptions().crossFade())
                            .into(imageView);

                } else {

                    requestManager
                            .load( imageUrlBase300 + object.getString("thumbnail"))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            //.transition(new DrawableTransitionOptions().crossFade())
                            .into(imageView);

                }

            } else if(object.get("image") != null){

                requestManager
                        .load(object.getParseFile("image").getUrl())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView);

            } else {

                imageView.setVisibility(View.GONE);

            }

        }

    }


    public void generalImageLoadingForHeader(ImageView imageView, String image, RequestManager requestManager){



        if(imageView != null){

            String imageUrl = image;
            String[] imageUrlArrray = imageUrl.split("/");
            String imageFileName = imageUrlArrray[1];

            requestManager
                    .load(MediaManager.get().url().transformation(new Transformation().width("500").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName))
                    //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .thumbnail(0.3f)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(imageView);

        }

    }


    public void generalImageLoading(ImageView imageView, ParseObject object, RequestManager requestManager, String size){



        if(imageView != null){

            if(object.get("image_cdn") != null){

                //MediaManager.get().url().transformation(new Transformation().quality("auto").fetchFormat("auto")).generate("pond_reflect.jpg");

                String imageUrl = object.getString("image_cdn");
                String[] imageUrlArrray = imageUrl.split("/");
                String imageFileName = imageUrlArrray[1];

                if(size.equals("500")){

                    Log.d("image size", "500");

                    requestManager
                            .load(MediaManager.get().url().transformation(new Transformation().width("250").quality("auto").fetchFormat("auto")).generate(imageFileName))
                            //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .thumbnail(0.3f)
                            .transition(new DrawableTransitionOptions().crossFade())
                            .into(imageView);

                } else if(size.equals("250")){

                    Log.d("image size", "250" + MediaManager.get().url().transformation(new Transformation().width("250").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName));

                    requestManager
                            .load(MediaManager.get().url().transformation(new Transformation().width("200").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName))
                            //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .thumbnail(0.3f)
                            .transition(new DrawableTransitionOptions().crossFade())
                            .into(imageView);

                }



            } else if(object.get("image_youtube") != null){

                requestManager
                        .load(  object.getString("image_youtube"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView);

            } else if(object.get("thumbnail") != null){

                if(gifChecker(imageUrlBase300 + object.getString("thumbnail"))){

                    requestManager
                            .load(imageUrlBase300 + object.getString("thumbnail"))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            //.transition(new DrawableTransitionOptions().crossFade())
                            .into(imageView);

                } else {

                    requestManager
                            .load( imageUrlBase300 + object.getString("thumbnail"))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            //.transition(new DrawableTransitionOptions().crossFade())
                            .into(imageView);

                }

            } else if(object.get("image") != null){

                requestManager
                        .load(object.getParseFile("image").getUrl())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView);

            } else {

                imageView.setVisibility(View.GONE);

            }

        }

    }


    public void generalImageLoading(ImageView imageView, ParseObject object, RequestManager requestManager, final ProgressBar loading_icon){

        if(imageView != null){

            if(loading_icon != null){

                loading_icon.setVisibility(View.VISIBLE);

            }



            if(object.get("image_cdn") != null){

                //MediaManager.get().url().transformation(new Transformation().quality("auto").fetchFormat("auto")).generate("pond_reflect.jpg");

                String imageUrl = object.getString("image_cdn");
                String[] imageUrlArrray = imageUrl.split("/");
                String imageFileName = imageUrlArrray[1];

                requestManager
                        .load(MediaManager.get().url().transformation(new Transformation().width("300").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .apply(RequestOptions.overrideOf(300, 300))
                        .thumbnail(0.1f)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                if(loading_icon != null){

                                    loading_icon.setVisibility(View.GONE);

                                }

                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if(loading_icon != null){

                                    loading_icon.setVisibility(View.GONE);

                                }
                                return false;
                            }
                        })
                        .into(imageView);




            } else if(object.get("image_youtube") != null){

                requestManager
                        .load( object.getString("image_youtube"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                if(loading_icon != null){

                                    loading_icon.setVisibility(View.GONE);

                                }
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if(loading_icon != null){

                                    loading_icon.setVisibility(View.GONE);

                                }
                                return false;
                            }
                        })
                        .into(imageView);

            } else if(object.get("thumbnail") != null){

                if(gifChecker(imageUrlBase300 + object.getString("thumbnail"))){

                    requestManager
                            .load(imageUrlBase300 + object.getString("thumbnail"))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            //.transition(new DrawableTransitionOptions().crossFade())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    if(loading_icon != null){

                                        loading_icon.setVisibility(View.GONE);

                                    }
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    if(loading_icon != null){

                                        loading_icon.setVisibility(View.GONE);

                                    }
                                    return false;
                                }
                            })
                            .into(imageView);

                } else {

                    requestManager
                            .load( imageUrlBase300 + object.getString("thumbnail"))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            //.transition(new DrawableTransitionOptions().crossFade())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    if(loading_icon != null){

                                        loading_icon.setVisibility(View.GONE);

                                    }
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    if(loading_icon != null){

                                        loading_icon.setVisibility(View.GONE);

                                    }
                                    return false;
                                }
                            })
                            .into(imageView);

                }

            } else if(object.get("image") != null){

                requestManager
                        .load(object.getParseFile("image").getUrl())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        //.transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView);

            } else {

                imageView.setVisibility(View.GONE);

            }

        }

    }

    public void patronManagerButtonSetOnclickFunction(LinearLayout patron_detail_edit, LinearLayout patron_stop, LinearLayout patron_cancel, LinearLayout patron_result_summit, TextView patron_result_summit_text, LinearLayout patron_withdraw,TextView patron_withdraw_text,final ParseObject postOb){

        if(patron_detail_edit != null){

            patron_detail_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronDetailEditorActivity.class);
                    intent.putExtra("itemId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }


        if(patron_stop != null){

            if(postOb.getString("progress").equals("finish")){

                patron_stop.setBackgroundResource(R.drawable.button_radius_5dp_gray_full_button);

                patron_stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        TastyToast.makeText(context, "이미 종료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);


                    }
                });

            } else {

                patron_stop.setBackgroundResource(R.drawable.button_radius_5dp_point_full_button);

                patron_stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                                .title(R.string.patron_stop_dialog_title)
                                .content(R.string.patron_stop_dialog_content)
                                .positiveText("네")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        postOb.put("progress", "production");
                                        postOb.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {

                                                activity.recreate();

                                            }
                                        });

                                    }
                                });

                        MaterialDialog dialog = builder.build();
                        dialog.show();



                    }
                });

            }



        }

        if(patron_cancel != null){

            patron_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                            .title("후원취소")
                            .content("후원을 취소 하겠습니까?. 지금까지 받은 후원은 모두 후원자분들에게 돌아갑니다.")
                            .positiveText("네")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    postOb.put("status", false);
                                    postOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                if(postOb.getInt("achieve_amount") == 0){

                                                    TastyToast.makeText(context, "후원이 취소 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                    activity.recreate();

                                                } else {

                                                    HashMap<String, Object> params = new HashMap<>();

                                                    params.put("patronId", postOb.getObjectId());
                                                    params.put("action", "cancel");
                                                    params.put("key", currentUser.getSessionToken());

                                                    Date uniqueIdDate = new Date();
                                                    String uniqueId = uniqueIdDate.toString();

                                                    params.put("uid", uniqueId);


                                                    ParseCloud.callFunctionInBackground("patron_cancel", params, new FunctionCallback<Map<String, Object>>() {

                                                        public void done(Map<String, Object> mapObject, ParseException e) {

                                                            if (e == null) {

                                                                Log.d("parse result", mapObject.toString());
                                                                if(mapObject.get("status").toString().equals("true")){

                                                                    TastyToast.makeText(context, "후원 취소 완료.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                    activity.finish();

                                                                } else {

                                                                    if(mapObject.get("message").toString().equals("badwritter")){

                                                                        TastyToast.makeText(context, "후원에 대한 권한이 없습니다. 작성자가 아닙니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                                    } else {

                                                                        TastyToast.makeText(context, "후원 취소에 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                                    }

                                                                }

                                                            } else {

                                                                if(mapObject != null){
                                                                    Log.d("parse result", mapObject.toString());
                                                                }


                                                                TastyToast.makeText(context, "후원 취소에 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                                Log.d("error", "error");
                                                                e.printStackTrace();

                                                            }
                                                        }
                                                    });

                                                }

                                            } else {

                                                e.printStackTrace();
                                            }

                                        }
                                    });

                                }
                            });

                    MaterialDialog dialog = builder.build();
                    dialog.show();

                }
            });

        }

        if(patron_result_summit != null){

            if(postOb.getString("progress").equals("finish") || postOb.getString("progress").equals("withdraw") || postOb.getString("progress").equals("withdraw_done")){


                patron_result_summit.setBackgroundResource(R.drawable.button_radius_5dp_point_full_button);

                patron_result_summit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });

            } else {

                patron_result_summit.setBackgroundResource(R.drawable.button_radius_5dp_gray_full_button);

                patron_result_summit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });

            }



        }

        if(patron_withdraw != null){

            if(postOb.getString("progress").equals("withdraw")){

                patron_withdraw.setBackgroundResource(R.drawable.button_radius_5dp_point_full_button);

                patron_withdraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                                .title("후원받기")
                                .content("후원 BOX를 받으시겠습니까?")
                                .positiveText("네")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        if(postOb.getInt("achieve_amount") == 0){

                                            TastyToast.makeText(context, "받을 수 있는 후원 BOX가 없습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                            activity.recreate();

                                        } else {

                                            HashMap<String, Object> params = new HashMap<>();

                                            params.put("patronId", postOb.getObjectId());
                                            params.put("action", "withdraw");
                                            params.put("key", currentUser.getSessionToken());
                                            Date uniqueIdDate = new Date();
                                            String uniqueId = uniqueIdDate.toString();

                                            params.put("uid", uniqueId);


                                            ParseCloud.callFunctionInBackground("patron_withdraw", params, new FunctionCallback<Map<String, Object>>() {

                                                public void done(Map<String, Object> mapObject, ParseException e) {

                                                    if (e == null) {

                                                        Log.d("parse result", mapObject.toString());
                                                        if(mapObject.get("status").toString().equals("true")){

                                                            String amount = mapObject.get("amount").toString();
                                                            String current_point = mapObject.get("current_point").toString();

                                                            TastyToast.makeText(context, amount + " BOX를 후원 받아 보유한 BOX가 총 " +  current_point + " BOX가 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            postOb.put("progress", "withdraw_done");
                                                            postOb.saveInBackground(new SaveCallback() {
                                                                @Override
                                                                public void done(ParseException e) {

                                                                    if(e==null){

                                                                        activity.recreate();

                                                                    } else {

                                                                        e.printStackTrace();
                                                                    }

                                                                }
                                                            });



                                                        } else {

                                                            if(mapObject.get("message").toString().equals("badwritter")){

                                                                TastyToast.makeText(context, "후원에 대한 권한이 없습니다. 작성자가 아닙니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            } else {

                                                                TastyToast.makeText(context, "후원 취소에 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            }

                                                        }

                                                    } else {

                                                        TastyToast.makeText(context, "후원 취소에 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                        Log.d("error", "error");
                                                        e.printStackTrace();

                                                    }
                                                }
                                            });

                                        }



                                    }
                                });

                        MaterialDialog dialog = builder.build();
                        dialog.show();

                    }
                });

            } else if(postOb.getString("progress").equals("withdraw_done")){

                patron_withdraw.setBackgroundResource(R.drawable.button_radius_5dp_gray_full_button);

                patron_withdraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "이미 후원 BOX가 전달되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    }
                });

            } else {

                patron_withdraw.setBackgroundResource(R.drawable.button_radius_5dp_gray_full_button);

                patron_withdraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "후원종료 3일 후 후원받기 가능해 집니다. 후원을 종료하시거나, 기한이 종료될 때까지 기다려주세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    }
                });

            }



        }



    }


    public Boolean parseArrayCheck(ParseObject object, String arrayColumName, String checkId){

        JSONArray parseArray = object.getJSONArray(arrayColumName);

        Boolean exist = false;

        if(parseArray == null){

            Log.d("checkedId", checkId);

            Log.d("array", "null");

            return false;

        } else {

            Log.d("checkedId", checkId);

            Log.d("array", parseArray.toString());

            for(int i=0;parseArray.length()>i;i++){

                try {

                    String item = parseArray.getString(i);

                    if(item.equals(checkId)){

                        exist = true;

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }

            return exist;

        }


    }

    public Boolean purchaseCheck(ParseObject pointOb, String arrayColumName, String commercialId){

        JSONArray parseArray = pointOb.getJSONArray(arrayColumName);
        Boolean exist = false;

        if(parseArray == null){

            return false;

        } else {

            for(int i=0;parseArray.length()>i;i++){

                try {

                    String item = parseArray.getString(i);

                    if(item.equals(commercialId)){

                        exist = true;

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }

            return exist;

        }


    }



    public void noMessageStatus(ParseObject object, final TextView noMessageLayout){

        if(object!=null){

            object.fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {

                    if(object.getInt("comment_count") == 0){

                        noMessageLayout.setVisibility(View.VISIBLE);

                    } else {

                        noMessageLayout.setVisibility(View.GONE);

                    }

                }
            });


        }

    }







    public Boolean affordableCheck(ParseObject contentOb, ParseUser currentUser){

        if(contentOb.getString("payment").equals("free")){

            return true;

        } else if ( contentOb.getString("payment").equals("point")){

            int currentPoint = currentUser.getInt("current_point");

            return contentOb.getInt("price") < currentPoint || contentOb.getInt("price") == currentPoint;


        } else if ( contentOb.getString("payment").equals("coin") ){

            int currentCoin = currentUser.getInt("current_coin");

            return contentOb.getInt("price") < currentCoin || contentOb.getInt("price") == currentCoin;

        }

        return null;
    }

    public Boolean doublePuchaseCheck(ParseObject contentOb, ParseUser currentUser) throws JSONException {

        JSONArray myContents = currentUser.getJSONArray("my_contents");

        Log.d("RecyclerParseQueryData", String.valueOf(myContents));

        Boolean exist = false;

        if(myContents == null){

            exist = false;

        } else {

            for(int i=0;myContents.length()>i;i++){

                if(contentOb.getObjectId().equals( myContents.get(i) )){

                    exist = true;

                };

            }

        }

        Log.d("RecyclerParseQueryExist", String.valueOf(exist));

        return exist;

    }

    public void contentTypeFilterColorBuilder(String type, ImageView filter_all_img, TextView filter_all_text, ImageView filter_post_img, TextView filter_post_text, ImageView filter_webtoon_img, TextView filter_webtoon_text,ImageView filter_novel_img, TextView filter_novel_text,ImageView filter_youtube_img, TextView filter_youtube_text){

        switch(type){

            case "all":

                filter_all_img.setColorFilter(likedColor);
                filter_all_text.setTextColor(likedColor);

                filter_post_img.setColorFilter(filterDefaultColor);
                filter_post_text.setTextColor(filterDefaultColor);

                filter_webtoon_img.setColorFilter(filterDefaultColor);
                filter_webtoon_text.setTextColor(filterDefaultColor);

                /*

                filter_novel_img.setColorFilter(filterDefaultColor);
                filter_novel_text.setTextColor(filterDefaultColor);

                filter_youtube_img.setColorFilter(filterDefaultColor);
                filter_youtube_text.setTextColor(filterDefaultColor);

                */
                break;

            case "post":

                filter_all_img.setColorFilter(filterDefaultColor);
                filter_all_text.setTextColor(filterDefaultColor);

                filter_post_img.setColorFilter(likedColor);
                filter_post_text.setTextColor(likedColor);

                filter_webtoon_img.setColorFilter(filterDefaultColor);
                filter_webtoon_text.setTextColor(filterDefaultColor);

                /*

                filter_novel_img.setColorFilter(filterDefaultColor);
                filter_novel_text.setTextColor(filterDefaultColor);

                filter_youtube_img.setColorFilter(filterDefaultColor);
                filter_youtube_text.setTextColor(filterDefaultColor);

                */

                break;

            case "webtoon":

                filter_all_img.setColorFilter(filterDefaultColor);
                filter_all_text.setTextColor(filterDefaultColor);

                filter_post_img.setColorFilter(filterDefaultColor);
                filter_post_text.setTextColor(filterDefaultColor);

                filter_webtoon_img.setColorFilter(likedColor);
                filter_webtoon_text.setTextColor(likedColor);

                /*

                filter_novel_img.setColorFilter(filterDefaultColor);
                filter_novel_text.setTextColor(filterDefaultColor);

                filter_youtube_img.setColorFilter(filterDefaultColor);
                filter_youtube_text.setTextColor(filterDefaultColor);

                */

                break;

            case "novel":

                filter_all_img.setColorFilter(filterDefaultColor);
                filter_all_text.setTextColor(filterDefaultColor);

                filter_post_img.setColorFilter(filterDefaultColor);
                filter_post_text.setTextColor(filterDefaultColor);

                filter_webtoon_img.setColorFilter(filterDefaultColor);
                filter_webtoon_text.setTextColor(filterDefaultColor);

                /*

                filter_novel_img.setColorFilter(likedColor);
                filter_novel_text.setTextColor(likedColor);

                filter_youtube_img.setColorFilter(filterDefaultColor);
                filter_youtube_text.setTextColor(filterDefaultColor);

                */

                break;

            case "youtube":

                filter_all_img.setColorFilter(filterDefaultColor);
                filter_all_text.setTextColor(filterDefaultColor);

                filter_post_img.setColorFilter(filterDefaultColor);
                filter_post_text.setTextColor(filterDefaultColor);

                filter_webtoon_img.setColorFilter(filterDefaultColor);
                filter_webtoon_text.setTextColor(filterDefaultColor);

                /*

                filter_novel_img.setColorFilter(filterDefaultColor);
                filter_novel_text.setTextColor(filterDefaultColor);

                filter_youtube_img.setColorFilter(likedColor);
                filter_youtube_text.setTextColor(likedColor);

                */

                break;

        }


    }




    public void coinPurchase(final ParseObject contentOb, TextView price_text, ParseUser currentUser, Context context){

        int myFreeCoin = currentUser.getInt("current_free_coin");
        int myAdCoin = currentUser.getInt("current_ad_coin");
        int myPurchaseCoin = currentUser.getInt("current_purchase_coin");

        int afterFreeCoin = contentOb.getInt("price") - myFreeCoin;

        if(afterFreeCoin > 0 || afterFreeCoin == 0){

            coinPurchaseExecute(contentOb, 0, contentOb.getInt("price"), 0, 0, price_text, currentUser, context);

        } else {

            int afterAdCoin = afterFreeCoin - myAdCoin;

            if(afterAdCoin > 0 || afterAdCoin == 0 ){

                coinPurchaseExecute(contentOb, 1, myFreeCoin, afterFreeCoin, 0, price_text, currentUser, context);

            } else {

                coinPurchaseExecute(contentOb, 2, myFreeCoin, myAdCoin, afterAdCoin, price_text , currentUser, context);

            }

        }





    }

    public void coinPurchaseExecute(final ParseObject contentOb, int step, int free_coin, int ad_coin, int purchase_coin, final TextView price_text, ParseUser currentUser, final Context context){

        if(step == 0){

            ParseObject coinManagerOb = new ParseObject("CoinManager");
            coinManagerOb.put("from", "purchase");
            coinManagerOb.put("type", "free_coin");
            coinManagerOb.put("amount", -free_coin);
            coinManagerOb.put("user", currentUser);
            coinManagerOb.saveInBackground();

            currentUser.increment("current_coin", -free_coin);
            currentUser.increment("current_free_coin", -free_coin);
            currentUser.addAllUnique("my_contents", Arrays.asList(contentOb.getObjectId()));
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        price_text.setText("구매 완료");
                        TastyToast.makeText(context, "구매 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        onclickFunctionEnd(contentOb, context);

                    } else {

                        e.printStackTrace();

                    }

                }
            });



        } else if(step == 1) {

            int total_purchase_coin = free_coin + ad_coin;

            ParseObject coinManagerOb = new ParseObject("CoinManager");
            coinManagerOb.put("from", "purchase");
            coinManagerOb.put("type", "free_coin");
            coinManagerOb.put("amount", -free_coin);
            coinManagerOb.put("user", currentUser);
            coinManagerOb.saveInBackground();

            ParseObject coinManagerOb2 = new ParseObject("CoinManager");
            coinManagerOb2.put("from", "purchase");
            coinManagerOb2.put("type", "ad_coin");
            coinManagerOb2.put("amount", -ad_coin);
            coinManagerOb2.put("user", currentUser);
            coinManagerOb2.saveInBackground();

            currentUser.increment("current_coin", -total_purchase_coin);
            currentUser.increment("current_free_coin", -free_coin);
            currentUser.increment("current_ad_coin", -ad_coin);
            currentUser.addAllUnique("my_contents", Arrays.asList(contentOb.getObjectId()));
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        price_text.setText("구매 완료");
                        TastyToast.makeText(context, "구매 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        onclickFunctionEnd(contentOb, context);

                    } else {

                        e.printStackTrace();

                    }

                }
            });

        } else if(step == 2) {

            int total_purchase_coin = free_coin + ad_coin + purchase_coin;

            ParseObject coinManagerOb = new ParseObject("CoinManager");
            coinManagerOb.put("from", "purchase");
            coinManagerOb.put("type", "free_coin");
            coinManagerOb.put("amount", -free_coin);
            coinManagerOb.put("user", currentUser);
            coinManagerOb.saveInBackground();

            ParseObject coinManagerOb2 = new ParseObject("CoinManager");
            coinManagerOb2.put("from", "purchase");
            coinManagerOb2.put("type", "ad_coin");
            coinManagerOb2.put("amount", -ad_coin);
            coinManagerOb2.put("user", currentUser);
            coinManagerOb2.saveInBackground();

            ParseObject coinManagerOb3 = new ParseObject("CoinManager");
            coinManagerOb3.put("from", "purchase");
            coinManagerOb3.put("type", "purchase_coin");
            coinManagerOb3.put("amount", -purchase_coin);
            coinManagerOb3.put("user", currentUser);
            coinManagerOb3.saveInBackground();

            currentUser.increment("current_coin", -total_purchase_coin);
            currentUser.increment("current_free_coin", -free_coin);
            currentUser.increment("current_ad_coin", -ad_coin);
            currentUser.increment("current_purchase_coin", -purchase_coin);
            currentUser.addAllUnique("my_contents", Arrays.asList(contentOb.getObjectId()));
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        price_text.setText("구매 완료");
                        TastyToast.makeText(context, "구매 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        onclickFunctionEnd(contentOb, context);

                    } else {

                        e.printStackTrace();

                    }

                }
            });

        }

    }


    public String dateToString(Date date){

        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int dateNum = date.getDate();
        int hour = date.getHours();
        int minute = date.getMinutes();

        String minuteString = String.valueOf(minute);

        if(minute < 10){

            minuteString = "0" + minuteString;

        }

        return String.valueOf( year ) + "." + String.valueOf(month) + "." + String.valueOf(dateNum) + ". " + String.valueOf(hour) + ":"+ minuteString;
    }

    public String dateToStringForDisplay(Date date){

        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int dateNum = date.getDate();


        return String.valueOf( year ) + "." + String.valueOf(month) + "." + String.valueOf(dateNum);
    }

    public String dateToStringForDisplayYear2String(Date date){

        if(date != null){

            int year = date.getYear() + 1900;
            int month = date.getMonth() + 1;
            int dateNum = date.getDate();


            return String.valueOf( year ).substring(2,4) + "." + String.valueOf(month) + "." + String.valueOf(dateNum);

        } else {

            return "날짜 입력안됨";
        }


    }


    public int getBitmapOfWidth(String fileName){
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);
            return options.outWidth;
        } catch(Exception e) {
            Log.d("error", e.getMessage().toString());
            return 0;
        }
    }

    /** Get Bitmap's height **/
    public int getBitmapOfHeight(String fileName){

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);

            return options.outHeight;
        } catch(Exception e) {
            Log.d("error",e.getMessage().toString());
            return 0;
        }
    }


    public String html2text(String html) {
        html = html.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
        html = html.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
        html = html.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
        html = html.replaceAll("&nbsp;"," ");
        html = html.replaceAll("&amp;"," ");

        return html;
    }

    public void tagAddActionFromObject(final ParseUser currentUser,final String tag, final SaveCallback saveCallback){

        final ParseObject tagOb = new ParseObject("TagLog");
        tagOb.put("tag", tag);
        tagOb.put("user", currentUser);
        tagOb.put("place", "RecommendTagActivity");
        tagOb.put("status", true);
        tagOb.put("add", true);
        tagOb.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e==null){

                    ParseRelation tagRelation = currentUser.getRelation("tag_log");
                    tagRelation.add(tagOb);

                    currentUser.addUnique("tags_for_recommend", tag);
                    currentUser.addUnique("tags", tag);
                    currentUser.saveInBackground(saveCallback);

                } else {

                    e.printStackTrace();
                }

            }
        });

    }

    public void tagRemoveActionFromString( final ParseUser currentUser,final String tag, final SaveCallback saveCallback){

        final ParseObject tagOb = new ParseObject("TagLog");
        tagOb.put("tag", tag);
        tagOb.put("user", currentUser);
        tagOb.put("place", "RecommendTagActivity");
        tagOb.put("status", true);
        tagOb.put("add", false);
        tagOb.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e==null){

                    ParseRelation tagRelation = currentUser.getRelation("tag_log");
                    tagRelation.add(tagOb);

                    ArrayList removeArray = new ArrayList();
                    removeArray.add(tag);

                    currentUser.removeAll("tags", removeArray);
                    currentUser.saveInBackground(saveCallback);

                } else {

                    e.printStackTrace();

                }

            }
        });

    }




    public void hideKeyboard(Activity activity){

        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow( activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );

    }

    public void onclickFunctionEnd(ParseObject contentOb , Context context){


        switch (contentOb.getString("type")){

            case "youtube":

                Log.d("msg", contentOb.getObjectId());

                contentOb.increment("today_count", 1);
                contentOb.increment("week_count", 1);
                contentOb.increment("month_count", 1);
                contentOb.increment("total_count", 1);
                contentOb.saveInBackground();

                Intent intent = new Intent(context, YoutubeActivity.class);
                intent.putExtra("cardId", contentOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);



                break;

            case "photo":

                Log.d("msg", contentOb.getObjectId());

                contentOb.increment("today_count", 1);
                contentOb.increment("week_count", 1);
                contentOb.increment("month_count", 1);
                contentOb.increment("total_count", 1);
                contentOb.saveInBackground();

                Intent intent1 = new Intent(context, PhotoContentsActivity.class);
                intent1.putExtra("cardId", contentOb.getObjectId());
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);



                break;

            case "gif":

                Log.d("msg", contentOb.getObjectId());

                contentOb.increment("today_count", 1);
                contentOb.increment("week_count", 1);
                contentOb.increment("month_count", 1);
                contentOb.increment("total_count", 1);
                contentOb.saveInBackground();

                Intent intent2 = new Intent(context, GifNativeActivity.class);
                intent2.putExtra("postId", contentOb.getObjectId());
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);



                break;

            case "webtoon":

                Log.d("msg", contentOb.getObjectId());

                contentOb.increment("today_count", 1);
                contentOb.increment("week_count", 1);
                contentOb.increment("month_count", 1);
                contentOb.increment("total_count", 1);
                contentOb.saveInBackground();

                Intent intent3 = new Intent(context, WebtoonContentActivity.class);
                intent3.putExtra("cardId", contentOb.getObjectId());
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent3);



                break;

        }






    }

    public void onClickFunction(Boolean purchase, ParseObject contentOb, TextView price_text, ParseUser currentUser , Context context){


        if(contentOb.getString("payment").equals("free")){

            onclickFunctionEnd(contentOb, context);

        } else {

            if(currentUser == null){

                TastyToast.makeText(context, "Point가 필요합니다. 로그인 하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

            } else {

                try {

                    if( doublePuchaseCheck(contentOb, currentUser) ){

                        onclickFunctionEnd(contentOb, context);

                    } else {

                        if(affordableCheck(contentOb, currentUser)){

                            if(contentOb.getString("payment").equals("point")){

                                pointPurchase(contentOb, price_text, currentUser, context);

                            } else {

                                coinPurchase(contentOb, price_text, currentUser, context);

                            }

                        } else {

                            TastyToast.makeText(context, "Point가 부족합니다. 댓글 쓰기, 좋아요 등의 활동을 통해 Point를 적립하세요!", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        };

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }



            }

        }

    }

    public void onClickFunction(Boolean purchase, ParseObject contentOb, TextView price_text, ParseUser currentUser , Context context, YouTubeBaseActivity activity){


        if(contentOb.getString("payment").equals("free")){

            onclickFunctionEnd(contentOb, context);

            activity.finish();

        } else {

            if(currentUser == null){

                TastyToast.makeText(context, "Point가 필요합니다. 로그인 하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

            } else {

                try {

                    if( doublePuchaseCheck(contentOb, currentUser) ){

                        onclickFunctionEnd(contentOb, context);

                    } else {

                        if(affordableCheck(contentOb, currentUser)){

                            if(contentOb.getString("payment").equals("point")){

                                pointPurchase(contentOb, price_text, currentUser, context);
                                activity.finish();

                            } else {

                                coinPurchase(contentOb, price_text, currentUser, context);
                                activity.finish();

                            }

                        } else {

                            TastyToast.makeText(context, "Point가 부족합니다. 댓글 쓰기, 좋아요 등의 활동을 통해 Point를 적립하세요!", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        };

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }



            }

        }

    }


    public void pointPurchase(final ParseObject contentOb, final TextView price_text, final ParseUser currentUser , final Context context){


        ParseObject pointManagerOb = new ParseObject("PointManager");
        pointManagerOb.put("from", "purchase");
        pointManagerOb.put("type", "free");
        pointManagerOb.put("amount", -contentOb.getInt("price"));
        pointManagerOb.put("user", currentUser);
        pointManagerOb.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e==null){

                    currentUser.increment("current_point", -contentOb.getInt("price"));
                    currentUser.addAllUnique("my_contents", Arrays.asList(contentOb.getObjectId()));
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){



                                price_text.setText("구매 완료");
                                TastyToast.makeText(context, "구매 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                onclickFunctionEnd(contentOb, context);

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


    public void priceLayoutBuilder(ParseObject contentOb, TextView price_text, LinearLayout price_layout, Boolean purchase){

        if(purchase){

            String purchaseText = "구매 완료";
            Log.d("case", "1");

            price_text.setText(purchaseText);
            price_layout.setVisibility(View.VISIBLE);

        } else {

            Log.d("case", "2");
            //Log.d("price error object", contentOb.getObjectId());

            if(contentOb == null){

                price_layout.setVisibility(View.GONE);

            } else {

                if(contentOb.get("payment") == null){

                    // 채널인 경우
                    price_layout.setVisibility(View.GONE);


                } else {
                    //컨텐츠인 경우
                    if(contentOb.getString("payment").equals("free")){

                        price_layout.setVisibility(View.GONE);

                    } else if(contentOb.getString("payment").equals("point")){

                        String pointText = contentOb.getInt("price") + "P";

                        price_text.setText(pointText);
                        price_layout.setVisibility(View.VISIBLE);

                    } else if(contentOb.getString("payment").equals("coin")){

                        String coinText = contentOb.getInt("price") + "C";

                        price_text.setText(coinText);
                        price_layout.setVisibility(View.VISIBLE);

                    }

                }

            }


        }



    }

    public void postIconColorFilterInit(ImageView comment_icon, ImageView comment_like_icon, ImageView comment_dislike_icon, Boolean like){

        if (like){

            comment_icon.setColorFilter(likeColor);
            comment_like_icon.setColorFilter(likeColor);
            comment_dislike_icon.setColorFilter(likeColor);

        } else {

            comment_icon.setColorFilter(likedColor);
            comment_like_icon.setColorFilter(likedColor);
            comment_dislike_icon.setColorFilter(likedColor);

        }



    }

    public void postIconColorFilterInit(ImageView comment_icon, ImageView comment_like_icon, ImageView comment_dislike_icon, ImageView request_icon,ImageView share_icon, ImageView option_icon, Boolean like){

        if (like){

            if(comment_icon != null){

                comment_icon.setColorFilter(likePostColor);

            }

            if(comment_like_icon != null){

                comment_like_icon.setColorFilter(likePostColor);

            }


            if(comment_dislike_icon != null){

                comment_dislike_icon.setColorFilter(likePostColor);

            }

            if(request_icon != null){

                request_icon.setColorFilter(likePostColor);

            }

            if(share_icon != null){

                share_icon.setColorFilter(likePostColor);

            }

            if(option_icon != null){

                option_icon.setColorFilter(likePostColor);
            }


        } else {

            if(comment_icon != null){

                comment_icon.setColorFilter(likedPostColor);

            }

            if(comment_like_icon != null){

                comment_like_icon.setColorFilter(likedPostColor);

            }

            if(comment_dislike_icon != null){

                comment_dislike_icon.setColorFilter(likedPostColor);

            }

            if(request_icon != null){

                request_icon.setColorFilter(likedPostColor);

            }

            if(share_icon != null){

                share_icon.setColorFilter(likedPostColor);

            }

            if(option_icon != null){

                option_icon.setColorFilter(likedPostColor);
            }

        }



    }

    public void postTextColorFilterInit(TextView comment_text, TextView comment_like_text, TextView comment_dislike_text, Boolean like){

        if(like){

            comment_text.setTextColor(likePostColor);
            comment_like_text.setTextColor(likePostColor);
            comment_dislike_text.setTextColor(likePostColor);

        } else {

            comment_text.setTextColor(likedPostColor);
            comment_like_text.setTextColor(likedPostColor);
            comment_dislike_text.setTextColor(likedPostColor);

        }

    }

    public void postCounterColorFilterInit(TextView comment_counter, TextView comment_like_counter, TextView comment_dislike_counter,TextView share_count_text, Boolean like){

        if(like){

            if(comment_counter != null){

                comment_counter.setTextColor(likePostColor);

            }

            if(comment_like_counter != null){

                comment_like_counter.setTextColor(likePostColor);

            }



            if(comment_dislike_counter != null){

                comment_dislike_counter.setTextColor(likePostColor);
            }

            if(share_count_text != null){

                share_count_text.setTextColor(likePostColor);

            }


        } else {

            if(comment_counter != null){

                comment_counter.setTextColor(likedPostColor);

            }

            if(comment_like_counter != null){

                comment_like_counter.setTextColor(likedPostColor);

            }

            if(comment_dislike_counter != null){

                comment_dislike_counter.setTextColor(likedPostColor);

            }

            if(share_count_text != null){

                share_count_text.setTextColor(likedPostColor);

            }


        }

    }


    public void postCounterColorFilterInit(TextView comment_counter, TextView comment_like_counter, TextView comment_dislike_counter, Boolean like){

        if(like){

            comment_counter.setTextColor(likePostColor);
            comment_like_counter.setTextColor(likePostColor);

            if(comment_dislike_counter != null){

                comment_dislike_counter.setTextColor(likePostColor);
            }



        } else {

            comment_counter.setTextColor(likedPostColor);
            comment_like_counter.setTextColor(likedPostColor);

            if(comment_dislike_counter != null){

                comment_dislike_counter.setTextColor(likedPostColor);

            }



        }

    }



    public Bitmap resizeBitmapImageFn(Bitmap bmpSource, int maxResolution){
        int iWidth = bmpSource.getWidth();      //비트맵이미지의 넓이
        int iHeight = bmpSource.getHeight();     //비트맵이미지의 높이
        int newWidth = iWidth ;
        int newHeight = iHeight ;
        float rate = 0.0f;

        //이미지의 가로 세로 비율에 맞게 조절
        if(iWidth > iHeight ){
            if(maxResolution < iWidth ){
                rate = maxResolution / (float) iWidth ;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < iHeight ){
                rate = maxResolution / (float) iHeight ;
                newWidth = (int) (iWidth * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(bmpSource, newWidth, newHeight, true);
    }


    protected int secondCalculate(Date time){

        int hour = time.getHours();
        int minute = time.getMinutes();
        int second = time.getSeconds();

        return ( hour * 60 * 60 ) + (minute * 60) + second;
    }


    public void iconColorFilterInit(ImageView comment_icon, ImageView comment_like_icon, ImageView comment_dislike_icon){

        comment_icon.setColorFilter(likeColor);
        comment_like_icon.setColorFilter(likeColor);
        comment_dislike_icon.setColorFilter(likeColor);

    }





    public void glideFunction(Context context, String imageUrl , ImageView imageView, RequestManager requestManager){

        try {

            Glide.with(context)
                    .load(imageUrl)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    //.transition(new DrawableTransitionOptions().crossFade())
                    .into(imageView);

        } catch(Exception e) {

            requestManager
                    .load(imageUrl)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    //.transition(new DrawableTransitionOptions().crossFade())
                    .into(imageView);
            e.printStackTrace();

        }


    }

    public void saveImageToParse(Context context, ArrayList<Image> images, String targetClass, SaveCallback saveCallback){

        for(int i=0; images.size()>i;i++){

            String path = images.get(i).path;

            final Bitmap image_bitmap;
            final ParseUser currentUser = ParseUser.getCurrentUser();
            final ParseFile file;
            final ParseFile thumFile;
            final Bitmap thumnailImg;

            if(gifChecker(path)){

                File gifFile = new File(path);

                byte[] b = new byte[(int) gifFile.length()];

                try {

                    FileInputStream fileInputStream = new FileInputStream(gifFile);
                    fileInputStream.read(b);

                    final ParseFile newFile = new ParseFile("save.gif", b);

                    Bitmap bmp = BitmapFactory.decodeByteArray(b,0, b.length);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    ParseFile pngFile = new ParseFile("save.png", stream.toByteArray());

                    newFile.saveInBackground();

                    ParseObject targetOb = new ParseObject(targetClass);
                    targetOb.put("user", currentUser);
                    targetOb.put("view_count", 0);
                    targetOb.put("status", true);
                    targetOb.put("image", newFile);
                    targetOb.put("png", pngFile);
                    targetOb.put("type", "gif");
                    targetOb.put("thumbnail", newFile);
                    targetOb.save();



                    ParseObject postOb = new ParseObject("Post");
                    postOb.put("user", currentUser);
                    postOb.put("warehouse", targetOb);
                    postOb.put("type", "new_image");
                    postOb.put("status", true);
                    postOb.put("body", new_image_msg);
                    postOb.put("like_count", 0);
                    postOb.put("dislike_count", 0);
                    postOb.put("comment_count", 0);
                    postOb.put("from", "timeline");
                    postOb.save();

                    if(images.size() -1 == i){

                        targetOb.saveInBackground(saveCallback);

                    }

                } catch (FileNotFoundException e) {
                    System.out.println("File Not Found.");
                    e.printStackTrace();

                } catch (IOException e1) {
                    System.out.println("Error Reading The File.");
                    e1.printStackTrace();

                } catch (ParseException e3){

                    e3.printStackTrace();

                }


            } else {

                try {
                    //Uri에서 이미지 이름을 얻어온다.

                    int imageWidth = getBitmapOfWidth(path);
                    int imageHeight = getBitmapOfHeight(path);

                    //1/2 감소
                    int firstHurdleWidthSize = 1200;
                    int firstHurdleHeightSize = 1200;

                    //1/3감소
                    int secondHurdleWidthSize = 1800;
                    int secondHurdleHeightSize = 1800;

                    //1/4감소
                    int maxWidthSize = 2400;
                    int maxHeightSize = 2400;

                    Log.d("width", String.valueOf(imageWidth));
                    Log.d("height", String.valueOf(imageHeight));

                    if (imageWidth > maxWidthSize || imageHeight > maxHeightSize) {
                        Log.d("msg","변환시작");
                        // 최대 크기를 벗어난 경우의 처리, 이미지 크기 변환 등
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 3;
                        image_bitmap = BitmapFactory.decodeFile(path, options);

                        thumnailImg = resizeBitmapImageFn(image_bitmap, 300);

                    } else if (imageWidth > secondHurdleWidthSize || imageHeight > secondHurdleHeightSize){

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        image_bitmap = BitmapFactory.decodeFile(path, options);

                        thumnailImg = resizeBitmapImageFn(image_bitmap, 300);


                    } else if (imageWidth > firstHurdleWidthSize || imageHeight > firstHurdleHeightSize){

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 1;
                        image_bitmap = BitmapFactory.decodeFile(path, options);

                        thumnailImg = resizeBitmapImageFn(image_bitmap, 300);

                    } else {

                        Log.d("msg","변환없음");
                        image_bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(path));
                        thumnailImg = resizeBitmapImageFn(image_bitmap, 300);

                    }


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    ByteArrayOutputStream thumStream = new ByteArrayOutputStream();
                    thumnailImg.compress(Bitmap.CompressFormat.PNG, 100, thumStream);

                    file = new ParseFile("image.png", stream.toByteArray());
                    thumFile = new ParseFile("image.png", thumStream.toByteArray());

                    file.save();
                    thumFile.save();

                    ParseObject targetOb = new ParseObject(targetClass);
                    targetOb.put("user", currentUser);
                    targetOb.put("view_count", 0);
                    targetOb.put("status", true);
                    targetOb.put("type", "png");
                    targetOb.put("image", file);
                    targetOb.put("thumbnail", thumFile);
                    targetOb.save();

                    ParseObject postOb = new ParseObject("Post");
                    postOb.put("user", currentUser);
                    postOb.put("warehouse", targetOb);
                    postOb.put("type", "new_image");
                    postOb.put("status", true);
                    postOb.put("body", new_image_msg);
                    postOb.put("like_count", 0);
                    postOb.put("dislike_count", 0);
                    postOb.put("comment_count", 0);
                    postOb.put("from", "timeline");
                    postOb.save();

                    if(images.size() -1 == i){

                        targetOb.saveInBackground(saveCallback);

                    }

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(context,"파일없음 "+e.getMessage().toString(), Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    // TODO Auto-generated catch block


                    e.printStackTrace();
                    Toast.makeText(context,"시스템에러 "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(context,"예외상황발생 "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

            }




        }

    }

    public void saveImageToParse(Context context, ArrayList<Image> images, String targetClass, ParseObject artistPostOb ,SaveCallback saveCallback){

        //final ArrayList<JSONObject> image_list = new ArrayList<JSONObject>();
        List<JSONObject> image_list = Arrays.asList();



        for(int i=0; images.size()>i;i++){

            String path = images.get(i).path;

            final Bitmap image_bitmap;
            final ParseUser currentUser = ParseUser.getCurrentUser();
            final ParseFile file;
            final ParseFile thumFile;
            final Bitmap thumnailImg;


            if(gifChecker(path)){

                File gifFile = new File(path);

                byte[] b = new byte[(int) gifFile.length()];

                try {

                    FileInputStream fileInputStream = new FileInputStream(gifFile);
                    fileInputStream.read(b);

                    final ParseFile newFile = new ParseFile("save.gif", b);

                    Bitmap bmp = BitmapFactory.decodeByteArray(b,0, b.length);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    ParseFile pngFile = new ParseFile("save.png", stream.toByteArray());

                    newFile.saveInBackground();

                    ParseObject targetOb = new ParseObject(targetClass);
                    targetOb.put("user", currentUser);
                    targetOb.put("artist_post", artistPostOb);
                    targetOb.put("artist_post_id", artistPostOb.getObjectId());
                    targetOb.put("status", true);
                    targetOb.put("image", newFile);
                    targetOb.put("image_png", pngFile);
                    targetOb.put("type", "gif");
                    targetOb.put("thumbnail", newFile);
                    targetOb.put("order", i);
                    targetOb.save();

                    artistPostOb.addAllUnique("image_array", Arrays.asList(targetOb.getObjectId()));

                    if(images.size() -1 == i){

                        artistPostOb.save();

                        targetOb.saveInBackground(saveCallback);

                    }

                } catch (FileNotFoundException e) {
                    System.out.println("File Not Found.");
                    e.printStackTrace();

                } catch (IOException e1) {
                    System.out.println("Error Reading The File.");
                    e1.printStackTrace();

                } catch (ParseException e3){

                    e3.printStackTrace();

                }


            } else {

                try {
                    //Uri에서 이미지 이름을 얻어온다.

                    int imageWidth = getBitmapOfWidth(path);
                    int imageHeight = getBitmapOfHeight(path);

                    //1/2 감소
                    int firstHurdleWidthSize = 1200;
                    int firstHurdleHeightSize = 1200;

                    //1/3감소
                    int secondHurdleWidthSize = 1800;
                    int secondHurdleHeightSize = 1800;

                    //1/4감소
                    int maxWidthSize = 2400;
                    int maxHeightSize = 2400;

                    Log.d("width", String.valueOf(imageWidth));
                    Log.d("height", String.valueOf(imageHeight));

                    if (imageWidth > maxWidthSize || imageHeight > maxHeightSize) {
                        Log.d("msg","변환시작");
                        // 최대 크기를 벗어난 경우의 처리, 이미지 크기 변환 등
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 3;
                        image_bitmap = BitmapFactory.decodeFile(path, options);

                        thumnailImg = resizeBitmapImageFn(image_bitmap, 300);

                    } else if (imageWidth > secondHurdleWidthSize || imageHeight > secondHurdleHeightSize){

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        image_bitmap = BitmapFactory.decodeFile(path, options);

                        thumnailImg = resizeBitmapImageFn(image_bitmap, 300);


                    } else if (imageWidth > firstHurdleWidthSize || imageHeight > firstHurdleHeightSize){

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 1;
                        image_bitmap = BitmapFactory.decodeFile(path, options);

                        thumnailImg = resizeBitmapImageFn(image_bitmap, 300);

                    } else {

                        Log.d("msg","변환없음");
                        image_bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse("file://" + path));
                        thumnailImg = resizeBitmapImageFn(image_bitmap, 300);

                    }


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    ByteArrayOutputStream thumStream = new ByteArrayOutputStream();
                    thumnailImg.compress(Bitmap.CompressFormat.PNG, 100, thumStream);

                    file = new ParseFile("image.png", stream.toByteArray());
                    thumFile = new ParseFile("image.png", thumStream.toByteArray());

                    file.save();
                    thumFile.save();

                    ParseObject targetOb = new ParseObject(targetClass);
                    targetOb.put("user", currentUser);
                    targetOb.put("artist_post", artistPostOb);
                    targetOb.put("artist_post_id", artistPostOb.getObjectId());
                    targetOb.put("status", true);
                    targetOb.put("image", file);
                    targetOb.put("type", "png");
                    targetOb.put("thumbnail", thumFile);
                    targetOb.put("order", i);
                    targetOb.save();

                    artistPostOb.addAllUnique("image_array", Arrays.asList(targetOb.getObjectId()));

                    if(images.size() -1 == i){

                        artistPostOb.save();

                        targetOb.saveInBackground(saveCallback);

                    }

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(context,"파일없음 "+e.getMessage().toString(), Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(context,"시스템에러 "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(context,"예외상황발생 "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

            }




        }

    }


    public void saveImageToParseForPost(Context context, String path, final ParseObject artistPostOb, final SaveCallback saveCallback){

        final Bitmap image_bitmap;
        final ParseUser currentUser = ParseUser.getCurrentUser();
        final ParseFile file;
        final ParseFile thumFile;
        final Bitmap thumnailImg;

        if(gifChecker(path)){

            File gifFile = new File(path);

            byte[] b = new byte[(int) gifFile.length()];

            try {

                FileInputStream fileInputStream = new FileInputStream(gifFile);
                fileInputStream.read(b);

                final ParseFile newFile = new ParseFile("save.gif", b);

                Bitmap bmp = BitmapFactory.decodeByteArray(b,0, b.length);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                final ParseFile pngFile = new ParseFile("save.png", stream.toByteArray());

                newFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e==null){

                            artistPostOb.put("image", newFile);
                            artistPostOb.put("thumbnail", newFile);
                            artistPostOb.put("status", true);
                            artistPostOb.put("image_type", "gif");
                            artistPostOb.put("image_png", pngFile);
                            artistPostOb.saveInBackground(saveCallback);

                        } else {

                            e.printStackTrace();

                        }

                    }
                });


            } catch (FileNotFoundException e) {
                System.out.println("File Not Found.");
                e.printStackTrace();

            } catch (IOException e1) {
                System.out.println("Error Reading The File.");
                e1.printStackTrace();

            }


        } else {

            try {
                //Uri에서 이미지 이름을 얻어온다.

                int imageWidth = getBitmapOfWidth(path);
                int imageHeight = getBitmapOfHeight(path);

                //1/2 감소
                int firstHurdleWidthSize = 1200;
                int firstHurdleHeightSize = 1200;

                //1/3감소
                int secondHurdleWidthSize = 1800;
                int secondHurdleHeightSize = 1800;

                //1/4감소
                int maxWidthSize = 2400;
                int maxHeightSize = 2400;

                Log.d("width", String.valueOf(imageWidth));
                Log.d("height", String.valueOf(imageHeight));

                if (imageWidth > maxWidthSize || imageHeight > maxHeightSize) {
                    Log.d("msg","변환시작");
                    // 최대 크기를 벗어난 경우의 처리, 이미지 크기 변환 등
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    image_bitmap = BitmapFactory.decodeFile(path, options);

                    thumnailImg = resizeBitmapImageFn(image_bitmap, 300);

                } else if (imageWidth > secondHurdleWidthSize || imageHeight > secondHurdleHeightSize){

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    image_bitmap = BitmapFactory.decodeFile(path, options);

                    thumnailImg = resizeBitmapImageFn(image_bitmap, 300);


                } else if (imageWidth > firstHurdleWidthSize || imageHeight > firstHurdleHeightSize){

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    image_bitmap = BitmapFactory.decodeFile(path, options);

                    thumnailImg = resizeBitmapImageFn(image_bitmap, 300);

                } else {

                    Log.d("msg","변환없음");
                    Log.d("path", path);
                    image_bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse("file://" + path));
                    thumnailImg = resizeBitmapImageFn(image_bitmap, 300);

                }


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                ByteArrayOutputStream thumStream = new ByteArrayOutputStream();
                thumnailImg.compress(Bitmap.CompressFormat.PNG, 100, thumStream);

                file = new ParseFile("image.png", stream.toByteArray());
                thumFile = new ParseFile("image.png", thumStream.toByteArray());

                file.save();
                thumFile.save();

                artistPostOb.put("image", file);
                artistPostOb.put("thumbnail", thumFile);
                artistPostOb.put("status", true);
                artistPostOb.put("image_type", "png");
                artistPostOb.saveInBackground(saveCallback);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(context,"파일없음 "+e.getMessage().toString(), Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(context,"시스템에러 "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            } catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(context,"예외상황발생 "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }

        }

    }

    public Bitmap getBitmapFromURL(String src) {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            return BitmapFactory.decodeStream(input);

        } catch (IOException e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection!=null) {

                connection.disconnect();
            }
        }
    }

    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }






    public Boolean gifChecker(String url){


        return url.contains(".gif");

    }

    public int dday(int myear, int mmonth, int mday) {

        try {
            Calendar today = Calendar.getInstance(); //현재 오늘 날짜
            today.set(today.getTime().getYear() + 1900, today.getTime().getMonth() , today.getTime().getDate(), 23,59, 59 );


            Calendar dday = Calendar.getInstance();

            dday.set(myear, mmonth , mday, 23,59, 59 );


            //dday.set(myear,mmonth,mday);// D-day의 날짜를 입력합니다.
            Log.d("today", today.getTime().toString());
            Log.d("daday", dday.getTime().toString());


            long day = dday.getTimeInMillis()/(24*60*60*1000);
            // 각각 날의 시간 값을 얻어온 다음
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )


            long tday = today.getTimeInMillis()/(24*60*60*1000);
            long count =day - tday; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.

            Log.d("day", String.valueOf(day));
            Log.d("tday", String.valueOf(tday));
            Log.d("count", String.valueOf(count));


            return (int) count +1; // 날짜는 하루 + 시켜줘야합니다.

        } catch (Exception e) {

            e.printStackTrace();
            return -1;

        }

    }


    public String makeComma(int num){

        String result;

        if(num > 9999){

            String numString = String.valueOf(num);
            String splitString = numString.substring(0, numString.length()-3);

            int splitedNumber = Integer.parseInt(splitString);

            NumberFormat nf = NumberFormat.getInstance();
            result = nf.format(splitedNumber);

            result = result + "K";

        } else {

            NumberFormat nf = NumberFormat.getInstance();
            result = nf.format(num);
        }

        return result;

    }

    public void tagReload(EditText tag_input, String tag){

        String currentTagString = tag_input.getText().toString();
        ArrayList<String> currentTagArray = new ArrayList<>();

        if(currentTagString.length() ==0){

            currentTagArray = new ArrayList<>();

        } else {

            currentTagArray = stringToArrayList(currentTagString, "#");

        }


        if(!currentTagArray.contains(tag)){

            currentTagArray.add(tag);

            String resultString = arrayListToTagString(currentTagArray);

            tag_input.setText(resultString);
            tag_input.setSelection(resultString.length());

        } else {

            TastyToast.makeText(context, "중복 태그는 제외 됩니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

        }


    }

    public void tagReplace(EditText tag_input, String tag, String query){

        String currentTagString = tag_input.getText().toString();
        ArrayList<String> currentTagArray = new ArrayList<>();

        if(currentTagString.length() ==0){

            currentTagArray = new ArrayList<>();

        } else {

            currentTagArray = stringToArrayList(currentTagString, "#");
            currentTagArray.remove(currentTagArray.size()-1);

        }


        if(!currentTagArray.contains(tag)){

            currentTagArray.add(tag);

            String resultString = arrayListToTagString(currentTagArray);

            tag_input.setText(resultString);
            tag_input.setSelection(resultString.length());

        } else {

            TastyToast.makeText(context, "중복 태그는 제외 됩니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

        }


    }


    public ArrayList<String> stringToArrayList(String target, String splitId){

        ArrayList<String> result = new ArrayList<>();

        String noEmptyString = target.replace(" ","");
        String[] targetArray = target.split(splitId);



        for(String tagItem : targetArray){

            tagItem = tagItem.replace(" ", "");
            tagItem = removeSpecialLetters(tagItem);

            if(tagItem.length() != 0 && !tagItem.isEmpty() && !result.contains(tagItem)){

                result.add(tagItem);

            }

        }

        return result;

    }

    public String removeSpecialLetters(String orginalString){

        String resultString = "";

        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";

        resultString = orginalString.replaceAll(match, "");

        return resultString;

    }

    public ArrayList<String> stringToArrayListForReplace(String target, String splitId){

        ArrayList<String> result = new ArrayList<>();

        String noEmptyString = target.replace(" ","");
        String[] targetArray = target.split(splitId);

        for(String tagItem : targetArray){

            tagItem = tagItem.replace(" ", "");

            if(tagItem.length() != 0 && !tagItem.isEmpty() && !result.contains(tagItem)){

                result.add(tagItem);

            }

        }

        return result;

    }

    public String[] arrayListToString( ArrayList<String> target ){

        String[] result = new String[target.size()];
        result = target.toArray(result);

        return result;

    }

    public String arrayListToTagString( ArrayList<String> target ){

        String resultString = "";

        for(String tag:target){

            resultString += "#" + tag + " ";

        }

        return resultString;

    }


    public int progressMaker(int current, int max){

        Double currentLong = Double.parseDouble( String.valueOf(current) );
        Double maxLong = Double.parseDouble( String.valueOf( max ) );

        Double result = currentLong / maxLong * 100;

        return result.intValue();

    }

    public ArrayList<HashMap<String, String>> jsonArrayToArray(JSONArray jsonArray){

        ArrayList<HashMap<String, String>> dataArray = new ArrayList<>();

        for(int i=0; jsonArray.length()>i;i++){

            try {

                JSONObject data = jsonArray.getJSONObject(i);
                Iterator<String> keys = data.keys();

                HashMap<String, String> dictData = new HashMap<>();

                while (keys.hasNext()) {

                    String key = keys.next();
                    dictData.put(key, data.getString(key));

                }

                dataArray.add(dictData);



            } catch (JSONException e) {

                e.printStackTrace();

            }

        }

        return dataArray;
    }

    public String[] jsonArrayToStringArray(JSONArray jsonArray){

        if(jsonArray != null){

            String[] dataArray = new String[jsonArray.length()];

            for(int i=0; jsonArray.length()>i;i++){

                try {

                    String data = jsonArray.getString(i);

                    dataArray[i] = data;

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }

            return dataArray;

        } else {

            return new String[]{};
        }


    }

    public ArrayList<String> jsonArrayToArrayList(JSONArray jsonArray){

        if(jsonArray != null){

            ArrayList<String> dataArray = new ArrayList();

            for(int i=0; jsonArray.length()>i;i++){

                try {

                    String data = jsonArray.getString(i);

                    dataArray.add(data);

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }

            return dataArray;

        } else {

            return new ArrayList<>();
        }


    }

    public Boolean pokeExist(JSONArray jsonArray, String userId){

        Boolean result = false;

        if(jsonArray != null){

            for(int i=0; jsonArray.length()>i;i++ ){

                try {

                    String item = jsonArray.getString(i);
                    Log.d("item", item);
                    Log.d("userId", userId);

                    if(item.equals(userId)){

                        result = true;

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }


            }

            return result;

        } else {

            return false;

        }



    }

    public void OptionButtonFunction(final Context context, LinearLayout option_button, final ParseUser currentUser, final ParseObject targetOb){

        PopupMenu popup = new PopupMenu(context, option_button);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.timeline_option_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.report){

                    reportPostDialog(context, currentUser, targetOb);

                }

                Toast.makeText(context ,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popup.show();//showing popup menu


    }

    private void reportPostDialog(final Context context, final ParseUser currentUser, final ParseObject postOb){

        boolean wrapInScrollView = true;

        final MaterialDialog imageDialog = new MaterialDialog.Builder(context)
                .title("이미지 정보")
                .customView(R.layout.list_item_report_dialog, wrapInScrollView)
                .show();



        ImageView currentImage = (ImageView) imageDialog.getCustomView().findViewById(R.id.current_image);

        TextView patent = (TextView) imageDialog.getCustomView().findViewById(R.id.patent);
        TextView porno = (TextView) imageDialog.getCustomView().findViewById(R.id.porno);
        TextView cruel = (TextView) imageDialog.getCustomView().findViewById(R.id.cruel);

        LinearLayout patent_button = (LinearLayout) imageDialog.getCustomView().findViewById(R.id.patent_button);
        LinearLayout porno_button = (LinearLayout) imageDialog.getCustomView().findViewById(R.id.porno_button);
        LinearLayout cruel_button = (LinearLayout) imageDialog.getCustomView().findViewById(R.id.cruel_button);

        patent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!parseArrayCheck(postOb, "report_user", currentUser.getObjectId())){

                    ParseObject reportOb = new ParseObject("ReportItem");
                    reportOb.put("user", currentUser);
                    reportOb.put("type", "timeline");
                    reportOb.put("content", "patent");
                    reportOb.put("artist_post", postOb);
                    reportOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            postOb.increment("report_count");
                            postOb.addAllUnique("report_user", Arrays.asList(currentUser.getObjectId()));
                            postOb.saveInBackground();

                            TastyToast.makeText(context, "저작권 침해 신고 완료! 고객 의견 주셔서 감사합니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            imageDialog.hide();

                        }
                    });

                } else {

                    TastyToast.makeText(context, "이미 신고 했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                }



            }
        });

        porno_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!parseArrayCheck(postOb, "report_user", currentUser.getObjectId())){

                    ParseObject reportOb = new ParseObject("ReportItem");
                    reportOb.put("user", currentUser);
                    reportOb.put("type", "timeline");
                    reportOb.put("content", "porno");
                    reportOb.put("artist_post", postOb);
                    reportOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            postOb.increment("report_count");
                            postOb.addAllUnique("report_user", Arrays.asList(currentUser.getObjectId()));
                            postOb.saveInBackground();

                            TastyToast.makeText(context, "음란물 신고 완료! 고객 의견 주셔서 감사합니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            imageDialog.hide();

                        }
                    });

                } else {

                    TastyToast.makeText(context, "이미 신고 했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                }

            }
        });

        cruel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!parseArrayCheck(postOb, "report_user", currentUser.getObjectId())){

                    ParseObject reportOb = new ParseObject("ReportItem");
                    reportOb.put("user", currentUser);
                    reportOb.put("type", "timeline");
                    reportOb.put("content", "cruel");
                    reportOb.put("artist_post", postOb);
                    reportOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            postOb.increment("report_count");
                            postOb.addAllUnique("report_user", Arrays.asList(currentUser.getObjectId()));
                            postOb.saveInBackground();

                            TastyToast.makeText(context, "혐오/잔인 신고 완료! 고객 의견 주셔서 감사합니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            imageDialog.hide();

                        }
                    });

                } else {

                    TastyToast.makeText(context, "이미 신고 했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                }



            }
        });


    }

    public float makeProgress(float bytes, float total_bytes ){

        float result;

        result = bytes / total_bytes;

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        nf.format(result);

        return result;
    }


}
