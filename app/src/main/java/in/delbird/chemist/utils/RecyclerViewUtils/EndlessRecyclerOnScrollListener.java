package in.delbird.chemist.utils.RecyclerViewUtils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by deepak on 3/2/16.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String Tag = "EndlessScrollListener";
    private int previousTotal = 0; //Total Number of  items in the dataset after the last load
    private Boolean loading = true; //true if we are still waiting for the last set of data
    private int visibleThreshold = 5 ; // The minimum amount of items to have below your currrent scroll position before loading more
    int firstVisibleItem, visibleItemCount,totalItemCount;
    private int current_page=1;
    RecyclerViewPositionHelper mRecyclerViewHelper;





    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
        visibleItemCount = recyclerView.getChildCount(); //getChildCount  = Returns the number of children in the group.
        totalItemCount = mRecyclerViewHelper.getItemCount(); // getItemCount = Returns the total number of items in the data set hold by the adapter.
        firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
        if(loading)
        {
            if(totalItemCount > previousTotal)
            {
                loading = false;
                previousTotal= totalItemCount;
            }
        }
        if(!loading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + visibleThreshold))
        {
            current_page++;
            onLoadMore(current_page);
            loading = true;
        }
    }
    public abstract void onLoadMore(int currentPage);

}
