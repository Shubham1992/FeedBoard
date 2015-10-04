package in.feedboard.tabsswipe;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;


/**
 * Created by Admin-PC on 9/30/2015.
 */
public class FullDetails extends AppCompatActivity
{

	private CollapsingToolbarLayout collapsingToolbarLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_details);
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
}
