package in.co.snapqa.clientapp0903.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.co.snapqa.clientapp0903.R;

/**
 * Created by OLA on 10/03/17.
 */

public class NewDealRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView subject;
    public TextView type;
    public TextView timing;
    public TextView price;

    public NewDealRecyclerViewHolder(View itemView) {
        super(itemView);

        subject = (TextView) itemView.findViewById(R.id.new_deal_rv_subject);
        type = (TextView) itemView.findViewById(R.id.new_deal_rv_type);
        timing = (TextView) itemView.findViewById(R.id.new_deal_rv_timing);
        price = (TextView) itemView.findViewById(R.id.new_deal_rv_price);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
