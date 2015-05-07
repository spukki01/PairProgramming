package se.chalmers.eda397.pairprogramming.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import se.chalmers.eda397.pairprogramming.R;
import se.chalmers.eda397.pairprogramming.model.RepoListItem;
import se.chalmers.eda397.pairprogramming.util.RepositoryStorage;

/**
 * Created by marcusisaksson on 15-04-28.
 */
public class RepoListAdapter extends ArrayAdapter {

    private Context context;
    private boolean useList = true;

    public RepoListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    /**
     * Holder for the list items.
     */
    private class ViewHolder{
        TextView titleText;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final RepoListItem item = (RepoListItem)getItem(position);
        View viewToUse = null;

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if(useList){
                viewToUse = mInflater.inflate(R.layout.repo_list_item, null);
            } /*else {
                viewToUse = mInflater.inflate(R.layout.example_grid_item, null);
            }*/

            holder = new ViewHolder();
            holder.titleText = (TextView)viewToUse.findViewById(R.id.repoNameText);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.titleText.setText("name: " + item.getRepository().getName()
                + " Owner: " + item.getRepository().getOwnerName());

        TextView textContent = (TextView)viewToUse.findViewById(R.id.repoNameText);
        //final String text = textContent.getText().toString();

        //Handle buttons and add onClickListeners
        Button addRepoButton = (Button)viewToUse.findViewById(R.id.addRepoButton);

        addRepoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO Change the text of this Toast.
                try {
                    RepositoryStorage.getInstance().store(item.getRepository(), context);
                }
                catch (Exception e) {
                    //TODO Do something useful.
                    e.printStackTrace();
                }

                /*List<Repository> list = RepositoryStorage.getInstance().fetchAll(context);
                for (Repository r: list){
                    System.out.println(r.getName());
                }*/
                String toastText = item.getRepository().getName() + " is stored";
                Toast addRepoClickToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
                addRepoClickToast.show();

            }
        });



        return viewToUse;
    }
}
