package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.helper;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.AppConfig;


/**
 * Created by Ravi on 01/06/15.
 */
public class ParseUtils {

    private static String TAG = ParseUtils.class.getSimpleName();

    public static void verifyParseConfiguration(Context context) {
        if (TextUtils.isEmpty(AppConfig.PARSE_APPLICATION_ID) || TextUtils.isEmpty(AppConfig.PARSE_CLIENT_KEY)) {
            Toast.makeText(context, "Please configure your Parse Application ID and Client Key in AppConfig.java", Toast.LENGTH_LONG).show();
            ((Activity) context).finish();
        }
    }

    public static void registerParse(Context context) {
        // initializing parse library
//        Parse.initialize(context, AppConfig.PARSE_APPLICATION_ID, AppConfig.PARSE_CLIENT_KEY);


        Parse.initialize(new Parse.Configuration.Builder(context)
            .applicationId(AppConfig.PARSE_APPLICATION_ID)
            .clientKey(AppConfig.PARSE_CLIENT_KEY)
            .server(AppConfig.PARSE_SERVER_URL)
            .build()
        );

        ParsePush.subscribeInBackground(AppConfig.PARSE_CHANNEL, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.e(TAG, "Successfully subscribed to Parse!");
            }
        });

        ParseInstallation.getCurrentInstallation().saveInBackground();


    }

    public static void registerB4AParse(Context context) {
        // initializing parse library
//        Parse.initialize(context, AppConfig.PARSE_APPLICATION_ID, AppConfig.PARSE_CLIENT_KEY);


        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId(AppConfig.B4A_APP_ID)
                .clientKey(AppConfig.B4A_CLIENT_KEY)
                .server(AppConfig.B4A_SERVER_URL)
                .build()
        );

        ParsePush.subscribeInBackground(AppConfig.PARSE_CHANNEL, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.e(TAG, "Successfully subscribed to Parse!");
            }
        });

        ParseInstallation.getCurrentInstallation().saveInBackground();


    }

    public static void subscribeWithEmail(String email) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();

        installation.put("email", email);
        installation.put("GCMSenderId", AppConfig.GCM_SENDER_ID);

        installation.saveInBackground();

        Log.e(TAG, "Subscribed with email: " + email);
    }
}
