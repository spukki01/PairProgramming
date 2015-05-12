package se.chalmers.eda397.pairprogramming.model;

import java.util.Date;

public class Commit {
    private String committer;
    private String message;
    private Date date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public String getCommitter() {
        return this.committer;
    }

}
