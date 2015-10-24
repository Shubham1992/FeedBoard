package in.feedboard.activities;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.NestedScrollView;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.feedboard.R;
import in.feedboard.adapter.MyVolleySingleton;
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
	ImageView headerImage, tmpImg; LinearLayout  detailContainer;
	TextView tvDetails;
	ViewGroup coordinatorLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_details);
		coordinatorLayout = (ViewGroup) findViewById(R.id.cordnatorFullDetail);
		headerImage = (ImageView)findViewById(R.id.header);
		detailContainer = (LinearLayout) findViewById(R.id.details_container);
		tmpImg = (ImageView)findViewById(R.id.tmpImg);
		tvDetails = (TextView) findViewById(R.id.tvDetails);
		id = getIntent().getStringExtra("id");
		Log.e("id in fulldeatil", id);
		makeJsonObjReq();
		//RecyclerView recyclerView= (RecyclerView) findViewById(R.id.scrollableview);
		NestedScrollView recyclerView= (NestedScrollView) findViewById(R.id.scrollableview);

		collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		//collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
		Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);


		collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#08aec4"));
		collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#08aec4"));



			}

	private void setImage(final ImageView headerImage , String urlImg)
	{

			RequestQueue requestQueue = MyVolleySingleton.getInstance(FullDetails.this).getRequestQueue();
			ImageRequest mainImageRequest = new ImageRequest("http://www.feedboard.in/api/media/images/" + urlImg,
					new Response.Listener<Bitmap>() {
						@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
						@Override
						public void onResponse(Bitmap bitmap) {
							// set the image here
							tmpImg.setImageBitmap(bitmap);
							headerImage.setBackground(tmpImg.getDrawable());
							//headerImage.setImageResource(Integer.parseInt(null));
							// hide the spinner here
						}
					}, 0, 0, null, null);

			requestQueue.add(mainImageRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				finish();
				return true;

			case R.id.action_bookmark:

				//Toast.makeText(FullDetails.this , "Bookmarked", Toast.LENGTH_SHORT).show();
				Snackbar snackbar = Snackbar
						.make(coordinatorLayout, "Bookmarked", Snackbar.LENGTH_SHORT);

				snackbar.show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * Making json object request
	 * */
	private JSONObject makeJsonObjReq() {


		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
				"http://www.feedboard.in/api/particularstory.php?id="+id, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.e("response full", response.toString());
						JSONArray arrStory = response.optJSONArray("stories");
						JSONObject objStoryParticular = arrStory.optJSONObject(0);

						String imgurl = objStoryParticular.optString("imageurl");
						String title = objStoryParticular.optString("title");
						String details = objStoryParticular.optString("details");

						collapsingToolbarLayout.setTitle(title);
						tvDetails.setText(details);
						setImage(headerImage, imgurl);




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
