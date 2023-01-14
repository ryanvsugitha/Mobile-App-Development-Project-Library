package com.example.projectlab.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectlab.test_halaman;
import com.example.projectlab.R;
import com.example.projectlab.model.Requester;

import java.util.Vector;

public class RecyclerViewRequestsAdapter extends RecyclerView.Adapter<RecyclerViewRequestsAdapter.ViewHolder> {

    private Vector<Requester> requests;
    private Context ctx;
    private int ID;

    public RecyclerViewRequestsAdapter(Vector<Requester> requests, Context ctx, int ID) {
        this.requests = requests;
        this.ctx = ctx;
        this.ID = ID;
    }

    @NonNull
    @Override
    public RecyclerViewRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_request_form, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewRequestsAdapter.ViewHolder holder, int position) {
        Requester request = requests.get(position);

        String cover = request.getCover().substring(7, request.getCover().length()-4).replace("-", "_");
        int id = ctx.getResources().getIdentifier(cover, "drawable", ctx.getPackageName());

        holder.ivCover.setImageResource(id);
        holder.tvTitle.setText(request.getTitle());
        holder.tvAuthor.setText(request.getAuthor());
        holder.tvRequester.setText(request.getRequester());
        holder.tvReceiver.setText(request.getReceiver());

        holder.cvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, test_halaman.class);
                intent.putExtra("title", request.getTitle());
                intent.putExtra("id", id);
                intent.putExtra("user_id", ID);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvTitle, tvAuthor, tvRequester, tvReceiver;
        protected ImageView ivCover;
        protected CardView cvBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvBook = itemView.findViewById(R.id.cv_book);
            ivCover = itemView.findViewById(R.id.tv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvRequester = itemView.findViewById(R.id.tv_requester_email);
            tvReceiver = itemView.findViewById(R.id.tv_receiver_email);
        }
    }
}
