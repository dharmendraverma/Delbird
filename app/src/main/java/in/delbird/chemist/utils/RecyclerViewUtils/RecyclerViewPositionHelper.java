package in.delbird.chemist.utils.RecyclerViewUtils;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by deepak on 3/2/16.
 */
public class RecyclerViewPositionHelper {
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;

    public RecyclerViewPositionHelper(RecyclerView recyclerView)
    {
        this.recyclerView = recyclerView;
        this.layoutManager  = recyclerView.getLayoutManager();
    }
    //static to make it accessible in EndlessRecyclerOnScrollListener java file
    public static RecyclerViewPositionHelper createHelper(RecyclerView recyclerView)
    {
        if(recyclerView==null)
        {
            throw new NullPointerException("Recycler View is Null");

        }
        return  new RecyclerViewPositionHelper(recyclerView);
    }

    //Returns the adapter item count
    //Returns the Total Number of Items in a layout manager
    public int getItemCount()
    {
        return layoutManager ==  null ? 0 : layoutManager.getItemCount();
    }

    public int findFirstVisibleItemPosition()
    {
        View child = findOneVisibleChild(0,layoutManager.getChildCount(),false,true);
        return child == null ?RecyclerView.NO_POSITION :  recyclerView.getChildAdapterPosition(child);
    }

    //Returns the adapter position of the first visible view. The Position does not include adapter
    // changes that were dispatched after the last layoutt pass

    //Returns the adapter position of the first fully visible item or RECYCLERVIEW : NOPOSITION

    public int findFisrtCompletelyVisibleItemPosition()
    {
        View child = findOneVisibleChild(0,layoutManager.getChildCount(),true,false);
        return child==null ? recyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);

    }

    //Returns the adapter position of the last visible view. This position does not include
    //adapter changes that were displayed after the last layout pass.

    //Returns the adapter position of the last visible view or the RecyclerView:NoPosition if there arent any
    // any visible items

    public int findLastVisibleItemPosition()
    {
        View child = findOneVisibleChild(layoutManager.getChildCount() -1,-1,false,false);
        return child == null? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    /**
     * Returns the adapter position of the last fully visible view. This position does not include
     * adapter changes that were dispatched after the last layout pass.
     *
     * @return The adapter position of the last fully visible view or
     * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
     */
    public int findLateCompletelyVisibleItemPosition()
    {
        View child = findOneVisibleChild(layoutManager.getChildCount()-1,-1,true,false);
        return child == null ?RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }


    View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible, boolean acceptPartiallyVisble)
    {
        OrientationHelper helper;

        //Check if this view can be scrolled vertically or Horizopntally in a certain direction.
        if(layoutManager.canScrollVertically())
        {
            //Creates a vertical OrientationHelper for the given LayoutManager.
            helper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        else
        {
            helper =  OrientationHelper.createHorizontalHelper(layoutManager);
        }
        int start = helper.getStartAfterPadding();//Returns the start position of the layout after the start padding is added.
        int end = helper.getEndAfterPadding();//Returns the end position of the layout after the end padding is removed.
        int next = toIndex > fromIndex ? 1: -1; //give 1 if to > from for traversing fwd else -1 to traverse back
        View partiallyVisible=null;
        for(int i = fromIndex;i!=toIndex; i+=next)
        {
            View child = layoutManager.getChildAt(i); //Returns the view at the specified position in the group.
            // Returns the view at the specified position in the group.
            int childStart = helper.getDecoratedStart(child); //Returns the end of the view including its decoration and margin.
            int childEnd = helper.getDecoratedEnd(child); //Returns the end of the view including its decoration and margin.
            if(childStart<end && childEnd>start)
            {
                if(completelyVisible)
                {
                    if(childStart>=start &&childEnd<=end)
                    {
                        return  child;
                    }
                    else if(acceptPartiallyVisble && partiallyVisible==null)
                    {
                        partiallyVisible = child;
                    }
                }
                else
                {
                    return child;
                }
            }
        }

        return partiallyVisible;
    }


}
