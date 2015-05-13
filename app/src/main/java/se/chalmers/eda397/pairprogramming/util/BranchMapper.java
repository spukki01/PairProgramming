package se.chalmers.eda397.pairprogramming.util;

import org.json.JSONException;
import org.json.JSONObject;

import se.chalmers.eda397.pairprogramming.model.Branch;

public class BranchMapper implements IMapper<Branch> {

    private static BranchMapper instance;

    public static BranchMapper getInstance() {
        if (instance == null){
            instance = new BranchMapper();
        }
        return instance;
    }


    @Override
    public Branch map(JSONObject jsonObject) throws JSONException {
        Branch branch = new Branch();

        branch.setName(jsonObject.getString("name"));


        return branch;
    }

}
