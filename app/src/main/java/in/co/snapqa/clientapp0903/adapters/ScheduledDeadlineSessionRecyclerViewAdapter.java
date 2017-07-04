package in.co.snapqa.clientapp0903.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.co.snapqa.clientapp0903.AcceptedLiveSessionFragment;
import in.co.snapqa.clientapp0903.MainActivity;
import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.interfaces.API;
import in.co.snapqa.clientapp0903.models.AcceptDealRequest;
import in.co.snapqa.clientapp0903.models.AcceptedDealResponse;
import in.co.snapqa.clientapp0903.models.NewDealResponses;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by OLA on 10/03/17.
 */

public class ScheduledDeadlineSessionRecyclerViewAdapter extends RecyclerView.Adapter<ScheduledDeadlineSessionRecyclerViewAdapter.MyViewHolder> {

    NewDealResponses newDealResponsess;
    String ftime, gtime;
    Date date;
    Calendar cal;
    AlertDialog.Builder alertDialog;
    String testDate;

    Context context;

    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Key = "key";

    public ScheduledDeadlineSessionRecyclerViewAdapter(NewDealResponses newDealResponses) {
        newDealResponsess = newDealResponses;
    }

    @Override
    public ScheduledDeadlineSessionRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_deal_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduledDeadlineSessionRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.subject.setText(newDealResponsess.getResponses().get(position).getSubjectName());

        String ty = newDealResponsess.getResponses().get(position).getDealType();
        Log.d("type ki value", "  " + ty);

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            date = formatter.parse(String.valueOf(newDealResponsess.getResponses().get(position).getTimeTo()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        cal.add(Calendar.MINUTE, 30);
        cal.add(Calendar.HOUR, 5);

        if(Calendar.HOUR<10){

            if(Calendar.MINUTE<10){
                ftime = "0" +cal.get(Calendar.HOUR) + ":" + "0"+ cal.get(Calendar.MINUTE);
            }else {
                ftime = "0" +cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);
            }
        }else {
            if(Calendar.MINUTE<10){
                ftime = cal.get(Calendar.HOUR) + ":" + "0"+ cal.get(Calendar.MINUTE);
            }else {
                ftime = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);
            }
        }

        if(Calendar.DATE<10){
            if(Calendar.MONTH < 10){
                testDate = "0" + cal.get(Calendar.DATE) + "/" + "0" + month + "/" + cal.get(Calendar.YEAR);
            }else {
                testDate = "0" + cal.get(Calendar.DATE) + "/" + month + "/" + cal.get(Calendar.YEAR);
            }
        }else {
            if(Calendar.MONTH < 10){
                testDate = cal.get(Calendar.DATE) + "/" + "0" + month + "/" + cal.get(Calendar.YEAR);
            }else {
                testDate = cal.get(Calendar.DATE) + "/" + month + "/" + cal.get(Calendar.YEAR);
            }
        }

        if(newDealResponsess.getResponses().get(position).getDealType().equals("Live Session")){
            DateFormat formatte = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            try {
                date = formatte.parse(String.valueOf(newDealResponsess.getResponses().get(position).getTimeFrom()));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            cal = Calendar.getInstance();
            cal.setTime(date);
            int months = cal.get(Calendar.MONTH) + 1;
            cal.add(Calendar.MINUTE, 30);
            cal.add(Calendar.HOUR, 5);

            if(Calendar.HOUR<10){

                if(Calendar.MINUTE<10){
                    gtime = "0" +cal.get(Calendar.HOUR) + ":" + "0"+ cal.get(Calendar.MINUTE);
                }else {
                    gtime = "0" +cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);
                }
            }else {
                if(Calendar.MINUTE<10){
                    gtime = cal.get(Calendar.HOUR) + ":" + "0"+ cal.get(Calendar.MINUTE);
                }else {
                    gtime = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);
                }
            }

