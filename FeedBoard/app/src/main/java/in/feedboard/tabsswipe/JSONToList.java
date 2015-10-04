package in.feedboard.tabsswipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin-PC on 9/23/2015.
 */
public class JSONToList
{
	/*String jsonStr = "{\"stories\":[" +
			"{\"id\":\"1\"," +
			"\"name\":\"test name\"," +
			"\"title\":\"test title\"," +
			"\"subtitle\":\"test subttile\"," +
			"\"dateadded\":\"2015-09-17\"," +
			"\"datemodified\":\"2015-09-17\"}," +
			"{\"id\":\"3\"," +
			"\"name\":\"test2\"," +
			"\"title\":\"test2\"," +
			"\"subtitle\":\"test2\"," +
			"\"dateadded\":\"2015-09-18\"," +
			"\"datemodified\":\"2015-09-18\"}]," +
			"\"success\":1}";*/

	List<HashMap> getListFromJSON(JSONObject jsonObject) throws JSONException
	{List<HashMap> list = new ArrayList<>();

		JSONArray arrStories = jsonObject.optJSONArray("stories");

		for (int i = 0; i <arrStories.length(); i++)
		{
			JSONObject objStories = arrStories.getJSONObject(i);
			String name = objStories.optString("name");
			String title = objStories.optString("title");
			String subtitle = objStories.optString("subtitle");
			//String name = objStories.optString("name");


			HashMap map = new HashMap();
			map.put("name", name);
			map.put("title", title);
			map.put("subtitle", subtitle);


			list.add(map);
			//map.put("name", name);
		}


		return list;
	}
}
