package com.example.lectureschedule.controller;

import com.example.lectureschedule.models.Group;
import com.example.lectureschedule.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroupController {

//    http://localhost:8080/api/group/
    @Autowired
    private GroupRepository groupRepository;

    private Group findGroupByName(String name){
        Group result = null;
        for (Group group : groupRepository.findAll()){
            if (name.equals(group.getName())){
                result = group;
            }
        }
        return result;
    }

    @PostMapping("/group")
    public ResponseEntity<Group> createGroup(@RequestBody Group group){
        System.out.println(group.getName());
        try {
            Group groupEntity = groupRepository.save(new Group(
                    group.getName()));
            return new ResponseEntity<>(groupEntity, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/group")
    public ResponseEntity<List<String>> getAllGroup(@RequestParam(required = false) String title){
        try {
            List<String> groups = new ArrayList<String>();
            for (Group group : groupRepository.findAll()){
                groups.add(group.toString());
            }
            System.out.println(groups.size());
            if (groups.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(groups, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable("id") int id){
        Optional<Group> groups = groupRepository.findById(id);
        if (groups.isPresent()){
            return new ResponseEntity<>(groups.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping("/group/{name}")
//    public ResponseEntity<?> getGroupByName(@PathVariable("name") String name){
//        Group group = findGroupByName(name);
//        if (group != null){
//            return new ResponseEntity<>(group.toString(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }


//    @PutMapping("/group/{name}")
//    public ResponseEntity<?> updateGroup(@PathVariable("name") String name, @RequestBody Group group){
//        Group necessaryGroup = findGroupByName(name);
//        if (necessaryGroup != null){
//            necessaryGroup.setName(group.getName());
//            return new ResponseEntity<>(groupRepository.save(necessaryGroup), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping("/group/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable("id") int id, @RequestBody Group group){
        Optional<Group> groupData = groupRepository.findById(id);
        if (groupData.isPresent()){
            Group group1 = groupData.get();
            group1.setName(group.getName());
            return new ResponseEntity<>(groupRepository.save(group1), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/group")
    public ResponseEntity<HttpStatus> deleteAllGroup(){
        try {
            groupRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/group/{id}")
    public ResponseEntity<HttpStatus> deleteGroup(@PathVariable("id") int id){
        try {
            groupRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}