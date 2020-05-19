package com.kaianchan.fetchdatarecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/*
* RecyclerView.Adapter
* RecyclerView.ViewHolder
*/

public class ChapAdapter extends RecyclerView.Adapter<ChapAdapter.ChapViewHolder> {

    private Context mCtx;
    private List<Chapter> chapterList; // From Project.java

    // Constructor
    public ChapAdapter(Context mCtx, List<Chapter> chapterList) {
        this.mCtx = mCtx;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ChapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        // (cardview.xml, null)
        View view = inflater.inflate(R.layout.chap_cardview, parent, false);
        ChapViewHolder chapViewHolder = new ChapViewHolder(view);
        return chapViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapViewHolder holder, final int position) {
        // Get position
        Chapter chapterPos = chapterList.get(position);

        // 定義每格card view的text來自getTitle()
        holder.chapTitle.setText(chapterPos.getTitle());
        Glide.with(mCtx)
                .load(chapterPos.getImage()) // Load the image
                .into(holder.chapIcon);

        // Used for static recyclerView only
        // holder.chapIcon.setImageDrawable(mCtx.getResources().getDrawable(chapter.getImage()));

        /*
        holder.chapRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Clicked " + chapterList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        // return the size of the length
        return chapterList.size();
    }

    public static class ChapViewHolder extends RecyclerView.ViewHolder {

        // Elements in CardView
        public ImageView chapIcon;
        public TextView chapTitle;

        // Relative Layout
        public RelativeLayout chapRelativeLayout;

        public ChapViewHolder(@NonNull View itemView) {
            super(itemView);

            chapIcon = (ImageView) itemView.findViewById(R.id.chap_icon);
            chapTitle = (TextView) itemView.findViewById(R.id.chap_title);

            chapRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.chap_relativeLayout);
        }
    }
}
