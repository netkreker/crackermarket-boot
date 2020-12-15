package com.crackermarket.app.user.restcontrollers;

import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.StackTraceToStringConverter;
import com.crackermarket.app.user.entities.Feedback;
import com.crackermarket.app.user.entities.User;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.user.services.FeedbackService;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class FeedbackRestController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private LogEntityService logService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/users/{id}/feedback/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createFeedback(@PathVariable(name = "id") String id, @RequestBody Feedback feedback){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "createFeedback", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (feedback == null){
            LogEntity log = new LogEntity("error", this.getClass(), "createFeedback", HttpStatus.BAD_REQUEST, "Feedback not created", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = null;
        user = userService.findUserById(UUID.fromString(id));

        if (user == null){
            LogEntity log = new LogEntity("error", this.getClass(), "createFeedback", HttpStatus.NOT_FOUND, "User with id\'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        feedback.setUser(user);
        feedbackService.saveFeedback(feedback);

        LogEntity log = new LogEntity("info", this.getClass(), "createFeedback", HttpStatus.CREATED, "Feedback with id \'" + feedback.getId() + "\' created", null);
        logService.save(log);
        return new ResponseEntity<>(feedback.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/feedback/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllFeedbacks(){

        List<Feedback> feedbacks = null;
        feedbacks = feedbackService.findAllFeedbacks();

        if (feedbacks == null || feedbacks.isEmpty()){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "getAllFeedbacks", HttpStatus.NO_CONTENT, "Feedbacks not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UUID> feedbacksId = new ArrayList<>();
        for (Feedback feedback: feedbacks)
            feedbacksId.add(feedback.getId());

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "getAllFeedbacks", HttpStatus.FOUND, "Feedbacks found", null);
        logService.save(log);
        return new ResponseEntity<>(feedbacksId, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/users/{id}/feedback/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserFeedbacks(@PathVariable(name = "id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUserFeedbacks", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = null;
        user = userService.findUserById(UUID.fromString(id));

        if (user == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "findUserFeedbacks", HttpStatus.NOT_FOUND, "User with id \'"+ id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Feedback> feedbacks = null;
        feedbacks = feedbackService.findAllUserFeedbacks(UUID.fromString(id));

        if (feedbacks == null || feedbacks.isEmpty()){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "findUserFeedbacks", HttpStatus.NO_CONTENT, "Feedbacks for user with id \'" + id + "\'not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UUID> feedbacksId = new ArrayList<>();
        for (Feedback feedback: feedbacks)
            feedbacksId.add(feedback.getId());

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "findUserFeedbacks", HttpStatus.FOUND, "Feedbacks for user with id \'" + id + "\'not found", null);
        logService.save(log);
        return new ResponseEntity<>(feedbacksId, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/feedbacks/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findFeedbackById(@PathVariable(name = "id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findFeedbackById", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Feedback feedback = null;
        feedback = feedbackService.findFeedbackById(UUID.fromString(id));

        if (feedback == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "findFeedbackById", HttpStatus.NOT_FOUND, "Feedback with id \'"+ id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "findFeedbackById", HttpStatus.FOUND, "Feedback with id \'" + id + "\' found", null);
        logService.save(log);
        return new ResponseEntity<>(feedback.getId(), HttpStatus.FOUND);
    }

    @RequestMapping(value = "/feedback/{id}/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFeedbackById(@PathVariable(name = "id") String id, @RequestBody Feedback feedback){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "updateFeedbackById", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (feedback == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "updateFeedbackById", HttpStatus.BAD_REQUEST, "Feedback from request is invalid", null);
            logService.save(log);
            return new ResponseEntity<>(feedback.getId(), HttpStatus.BAD_REQUEST);
        }

        Feedback oldFeedback = null;
        oldFeedback = feedbackService.findFeedbackById(UUID.fromString(id));

        if (oldFeedback == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "updateFeedbackById", HttpStatus.NOT_FOUND, "Feedback with id\'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (oldFeedback.equals(feedback)){
            LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "updateFeedbackById", HttpStatus.NOT_MODIFIED, "Feedback with id\'" + id + "\' not modified", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        feedbackService.updateFeedback(feedback);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "updateFeedbackById", HttpStatus.OK, "Feedback with id \'" + feedback.getId() + "\' modified", null);
        logService.save(log);
        return new ResponseEntity<>(feedback.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/feedback/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFeedbackById(@PathVariable(name="id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "deleteFeedbackById", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Feedback feedback = null;
        feedback = feedbackService.findFeedbackById(UUID.fromString(id));

        if (feedback == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "deleteFeedbackById", HttpStatus.NOT_FOUND, "Feedback with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        feedbackService.deleteFeedback(feedback);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "deleteFeedbackById", HttpStatus.OK, "Feedback with id \'" + id + "\' deleted", null);
        logService.save(log);
        return new ResponseEntity<>(feedback.getId(), HttpStatus.OK);

    }
}
