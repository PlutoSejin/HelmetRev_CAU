package com.CAU.helmetrev_new;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class TalkAdapter extends BaseAdapter {
    ArrayList<TalkItem> talkItems;
    @Override
    public int getCount() {
        return talkItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
