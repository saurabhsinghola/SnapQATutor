package in.co.snapqa.clientapp0903.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.models.NewDealResponses;

/**
 * Created by OLA on 28/03/17.
 */

public class NewDealsRecyclerViewAdapter extends RecyclerView.Adapter<NewDealsRecyclerViewAdapter.MyViewHolder> {


    NewDealResponses newDealResponsess;
    String ftime;
    Date date;
    Calendar cal;

    public NewDealsRecyclerViewAdapter(NewDealResponses newDealResponses){
        newDealResponsess = newDealResponses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_deal_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.subject.setText(newDealResponsess.getResponses().get(position).getSubjectName());
        holder.type.setText(newDealResponsess.getResponses().get(position).getDealType());

        String ty = newDealResponsess.getResponses().get(position).getDealType();
        Log.d("type ki value", "  " + ty);

        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        try {
            date = (Date)formatter.parse(String.valueOf(newDealResponsess.getResponses().get(position).getDealDate()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal = Calendar.getInstance();
        cal.setTime(date);
        ftime = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);
        String testDate = cal.get(Calendar.DATE) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
        holder.date.setText(testDate);

        if(ty.equals("Home Work") || ty.equals("HomeWork")){
            holder.leftIV.setBackgroundResource(R.drawable.ic_timer_off_black_24dp);
            holder.leftTV.setText(ftime);
            holder.rightIV.setBackgroundResource(R.drawable.ic_attach_money_black_24dp);
            holder.rightTV.setText(Integer.toString(newDealResponsess.getResponses().get(position).getPointsOffered()));
            holder.type.setBackgroundResource(R.color.colorTurquoise);
        }else{
            holder.leftIV.setBackgroundResource(R.drawable.ic_timer_black_24dp);
            holder.leftTV.setText(ftime);
            holder.rightIV.setBackgroundResource(R.drawable.ic_timer_off_black_24dp);
            holder.rightTV.setText(Integer.toString(newDealResponsess.getResponses().get(position).getPointsOffered()));
        }

        holder.linearLayout.setVisibility(LinearLayout.GONE);

        final LinearLayout layout = holder.linearLayout;


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layout.getVisibility() == LinearLayout.VISIBLE) {
                    layout.setVisibility(LinearLayout.GONE);
                }else {
                    layout.setVisibility(LinearLayout.VISIBLE);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return newDealResponsess.getResponses().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView subject, type, date, leftTV, rightTV, bookName, material, comments;
        LinearLayout linearLayout;
        ImageView leftIV, rightIV;

        public MyViewHolder(final View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_subject);
            type = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_type);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.new_deal_fragment_rv_extended_layout);
            date = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_date);
            leftTV = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_left_tv);
            rightTV=(TextView) itemView.findViewById(R.id.new_deal_fragment_rv_right_tv);
            bookName = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_book_name);
            material = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_material);
            comments = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_comments);

            leftIV = (ImageView) itemView.findViewById(R.id.new_deal_fragment_rv_left_iv);
            rightIV = (ImageView) itemView.findViewById(R.id.new_deal_fragment_rv_right_iv);


        }
    }
}
