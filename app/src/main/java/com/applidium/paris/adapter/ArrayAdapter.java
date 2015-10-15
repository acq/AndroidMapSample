package com.applidium.paris.adapter;

import android.widget.BaseAdapter;

import java.util.List;

public abstract class ArrayAdapter<T> extends BaseAdapter {

    List<T> elements;

    public ArrayAdapter(List<T> elements) {
        this.elements = elements;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public T getItem(int i) {
        return elements.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
