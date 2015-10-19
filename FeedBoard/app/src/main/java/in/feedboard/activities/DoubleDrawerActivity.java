package in.feedboard.activities;


import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.feedboard.R;
import in.feedboard.adapter.MyVolleySingleton;
import in.feedboard.headerclass.RecyclerViewHeader;
import in.feedboard.utils.JSONToList;

import in.feedboard.adapter.HeadlinesTabsPagerAdapter;
import in.feedboard.adapter.RVAdapter;
import in.feedboard.app.AppController;
import in.feedboard.savedobjects.SavedViewpagerJson;
import in.feedboard.utils.Const;


/**
 * Created by Admin-PC on 9/14/2015.
 */
public class DoubleDrawerActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener
{//00BCD4

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private View mLeftDrawerView;
	private View mRightDrawerView;
	private Toolbar toolbar;
    private TextView btnEntertainment;
	Button btnHome, btnNews, btnTechnology;
    Fragment homeFragment;
    Fragment entertainmentFragment;
    Fragment newsFragment;
    Fragment techFragment;
	Fragment photographyFragment;
	Fragment healthFragment;
    RecyclerView rvHome;
    private LayoutInflater inflater;
    private ViewPager viewPager;
    String tag_json_obj = "json_obj_req";
    private List<HashMap> list;
    private HeadlinesTabsPagerAdapter mAdapter;
    ViewGroup vgCntnr;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView headLineBookmark;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.toolbar, null);*/
		toolbar = (Toolbar) findViewById(R.id.toolbar);
	    headLineBookmark = (ImageView) findViewById(R.id.bookmark);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayoutid);

        swipeRefreshLayout.setOnRefreshListener(this);

       /* swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        recreate();
                                    }
                                }
        );*/
			Log.e("toolbar", toolbar.toString());
		if(toolbar != null)

			setSupportActionBar(toolbar);

		getSupportActionBar().setTitle(getString(R.string.app_name));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		//getSupportActionBar().setHomeButtonEnabled(true);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        if(mDrawerLayout == null || mLeftDrawerView == null || mRightDrawerView == null || mDrawerToggle == null) {
            // Configure navigation drawer
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mLeftDrawerView = findViewById(R.id.left_drawer);
            mRightDrawerView = findViewById(R.id.right_drawer);



            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View drawerView) {
                    if(drawerView.equals(mLeftDrawerView)) {
                        getSupportActionBar().setTitle(getTitle());
                        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                        mDrawerToggle.syncState();
                    }
                }

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    if(drawerView.equals(mLeftDrawerView)) {
                        getSupportActionBar().setTitle(getString(R.string.app_name));
                        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                        mDrawerToggle.syncState();
                    }
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    // Avoid normal indicator glyph behaviour. This is to avoid glyph movement when opening the right drawer
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            };



            mDrawerLayout.setDrawerListener(mDrawerToggle); // Set the drawer toggle as the DrawerListener


        }



        rvHome = (RecyclerView) findViewById(R.id.rvHome);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        final StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);


        vgCntnr = (ViewGroup) findViewById(R.id.cntainerBelowToolbar);

        rvHome.setLayoutManager(layoutManager2);





        RecyclerViewHeader header = (RecyclerViewHeader) findViewById( R.id.header);
        header.attachTo(rvHome, true);






        makeJsonObjReq();
        makeJsonObjReqHeadlines();
        headLineBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String android_id = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            Log.e("Android id", android_id);
            }
        });

        setDrawerButtons();


    }


	void clearAllFragments()
	{
		if(newsFragment != null)
		{
			getSupportFragmentManager().beginTransaction().
				remove(newsFragment).commit();
		}


		if(entertainmentFragment != null)
		{
			getSupportFragmentManager().beginTransaction().
					remove(entertainmentFragment).commit();
		}


		if(techFragment != null)
		{
			getSupportFragmentManager().beginTransaction().
					remove(techFragment).commit();
		}

	}
    void setDrawerButtons()
    {
		btnHome = (Button) findViewById(R.id.btnhome);
		btnHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

                //clear all fragments function
				clearAllFragments();
                vgCntnr.setVisibility(View.VISIBLE);
                mDrawerLayout.closeDrawers();
			}
		});

        btnNews = (Button) findViewById(R.id.news);
        btnNews.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //clear all fragments function
				clearAllFragments();

                newsFragment = new News();
                vgCntnr.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, newsFragment).commit();
                mDrawerLayout.closeDrawers();
            }
        });

        btnEntertainment = (Button) findViewById(R.id.entertainment);
        btnEntertainment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //clear all fragments function
				clearAllFragments();

                entertainmentFragment = new Entertainment();
                vgCntnr.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, entertainmentFragment).commit();
                mDrawerLayout.closeDrawers();
            }
        });

        btnTechnology = (Button) findViewById(R.id.technology);
        btnTechnology.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //clear all fragments function
				clearAllFragments();

                techFragment = new Technology();
                vgCntnr.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, techFragment).commit();
                mDrawerLayout.closeDrawers();
            }
        });


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
		btnHome.setTypeface(custom_font);
		btnEntertainment.setTypeface(custom_font);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		// If the nav drawer is open, hide action items related to the content view
		for(int i = 0; i< menu.size(); i++)
			menu.getItem(i).setVisible(!mDrawerLayout.isDrawerOpen(mLeftDrawerView));

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch(item.getItemId()) {
			case android.R.id.home:
				mDrawerToggle.onOptionsItemSelected(item);

				if(mDrawerLayout.isDrawerOpen(mRightDrawerView))
					mDrawerLayout.closeDrawer(mRightDrawerView);

				return true;

		/*	case R.id.action_right_drawer:
				if(mDrawerLayout.isDrawerOpen(mRightDrawerView))
					mDrawerLayout.closeDrawer(mRightDrawerView);
				else
				mDrawerLayout.openDrawer(mRightDrawerView);


				return true;*/
		}

		return super.onOptionsItemSelected(item);
	}


    /**
     * Making json object request
     * */
    private JSONObject makeJsonObjReq() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_HOME, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());


                        try
                        {

                            JSONToList jsonToList = new JSONToList();
                            list = jsonToList.getListFromJSON(response);
							Log.e("list", list.toString());

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        RVAdapter rvAdapter = new RVAdapter(list , DoubleDrawerActivity.this);
                        rvHome.setAdapter(rvAdapter);
                        swipeRefreshLayout.setRefreshing(false);
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
    /**
     * Making json object request
     * */
    private JSONObject makeJsonObjReqHeadlines() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_HEADLINES, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());

                        SavedViewpagerJson.getObject().setObj(response);
                        setHeadlines(response);

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

    private void setHeadlines(JSONObject response)
    {      Log.e("setting", "viewpager");
       TextView tvTitle = (TextView) findViewById(R.id.title);

        tvTitle.setText(response.optJSONArray("stories").optJSONObject(0).optString("title").toString());

        final ImageView imgHead = (ImageView) findViewById(R.id.imgMain);
        final ImageView imgTmp = (ImageView) findViewById(R.id.tmpImg);

        String imgurl =response.optJSONArray("stories").optJSONObject(0).optString("imageurl").toString();

        if (imgurl != null)


        {
            RequestQueue requestQueue = MyVolleySingleton.getInstance(DoubleDrawerActivity.this).getRequestQueue();
            ImageRequest mainImageRequest = new ImageRequest("http://www.feedboard.in/api/media/images/" + imgurl,
                    new Response.Listener<Bitmap>() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            // set the image here
                            imgTmp.setImageBitmap(bitmap);
                            imgHead.setBackground(imgTmp.getDrawable());
                            // hide the spinner here
                        }
                    }, 0, 0, null, null);

            requestQueue.add(mainImageRequest);

            //   makeImageRequest("http://www.feedboard.in/api/media/images/" + imgurl, holder.imgMain, holder.tempImg);
        }

        //makeImageRequest("http://www.feedboard.in/api/media/images/"+imgurl, imgHead);
    }


    private void makeImageRequest(String url , ImageView img)
    {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();


        // Loading image with placeholder and error image
        imageLoader.get(url, ImageLoader.getImageListener(
                img, R.drawable.ico_loading, R.drawable.ico_error));

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            // cached response doesn't exists. Make a network call here
        }

    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        recreate();
    }
}