package org.gmdev.springbootdemo.configuration.beans;

import org.springframework.stereotype.Component;

@Component("lap-1")
public class Laptop {

    private Integer id;
    private String model;

    public Laptop() {
        System.out.println("Laptop object created");
    }

    public void compile() {
        System.out.println("Compiling .... ");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
