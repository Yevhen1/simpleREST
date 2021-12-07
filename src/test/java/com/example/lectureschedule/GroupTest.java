package com.example.lectureschedule;

import com.example.lectureschedule.models.Group;
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
public class GroupTest {


    @Autowired
    private TestRestTemplate restTemplate;


    @LocalServerPort
    int randomServerPort;


    private final String host = "http://localhost:";
    private final String groupName = "Enginer";
    private final String updateName = "Filosof";
    private String id = "";


    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        return headers;
    }


    private URI getUri() throws URISyntaxException{
        final String baseUrl = host + randomServerPort + "/api/group/";
        URI uri = new URI(baseUrl);
        return uri;
    }


    private HttpEntity<Group> getEntity(String name){
        Group group = new Group(name);
        HttpEntity<Group> request = new HttpEntity<>(group, getHeaders());
        return request;
    }


    @Test
    public void testGroupCRUD()throws URISyntaxException{
        ResponseEntity<Group> result;

        result = this.restTemplate.postForEntity(getUri(), getEntity(groupName), Group.class);
        Assert.assertEquals(201, result.getStatusCodeValue());
        id = String.valueOf(result.getBody().getId());


        result = this.restTemplate.getForEntity(getUri() + id, Group.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(result.getBody().getName(), groupName);


        this.restTemplate.put(getUri() + id, getEntity(updateName));
        result = this.restTemplate.getForEntity(getUri() + id, Group.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(result.getBody().getName(), updateName);


        this.restTemplate.delete(getUri() + id);
        result = this.restTemplate.getForEntity(getUri() + id, Group.class);
        Assert.assertEquals(404, result.getStatusCodeValue());
    }
}
