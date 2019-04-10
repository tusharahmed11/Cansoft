package com.cansoft.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cansoft.app.R;
import com.cansoft.app.model.Client;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.MyViewHolder> {
    private Context context;
    private List<Client> clients;

    public ClientListAdapter(Context context, List<Client> clients) {
        this.context = context;
        this.clients = clients;
    }

    @NonNull
    @Override
    public ClientListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.client_slider_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientListAdapter.MyViewHolder holder, int position) {
        Client client = clients.get(position);
       /* if (!(client.getEmbedded().getWpFeaturedmedia() == null)){
            Picasso.get().load("https://cansoft.com" +client.getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl()).into(holder.image);
        }*/
        Picasso.get().load(client.getImage().getData().getFullUrl()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.client_image);
        }
    }
}
