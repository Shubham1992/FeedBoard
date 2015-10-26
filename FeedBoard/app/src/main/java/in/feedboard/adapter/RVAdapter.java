package in.feedboard.adapter;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;
import java.util.Map;

import in.feedboard.activities.FullDetails;
import in.feedboard.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TrendViewHolder>{




    List<Map> trendlist;

    Context context;
    int loaderimg = R.drawable.ronaldo;


    public RVAdapter(List dv, Context c)
    {
         trendlist =dv;
         context=c;
         Log.d("list in rv adapter", trendlist.toString());


    }


    public class TrendViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ViewGroup llcardView;
		ImageView imgMain , tempImg;
		TextView title, tvDesc, tvComment , tvheadline;
		ImageView share, bookmark;


        View v;

        TrendViewHolder(View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cardView);
			title = (TextView) itemView.findViewById(R.id.title);
            share = (ImageView) itemView.findViewById(R.id.share);
            imgMain = (ImageView)itemView.findViewById(R.id.imgMain);
            tempImg = (ImageView)itemView.findViewById(R.id.tempimg);
            tvheadline = (TextView)itemView.findViewById(R.id.tvHeadline);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");

            tvheadline.setTypeface(custom_font);

        }
    }





    @Override
    public TrendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_for_staggered, parent, false);
        TrendViewHolder cvh = new TrendViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(final TrendViewHolder holder, int position) {

        final String titleText = trendlist.get(position).get("title").toString();
        final String imgurl = trendlist.get(position).get("imgurl").toString();
        final String id = trendlist.get(position).get("id").toString();
        final  String headline = trendlist.get(position).get("headline").toString();
       //"http://www.feedboard.in/api/media/images/"+imgurl;
        holder.cv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, FullDetails.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });

		holder.title.setText(titleText);
        if (imgurl != null)


        {
            RequestQueue requestQueue = MyVolleySingleton.getInstance(context).getRequestQueue();
            ImageRequest mainImageRequest = new ImageRequest("http://www.feedboard.in/api/media/images/" + imgurl,
                    new Response.Listener<Bitmap>() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            // set the image here
							holder.tempImg.setImageBitmap(bitmap);
							holder.imgMain.setBackground(holder.tempImg.getDrawable());

                            // hide the spinner here
                        }
                    }, 0, 0, null, null);

            requestQueue.add(mainImageRequest);

         //   makeImageRequest("http://www.feedboard.in/api/media/images/" + imgurl, holder.imgMain, holder.tempImg);
        }
        holder.share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               try
               {


				   String shareBody = "Here is the share content body";
				   Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				   sharingIntent.setType("text/plain");
				   sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
				   sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				   context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
               }
               catch (ActivityNotFoundException e)
               {
                  // Snackbar snackbar = Snackbar.make()
                   Toast.makeText(context, "No app to perform task",Toast.LENGTH_SHORT).show();
               }
            }
        });

        holder.tvheadline.setText(headline);
    }

    @Override
    public int getItemCount() {
        return trendlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}