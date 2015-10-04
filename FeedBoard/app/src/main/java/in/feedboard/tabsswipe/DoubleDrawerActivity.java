package in.feedboard.tabsswipe;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Timer;
import java.util.TimerTask;

import in.feedboard.tabsswipe.adapter.HeadlinesTabsPagerAdapter;
import in.feedboard.tabsswipe.adapter.RVAdapter;
import in.feedboard.tabsswipe.adapter.TabsPagerAdapter;
import in.feedboard.tabsswipe.app.AppController;
import in.feedboard.tabsswipe.savedobjects.SavedViewpagerJson;
import in.feedboard.tabsswipe.utils.Const;

/**
 * Created by Admin-PC on 9/14/2015.
 */
public class DoubleDrawerActivity extends ActionBarActivity
{//00BCD4

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private View mLeftDrawerView;
	private View mRightDrawerView;
	private Toolbar toolbar;
    private TextView tvEntertainment ;
	Button btnHome;
    Fragment homeFragment;
    Fragment entertainmentFragment;
	Fragment techFragment;
	Fragment photographyFragment;
	Fragment healthFragment;
    RecyclerView rvHome;
    private LayoutInflater inflater;
    private ViewPager viewPager;
    String tag_json_obj = "json_obj_req";
    private List<HashMap> list;
    private HeadlinesTabsPagerAdapter mAdapter;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.toolbar, null);*/
		toolbar = (Toolbar) findViewById(R.id.toolbar);
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



        rvHome.setLayoutManager(layoutManager2);





        RecyclerViewHeader header = (RecyclerViewHeader) findViewById( R.id.header);
        header.attachTo(rvHome, true);

        viewPager = (ViewPager) findViewById(R.id.pager);




        makeJsonObjReq();
        makeJsonObjReqHeadlines();

        setDrawerButtons();


    }



    void setDrawerButtons()
    {
        tvEntertainment = (TextView) findViewById(R.id.entertainment);
        tvEntertainment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                entertainmentFragment = new Entertainment();
                //homeFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, entertainmentFragment).commit();
                mDrawerLayout.closeDrawers();
            }
        });

		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
		Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

		btnHome = (Button) findViewById(R.id.btnhome);
		btnHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});


		btnHome.setTypeface(custom_font);
		tvEntertainment.setTypeface(custom_font);
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

                        RVAdapter rvAdapter = new RVAdapter(list , DoubleDrawerActivity.this);
                        rvHome.setAdapter(rvAdapter);
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
        mAdapter = new HeadlinesTabsPagerAdapter(getSupportFragmentManager());

         viewPager.setAdapter(mAdapter);
         viewPager.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               view.getParent().requestDisallowInterceptTouchEvent(true);
               Toast.makeText(DoubleDrawerActivity.this, "hmm", Toast.LENGTH_SHORT).show();
               return false;
           }
       });

    }


}