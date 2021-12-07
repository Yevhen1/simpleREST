package com.example.lectureschedule.models;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String surname;

    @ManyToOne (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name="student_id")
    private Group group;

    public Student(){
    }


    public Student(String name, String surname){
        this.name = name;
        this.surname = surname;
    }


    public Student(String name, String surname, Group group){
        this.name = name;
        this.surname = surname;
        this.group = group;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surName) {
        this.surname = surName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString(){
        return surname + name;
    }
}