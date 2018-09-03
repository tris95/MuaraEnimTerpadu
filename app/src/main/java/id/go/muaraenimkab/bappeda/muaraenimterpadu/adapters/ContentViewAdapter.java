package id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Content;

public class ContentViewAdapter extends RecyclerView.Adapter<ContentViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Content> mListContent;

    public ContentViewAdapter(Context context, ArrayList<Content> mListContent) {
        this.context = context;
        this.mListContent = mListContent;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, int position) {
        holder.imgContent.setImageResource(R.drawable.jalan);
        holder.content.setText("Kuliner");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgContent;
        TextView content;
        DataObjectHolder(View itemView) {
            super(itemView);
            imgContent=itemView.findViewById(R.id.imgContent);
            content=itemView.findViewById(R.id.lblContent);
        }
    }
}
