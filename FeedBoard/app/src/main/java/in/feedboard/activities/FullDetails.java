package in.feedboard.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.feedboard.R;
import in.feedboard.adapter.RVAdapter;
import in.feedboard.app.AppController;
import in.feedboard.utils.Const;
import in.feedboard.utils.JSONToList;


/**
 * Created by Admin-PC on 9/30/2015.
 */
public class FullDetails extends AppCompatActivity
{

	private CollapsingToolbarLayout collapsingToolbarLayout;
	String id ;
	String tag_json_obj = "json_obj_req";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_details);

		id = getIntent().getStringExtra("id");
		Log.e("id in fulldeatil", id);
		makeJsonObjReq();
		//RecyclerView recyclerView= (RecyclerView) findViewById(R.id.scrollableview);
		NestedScrollView recyclerView= (NestedScrollView) findViewById(R.id.scrollableview);

		collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		collapsingToolbarLayout.setTitle("Ronaldo");
		//collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
		Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);


		collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#08aec4"));
		collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#08aec4"));

			}

	/**
	 * Making json object request
	 * */
	private JSONObject makeJsonObjReq() {


		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				"http://postcatcher.in/catchers/561fa168653e400300000023", null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.e("response", response.toString());





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
			/*@Override
			public Map<String, String> getHeaders() throws AuthFailureError
			{
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				return null;
			}*/

			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("id", id);

				return params;
			}

		};

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_obj);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
		return null;

	}
}
