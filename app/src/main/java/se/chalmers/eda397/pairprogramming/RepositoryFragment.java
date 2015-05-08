package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.chalmers.eda397.pairprogramming.model.Repository;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RepositoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RepositoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepositoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REPOSITORY = "Repository";

    // TODO: Rename and change types of parameters
    private Repository mRepository;
    private View mRootView;


    private OnFragmentInteractionListener mListener;


    public static RepositoryFragment newInstance(Repository repo) {
        RepositoryFragment fragment = new RepositoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REPOSITORY, repo);
        fragment.setArguments(args);
        return fragment;
    }

    public RepositoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRepository = (Repository)getArguments().getSerializable(ARG_REPOSITORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_repository, container, false);

        //Set text views
        ((TextView) mRootView.findViewById(R.id.repo_name_text)).setText(mRepository.getName());
        ((TextView) mRootView.findViewById(R.id.repo_owner_text)).setText("Owner: " + mRepository.getOwnerName());
        ((TextView) mRootView.findViewById(R.id.repo_desc)).setText(mRepository.getDescription());

        //Add branch list
        Fragment branchFragment = BranchFragment.newInstance(mRepository.getName(), mRepository.getOwnerName());
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.branch_fragment_container, branchFragment).commit();
        return mRootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.*/
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
