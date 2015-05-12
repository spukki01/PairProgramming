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
import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.RepositoryStorage;


public class SubscribedRepositoryAdapter extends ArrayAdapter {

        private Context mContext;

        public SubscribedRepositoryAdapter(Context context, List items) {
            super(context, android.R.layout.simple_list_item_1, items);
            this.mContext = context;
        }

        private class ViewHolder{
            TextView titleText;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final View viewToUse;
            ViewHolder holder;

            // This block exists to inflate the settings list item conditionally based on whether
            // we want to support a grid or list view.
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

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

            final Repository item = (Repository)getItem(position);
            holder.titleText.setText("Name: " + item.getName() + " Owner: " + item.getOwnerName());

            Button removeRepoButton = (Button)viewToUse.findViewById(R.id.addRepoButton);
            removeRepoButton.setText(mContext.getString(R.string.repo_item_remove));

            removeRepoButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    RepositoryStorage.getInstance().remove(item, mContext);
                    clear();
                    addAll(RepositoryStorage.getInstance().fetchAll(mContext));
                    notifyDataSetChanged();

                    String toastText = item.getName() + " is removed";
                    Toast addRepoClickToast = Toast.makeText(mContext, toastText, Toast.LENGTH_SHORT);
                    addRepoClickToast.show();
                }
            });

            return viewToUse;
        }


}
