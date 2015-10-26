package in.feedboard.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import in.feedboard.R;

/**
 * Created by Admin-PC on 10/15/2015.
 */
public class Splash extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.parseColor("#019aae"));//0b616c
		}
		new Handler().postDelayed(new Runnable()
		{

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

			@Override
			public void run()
			{
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(Splash.this, MainActivity.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, 1800);
	}
}
