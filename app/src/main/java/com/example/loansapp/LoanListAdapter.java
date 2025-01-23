package com.example.loansapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.loansapp.model.Loan;

import java.util.List;

public class LoanListAdapter extends RecyclerView.Adapter<LoanViewHolder> {

    public interface OnLoanActionListener {
        void onMarkReturned(int loanId);
    }

    private Context context;
    private List<Loan> loanList;
    private OnLoanActionListener listener;

    public LoanListAdapter(Context context, List<Loan> loanList, OnLoanActionListener listener) {
        this.context = context;
        this.loanList = loanList;
        this.listener = listener;
    }

    @Override
    public LoanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_loan, parent, false);
        return new LoanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LoanViewHolder holder, int position) {
        final Loan loan = loanList.get(position);
        holder.tvItemBrand.setText("Brand: " + loan.getTabletBrand());
        holder.tvItemCable.setText("Kabel: " + (loan.getCableType().isEmpty() ? "Ingen" : loan.getCableType()));
        holder.tvItemName.setText("Navn: " + loan.getBorrowerName());
        holder.tvItemContact.setText("Kontakt: " + (loan.getBorrowerContact().isEmpty() ? "Ingen" : loan.getBorrowerContact()));
        holder.tvItemTime.setText("Tid: " + loan.getLoanTime());

        holder.btnMarkReturned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMarkReturned(loan.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return loanList.size();
    }

    public void setData(List<Loan> newList) {
        this.loanList = newList;
        notifyDataSetChanged();
    }
}
