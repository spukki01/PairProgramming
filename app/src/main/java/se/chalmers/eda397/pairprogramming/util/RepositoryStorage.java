package se.chalmers.eda397.pairprogramming.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Repository;

public class RepositoryStorage implements IStorage<Repository> {

    private static RepositoryStorage instance = null;
    private String FILENAME = "repositories";

    private RepositoryStorage() {

    }

    public static RepositoryStorage getInstance() {
        if (instance == null){
            instance = new RepositoryStorage();
        }
        return instance;
    }

    @Override
    public void store(Repository repository, Context context) throws JSONException {
        JSONObject jsonObject;
        JSONArray array;

        try {
            jsonObject = readJsonFile(context.openFileInput(FILENAME));
            array = jsonObject.getJSONArray("repositories");
        } catch (FileNotFoundException e) {
            array = new JSONArray();
        }

        try {
            array.put(createJsonRepo(repository));

            JSONObject newJsonObject = new JSONObject();
            newJsonObject.put("repositories", array);

            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(newJsonObject.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            //TODO Do something useful.
            e.printStackTrace();
        }
    }

    @Override
    public void update(Repository repository, Context context) {
        if (this.remove(repository, context)) {
            try {
                this.store(repository, context);
            } catch (JSONException e) {
                e.printStackTrace();
                //TODO: Do something useful
            }
        }
    }

    @Override
    public boolean remove(Repository repository, Context context){
        try {
            JSONObject jsonObject = readJsonFile(context.openFileInput(FILENAME));
            JSONArray array = jsonObject.getJSONArray("repositories");

            //loop through all repos in array to find the one we want to remove
            for(int i = 0; i < array.length(); i++){
                JSONObject temp = (JSONObject)array.get(i);

                //We have found it if owner and name is the same.
                if(temp.get("name").equals(repository.getName()) && temp.get("owner").equals(repository.getOwnerName())){
                    //After we've removed it from the array, we write the array to the file again.
                    // Overwriting the previous content of the file
                    array.remove(i);
                    try {
                        JSONObject newJsonObject = new JSONObject();
                        newJsonObject.put("repositories", array);

                        FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                        fos.write(newJsonObject.toString().getBytes());
                        fos.close();
                    } catch (Exception e) {
                        //TODO Do something useful.
                        e.printStackTrace();
                        return false;
                    }

                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public List<Repository> fetchAll(Context context) {
        List<Repository> list = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            JSONObject jsonObject = readJsonFile(fis);

            JSONArray array = jsonObject.getJSONArray("repositories");
            for (int i = 0; i < array.length(); i++) {
                Repository repo = new Repository();

                JSONObject temp = array.getJSONObject(i);
                repo.setId(temp.getInt("id"));
                repo.setDescription(temp.getString("description"));
                repo.setOwnerName(temp.getString("owner"));
                repo.setPrivate(temp.getBoolean("isPrivate"));
                repo.setName(temp.getString("name"));

                repo.setIsMergeNotificationOn(temp.getBoolean("isMergeNotificationOn"));
                repo.setIsCommitNotificationOn(temp.getBoolean("isCommitNotificationOn"));

                if(temp.has("branchesURL")){
                    repo.setBranchesUrl(temp.getString("branchesURL"));
                }
                list.add(repo);
            }
        } catch (Exception e) {
            //TODO Do something useful.
            e.printStackTrace();
        }
        return list;
    }

    private JSONObject readJsonFile(FileInputStream fis){

        try {
            int content;
            String input = "";

            while ((content = fis.read()) != -1) {
                input += (char)content;
            }
            return new JSONObject(input);
        } catch (Exception e) {
            //TODO Do something useful.
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject createJsonRepo(Repository r){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", r.getName());
            jsonObject.put("owner", r.getOwnerName());
            jsonObject.put("id", r.getId());
            jsonObject.put("isPrivate", r.isPrivate());
            jsonObject.put("description", r.getDescription());
            jsonObject.put("branchesURL", r.getBranchesUrl());
            jsonObject.put("isMergeNotificationOn", r.isMergeNotificationOn());
            jsonObject.put("isCommitNotificationOn", r.isCommitNotificationOn());

            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
