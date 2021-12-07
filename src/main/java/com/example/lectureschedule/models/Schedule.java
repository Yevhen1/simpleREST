package com.example.lectureschedule.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Calendar date;

    @ElementCollection
    @Column(name = "schedule_list")
    private List<String> scheduleList = new ArrayList<>();

    @ManyToOne (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name="schedule_id")
    private Group group;

    public Schedule(){
    }


    public Schedule(Calendar date, List<String>scheduleList){
        this.date = date;
        this.scheduleList = scheduleList;
    }


    public Schedule(Calendar date, Group group, List<String> scheduleList){
        this.date = date;
        this.group = group;
        this.scheduleList = scheduleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public List<String> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<String> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString(){
        return scheduleList.stream().collect(Collectors.joining(", "));
    }
}