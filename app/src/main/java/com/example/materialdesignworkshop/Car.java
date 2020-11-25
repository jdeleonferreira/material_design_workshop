package com.example.materialdesignworkshop;

public class Car {
    private String id;
    private String licensePlate;
    private String model;
    private String Owner;

    public Car(){}

    public Car(String licensePlate, String model, String owner) {
        this.licensePlate = licensePlate;
        this.model = model;
        Owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public void save(){
        Data.save(this);
    }

    public  void delete(){
        Data.delete(this);
    }
}
