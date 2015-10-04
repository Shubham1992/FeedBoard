package in.feedboard.tabsswipe.savedobjects;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Shubham on 10/2/2015.
 */
public class SavedViewpagerJson {
    static SavedViewpagerJson savedViewpagerJson =new SavedViewpagerJson();

    private SavedViewpagerJson(){}

    public JSONObject getObj() {
        return obj;
    }

    public void setObj(JSONObject obj) {
        this.obj = obj;
        Log.e("headlines", obj.toString());
    }

    private JSONObject obj;
    public static SavedViewpagerJson getObject()
    {
        return savedViewpagerJson;
    }



}
