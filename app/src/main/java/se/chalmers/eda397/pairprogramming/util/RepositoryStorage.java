package se.chalmers.eda397.pairprogramming.util;

import android.content.Context;

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
        JSONObject repo = new JSONObject();
        repo.put("name", repository.getName());
        repo.put("owner", repository.getOwner());
        repo.put("id", repository.getId());
        repo.put("isPrivate", repository.isPrivate());
        repo.put("description", repository.getDescription());
        mArray.put(repo);
        mJsonObject.put("repositories", mArray);
        try {
            File file = new File(FILENAME);
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_APPEND);
            fos.write(mJsonObject.toString().getBytes());
            fos.close();
        }
        catch (FileNotFoundException e) {
            //TODO Do something useful.
        }
        catch (IOException ioE) {
            //TODO Do something useful.
        }
    }

    @Override
    public List<Repository> fetchAll(Context context) {
        List<Repository> list = new ArrayList<Repository>();
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            int content;
            String input = "";
            while ((content = fis.read()) != -1) {
                input += (char)content;
            }
            JSONObject jsonObject = new JSONObject(input);
            //TODO REMOVE
            System.out.println(input);
            JSONArray array = jsonObject.getJSONArray("repositories");
            for (int i = 0; i < array.length(); i++) {
                Repository repo = new Repository();
                JSONObject temp = array.getJSONObject(i);
                repo.setId(temp.getInt("id"));
                repo.setDescription(temp.getString("description"));
                repo.setOwner(temp.getString("owner"));
                repo.setPrivate(temp.getBoolean("isPrivate"));
                repo.setName(temp.getString("name"));
                list.add(repo);
            }
        } catch (Exception e) {
            //TODO Do something useful.
        }
        return list;
    }
}
