package com.example.familymapclient;

import android.content.Context;
import android.graphics.fonts.Font;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.Person;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private List<Family> itemIds = new ArrayList<>();
    private List<Event> Events = new ArrayList<>();
    private String type;
    private Context mContext;

    public RecyclerViewAdapter(List<Family> itemIds, Context mContext) {
        Iconify.with(new FontAwesomeModule());
        this.itemIds = itemIds;
        this.mContext = mContext;
        this.type="person";
    }

    public RecyclerViewAdapter(List<Event> itemIds, Context mContext,String type) {
        Iconify.with(new FontAwesomeModule());
        this.Events = itemIds;
        this.mContext = mContext;
        this.type="event";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Responsible for inflating view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(type.equals("person")){
            final Person person = itemIds.get(position).getFam();

            if(person.getGender().equals("m")){
               holder.mIcon.setImageDrawable( new IconDrawable(mContext,FontAwesomeIcons.fa_male).colorRes(R.color.male_color).sizeDp(40));
            }
            else{
                holder.mIcon.setImageDrawable(new IconDrawable(mContext, FontAwesomeIcons.fa_female).colorRes(R.color.female_color).sizeDp(40));
            }
            holder.mUpper.setText(person.getFirst_name() + " " +person.getLast_name());
            holder.mLower.setText(itemIds.get(position).getRelation());
            holder.mIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,person.getFirst_name(),Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Event event = Events.get(position);
            String top = event.getEvent_type()+": " + event.getCity() + ", " + event.getCountry() + "(" + event.getYear() + ")";
            Person person = ClientInfo.getInstance().getPersonFromID(event.getPerson_id());
            String bottom = person.getFirst_name()+" "+person.getLast_name();

            holder.mUpper.setText(top);
            holder.mLower.setText(bottom);
            holder.mIcon.setImageDrawable(new IconDrawable(mContext, FontAwesomeIcons.fa_map_marker).colorRes(R.color.android_green).sizeDp(40));


            //TODO put icons. this one can be any color marker.
        }
    }

    @Override
    public int getItemCount() {
        return itemIds.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mUpper,mLower;
        ImageView mIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUpper = itemView.findViewById(R.id.layoutUpper);
            mLower=itemView.findViewById(R.id.layoutLower);
            mIcon = itemView.findViewById(R.id.imageView);
        }
    }
}
