package com.example.lectureschedule.controller;

import com.example.lectureschedule.models.Schedule;
import com.example.lectureschedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;


    @PostMapping("/schedule")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule){
        try {
            Schedule scheduleEntity = scheduleRepository.save(new Schedule(
                    schedule.getDate(),
                    schedule.getGroup(),
                    schedule.getScheduleList()));
            return new ResponseEntity<>(scheduleEntity, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/schedule")
    public ResponseEntity<List<Schedule>> getAllSchedule(@RequestParam(required = false) String title){
        try {
            List<Schedule> schedules = new ArrayList<Schedule>();
            scheduleRepository.findAll().forEach(schedules::add);
            System.out.println(schedules.size());
            if (schedules.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(schedules, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/schedule/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable("id") int id){
        Optional<Schedule> schedules = scheduleRepository.findById(id);
        if (schedules.isPresent()){
            return new ResponseEntity<>(schedules.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/schedule/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable("id") int id, @RequestBody Schedule schedule){
        Optional<Schedule> scheduleData = scheduleRepository.findById(id);
        if (scheduleData.isPresent()){
            Schedule scheduleChange = scheduleData.get();
            scheduleChange.setDate(schedule.getDate());
            scheduleChange.setGroup(schedule.getGroup());
            scheduleChange.setScheduleList(schedule.getScheduleList());
            return new ResponseEntity<>(scheduleRepository.save(scheduleChange), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/schedule")
    public ResponseEntity<HttpStatus> deleteAllSchedule(){
        try {
            scheduleRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<HttpStatus> deleteSchedule(@PathVariable("id") int id){
        try {
            scheduleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}