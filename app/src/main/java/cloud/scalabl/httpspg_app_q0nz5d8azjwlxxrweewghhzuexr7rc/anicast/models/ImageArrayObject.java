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


public class ImageArrayObject {

    private int order;
    private String url;

    public ImageArrayObject(int order, String url) {
        this.order = order;
        this.url = url;
    }

    public void setOrder(int order){
        this.order = order;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public int getOrder(){return order;}
    public String getUrl(){return url;}

}
