package com.example.familymapclient;

import android.content.Context;
import android.content.Intent;
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

public class RecyclerViewAdapterEvent extends RecyclerView.Adapter<RecyclerViewAdapterEvent.ViewHolderEvent>{//RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private List<Event> Events = new ArrayList<>();
    private Context mContext;


    public RecyclerViewAdapterEvent(List<Event> itemIds, Context mContext) {
        Iconify.with(new FontAwesomeModule());
        this.Events = itemIds;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Responsible for inflating view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        ViewHolderEvent viewHolder = new ViewHolderEvent(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterEvent.ViewHolderEvent holder, int position) {

                final Event event = Events.get(position);
                if(!ClientInfo.getInstance().filteredEvents.get(event)) {
                    String top = event.getEvent_type() + ": " + event.getCity() + ", " + event.getCountry() + "(" + event.getYear() + ")";
                    Person person = ClientInfo.getInstance().getPersonFromID(event.getPerson_id());
                    String bottom = person.getFirst_name() + " " + person.getLast_name();

                    holder.mUpper.setText(top);
                    holder.mLower.setText(bottom);
                    holder.mIcon.setImageDrawable(new IconDrawable(mContext, FontAwesomeIcons.fa_map_marker).colorRes(R.color.marker_color).sizeDp(40));

                    holder.mIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent data;
                            data = new Intent(mContext, MapFragment.class);
                            //tell who is selected.
                            ClientInfo.getInstance().setPassedEvent(event);
                            mContext.startActivity(data);
                        }
                    });
                }

        }


    @Override
    public int getItemCount() {
        return Events.size();
    }


    public class ViewHolderEvent extends RecyclerView.ViewHolder{

        TextView mUpper,mLower;
        ImageView mIcon;


        public ViewHolderEvent(@NonNull View itemView) {
            super(itemView);
            mUpper = itemView.findViewById(R.id.layoutUpper);
            mLower=itemView.findViewById(R.id.layoutLower);
            mIcon = itemView.findViewById(R.id.imageView);
        }
    }
}
