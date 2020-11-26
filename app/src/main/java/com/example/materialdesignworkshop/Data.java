package com.example.materialdesignworkshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.SnapshotHolder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Data {
    private static String db = "Cars";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private static ArrayList<Car> cars = new ArrayList<>();

    public static String getId(){
        return databaseReference.push().getKey();
    }
    public static void save(Car c){
        databaseReference.child(db).child(c.getLicensePlate()).push().setValue(c);
    }

    public static void  delete(Car c){
        databaseReference.child(db).child(c.getLicensePlate()).removeValue();
        storageReference.child(c.getId()).delete();
    }

    public static void setCars(ArrayList<Car> cars){
        cars = cars;
    }


}
