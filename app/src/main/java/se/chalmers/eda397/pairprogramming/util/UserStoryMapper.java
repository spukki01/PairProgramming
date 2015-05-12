package se.chalmers.eda397.pairprogramming.util;

import org.json.JSONException;
import org.json.JSONObject;

import se.chalmers.eda397.pairprogramming.model.UserStory;

public class UserStoryMapper implements IMapper<UserStory>{

    @Override
    public UserStory map(JSONObject jsonObject) throws JSONException {
        UserStory us = new UserStory();

        us.setName(jsonObject.getString("name"));
        us.setCreatedDate(jsonObject.getString("created_at"));
        us.setType(jsonObject.getString("story_type"));
        us.setDescription(jsonObject.getString("description"));
        us.setEstimate(jsonObject.getInt("estimate"));

        return us;
    }

}