            if(Calendar.DATE<10){
                if(Calendar.MONTH < 10){
                    testDate = "0" + cal.get(Calendar.DATE) + "/" + "0" + months + "/" + cal.get(Calendar.YEAR);
                }else {
                    testDate = "0" + cal.get(Calendar.DATE) + "/" + months + "/" + cal.get(Calendar.YEAR);
                }
            }else {
                if(Calendar.MONTH < 10){
                    testDate = cal.get(Calendar.DATE) + "/" + "0" + months + "/" + cal.get(Calendar.YEAR);
                }else {
                    testDate = cal.get(Calendar.DATE) + "/" + months + "/" + cal.get(Calendar.YEAR);
                }
            }
        }


        String testDate = cal.get(Calendar.DATE) + "/" + month + "/" + cal.get(Calendar.YEAR);
        holder.date.setText(testDate);

        if(ty.equals("Deadline Session") || ty.equals("HomeWork")){
            holder.leftIV.setBackgroundResource(R.drawable.hourglass);
            holder.type.setText("Deadline Session");
            holder.leftTV.setText(testDate+ ", " + ftime);
            holder.rightIV.setBackgroundResource(R.drawable.rupee);
            holder.rightTV.setText(Integer.toString(newDealResponsess.getResponses().get(position).getAmount()));
            holder.type.setBackgroundResource(R.color.colorTurquoise);
        }else{
            holder.leftIV.setBackgroundResource(R.drawable.hourglass);
            holder.type.setText("Live Session");
            holder.leftTV.setText(gtime);
            holder.rightIV.setBackgroundResource(R.drawable.hourglass);
            holder.rightTV.setText(ftime);
        }
        holder.linearLayout.setVisibility(LinearLayout.GONE);

        final LinearLayout layout = holder.linearLayout;


        if(newDealResponsess.getResponses().get(position).getMaterialComment() != null) {
            holder.material.setText(newDealResponsess.getResponses().get(position).getMaterialComment());
        }
        if(newDealResponsess.getResponses().get(position).getBookName() != null){
            holder.bookName.setText(newDealResponsess.getResponses().get(position).getBookName());
        }


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

        sharedpreferences = holder.itemView.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String token = sharedpreferences.getString(Key, "notPresent");

        final AcceptDealRequest acceptDealRequest = new AcceptDealRequest();
        acceptDealRequest.set_id(newDealResponsess.getResponses().get(position).get_id());
        acceptDealRequest.setToken(token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(holder.itemView.getContext().getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final API service = retrofit.create(API.class);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<AcceptedDealResponse> call = service.acceptedDeal(acceptDealRequest);
                call.enqueue(new Callback<AcceptedDealResponse>() {
                    @Override
                    public void onResponse(Call<AcceptedDealResponse> call, Response<AcceptedDealResponse> response) {
                        AcceptedDealResponse response1 = response.body();
                        if(response.body().getMessage().equals("Successful !! Deal Booked !!")){
                            Log.d(",,", "" + " deal boooked");
                        }else{
                            AcceptedLiveSessionFragment newDealFragment = new AcceptedLiveSessionFragment();
                            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_frame_main_activity, newDealFragment).commit();
                        }
                    }
                    @Override
                    public void onFailure(Call<AcceptedDealResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if(newDealResponsess != null) {
            return newDealResponsess.getResponses().size();
        }else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView subject, type, date, leftTV, rightTV, bookName, material, comments;
        LinearLayout linearLayout, acceptReject;
        ImageView leftIV, rightIV;
        Button accept, reject;

        Typeface font1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/opensanslight.ttf");
        Typeface font2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/opensansregular.ttf");
        Typeface font3 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/opensanssemibold.ttf");

        public MyViewHolder(final View itemView) {
            super(itemView);

            Calligrapher calligrapher = new Calligrapher(itemView.getContext());
            calligrapher.setFont((Activity) itemView.getContext(), "fonts/opensanslight.ttf", true);

            subject = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_subject);
            type = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_type);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.new_deal_fragment_rv_extended_layout);
            date = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_date);
            leftTV = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_left_tv);
            rightTV=(TextView) itemView.findViewById(R.id.new_deal_fragment_rv_right_tv);
            bookName = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_book_name);
            material = (TextView) itemView.findViewById(R.id.new_deal_fragment_rv_material);

            leftIV = (ImageView) itemView.findViewById(R.id.new_deal_fragment_rv_left_iv);
            rightIV = (ImageView) itemView.findViewById(R.id.new_deal_fragment_rv_right_iv);

            accept = (Button) itemView.findViewById(R.id.new_deal_fragment_rv_accept);
            reject = (Button) itemView.findViewById(R.id.new_deal_fragment_rv_reject);

            acceptReject = (LinearLayout) itemView.findViewById(R.id.new_deal_fragment_rv_accept_reject_layout);
            acceptReject.setVisibility(LinearLayout.GONE);

            accept.setTypeface(font2);
            reject.setTypeface(font2, Typeface.BOLD);
            subject.setTypeface(font3);
            type.setTypeface(font1);
            date.setTypeface(font1);
            leftTV.setTypeface(font1, Typeface.BOLD);
            rightTV.setTypeface(font1, Typeface.BOLD);
            bookName.setTypeface(font1);
            material.setTypeface(font1);


        }
    }
}
