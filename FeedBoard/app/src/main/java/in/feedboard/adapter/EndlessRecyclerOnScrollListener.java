package in.feedboard.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int[] firstVisibleItem;
    int visibleItemCount;
    int totalItemCount;

    private int current_page = 1;

    private StaggeredGridLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(StaggeredGridLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager =  linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstCompletelyVisibleItemPositions(new int[2]);
        Log.e("last", ""+firstVisibleItem[0] +" "+ totalItemCount +" previous:"+previousTotal);


        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                Log.e("loading", ""+loading); }
        }


        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem[0] + visibleThreshold)) {
            // End has been reached
            Log.e("last in end", ""+firstVisibleItem[0] +" "+ totalItemCount +" previous:"+previousTotal);
            Log.e("last", "item official");
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
    }

    public abstract void onLoadMore(int current_page);
}