package com.example.lectureschedule.controller;

import com.example.lectureschedule.models.Group;
import com.example.lectureschedule.models.Schedule;
import com.example.lectureschedule.models.Student;
import com.example.lectureschedule.repository.GroupRepository;
import com.example.lectureschedule.repository.ScheduleRepository;
import com.example.lectureschedule.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@RestController
public class EndpointController {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private StudentRepository studentRepository;

    private String result = "For this name does not have any student!";
    private final String notLecture = "For today does not have any lecture";


    @GetMapping("/schedule/{id}")
    public String schedule(@PathVariable("id") int id){
        for (Student student : studentRepository.findAll()){
            if (student.getId() == id){
                result = notLecture;
                result = getScheduleToday(student.getGroup(), result);
            }
        }
        return result;
    }


    @GetMapping("/schedule")
    public String schedule(@RequestParam(value = "name") String name,
                           @RequestParam(value = "surname") String surname){
        for (Student student : studentRepository.findAll()){
            if (name.equals(student.getName()) && surname.equals(student.getSurname())){
                result = notLecture;
                result = getScheduleToday(student.getGroup(), result);
            }
        }

        return result;
    }


    private String getScheduleToday(Group group, String result){
        for (Schedule schedule : group.getSchedules()){
            if (new SimpleDateFormat("MM/dd/yyyy").format(schedule.getDate().getTime()).equals(
                    new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()))){
                result = schedule.getDate().getTime().toString()+ " lecture list: " + schedule.toString();
            }
        }
        return result;
    }

//    http://localhost:8080/schedule?name=Dmitriy&surname=Ivanov
//    http://localhost:8080/schedule_id?id=255
//    http://localhost:8080/createData

    @GetMapping("/createData")
    public void createData(){
        final String groupName = "Enginer";

        Group group = new Group();
        group.setName(groupName);

        List<String> lectures1 = new ArrayList<>();
        List<String> lectures2 = new ArrayList<>();

        lectures1.add("math");
        lectures1.add("physics");
        lectures1.add("philosophy");
        lectures1.add("economy");

        lectures2.add("math");
        lectures2.add("chemistry");
        lectures2.add("computer science");
        lectures2.add("geometry");

        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        schedule1.setDate(Calendar.getInstance());
        schedule1.setScheduleList(lectures1);
        schedule1.setGroup(group);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        schedule2.setDate(calendar);
        schedule2.setScheduleList(lectures2);
        schedule2.setGroup(group);

        List<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule1);
        schedules.add(schedule2);
        group.setSchedules(schedules);

        Student student1 = new Student("Dmitriy", "Ivanov");
        Student student2 = new Student("Victor", "Petrov");
        Student student3 = new Student("Maria", "Zaitseva");
        Student student4 = new Student("Alexandra", "Smirnova");
        Student student5 = new Student("Nikolay", "Sazonov");

        student1.setGroup(group);
        student2.setGroup(group);
        student3.setGroup(group);
        student4.setGroup(group);
        student5.setGroup(group);


        groupRepository.save(group);
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        studentRepository.save(student5);

    }
}
