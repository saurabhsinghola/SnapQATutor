package in.co.snapqa.clientapp0903.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.models.NewDealFragmentResponse;
import in.co.snapqa.clientapp0903.viewHolders.NewDealRecyclerViewHolder;

/**
 * Created by OLA on 10/03/17.
 */

public class NewDealRecyclerViewAdapter extends RecyclerView.Adapter<NewDealRecyclerViewHolder>{

    List<NewDealFragmentResponse> newDealList;

    public  NewDealRecyclerViewAdapter(List<NewDealFragmentResponse> newDealList){
        this.newDealList = newDealList;
    }


    @Override
    public NewDealRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_deal_rv, null);

        NewDealRecyclerViewHolder newDealRecyclerViewHolder = new NewDealRecyclerViewHolder(view);

        return  newDealRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(NewDealRecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
