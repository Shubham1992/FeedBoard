package in.feedboard.tabsswipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.feedboard.tabsswipe.adapter.RVAdapter;
import in.feedboard.tabsswipe.app.AppController;
import in.feedboard.tabsswipe.savedobjects.SavedViewpagerJson;
import in.feedboard.tabsswipe.utils.Const;

public class News extends Fragment {


	private List<HashMap> list;
	RecyclerView rvNews;
    Button button ;
	String tag_json_obj = "json_obj_req";



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.news, container, false);
		rvNews = (RecyclerView) rootView.findViewById(R.id.rvNews);
        button = (Button) rootView.findViewById(R.id.btn);
		StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
		final StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);



		rvNews.setLayoutManager(layoutManager2);
        button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				rvNews.setLayoutManager(layoutManager2);
			}
		});

		RecyclerViewHeader header = RecyclerViewHeader.fromXml(getActivity(), R.layout.headlines);
		header.attachTo(rvNews);


        makeJsonObjReq();


		return rootView;
	}

	/**
	 * Making json object request
	 * */
	private JSONObject makeJsonObjReq() {


		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
				Const.URL_NEWS, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.e("response", response.toString());


						try
						{

							JSONToList jsonToList = new JSONToList();
							list = jsonToList.getListFromJSON(response);

						} catch (JSONException e)
						{
							e.printStackTrace();
						}

						RVAdapter rvAdapter = new RVAdapter(list , getActivity());
						rvNews.setAdapter(rvAdapter);
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("Error Volley Json", "Error: " + error.getMessage());

			}
		}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				return headers;
			}

			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("name", "Androidhive");
				params.put("email", "abc@androidhive.info");
				params.put("pass", "password123");

				return null;
			}

		};

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	return null;

	}


    @Override
	public void onResume() {

	}
}
