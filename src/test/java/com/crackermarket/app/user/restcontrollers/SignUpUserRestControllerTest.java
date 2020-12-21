package com.crackermarket.app.user.restcontrollers;

import com.crackermarket.app.user.entities.User;
import com.crackermarket.app.user.enumerations.LevelAccess;
import com.crackermarket.app.user.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SignUpUserRestControllerTest {

    @Autowired
    public MockMvc mockMvc;                     // MVC

    @Mock
    private UserService userService;            // Mock service with dummy methods

    @Autowired
    private UserService databaseService;        // Service with db connection

    private ObjectMapper mapper;                // For converting from object to JSON and vice versa

    private List<User> testDataUsers;                   // Test array of users

    @BeforeEach
    void setUp() {

        mapper = new ObjectMapper();

        testDataUsers = new ArrayList<>();

        // Creating data
        User user1 = new User();
        User user2 = new User();

        user1.setName("Stas");
        user2.setName("Evgenia");

        user1.setUserName("StasON2020");
        user2.setUserName("Salamandra236");

        user1.setEmail("stas28mes@gmail.com");
        user2.setEmail("evgenia2712@mail.ru");

        user1.setPassword("1234");
        user2.setPassword("5678");

        user1.setPhoneNumber("83463421228");
        user2.setPhoneNumber("89126173459");
        
        user1.setSurname("Gavrilov");
        user2.setSurname("Yakobenchuk");
        
        user1.setAccess(LevelAccess.CUSTOMER);
        user2.setAccess(LevelAccess.SELLER);

        databaseService.saveUser(user1);
        databaseService.saveUser(user2);

        testDataUsers.add(user1);
        testDataUsers.add(user2);

        // Creating behaviors to dummy methods of mock
        Mockito.when(userService.findAllUsers(0,0)).thenReturn(testDataUsers);
        Mockito.when(userService.findUserById(Mockito.any())).thenReturn(testDataUsers.get(0));

    }

    @AfterEach
    void tearDown(){

        // Deleting data
        List<User> testUsers = userService.findAllUsers(0,0);

        for (User current: testUsers)
            if (databaseService.findUserById(current.getId()) != null)
                databaseService.deleteUser(databaseService.findUserById(current.getId()));
    }


    @Test
    void createUser() throws Exception {

        // Test data for creating user
        User testUser = new User();

        testUser.setName("Alex");
        testUser.setSurname("Petrov");
        testUser.setPhoneNumber("83543431212");
        testUser.setUserName("PetrovSV");
        testUser.setEmail("PetrovAlex@gmail.com");
        testUser.setAccess(LevelAccess.SELLER);
        testUser.setPassword("alex123");

        // Converting data to JSON
        String testJson = mapper.writeValueAsString(testUser);

        // Test for status by sending JSON to URL
        ResultActions resultActions = mockMvc.perform(
          MockMvcRequestBuilders.post("/users/register")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(testJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Response object (for get a result in JSON format)
        MvcResult response = resultActions.andReturn();

        // Convert JSON string from response to object
        User resultUser = mapper.readValue(response.getResponse().getContentAsString(), User.class);

        // Add created user to array (for correct deleting in tearDown()
        testDataUsers.add(databaseService.findUsersByName("Alex", 0, 1).get(0));

        // Test for content
        Assert.assertTrue(resultUser.getName().equals(testUser.getName())
                && resultUser.getSurname().equals(testUser.getSurname())
                && resultUser.getEmail().equals(testUser.getEmail())
                && resultUser.getPhoneNumber().equals(testUser.getPhoneNumber())
                && resultUser.getUserName().equals(testUser.getUserName())
                && resultUser.getPassword().equals(testUser.getPassword())
                && resultUser.getAccess().equals(testUser.getAccess()));
    }

    @Test
    void showAllUsers() throws Exception {

        List<User> result = databaseService.findAllUsers(0, 3);

        // Test for status by sending JSON to URL
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/users/all/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound());

        // Response object (for get a result in JSON format)
        MvcResult response = resultActions.andReturn();

        // Get JSON string and parsing it to List
        List<User> allUsers = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<List<User>>(){});

        // Check for containing
        List<UUID> allUsersId = new ArrayList<>();

        for (User current : allUsers)
            allUsersId.add(current.getId());

        Assert.assertTrue(allUsersId.contains(result.get(0).getId()) && allUsersId.contains(result.get(1).getId()));

    }

    @Test
    void getUserById() throws Exception {

        User testUser = userService.findUserById(UUID.randomUUID());

        String testJson = mapper.writeValueAsString(testUser);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().json(testJson));
    }

    /*@Test
    void updateUser() throws Exception {
        User testUser = userService.findUserById(UUID.randomUUID());

        testUser.setUserName("ShawshankRedemption");
        testUser.setEmail("testEmail@mail.ru");

        String testJson = mapper.writeValueAsString(testUser);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/users/update/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(testJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(testJson));
    }*/

    @Test
    void deleteUser() throws Exception {

        User testUser = userService.findUserById(UUID.randomUUID());

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/delete/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}