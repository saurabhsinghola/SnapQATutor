package in.co.snapqa.clientapp0903.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.adapters.NewDealsRecyclerViewAdapter;
import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.SessionDealController;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.AuthRequest;
import in.co.snapqa.clientapp0903.models.NewDealResponses;
import in.co.snapqa.clientapp0903.utils.AppSharedPreferenceHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewDealsFragment extends Fragment implements ApiControllerListener {
    RecyclerView newDealsRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    NewDealsRecyclerViewAdapter newDealsRecyclerViewAdapter;

    TextView tv;

    ProgressDialog progressDialog;


    public NewDealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_deals, container, false);

        newDealsRecyclerView = (RecyclerView) view.findViewById(R.id.new_deals_rv);
        tv = (TextView) view.findViewById(R.id.new_deals_fragment_rv_tv);
        tv.setVisibility(View.GONE);
        String token =
                AppSharedPreferenceHelper.getString(AppSharedPreferenceHelper.KEY, "notPresent");
        AuthRequest authRequest = new AuthRequest();
        authRequest.setToken(token);

        new SessionDealController((BaseActivity) getActivity(), this).callNewDeal(authRequest);

        return view;
    }

    @Override
    public void onSuccessResult(Object successResponse) {
        NewDealResponses response = (NewDealResponses) successResponse;
        switch (response.getMessage()) {
            case "Successful":
                progressDialog.dismiss();
                //Log.d("New deal resp:  ", "" + newDealResponses.getResponses().get(2).getAdminName());

                newDealsRecyclerViewAdapter = new NewDealsRecyclerViewAdapter(response);
                layoutManager = new LinearLayoutManager(getActivity());
                newDealsRecyclerView.setLayoutManager(layoutManager);
                newDealsRecyclerView.setAdapter(newDealsRecyclerViewAdapter);
                break;
            case "phoneNotVerified":
                Intent verifyOTP = new Intent(getActivity(), VerifyOTPActivity.class);
                startActivity(verifyOTP);
                break;
            case "subjectsNotAdded":
                Intent subjectIntent = new Intent(getActivity(), SelectSubjectActivity.class);
                startActivity(subjectIntent);
                break;
        }

    }

    @Override
    public void onFailureResult() {
        tv.setVisibility(View.VISIBLE);
    }
}
