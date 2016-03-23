package com.boxfish.ming.rxjavaretrofitdemo.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boxfish.ming.rxjavaretrofitdemo.R;
import com.boxfish.ming.rxjavaretrofitdemo.entity.MovieEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

// +----------------------------------------------------------------------
// | CreateTime: 16/3/23 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.zhangkun.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{


    private List<MovieEntity.Subject> mSubjects;

    public MovieAdapter() {
        mSubjects = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        //http://gank.io/post/56e80c2c677659311bed9841
        MovieEntity.Subject subject = mSubjects.get(position);
        holder.movieIcon.setImageURI(Uri.parse(subject.getImages().getMedium()));
        holder.movieTitle.setText(subject.getTitle());
        holder.movieCatagory.setText(subject.getGenres().get(0)+".."+subject.getGenres().get(1));
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }


    public void addAll(Collection<? extends MovieEntity.Subject> subjects){
        mSubjects.addAll(subjects);
        notifyDataSetChanged();
    }

    public void clear(){

        mSubjects.clear();
        notifyDataSetChanged();

    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{


        @Bind(R.id.movie_icon)
        SimpleDraweeView movieIcon;

        @Bind(R.id.movie_title)
        TextView movieTitle;

        @Bind(R.id.movie_category)
        TextView movieCatagory;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
