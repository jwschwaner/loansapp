package com.example.loansapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LoanViewHolder extends RecyclerView.ViewHolder {

    public TextView tvItemBrand, tvItemCable, tvItemName, tvItemContact, tvItemTime;
    public Button btnMarkReturned;

    public LoanViewHolder(View itemView) {
        super(itemView);
        tvItemBrand = itemView.findViewById(R.id.tvItemBrand);
        tvItemCable = itemView.findViewById(R.id.tvItemCable);
        tvItemName = itemView.findViewById(R.id.tvItemName);
        tvItemContact = itemView.findViewById(R.id.tvItemContact);
        tvItemTime = itemView.findViewById(R.id.tvItemTime);
        btnMarkReturned = itemView.findViewById(R.id.btnMarkReturned);
    }
}
