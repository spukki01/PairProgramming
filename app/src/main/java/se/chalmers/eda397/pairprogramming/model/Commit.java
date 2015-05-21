package se.chalmers.eda397.pairprogramming.model;

import java.util.Date;

import se.chalmers.eda397.pairprogramming.util.DateHelper;

public class Commit {
    private String committer;
    private String message;
    private Date date;
    private String sha;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(String dateString) {
        this.date = DateHelper.getInstance().parseDate(dateString);
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public String getCommitter() {
        return this.committer;
    }

}
