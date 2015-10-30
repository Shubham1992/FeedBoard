package in.feedboard.activities;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.feedboard.R;
import in.feedboard.adapter.EndlessRecyclerOnScrollListener;
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
    private Button btnFunny , btnHealth, btnSports;
    private Fragment funnyFragment;
    private Button btnBusiness;
    private Fragment businessFragment;
    private Fragment sportsFragment;
    private TextView toolbartitle;
    private ImageView btnShare;
    private Button btnLifestyle;
    private Fragment lifestyleFragment;
    private SharedPreferences sharedpreferences;
	private Button btnContribute, btnShareApp, btnRateApp, btnContact;

	private Fragment contactFragment;
    TextView tvLoading;
    private BottomSheetLayout bottomSheet;
    private Intent sharingIntent;
    private String shareBody;
    private IntentPickerSheetView intentPickerSheet;
	ViewGroup loginUser;
	CoordinatorLayout coordinatorLayout;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.toolbar, null);*/
		toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbartitle = (TextView) findViewById(R.id.toolbartitle);
	    headLineBookmark = (ImageView) findViewById(R.id.bookmark);
        tvLoading = (TextView) findViewById(R.id.tvLoading);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayoutid);
        bottomSheet = (BottomSheetLayout) findViewById(R.id.bottomsheet);
		coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        swipeRefreshLayout.setOnRefreshListener(this);


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

        rvHome.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager2)
		{
			@Override
			public void onLoadMore(int current_page)
			{
				Log.e("last", "item");
				// do something...
			}
		});




        // check for connection
        checkConnectionAndSendReqst();





        setDrawerButtons();


    }

    void checkConnectionAndSendReqst()
    {


        if(isConnected(DoubleDrawerActivity.this))
        {

            sendAndroidId();

            makeJsonObjReqHeadlines();

        }
        else
        {
            buildDialog(DoubleDrawerActivity.this).show();
        }

    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else return false;
    }

    public AlertDialog.Builder buildDialog(Context context) {
        Log.e("build dialog", "building");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You have no internet connection");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                checkConnectionAndSendReqst();
            }
        });

        return builder;
    }

    private void sendAndroidId()
    {
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        boolean isFirst = prefs.getBoolean("first", true);
        if (isFirst == true) {
            String android_id = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
          Log.e("android id first", android_id);
        }


        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("first", false);

        editor.commit();
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
        if(businessFragment != null)
        {
            getSupportFragmentManager().beginTransaction().
                    remove(businessFragment).commit();
        }
        if(funnyFragment != null)
        {
            getSupportFragmentManager().beginTransaction().
                    remove(funnyFragment).commit();
        }

        if(sportsFragment != null)
        {
            getSupportFragmentManager().beginTransaction().
                    remove(sportsFragment).commit();
        }
        if(healthFragment != null)
		{
			getSupportFragmentManager().beginTransaction().
					remove(healthFragment).commit();
		}
		if(lifestyleFragment != null)
		{
			getSupportFragmentManager().beginTransaction().
					remove(lifestyleFragment).commit();
		}


		if(contactFragment != null)
		{
			getSupportFragmentManager().beginTransaction().
					remove(contactFragment).commit();
		}

        toolbartitle.setText("FeedBoard");
	}
    void setDrawerButtons()
    {
		loginUser = (ViewGroup) findViewById(R.id.loginContainer);

		loginUser.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				/*Intent intent = new Intent(DoubleDrawerActivity.this, UserRegisteration.class);
				startActivity(intent);*/

			}
		});

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
                toolbartitle.setText("News");
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
                toolbartitle.setText("Entertainment");
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
                toolbartitle.setText("Technology");
            }
        });

        btnFunny = (Button) findViewById(R.id.funny);
        btnFunny.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //clear all fragments function
                clearAllFragments();

                funnyFragment = new Funny();
                vgCntnr.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, funnyFragment).commit();
                mDrawerLayout.closeDrawers();
                toolbartitle.setText("Funny");
            }
        });

        btnSports = (Button) findViewById(R.id.sports);
        btnSports.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //clear all fragments function
                clearAllFragments();

               sportsFragment = new Sports();
                vgCntnr.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, sportsFragment).commit();
                mDrawerLayout.closeDrawers();
                toolbartitle.setText("Sports");
            }
        });

        btnHealth = (Button) findViewById(R.id.health);
        btnHealth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //clear all fragments function
                clearAllFragments();

                healthFragment = new Health();
                vgCntnr.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, healthFragment).commit();
                mDrawerLayout.closeDrawers();
                toolbartitle.setText("Health");
            }
        });

        btnBusiness = (Button) findViewById(R.id.business);
        btnBusiness.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //clear all fragments function
                clearAllFragments();

                businessFragment = new Business();
                vgCntnr.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, businessFragment).commit();
                mDrawerLayout.closeDrawers();
                toolbartitle.setText("Business");
            }
        });

        btnLifestyle = (Button) findViewById(R.id.lifestyle);
        btnLifestyle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //clear all fragments function
                clearAllFragments();

                lifestyleFragment = new Lifestyle();
                vgCntnr.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, lifestyleFragment).commit();
                mDrawerLayout.closeDrawers();
                toolbartitle.setText("Lifestyle");
            }
        });



		btnContribute = (Button) findViewById(R.id.contribute);
		btnContribute.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				//clear all fragments function
				clearAllFragments();

				contactFragment = new Contact();
				vgCntnr.setVisibility(View.GONE);

				getSupportFragmentManager().beginTransaction()
						.add(R.id.fragmentHolder, contactFragment).commit();
				mDrawerLayout.closeDrawers();
				toolbartitle.setText("Contact");
			}
		});
		btnShareApp = (Button) findViewById(R.id.shareapp);
		btnShareApp.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

				mDrawerLayout.closeDrawers();

				sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, Bored of reading the same old stuff on internet. Download Feedboard at https://goo.gl/1QR36e and read interesting stories on-the-go. ");
				intentPickerSheet = new IntentPickerSheetView(DoubleDrawerActivity.this, sharingIntent, "Share with...", new IntentPickerSheetView.OnIntentPickedListener() {
					@Override
					public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo)
					{
						bottomSheet.dismissSheet();
						startActivity(activityInfo.getConcreteIntent(sharingIntent));
					}

				});
				bottomSheet.showWithSheetView(intentPickerSheet);
			}
		});
		btnRateApp = (Button) findViewById(R.id.rate);
        btnRateApp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mDrawerLayout.closeDrawers();
                String url = "https://play.google.com/store/apps/details?id=in.feedboard&hl=en";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

		btnContact = (Button) findViewById(R.id.contact);
		btnContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //clear all fragments function
                clearAllFragments();

                contactFragment = new Contact();
                vgCntnr.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentHolder, contactFragment).commit();
                mDrawerLayout.closeDrawers();
                toolbartitle.setText("Contact");
            }
        });





		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

		btnHome.setTypeface(custom_font);
		btnEntertainment.setTypeface(custom_font);
        btnNews.setTypeface(custom_font);
        btnTechnology.setTypeface(custom_font);
        btnFunny.setTypeface(custom_font);
        btnSports.setTypeface(custom_font);
        btnHealth.setTypeface(custom_font);
        btnBusiness.setTypeface(custom_font);
        btnLifestyle.setTypeface(custom_font);
		btnContact.setTypeface(custom_font);
		btnShareApp.setTypeface(custom_font);
		btnRateApp.setTypeface(custom_font);
		btnContribute.setTypeface(custom_font);

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
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

                        RecyclerViewHeader header = (RecyclerViewHeader) findViewById( R.id.header);
                        header.attachTo(rvHome, true);
                        setButtonsShareAndBookmark();
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

    void setButtonsShareAndBookmark()
    {

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
                        tvLoading.setVisibility(View.GONE);
                        makeJsonObjReq();
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

    private void setHeadlines(final JSONObject response)
    {      Log.e("setting", "headline");
        TextView tvTitle = (TextView) findViewById(R.id.title);
		TextView tvHeadline = (TextView) findViewById(R.id.tvHeadline);

        tvTitle.setText(response.optJSONArray("stories").optJSONObject(0).optString("title").toString());
		tvHeadline.setText(response.optJSONArray("stories").optJSONObject(0).optString("headline").toString());
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

		tvHeadline.setTypeface(custom_font);

		final ImageView imgHead = (ImageView) findViewById(R.id.imgMain);
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoubleDrawerActivity.this, FullDetails.class);
                intent.putExtra("id",response.optJSONArray("stories").optJSONObject(0).optString("id").toString());
                startActivity(intent);
            }
        });
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


        shareBody = response.optJSONArray("stories").optJSONObject(0).optString("headline").toString();
        btnShare = (ImageView) findViewById(R.id.share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
				shareBody = shareBody + ". Read more at FeedBoard App: https://goo.gl/1QR36e";
                sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "feedboard.in");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                intentPickerSheet = new IntentPickerSheetView(DoubleDrawerActivity.this, sharingIntent, "Share with...", new IntentPickerSheetView.OnIntentPickedListener() {
                    @Override
                    public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo)
                    {
                        bottomSheet.dismissSheet();
                        startActivity(activityInfo.getConcreteIntent(sharingIntent));
                    }

                });
                bottomSheet.showWithSheetView(intentPickerSheet);
            }
        });
		headLineBookmark.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String android_id = Settings.Secure.getString(getContentResolver(),
						Settings.Secure.ANDROID_ID);
				Snackbar snackbar = Snackbar
						.make(coordinatorLayout, "Feature not Available", Snackbar.LENGTH_SHORT);

				snackbar.show();
				Log.e("Android id", android_id);
			}
		});


    }




    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        recreate();
    }
}