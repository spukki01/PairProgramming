package se.chalmers.eda397.pairprogramming.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import se.chalmers.eda397.pairprogramming.R;
import se.chalmers.eda397.pairprogramming.model.Branch;

public class BranchListAdapter extends ArrayAdapter<Branch>  {

    private final Context mContext;
    private List<Branch> mValues;

    public BranchListAdapter(Context context, List<Branch> values) {
        super(context, R.layout.branch_row, values);
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
            rowView = inflater.inflate(R.layout.branch_row, parent, false);

            ViewHolder holder = new ViewHolder();

            TextView branchNameLabel = (TextView) rowView.findViewById(R.id.branch_name_text);
            holder.branchName = branchNameLabel;

            TextView latestCommitLabel = (TextView) rowView.findViewById(R.id.latest_commit_text);
            holder.latestCommitDate = latestCommitLabel;

            rowView.setTag(holder);
        }
        else {
            rowView = convertView;
        }


        ViewHolder tag = (ViewHolder) rowView.getTag();
        tag.branchName.setText(this.mValues.get(position).getName());

        //TODO: fix hard coded commit date to show date of latest commit
        tag.latestCommitDate.setText("2015-04-02 [Hard coded]");

        return rowView;
    }

    private static class ViewHolder {
        public TextView branchName;
        public TextView latestCommitDate;
    }

}