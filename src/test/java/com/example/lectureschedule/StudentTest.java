package com.example.lectureschedule;

import com.example.lectureschedule.models.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.URISyntaxException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @LocalServerPort
    int randomServerPort;


    private final String host = "http://localhost:";
    private final String name = "Alex";
    private final String surName = "Egorov";
    private final String updateName = "Roy";
    private final String updateSurName = "Komarov";

    private String id = "";


    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        return headers;
    }


    private URI getUri() throws URISyntaxException {
        final String baseUrl = host + randomServerPort + "/api/group/";
        URI uri = new URI(baseUrl);
        return uri;
    }


    private HttpEntity<Student> getEntity(String nameEntity, String surNameEntity){
        Student student = new Student(nameEntity, surNameEntity);
        HttpEntity<Student> request = new HttpEntity<>(student, getHeaders());
        return request;
    }


    @Test
    public void testGroupCRUD()throws URISyntaxException{
        ResponseEntity<Student> result;

        result = this.restTemplate.postForEntity(getUri(), getEntity(name, surName), Student.class);
        Assert.assertEquals(201, result.getStatusCodeValue());
        id = String.valueOf(result.getBody().getId());


        result = this.restTemplate.getForEntity(getUri() + id, Student.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(result.getBody().getName(), name);


        this.restTemplate.put(getUri() + id, getEntity(updateName, updateSurName));
        result = this.restTemplate.getForEntity(getUri() + id, Student.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(result.getBody().getName(), updateName);


        this.restTemplate.delete(getUri() + id);
        result = this.restTemplate.getForEntity(getUri() + id, Student.class);
        Assert.assertEquals(404, result.getStatusCodeValue());
    }
}
