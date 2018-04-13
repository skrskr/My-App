package com.mohamed.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by mohamed on 12/04/18.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private Context context;
    private List<ServiceModel> serviceModelList;

    public ServiceAdapter(Context context, List<ServiceModel> serviceModelList) {
        this.context = context;
        this.serviceModelList = serviceModelList;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_service_item,parent,false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        ServiceModel model = serviceModelList.get(position);
        holder.serviceName.setText(model.getName());
        Glide.with(context).load(model.getImage_url()).into(holder.serviceImage);
    }

    @Override
    public int getItemCount() {
        return serviceModelList.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder{

        ImageView serviceImage;
        TextView serviceName;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            serviceImage = itemView.findViewById(R.id.image_service);
            serviceName = itemView.findViewById(R.id.text_service_name);
        }
    }
}
