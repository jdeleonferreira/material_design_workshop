package com.example.materialdesignworkshop;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private ArrayList<Car> cars;
    private OnPersonClickListener clickListener;
    private StorageReference storageReference;

    public CarAdapter(ArrayList<Car> cars, OnPersonClickListener clickListener){
        this.cars = cars;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item,parent,false);
        return new CarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car c = cars.get(position);
        holder.id.setText(c.getId());
        holder.licensePlate.setText(c.getLicensePlate());
        holder.model.setText(c.getModel());
        holder.owner.setText(c.getOwner());

        storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child(c.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.photo);
            }
        });

        holder.v.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clickListener.onPersonClick(c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView photo;
        private TextView id;
        private TextView licensePlate;
        private TextView model;
        private TextView owner;
        private View v;

        public CarViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            photo = v.findViewById(R.id.imgItemPhoto);
            id = v.findViewById(R.id.lblItemLicensePlate);
            licensePlate = v.findViewById(R.id.lblItemLicensePlate);
            model = v.findViewById(R.id.lblItemModel);
            owner = v.findViewById(R.id.lblItemOwner);
        }
    }

    public interface OnPersonClickListener{
        void onPersonClick(Car c);
    }
}
