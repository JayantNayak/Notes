package com.example.jayant.notes.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jayant.notes.R;
import com.example.jayant.notes.activities.NoteDetailActivity;
import com.example.jayant.notes.customwidgets.TextViewWithFont;
import com.example.jayant.notes.model.HomeActivityCardDataObject;
import com.example.jayant.notes.model.NoteColor;

import java.util.ArrayList;



/**
  *  Refer for code http://www.truiton.com/2015/03/android-cardview-example/
 */
public class HomeActivityRecyclerViewAdapter extends RecyclerView
        .Adapter<HomeActivityRecyclerViewAdapter.HomeCardDataObjectHolder> {
    static private final String TAG = "ContactsRecyclerAdapter";
    private ArrayList<HomeActivityCardDataObject> mHomeCardsData;
    // private static OutletsRecyclerViewAdapter.MyClickListener myClickListener;

    public static class HomeCardDataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextViewWithFont mPreviewText, mDateText;
        ImageView mPinned,mReminder,mNoteColorShape;




        public HomeCardDataObjectHolder(View itemView) {
            super(itemView);
            mPreviewText = (TextViewWithFont) itemView.findViewById(R.id.activity_home_card_view_row_note_preview);
            mDateText = (TextViewWithFont) itemView.findViewById(R.id.activity_home_card_view_row_note_date);
            mReminder = (ImageView) itemView.findViewById(R.id.activity_home_card_view_row_note_reminderImage);
            mPinned = (ImageView) itemView.findViewById(R.id.activity_home_card_view_row_note_pinImage);
            mNoteColorShape = (ImageView) itemView.findViewById(R.id.activity_home_card_view_row_note_colorDrawable);


            Log.d(TAG, "adding item");
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context mContext = v.getContext();
            Log.d(TAG, "Contacts recycleradapter pos is : " + getAdapterPosition());
            // Toast.makeText(v.getContext()," Home recycleradapter pos is : "+getAdapterPosition() , Toast.LENGTH_SHORT).show();


                    Log.d(TAG, "perform task clicked : " + getAdapterPosition());
                    Toast.makeText(mContext, " perform task clicked : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    Intent activityIntent = new Intent(mContext, NoteDetailActivity.class);
                    mContext.startActivity(activityIntent);


            //myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    /*public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }*/

    public HomeActivityRecyclerViewAdapter(ArrayList<HomeActivityCardDataObject> mHomeCardsData) {
        this.mHomeCardsData = mHomeCardsData;
        Log.d(TAG, "constructor");
    }

    @Override
    public HomeCardDataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.activity_home_cardview_row, parent, false);

        HomeCardDataObjectHolder homeCardDataObjectHolder = new HomeCardDataObjectHolder(view);
        Log.d(TAG, "onCreateViewHolder");
        return homeCardDataObjectHolder;

    }

    @Override
    public void onBindViewHolder(HomeCardDataObjectHolder holder, int position) {
        if(mHomeCardsData != null) {
            holder.mPreviewText.setText(mHomeCardsData.get(position).getmPreviewText());
            holder.mDateText.setText(mHomeCardsData.get(position).getmDateText());

            if(mHomeCardsData.get(position).getmPinned()){
                holder.mPinned.setVisibility(View.VISIBLE);
            }
            else{
                holder.mPinned.setVisibility(View.INVISIBLE);
            }


            if(mHomeCardsData.get(position).getmReminder()){
                holder.mReminder.setVisibility(View.VISIBLE);
            }
            else{
                holder.mReminder.setVisibility(View.INVISIBLE);
            }

            switch (mHomeCardsData.get(position).getmNoteColor()){
                case YELLOW:
                    holder.mNoteColorShape.setVisibility(View.VISIBLE);
                    holder.mNoteColorShape.setImageResource(R.drawable.yellow_filled_circle);
                    break;
                case GREEN:
                    holder.mNoteColorShape.setVisibility(View.VISIBLE);
                    holder.mNoteColorShape.setImageResource(R.drawable.green_filled_circle);
                    break;
                case ORANGE:
                    holder.mNoteColorShape.setVisibility(View.VISIBLE);
                    holder.mNoteColorShape.setImageResource(R.drawable.orange_filled_circle);
                    break;
                case BLUE:
                    holder.mNoteColorShape.setVisibility(View.VISIBLE);
                    holder.mNoteColorShape.setImageResource(R.drawable.blue_filled_circle);
                    break;
                case WHITE:
                    holder.mNoteColorShape.setVisibility(View.INVISIBLE);
                    break;
            }


            Log.d(TAG, "onBindViewHolder  pos-> " + position);
        } else {
            Log.d(TAG, " Nulllll  onBindViewHolder  pos-> " + position);
        }
    }

    public void addItem(HomeActivityCardDataObject homeActivityCardDataObj, int index) {
        mHomeCardsData.add(index, homeActivityCardDataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mHomeCardsData.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mHomeCardsData.size();
    }

   /* public interface MyClickListener {
        public void onItemClick(int position, View v);
    }*/
}
