package com.example.test.commonlibrary.customWidget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import retrofit2.http.PUT;

/**
 * Created by lijie on 2017/4/7.
 */

public class RecyclerRefreshHelper {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener;

    public RecyclerRefreshHelper(RecyclerView recyclerView, SwipeRefreshLayout refreshLayout){
        this.recyclerView = recyclerView;
        this.refreshLayout = refreshLayout;

        init();
    }

    private void init(){

        //加载更多
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                private int lastVisibleItem;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    //0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                    // 滑动状态停止并且剩余少于两个item时，自动加载下一页
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastVisibleItem + 2 >= recyclerView.getLayoutManager().getItemCount()) {
                        //加载更多
                        if (onRefreshAndLoadMoreListener != null) {
                            onRefreshAndLoadMoreListener.onLoadMore();
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //获取加载的最后一个可见视图在适配器的位置。
                    lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                }
            });
        }

        //刷新
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //下拉刷新
                    if (onRefreshAndLoadMoreListener != null) {
                        onRefreshAndLoadMoreListener.onRefresh();
                    }
                }
            });
        }
    }

    /**
     * 设置下拉刷新以及加载更多监听
     * @param onRefreshAndLoadMoreListener
     */
    public void setOnRefreshAndLoadMoreListener(OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener){
        this.onRefreshAndLoadMoreListener = onRefreshAndLoadMoreListener;
    }

    public interface OnRefreshAndLoadMoreListener{
        void onRefresh();
        void onLoadMore();
    }
}
