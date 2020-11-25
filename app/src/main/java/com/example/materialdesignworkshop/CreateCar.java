package com.example.materialdesignworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateCar extends AppCompatActivity {

    private EditText licensePlate, model, owner;
    private ImageView photo;
    private Uri uri;
    private Car c;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);

        licensePlate = findViewById(R.id.txtLicensePlate);
        model = findViewById(R.id.txtModel);
        owner = findViewById(R.id.txtOwner);
        photo = findViewById(R.id.imgSelectedPicture);

        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void save(View v){
        c = new Car();
        InputMethodManager imp = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        c.setId(Data.getId());
        c.setLicensePlate(this.licensePlate.getText().toString());
        c.setModel(this.model.getText().toString());
        c.setOwner(this.owner.getText().toString());


        c.save();
        updatePhoto(Data.getId());
        clear();
        imp.hideSoftInputFromWindow(this.licensePlate.getWindowToken(),0);

        Snackbar.make(v, R.string.strSuccessfulySave, Snackbar.LENGTH_LONG).show();

    }

    public void updatePhoto(String id){
        StorageReference child = storageReference.child(id);
        UploadTask uploadTask = child.putFile(uri);
    }

    public void clear(View v){
        clear();
    }

    public void clear(){
        licensePlate.setText("");
        model.setText("");
        owner.setText("");
        photo.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(CreateCar.this,MainActivity.class);
        startActivity(i);
    }

    public void selectPhoto(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,getString(R.string.strSelectPhoto)),1);
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            uri = data.getData();
            if(uri != null) {
                photo.setImageURI(uri);
            }
        }
    }
}