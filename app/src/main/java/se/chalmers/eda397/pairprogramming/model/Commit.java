package se.chalmers.eda397.pairprogramming.model;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            this. date = formatter.parse(dateString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public String getCommitter() {
        return this.committer;
    }

}
