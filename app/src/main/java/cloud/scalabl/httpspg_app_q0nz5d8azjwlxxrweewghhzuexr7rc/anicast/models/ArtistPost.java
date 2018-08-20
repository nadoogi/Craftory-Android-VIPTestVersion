package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.util.Date;
import java.util.List;

/**
 * Created by ssamkyu on 2017. 9. 5..
 */

@ParseClassName("ArtistPost")
public class ArtistPost extends ParseObject {

    private static final String USER_KEY = "user";
    private static final String USER_ID_KEY = "userId";
    private static final String BODY_KEY = "body";
    private static final String STATUS_KEY = "status";
    private static final String COMMENT_COUNT_KEY = "comment_count";
    private static final String LIKE_COUNT_KEY = "like_count";
    private static final String PATRON_COUNT_KEY = "patron_count";

    private static final String TAGS_KEY = "tags";
    private static final String THUMB_KEY = "thumbnail";
    private static final String IMAGE_KEY = "image";
    private static final String IMAGE_TYPE_KEY = "image_type";
    private static final String IMAGE_PNG_KEY = "image_png";
    private static final String IMAGE_ARRAY_KEY = "image_array";
    private static final String POST_TYPE_KEY = "post_type";
    private static final String TITLE_KEY = "title";
    private static final String YOUTUBE_ID_KEY = "youtubeId";
    private static final String IMAGE_YOUTUBE_KEY = "image_youtube";
    private static final String CONTENT_TYPE_KEY = "content_type";
    private static final String TARGET_AMOUNT_KEY = "target_amount";
    private static final String END_DATE_KEY = "endDate";
    private static final String REQUEST_TYPE_KEY = "request_type";
    private static final String OPEN_TYPE_KEY = "open_type";
    private static final String PRICE_TYPE_KEY = "price_type";
    private static final String PROFIT_SHARE_RATIO = "profit_share_ratio";
    private static final String LIKE_ARRAY_KEY ="like_array";
    private static final String IMAGE_CDN_KEY ="image_cdn";
    private static final String REPORT_COUNT_KEY = "report_count";
    private static final String REPORT_USER_KEY = "report_user";
    private static final String OPEN_TIME_KEY = "open_time";
    private static final String PROGRESS_KEY = "progress";
    private static final String NOVEL_CONTENT = "novel_content";
    private static final String COMMENT_KEY = "comment";
    private static final String ACHIEVE_AMOUNT_KEY = "achieve_amount";
    private static final String PATRON_ARRAY_KEY = "patron_array";
    private static final String PATRON_POINT_MANAGER_KEY = "patron_point_manager";
    private static final String SERIESE_ITEM_KEY = "seriese_item";
    private static final String SERIESE_PARENT_KEY = "seriese_parent";
    private static final String SERIESE_COUNT_KEY = "seriese_count";
    private static final String TAG_ARRAY_KEY = "tag_array";
    private static final String IMAGE_CDN_TEMP_KEY = "image_cdn_temp";
    private static final String SERIESE_STATUS_KEY = "seriese_status";
    private static final String DAY_ARRAY_KEY = "day_array";
    private static final String COMMERCIAL_KEY = "commercial";
    private static final String OPEN_DATE_KEY = "open_date";
    private static final String LEVEL_KEY = "level";
    private static final String REWARD_DETAIL_KEY = "reward_detail";
    private static final String REWARD_EXIST_KEY = "reward_exist";
    private static final String REQUEST_TO_KEY = "request_to";
    private static final String LIKE_USER_KEY = "like_user";
    private static final String SEARCH_STRING = "search_string";
    private static final String PATRON_DETAIL_INFO_KEY = "patron_detail_info";
    private static final String SERIESE_IN_KEY = "seriese_in";
    private static final String REWARD_DETAIL_INFO_KEY = "reward_detail_info";


    public int getProfitShareRatio(){return getInt(PROFIT_SHARE_RATIO);}
    public String getPriceType(){return getString(PRICE_TYPE_KEY);}
    public String getOpenType(){return getString(OPEN_TYPE_KEY);}
    public Date getOpenTime(){return getDate(OPEN_TIME_KEY);}
    public String getRequestType(){return getString(REQUEST_TYPE_KEY);}
    public Date getEndDate(){return getDate(END_DATE_KEY);}
    public int getTargetAmount(){return getInt(TARGET_AMOUNT_KEY);}
    public String getContentType(){return getString(CONTENT_TYPE_KEY);}
    public int getPatronCount(){return getInt(PATRON_COUNT_KEY);}
    public ParseObject getUserOb(){return getParseObject(USER_KEY);}
    public String getUserId(){return getString(USER_ID_KEY);}
    public String getBody(){return getString(BODY_KEY);}
    public Boolean getStatus(){return getBoolean(STATUS_KEY);}
    public int getCommentCount(){return getInt(COMMENT_COUNT_KEY);}
    public int getLikeCount(){return getInt(LIKE_COUNT_KEY);}
    public String getTags(){return getString(TAGS_KEY);}
    public ParseFile getThumbnail(){return getParseFile(THUMB_KEY);}
    public ParseFile getImage(){return getParseFile(IMAGE_KEY);}
    public String getImageType(){return getString(IMAGE_TYPE_KEY);}
    public ParseFile getImagePNG(){return getParseFile(IMAGE_PNG_KEY);}
    public JSONArray getImageArray(){return getJSONArray(IMAGE_ARRAY_KEY);}
    public String getPostType(){return getString(POST_TYPE_KEY);}
    public String getTitle(){return getString(TITLE_KEY);}
    public String getYoutubeId(){return getString(YOUTUBE_ID_KEY);}
    public String getYoutubeImage(){return getString(IMAGE_YOUTUBE_KEY);}

    public void setUserOb(ParseObject userOb){put(USER_KEY, userOb);}
    public void setBody(String body){put(BODY_KEY, body);}
    public void setStatus(Boolean status){put(STATUS_KEY, status);}
    public void setCommentCount(int commentCount){put(COMMENT_COUNT_KEY, commentCount);}
    public void setLikeCount(int likeCount){put(LIKE_COUNT_KEY, likeCount);}
    public void setTags(String tags){put(TAGS_KEY, tags);}
    public void setThumbnail(ParseFile thumbnail){put(THUMB_KEY, thumbnail);}
    public void setImage(ParseFile image){put(IMAGE_KEY, image);}
    public void setImageType(String imageType){put(IMAGE_TYPE_KEY, imageType);}
    public void setImagePNG(ParseFile imagePNG){put(IMAGE_PNG_KEY, imagePNG);}
    public void setImageArray(List list){put(IMAGE_ARRAY_KEY, list);}
    public void setPostType(String postType){put(POST_TYPE_KEY, postType);}
    public void setTitle(String title){put(TITLE_KEY,title);}
    public void setYoutubeId(String youtubeId){put(YOUTUBE_ID_KEY, youtubeId);}
    public void setYoutubeImage(String youtubImage){put(IMAGE_YOUTUBE_KEY, youtubImage);}

    

}
