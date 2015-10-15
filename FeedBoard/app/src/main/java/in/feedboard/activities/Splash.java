package in.feedboard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
				//finish();
			}
		}, 3000);
	}
}
