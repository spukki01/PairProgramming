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
import se.chalmers.eda397.pairprogramming.util.DateHelper;

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
            holder.branchName = (TextView) rowView.findViewById(R.id.branch_name_text);
            holder.latestCommitDate = (TextView) rowView.findViewById(R.id.latest_commit_text);

            rowView.setTag(holder);
        }
        else {
            rowView = convertView;
        }

        Branch branch = this.mValues.get(position);

        ViewHolder tag = (ViewHolder) rowView.getTag();
        tag.branchName.setText(branch.getName());

        if (branch.getLatestCommitDate() != null)
            tag.latestCommitDate.setText(DateHelper.getInstance().toSimpleDateString(branch.getLatestCommitDate()));

        return rowView;
    }

    private static class ViewHolder {
        public TextView branchName;
        public TextView latestCommitDate;
    }

}