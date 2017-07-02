package in.co.snapqa.clientapp0903.ui;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.adapters.ScheduledDeadlineSessionRecyclerViewAdapter;
import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.SessionDealController;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.AuthRequest;
import in.co.snapqa.clientapp0903.models.NewDealResponses;
import in.co.snapqa.clientapp0903.utils.AppSharedPreferenceHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptedLiveSessionFragment extends Fragment implements ApiControllerListener {


    RecyclerView newDealsRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    //    NewDealResponses newDealResponses;
    ScheduledDeadlineSessionRecyclerViewAdapter scheduledDeadlineSessionRecyclerViewAdapter;
    ProgressDialog progressDialog;
    TextView tv;

    public AcceptedLiveSessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accepted_live_session, container, false);
        newDealsRecyclerView =
                (RecyclerView) view.findViewById(R.id.accepted_live_session_recycler_view);

        tv = (TextView) view.findViewById(R.id.accepted_live_session_fragment_rv_tv);
        tv.setVisibility(View.GONE);
        String token =
                AppSharedPreferenceHelper.getString(AppSharedPreferenceHelper.KEY, "notPresent");

        AuthRequest authRequest = new AuthRequest();
        authRequest.setToken(token);

        new SessionDealController((BaseActivity) getActivity(), this).callLiveDeals(authRequest);


        return view;
    }

    @Override
    public void onSuccessResult(Object response) {
        NewDealResponses newDealResponses = (NewDealResponses) response;
        if (!newDealResponses.getResponses().isEmpty()) {
            scheduledDeadlineSessionRecyclerViewAdapter =
                    new ScheduledDeadlineSessionRecyclerViewAdapter(newDealResponses);
            layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            newDealsRecyclerView.setLayoutManager(layoutManager);
            newDealsRecyclerView.setAdapter(scheduledDeadlineSessionRecyclerViewAdapter);

        }
    }

    @Override
    public void onFailureResult() {
        tv.setVisibility(View.VISIBLE);
    }
}
