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

import com.example.projectlab.Book_detail_form;
import com.example.projectlab.R;
import com.example.projectlab.model.Book;

import java.util.Vector;

public class RecyclerViewBooksAdapter extends RecyclerView.Adapter<RecyclerViewBooksAdapter.ViewHolder>{
    private Vector<Book> books;
    private Context ctx;
    private int ID;

    public RecyclerViewBooksAdapter(Vector<Book> books, Context ctx, int ID) {
        this.books = books;
        this.ctx = ctx;
        this.ID = ID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBooksAdapter.ViewHolder holder, int position) {
        Book book = books.get(position);

        String cover = book.getCover().substring(7, book.getCover().length()-4).replace("-", "_");
        int id = ctx.getResources().getIdentifier(cover, "drawable", ctx.getPackageName());

        holder.ivCover.setImageResource(id);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvSynopsis.setText(book.getSynopsis());

        holder.cvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, Book_detail_form.class);
                intent.putExtra("title", book.getTitle());
                intent.putExtra("id", id);
                intent.putExtra("user_id", ID);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvTitle, tvAuthor, tvSynopsis;
        protected ImageView ivCover;
        protected CardView cvBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvBook = itemView.findViewById(R.id.cv_book);
            ivCover = itemView.findViewById(R.id.tv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvSynopsis = itemView.findViewById(R.id.tv_synopsis);
        }
    }
}