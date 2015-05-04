package se.chalmers.eda397.pairprogramming.util;

import android.content.Context;

public interface IStorage<T> {
    void store(T t, Context context);

    T fetch(Context context);
}
