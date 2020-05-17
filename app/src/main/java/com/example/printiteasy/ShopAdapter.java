package com.example.printiteasy;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<Source> mSource;
    //**********?/

    //////////////
    private OnNoteListener mOnNoteListener;

    ShopAdapter(List<Source> shopDetails, OnNoteListener OnNoteListener){
        mSource = shopDetails;
        this.mOnNoteListener = OnNoteListener;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View shopView = inflater.inflate(R.layout.listviewitems,parent,false);
        return new ViewHolder(shopView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(ShopAdapter.ViewHolder viewHolder, final int position) {
        Source source = mSource.get(position);
        TextView textView = viewHolder.shopNameTextView;
        textView.setText(source.getShopName());
        TextView textView2 = viewHolder.rateBlackTextView;
        textView2.setText(source.getRateBlack());
        TextView textView3 = viewHolder.rateColorTextView;
        textView3.setText(source.getRateColor());
        TextView textView4 = viewHolder.addressTextView;
        textView4.setText(source.getAddress());


    }

    @Override
    public int getItemCount() {
        return mSource.size();
    }

    //check implements onClickListener

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView shopNameTextView, rateBlackTextView, rateColorTextView,addressTextView;
        ///////////////
        String str;
        //***********//
        OnNoteListener onNoteListener;

        ViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
             shopNameTextView = itemView.findViewById(R.id.shopName);
             rateBlackTextView = itemView.findViewById(R.id.printRatesBlack);
             rateColorTextView = itemView.findViewById(R.id.printRatesColor);
             addressTextView = itemView.findViewById(R.id.address);
             //********//
            str = shopNameTextView.getText().toString().trim();
            /////////////
            this.onNoteListener = onNoteListener;

             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }
    }
    //**********************************************************************************//
    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
