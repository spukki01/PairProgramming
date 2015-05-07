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


public class SubscribedRepositoryAdapter extends ArrayAdapter {

        private Context context;

        public SubscribedRepositoryAdapter(Context context, List items) {
            super(context, android.R.layout.simple_list_item_1, items);
            this.context = context;
        }

        private class ViewHolder{
            TextView titleText;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            final RepoListItem item = (RepoListItem)getItem(position);
            View viewToUse = null;

            // This block exists to inflate the settings list item conditionally based on whether
            // we want to support a grid or list view.
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                viewToUse = mInflater.inflate(R.layout.repo_list_item, null);

                holder = new ViewHolder();
                holder.titleText = (TextView)viewToUse.findViewById(R.id.repoNameText);
                viewToUse.setTag(holder);
            }
            else {
                viewToUse = convertView;
                holder = (ViewHolder) viewToUse.getTag();
            }

            holder.titleText.setText("name: " + item.getRepository().getName()
                    + " Owner: " + item.getRepository().getOwnerName());

            //Handle buttons and add onClickListeners
            Button addRepoButton = (Button)viewToUse.findViewById(R.id.addRepoButton);

            addRepoButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String toastText = item.getRepository().getName() + " is removed";
                    Toast addRepoClickToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
                    addRepoClickToast.show();

                }
            });

            return viewToUse;
        }


}
