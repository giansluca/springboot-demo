package org.gmdev.springbootdemo.configuration.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class Alien {

    private Integer id;
    private String name;
    private String skill;
    private Laptop laptop;

    @Autowired
    public Alien(@Qualifier("lap-1")Laptop laptop) {
        this.laptop = laptop;

        System.out.println("Alien object created");
    }

    public void printHello() {
        System.out.println("Hi i'm an alien");
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }
}
