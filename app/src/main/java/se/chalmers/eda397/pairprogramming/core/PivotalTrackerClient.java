package se.chalmers.eda397.pairprogramming.core;

public class PivotalTrackerClient implements IPivotalTrackerClient {
    private IConnectionManager mConnectionManager;

    public PivotalTrackerClient(IConnectionManager connectionManager) {
        this.mConnectionManager = connectionManager;
    }
}
