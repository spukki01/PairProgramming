package se.chalmers.eda397.pairprogramming.util;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Repository;

/**
 * TODO For now, only one Repository can be saved.
 */
public class RepositoryStorage implements IStorage<Repository> {
    private static RepositoryStorage instance = null;
    private String FILENAME = "repositories";
    private JSONObject mJsonObject = new JSONObject();
    private JSONArray mArray = new JSONArray();

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
            jsonObject = new JSONObject();
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
    public List<Repository> fetchAll(Context context) {
        List<Repository> list = new ArrayList<Repository>();
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
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
