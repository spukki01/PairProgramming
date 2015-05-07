package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        System.out.println("New instance " + repo.getName());
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
        System.out.println("onCreateView");
        mRootView = inflater.inflate(R.layout.fragment_repository, container, false);

        /*TextView tv = (TextView)mRootView.findViewById(R.id.repo_name_text);
        tv.setText(mRepository.getName());*/

        ((TextView) mRootView.findViewById(R.id.repo_name_text)).setText(mRepository.getName());
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
