package se.chalmers.eda397.pairprogramming.model;

public class UserStory {

    private int mEstimate;
    private String mDescription;
    private String mType;
    private String mCreatedDate;
    private String mName;

    public void setName(String name) {
        this.mName = name;
    }

    public void setCreatedDate(String createdDate) {
        this.mCreatedDate = createdDate;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public void setEstimate(int estimate) {
        this.mEstimate = estimate;
    }

    public String getName() {
        return this.mName;
    }

    public String getType() {
        return this.mType;
    }

    public int getEstimate() {
        return this.mEstimate;
    }

    public String getCreatedDate() {
        return this.mCreatedDate;
    }

    public String getDescription() {
        return this.mDescription;
    }
}
