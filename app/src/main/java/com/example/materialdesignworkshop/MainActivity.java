package com.example.materialdesignworkshop;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CarAdapter.OnPersonClickListener {

    private RecyclerView list;
    private CarAdapter adapter;
    private LinearLayoutManager llm;
    private ArrayList<Car> cars;
    private DatabaseReference databaseReference;
    private String db = "Cars";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        list = findViewById(R.id.lstCars);

        cars = new ArrayList<Car>();
        llm = new LinearLayoutManager(this);
        adapter = new CarAdapter(cars, this);
        llm.setOrientation(RecyclerView.VERTICAL);

        list.setLayoutManager(llm);
        list.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(db).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cars.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Car c = snapshot.getValue(Car.class);
                        cars.add(c);
                    }
                }
                adapter.notifyDataSetChanged();
                Data.setCars(cars);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void add(View v){
        Intent intent;
        intent = new Intent(MainActivity.this, CreateCar.class);
        startActivity(intent);
    }

    @Override
    public void onPersonClick(Car c) {

        Intent intent = new Intent(MainActivity.this, CarDetail.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", c.getId());
        bundle.putString("licensePlate", c.getLicensePlate());
        bundle.putString("model", c.getModel());
        bundle.putString("owner", c.getOwner());
        intent.putExtra("car",bundle);
        startActivity(intent);
    }
}