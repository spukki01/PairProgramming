package se.chalmers.eda397.pairprogramming.core;

public interface IConnectionManager {

    String select(String query);
    String insert(String query);
    String update(String query);
    String delete(String query);

}
