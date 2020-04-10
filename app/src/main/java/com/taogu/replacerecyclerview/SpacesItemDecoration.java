package com.taogu.replacerecyclerview;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * LinearLayoutManager  设置RecyclerView列表布局Item之间间距
 * */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;//间距
    private int spanCount=2;//行数
    private int startPosition=0;//从需要位置设置间距
    public SpacesItemDecoration(int space) {
        this.space = space;
    }
    public SpacesItemDecoration(int space,int spanCount) {
        this.space = space;
        this.spanCount=spanCount;
    }
    public SpacesItemDecoration(int space,int spanCount,int startPosition) {
        this.space = space;
        this.spanCount=spanCount;
        this.startPosition=startPosition;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int position=parent.getChildAdapterPosition(view);
        if(position<startPosition){
            return;
        }
        position-=startPosition;

        StaggeredGridLayoutManager.LayoutParams params= (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        if (params.getSpanIndex() != StaggeredGridLayoutManager.LayoutParams.INVALID_SPAN_ID) {

            //getSpanIndex方法不管控件高度如何，始终都是从左往右返回index
            if(params.getSpanIndex() % spanCount == 0) {
                outRect.left = space;
                outRect.right = space / 2;
            }else if (params.getSpanIndex() % spanCount == spanCount - 1){
                //右列
                outRect.left = space / 2;
                outRect.right = space;
            }else {
                outRect.left = space / 2;
                outRect.right = space / 2;
            }
        }

        if (position < spanCount) {
            outRect.top = space;
        }
        outRect.bottom = space;
    }
}
