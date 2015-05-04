package se.chalmers.eda397.pairprogramming;

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
        RepoListItem item = (RepoListItem)getItem(position);
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
                + " id: " + item.getRepository().getId());

        TextView textContent = (TextView)viewToUse.findViewById(R.id.repoNameText);
        final String text = textContent.getText().toString();

        //Handle buttons and add onClickListeners
        Button addRepoButton = (Button)viewToUse.findViewById(R.id.addRepoButton);

        addRepoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast addRepoClickToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                addRepoClickToast.show();
            }
        });


        return viewToUse;
    }
}
