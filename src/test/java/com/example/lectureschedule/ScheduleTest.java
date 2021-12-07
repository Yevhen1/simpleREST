package com.example.lectureschedule;

import com.example.lectureschedule.models.Schedule;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;


    private final String host = "http://localhost:";
    private final Calendar calendar = Calendar.getInstance();
    private final List<String> schedules = Arrays.asList("a", "b", "c", "d");
    private final List<String> changeSchedules = Arrays.asList("d", "c", "b", "a");
    private String id = "";


    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        return headers;
    }


    private URI getUri() throws URISyntaxException {
        final String baseUrl = host + randomServerPort + "/api/schedule/";
        URI uri = new URI(baseUrl);
        return uri;
    }


    private HttpEntity<Schedule> getEntity(Calendar calendarDate, List<String> testSchedule){
        Schedule schedule = new Schedule(calendarDate, testSchedule);
        HttpEntity<Schedule> request = new HttpEntity<Schedule>(schedule, getHeaders());
        return request;
    }


    @Test
    public void testGroupCRUD()throws URISyntaxException{
        ResponseEntity<Schedule> result;

        result = this.restTemplate.postForEntity(getUri(), getEntity(calendar, schedules), Schedule.class);
        Assert.assertEquals(201, result.getStatusCodeValue());
        id = String.valueOf(result.getBody().getId());

        System.out.println(id);

        result = this.restTemplate.getForEntity(getUri() + id, Schedule.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(result.getBody().getScheduleList(), schedules);


        this.restTemplate.put(getUri() + id, getEntity(calendar, changeSchedules));
        result = this.restTemplate.getForEntity(getUri() + id, Schedule.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(result.getBody().getScheduleList(), changeSchedules);


        this.restTemplate.delete(getUri() + id);
        result = this.restTemplate.getForEntity(getUri() + id, Schedule.class);
        Assert.assertEquals(404, result.getStatusCodeValue());
    }
}
