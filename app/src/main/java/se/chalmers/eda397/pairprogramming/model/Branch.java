package se.chalmers.eda397.pairprogramming.model;

import java.util.Date;

public class Branch {

    private String name;
    private Date latestCommitDate;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getLatestCommitDate() {
        return latestCommitDate;
    }

    public void setLatestCommitDate(Date latestCommitDate) {
        this.latestCommitDate = latestCommitDate;
    }
}
