package com.example.lease_project;

public class CarModelData {
    String name, brand_name, image ;
    int doors, max_speed, rent_price, lease_price, seating;


    public CarModelData(){}

    public CarModelData(String name, String brand_name, String image, int doors, int max_speed, int rent_price, int lease_price, int seating) {
        this.name = name;
        this.brand_name = brand_name;
        this.image = image;
        this.doors = doors;
        this.max_speed = max_speed;
        this.rent_price = rent_price;
        this.lease_price = lease_price;
        this.seating = seating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }

    public int getRent_price() {
        return rent_price;
    }

    public void setRent_price(int rent_price) {
        this.rent_price = rent_price;
    }

    public int getLease_price() {
        return lease_price;
    }

    public void setLease_price(int lease_price) {
        this.lease_price = lease_price;
    }

    public int getSeating() {
        return seating;
    }

    public void setSeating(int seating) {
        this.seating = seating;
    }
}
