package se.chalmers.eda397.pairprogramming.core;

import se.chalmers.eda397.pairprogramming.model.UserStory;

public interface IPivotalTrackerClient {

    UserStory fetchUserStory(long projectId, long storyId);

}
