package com.example.alex.alex_backingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.alex_backingapp.R;
import com.example.alex.alex_backingapp.listener.MyItemClickListener;
import com.example.alex.alex_backingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetpAdapter extends RecyclerView.Adapter<SetpAdapter.StepViewHolder>  {



    Context mContext;

    List<Step> mStepList;

    public List<Step> getmStepList() {
        return mStepList;
    }

    public void setmStepList(List<Step> mStepList) {
        this.mStepList = mStepList;
    }

    MyItemClickListener mMyItemClickListener;



    public SetpAdapter(Context context) {

        this.mContext = context;
        notifyDataSetChanged();

    }


    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        return new SetpAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {

        holder.tv_step_item_shortDescription.setText(mStepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepList ==null) return 0;
        return mStepList.size();
    }



    public void setmMyItemClickListener(MyItemClickListener mMyItemClickListener) {
        this.mMyItemClickListener = mMyItemClickListener;
    }



    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_step_item_shortDescription)
        TextView tv_step_item_shortDescription;

        public StepViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mMyItemClickListener.onClickItemObject(getAdapterPosition());

        }
    }


}
