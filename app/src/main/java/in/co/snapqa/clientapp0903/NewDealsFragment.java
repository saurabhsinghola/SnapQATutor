package in.co.snapqa.clientapp0903;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.co.snapqa.clientapp0903.adapters.NewDealsRecyclerViewAdapter;
import in.co.snapqa.clientapp0903.interfaces.API;
import in.co.snapqa.clientapp0903.models.AuthRequest;
import in.co.snapqa.clientapp0903.models.NewDealResponses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewDealsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    RecyclerView newDealsRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    NewDealResponses newDealResponses;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Key = "key";
    NewDealsRecyclerViewAdapter newDealsRecyclerViewAdapter;



    public NewDealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_deals, container, false);

        newDealsRecyclerView = (RecyclerView) view.findViewById(R.id.new_deals_rv);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final API service = retrofit.create(API.class);

        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Key, "notPresent");

        AuthRequest authRequest = new AuthRequest();
        authRequest.setToken(token);

        Call<NewDealResponses> call = service.newDeals(authRequest);

        call.enqueue(new Callback<NewDealResponses>() {
            @Override
            public void onResponse(Call<NewDealResponses> call, Response<NewDealResponses> response) {
                newDealResponses = response.body();
                //Log.d("New deal resp:  ", "" + newDealResponses.getResponses().get(2).getAdminName());

                newDealsRecyclerViewAdapter = new NewDealsRecyclerViewAdapter(newDealResponses);
                layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                newDealsRecyclerView.setLayoutManager(layoutManager);
                newDealsRecyclerView.setAdapter(newDealsRecyclerViewAdapter);


            }

            @Override
            public void onFailure(Call<NewDealResponses> call, Throwable t) {

            }
        });

        return view;
    }

}
