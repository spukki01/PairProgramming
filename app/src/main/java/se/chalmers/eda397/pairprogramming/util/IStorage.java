package se.chalmers.eda397.pairprogramming.util;

import android.content.Context;

import org.json.JSONException;

import java.util.List;

public interface IStorage<T> {

    void store(T t, Context context) throws JSONException;

    List<T> fetchAll(Context context);

    boolean remove(T t, Context context);
}
