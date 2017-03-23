package com.example.test.commonlibrary.customWidget;

import android.view.View;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by lijie on 2017/3/23.
 */

public abstract class SplitWorldHelper {
    private TagFlowLayout tagFlowLayout;
    private String[] words;

    public SplitWorldHelper(TagFlowLayout tagFlowLayout){
        this.tagFlowLayout = tagFlowLayout;
        init();
    }

    private void init(){

        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                List<String> selectWords = new ArrayList<String>();
                Iterator<Integer> iterator = selectPosSet.iterator();
                while (iterator.hasNext()){
                    selectWords.add(words[iterator.next()]);
                }

                onSelect(selectWords);
            }
        });
    }

    public void show(String text){
        words = text.split(" |,|\\?|\\.");

        tagFlowLayout.setAdapter(new TagAdapter(words) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                return getButton((String) o);
            }
        });

    }

    public abstract View getButton(String word);
    public abstract void onSelect(List<String> selectWords);
}
