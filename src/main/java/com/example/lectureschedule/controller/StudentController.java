package com.example.lectureschedule.controller;

import com.example.lectureschedule.models.Student;
import com.example.lectureschedule.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;


    @PostMapping("/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        try {
            Student studentEntity = studentRepository.save(new Student(
                    student.getName(),
                    student.getSurname(),
                    student.getGroup()));
            return new ResponseEntity<>(studentEntity, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/student")
    public ResponseEntity<List<Student>> getAllStudent(@RequestParam(required = false) String title){
        try {
            List<Student> students = new ArrayList<Student>();
            studentRepository.findAll().forEach(students::add);
            System.out.println(students.size());
            if (students.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int id){
        Optional<Student> students = studentRepository.findById(id);
        if (students.isPresent()){
            return new ResponseEntity<>(students.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/student/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") int id, @RequestBody Student student){
        Optional<Student> groupData = studentRepository.findById(id);
        if (groupData.isPresent()){
            Student studentChange = groupData.get();
            studentChange.setName(student.getName());
            studentChange.setSurname(student.getSurname());
            studentChange.setGroup(student.getGroup());
            return new ResponseEntity<>(studentRepository.save(studentChange), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/student")
    public ResponseEntity<HttpStatus> deleteAllStudent(){
        try {
            studentRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/student/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") int id){
        try {
            studentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}