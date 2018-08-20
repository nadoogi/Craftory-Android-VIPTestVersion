package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.parse.ParseUser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Heechul Ryu on 2014. 9. 13..
 */
public class ParseSessionSyncJsInterface {

    public String parseId;
    public String parseJSSecret;
    @JavascriptInterface
    public String getParseInfoFromApp(){
        ParseUser user = ParseUser.getCurrentUser();

        if (user == null) {
            return null;
        }

        Map<String, String> result = new HashMap<String, String>();

        result.put("_sessionToken", user.getSessionToken());
        result.put("objectId", user.getObjectId());
        result.put("applicationId", parseId);
        result.put("appSecret", parseJSSecret);

        JSONObject jsonObject = new JSONObject(result);

        return jsonObject.toString();
    }

    public static void applyInterface(WebView webView, String parseId, String parseJSSecret) {
        ParseSessionSyncJsInterface sessionSyncJsInterface = new ParseSessionSyncJsInterface();
        sessionSyncJsInterface.parseId = parseId;
        sessionSyncJsInterface.parseJSSecret = parseJSSecret;
        webView.addJavascriptInterface(sessionSyncJsInterface , "android");
    }
}