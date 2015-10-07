package in.feedboard.tabsswipe.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.toolbox.ImageLoader;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import in.feedboard.tabsswipe.FullDetails;
import in.feedboard.tabsswipe.R;
import in.feedboard.tabsswipe.app.AppController;

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
		ImageView imgMain;
		TextView title, tvDesc, tvComment;
		ImageView share, bookmark;


        View v;

        TrendViewHolder(View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cardView);
			title = (TextView) itemView.findViewById(R.id.title);
            share = (ImageView) itemView.findViewById(R.id.share);
            imgMain = (ImageView)itemView.findViewById(R.id.imgMain);


        }
    }





    @Override
    public TrendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_for_staggered, parent, false);
        TrendViewHolder cvh = new TrendViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(TrendViewHolder holder, int position) {

        final String titleText = trendlist.get(position).get("title").toString();
        final String imgurl = trendlist.get(position).get("imgurl").toString();
       //"http://www.feedboard.in/api/media/images/"+imgurl;
        holder.cv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, FullDetails.class);
                context.startActivity(intent);
            }
        });
		holder.title.setText(titleText);
        if (imgurl != null)
        makeImageRequest("http://www.feedboard.in/api/media/images/"+imgurl, holder.imgMain);

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
    }

    @Override
    public int getItemCount() {
        return trendlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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

}