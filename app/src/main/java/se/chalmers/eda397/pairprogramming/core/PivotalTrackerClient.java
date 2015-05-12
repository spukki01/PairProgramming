package se.chalmers.eda397.pairprogramming.core;

import org.json.JSONException;
import org.json.JSONObject;

import se.chalmers.eda397.pairprogramming.model.UserStory;
import se.chalmers.eda397.pairprogramming.util.IMapper;
import se.chalmers.eda397.pairprogramming.util.UserStoryMapper;

public class PivotalTrackerClient implements IPivotalTrackerClient {

    private IConnectionManager mConnectionManager;
    private IMapper<UserStory> mMapper;

    public PivotalTrackerClient(IConnectionManager connectionManager) {
        this.mConnectionManager = connectionManager;
        this.mMapper = new UserStoryMapper();
    }

    @Override
    public UserStory fetchUserStory(int projectId, int storyId) {
        String find_repo_url = "https://www.pivotaltracker.com/services/v5/projects/"+projectId+"/stories/"+storyId;
        String response = this.mConnectionManager.executeQuery(find_repo_url);

        UserStory us = null;
        try {
            JSONObject jResult = new JSONObject(response);
            us = mMapper.map(jResult);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return us;
    }
}
