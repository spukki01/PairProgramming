package se.chalmers.eda397.pairprogramming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import se.chalmers.eda397.pairprogramming.MainActivity;
import se.chalmers.eda397.pairprogramming.R;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rowView;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.commit_row, parent, false);

            ViewHolder holder = new ViewHolder();

            holder.commitMessage = (TextView) rowView.findViewById(R.id.commit_message_text);
            holder.commitAuthor = (TextView) rowView.findViewById(R.id.commit_author);
            holder.userStoryButton = (Button)rowView.findViewById(R.id.commit_open_pt_btn);

            rowView.setTag(holder);
        }
        else {
            rowView = convertView;
        }

        String commitMessage = this.mValues.get(position).getMessage();

        ViewHolder tag = (ViewHolder) rowView.getTag();
        tag.commitMessage.setText(commitMessage);
        tag.commitAuthor.setText(this.mValues.get(position).getCommitter());

        if (commitMessage.contains("#PT")) {

            //TODO: handle parse error
            String[] parts = commitMessage.split("#PT")[1].split(":");
            final int projectId = Integer.parseInt(parts[0]);
            final int userStoryId = Integer.parseInt(parts[1]);

            tag.userStoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)mContext).openUserStoryFragment(projectId, userStoryId);
                }
            });

            tag.userStoryButton.setVisibility(View.VISIBLE);
        }

        return rowView;
    }

    private static class ViewHolder {
        public TextView commitMessage;
        public TextView commitAuthor;
        public Button userStoryButton;
    }
}
