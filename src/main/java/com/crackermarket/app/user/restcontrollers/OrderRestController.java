package com.crackermarket.app.user.restcontrollers;

import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.StackTraceToStringConverter;
import com.crackermarket.app.user.entities.Order;
import com.crackermarket.app.user.entities.User;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.user.services.OrderService;
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
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private LogEntityService logService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/users/{id}/order/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewOrder(@PathVariable(name = "id") String id, @RequestBody Order order){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "createNewOrder", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = null;
        user = userService.findUserById(UUID.fromString(id));

        if (user == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "createNewOrder", HttpStatus.NOT_FOUND, "User with id\'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (order == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "createNewOrder", HttpStatus.BAD_REQUEST, "Order not created", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        order.setUser(user);
        orderService.saveOrder(order);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "createNewOrder", HttpStatus.CREATED, "Order with id \'" + order.getId() + "\' created", null);
        logService.save(log);
        return new ResponseEntity<>(order.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/orders/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllOrders(){

        List<Order> orders = null;
        orders = orderService.findAllOrders();

        if (orders == null || orders.isEmpty()){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "getAllOrders", HttpStatus.NO_CONTENT, "Orders not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UUID> ordersId = new ArrayList<>();
        for (Order order : orders)
            ordersId.add(order.getId());

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "getAllOrders", HttpStatus.FOUND, "Orders found", null);
        logService.save(log);
        return new ResponseEntity<>(ordersId, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/users/{id}/orders/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserOrders(@PathVariable(name="id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "getUserOrders", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = null;
        user = userService.findUserById(UUID.fromString(id));

        if (user == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "getUserOrders", HttpStatus.NOT_FOUND, "User with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Order> orders = null;
        orders = orderService.findUserOrders(UUID.fromString(id));

        if (orders == null || orders.isEmpty()){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "getUserOrders", HttpStatus.NOT_FOUND, "Orders for user with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UUID> ordersId = new ArrayList<>();
        for (Order order : orders)
            ordersId.add(order.getId());

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "getUserOrders", HttpStatus.NOT_FOUND, "Orders for user with id \'" + id + "\' found", null);
        logService.save(log);
        return new ResponseEntity<>(ordersId, HttpStatus.FOUND);

    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findOrderById(@PathVariable(name="id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findOrderById", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = null;
        order = orderService.findOrderById(UUID.fromString(id));

        if (order == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "findOrderById", HttpStatus.NOT_FOUND, "Order with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "findOrderById", HttpStatus.NOT_FOUND, "Order with id \'" + id + "\' found", null);
        logService.save(log);
        return new ResponseEntity<>(order.getId(), HttpStatus.FOUND);

    }

    @RequestMapping(value = "/order/{id}/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOrderById(@RequestBody Order updatedOrder, @PathVariable(name="id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "updateOrderById", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = null;
        order = orderService.findOrderById(UUID.fromString(id));

        if (order == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "updateOrderById", HttpStatus.NOT_FOUND, "Order with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedOrder == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "updateOrderById", HttpStatus.BAD_REQUEST, "Order from request is invalid", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (order.equals(updatedOrder)){
            LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "updateOrderById", HttpStatus.NOT_MODIFIED, "Order with id \'" + id + "\' not modified", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        orderService.updateOrder(updatedOrder);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "updateOrderById", HttpStatus.OK, "Order with id \'" + id + "\' modified", null);
        logService.save(log);
        return new ResponseEntity<>(updatedOrder.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteOrderById(@PathVariable(name="id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "deleteOrderById", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = null;
        order = orderService.findOrderById(UUID.fromString(id));

        if (order == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "deleteOrderById", HttpStatus.NOT_FOUND, "Order with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        orderService.deleteOrder(order);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "deleteOrderById", HttpStatus.OK, "Order with id \'" + id + "\' deleted", null);
        logService.save(log);
        return new ResponseEntity<>(order.getId(), HttpStatus.OK);

    }
}
