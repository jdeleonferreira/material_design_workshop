package com.example.materialdesignworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class CarDetail extends AppCompatActivity {

    private ImageView photo;
    private TextView lblLicensePlate, lblModel, lblOwner;
    private Intent intent;
    private StorageReference storageReference;
    private Car c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        intent = getIntent();
        Bundle person = intent.getBundleExtra("person");

        lblLicensePlate = findViewById(R.id.lblItemLicensePlate);
        lblModel = findViewById(R.id.lblItemModel);
        lblOwner = findViewById(R.id.lblItemOwner);
        photo = findViewById(R.id.imgItemPhoto);

        c = new Car(
                person.getString("licensePlate"),
                person.getString("model"),
                person.getString("owner")
        ); //person.getString("id")

        storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child(c.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(photo);
            }
        });

        lblLicensePlate.setText(c.getLicensePlate());
        lblModel.setText(c.getModel());
        lblOwner.setText(c.getOwner());
    }

    public void delete(View v){
        String positive, negative;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.strDeleteTitle);
        builder.setMessage(R.string.strDeleteCarMessage);
        positive = getString(R.string.strPositiveOption);
        negative = getString(R.string.strNegativeOption);

        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                c.delete();
                onBackPressed();
            }
        });

        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(CarDetail.this, MainActivity.class);
        startActivity(intent);
    }
}