package se.chalmers.eda397.pairprogramming.core;

import org.json.JSONException;
import org.json.JSONObject;

import se.chalmers.eda397.pairprogramming.model.UserStory;
import se.chalmers.eda397.pairprogramming.util.IMapper;
import se.chalmers.eda397.pairprogramming.util.UserStoryMapper;

public class PivotalTrackerClient implements IPivotalTrackerClient {

    private IConnectionManager mConnectionManager;

    public PivotalTrackerClient(IConnectionManager connectionManager) {
        this.mConnectionManager = connectionManager;
    }

    @Override
    public UserStory fetchUserStory(int projectId, int storyId) {
        String find_repo_url = "https://www.pivotaltracker.com/services/v5/projects/"+projectId+"/stories/"+storyId;
        String response = this.mConnectionManager.select(find_repo_url);

        UserStory us = null;
        try {
            JSONObject jResult = new JSONObject(response);
            us = UserStoryMapper.getInstance().map(jResult);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return us;
    }
}
