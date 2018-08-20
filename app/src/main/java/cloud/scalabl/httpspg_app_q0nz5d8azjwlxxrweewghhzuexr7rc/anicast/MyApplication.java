package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.signed.Signature;
import com.cloudinary.android.signed.SignatureProvider;
import com.igaworks.IgawCommon;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.helper.ParseUtils;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;

/**
 * Created by ssamkyu on 2017. 4. 27..
 */

public class MyApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(ArtistPost.class);

        ParseUtils.registerParse(this);
        //ParseUtils.registerB4AParse(this);
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        ParseFacebookUtils.initialize(this);
        ParseTwitterUtils.initialize( getString(R.string.twitter_api_key), getString(R.string.twitter_api_secret));

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.twitter_api_key), getString(R.string.twitter_api_secret)))
                .debug(true)
                .build();
        Twitter.initialize(twitterConfig);

        IgawCommon.autoSessionTracking(MyApplication.this);
        TypefaceProvider.registerDefaultIconSets();

        MediaManager.init(this);


        /*
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        */




    }

}
