package com.example.test.commonlibrary.customWidget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by lijie on 2017/6/7.
 */

public abstract class RecyclerDragHelper {
    private ItemTouchHelper itemTouchHelper;

    public RecyclerDragHelper(){
        itemTouchHelper = new ItemTouchHelper(new ItemTouchCallback());
    }

    public void init(RecyclerView recyclerView){

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    class ItemTouchCallback extends ItemTouchHelper.Callback{

        /**
         * Item是否能被Swipe到dismiss
         * 也就是删除这条数据
         */
        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        /**
         * Item长按是否可以拖拽
         */
        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT;
            int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;

            return makeMovementFlags(dragFlag, swipeFlag);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            move(recyclerView, viewHolder, target);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            swiped(viewHolder, direction);
        }
    }

    public abstract void move(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);
    public abstract void swiped(RecyclerView.ViewHolder viewHolder, int direction);
}
