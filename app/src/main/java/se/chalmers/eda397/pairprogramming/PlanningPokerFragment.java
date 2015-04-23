package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.app.AlertDialog;
import android.widget.TextView;

public class PlanningPokerFragment extends Fragment implements View.OnClickListener {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlanningPokerFragment newInstance(int sectionNumber) {
        PlanningPokerFragment fragment = new PlanningPokerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlanningPokerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planning_poker, container, false);

        ViewGroup group = (ViewGroup)rootView.findViewById(R.id.planning_poker_layout);
        View v;
        for(int i = 0; i < group.getChildCount(); i++) {
            v = group.getChildAt(i);
            if(v instanceof Button) v.setOnClickListener(this);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(((Button)v).getText())
                .setTitle("Your chosen number");

        AlertDialog dialog = builder.create();

        dialog.show();

        TextView textView = (TextView)dialog.findViewById(android.R.id.message);
        textView.setTextSize(60);
    }
}
