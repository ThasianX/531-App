package com.example.a531app.utilities;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener{
        void changeActionBarTitle(String title);
        void setBackButton(boolean back);
    }

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a = null;
        if(context instanceof Activity){
            a=(Activity) context;
        }
        try{

            mListener = (OnFragmentInteractionListener) a;

        } catch(ClassCastException e){
            throw new ClassCastException(a.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = providedView(inflater, container, savedInstanceState);
        if(mListener!=null){
            mListener.changeActionBarTitle(actionTitle());
            mListener.setBackButton(setBackButton());
        }
        return view;
    }

    public abstract View providedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract String actionTitle();

    public abstract boolean setBackButton();

}
