package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models;

/**
 * Created by ssamkyu on 2017. 9. 15..
 */

public class NovelContent {

    private int order;
    private String content;
    private String type;

    public NovelContent(int order, String content, String type) {
        this.order = order;
        this.content = content;
        this.type = type;
    }

    public int getOrder(){return order;}
    public String getContent(){return content;}
    public String getType(){return type;}
}
