package com.example.demo;

public class Bike {
    private String make;
    private String model;
    private String year;
    private String type;
    private String displacement;
    private String engine;
    private String power;
    private String torque;

    public Bike(String make, String model, String year, String type, String displacement, String engine, String power, String torque) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
        this.displacement = displacement;
        this.engine = engine;
        this.power = power;
        this.torque = torque;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getTorque() {
        return torque;
    }

    public void setTorque(String torque) {
        this.torque = torque;
    }

    public String getBriefDescription() {
        return String.format("The %s %s is a %s bike made in %s, featuring a %s engine generating %s and %s.",
                make, model, type, year, engine, power, torque);
    }
}