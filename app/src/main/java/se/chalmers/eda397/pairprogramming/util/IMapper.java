package se.chalmers.eda397.pairprogramming.util;

import org.json.JSONException;
import org.json.JSONObject;

public interface IMapper<T> {
    T map(JSONObject jsonObject) throws JSONException;
}
