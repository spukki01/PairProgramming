package se.chalmers.eda397.pairprogramming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import se.chalmers.eda397.pairprogramming.R;
import se.chalmers.eda397.pairprogramming.model.Branch;
import se.chalmers.eda397.pairprogramming.model.Commit;


public class CommitListAdapter extends ArrayAdapter<Commit> {

    private final Context mContext;
    private List<Commit> mValues;

    public CommitListAdapter(Context context, List<Commit> values) {
        super(context, R.layout.commit_row, values);
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rowView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.commit_row, parent, false);

            ViewHolder holder = new ViewHolder();

            TextView commitMessageLabel = (TextView) rowView.findViewById(R.id.commit_message_text);
            holder.commitMessage = commitMessageLabel;

            TextView commitAuthorLabel = (TextView) rowView.findViewById(R.id.commit_author);
            holder.commitAuthor = commitAuthorLabel;

            rowView.setTag(holder);
        }
        else {
            rowView = convertView;
        }


        ViewHolder tag = (ViewHolder) rowView.getTag();
        tag.commitMessage.setText(this.mValues.get(position).getMessage());
        tag.commitAuthor.setText(this.mValues.get(position).getCommitter());

        return rowView;
    }

    private static class ViewHolder {
        public TextView commitMessage;
        public TextView commitAuthor;
    }
}
