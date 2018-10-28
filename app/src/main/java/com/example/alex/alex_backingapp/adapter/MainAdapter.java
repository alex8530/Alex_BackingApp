package com.example.alex.alex_backingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.alex_backingapp.R;
import com.example.alex.alex_backingapp.listener.MyItemClickListener;
import com.example.alex.alex_backingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecipeViewHolder> {

    Context mContext;

    List<Recipe> mRecipeList;



    MyItemClickListener mMyItemClickListener;



    public MainAdapter(Context context) {

        this.mContext = context;
        notifyDataSetChanged();

    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.menu_item__tv_recipe_servings.setText(mRecipeList.get(position).getServings()+"");
         holder.menu_item__tv_recipe_steps_count.setText(mRecipeList.get(position).getSteps().size()+"");
        holder.menu_item_recipe_name.setText(mRecipeList.get(position).getName());
    }




    @Override
    public int getItemCount() {
        if (mRecipeList==null) return 0;
        return mRecipeList.size();
    }



    public void setmMyItemClickListener(MyItemClickListener mMyItemClickListener) {
        this.mMyItemClickListener = mMyItemClickListener;
    }

    public List<Recipe> getmRecipeList() {
        return mRecipeList;
    }

    public void setmRecipeList(List<Recipe> mRecipeList) {
        this.mRecipeList = mRecipeList;
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View  view;
        @BindView(R.id.menu_item__tv_recipe_servings)
        TextView menu_item__tv_recipe_servings;
        @BindView(R.id.menu_item__tv_recipe_steps_count)
        TextView menu_item__tv_recipe_steps_count;

        @BindView(R.id.menu_item_recipe_name)
        TextView menu_item_recipe_name;



        public RecipeViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mMyItemClickListener.onClickItemObject(getAdapterPosition());

        }
    }


}
