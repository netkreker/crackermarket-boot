package com.crackermarket.app.shop.restcontrollers;

package com.crackermarket.app.shop.restcontrollers;

import com.crackermarket.app.shop.entities.Category;
import com.crackermarket.app.shop.services.CategoryService;
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
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryRestControllerTest {

    @Autowired
    public MockMvc mockMvc;                     // MVC

    @Mock
    private CategoryService categoryService;            // Mock service with dummy methods

    @Autowired
    private CategoryService databaseService;        // Service with db connection

    private ObjectMapper mapper;                // For converting from object to JSON and vice versa

    private List<Category> testDataCategory;                   // Test array of users


    @BeforeEach
    void setUp() {

        mapper = new ObjectMapper();

        testDataCategory = new ArrayList<>();

        // Creating data
        Category mainCategory = new Category();
        mainCategory.setName("Main");

        Category childCategory = new Category();
        childCategory.setName("Child");


        databaseService.save(mainCategory);
        databaseService.save(childCategory);

        testDataCategory.add(mainCategory);
        testDataCategory.add(childCategory);
        // Creating behaviors to dummy methods of mock
        Mockito.when(categoryService.findAll()).thenReturn(testDataCategory);
        Mockito.when(categoryService.findById(Mockito.any())).thenReturn(testDataCategory.get(0));

    }


    @AfterEach
    void tearDown(){

        // Deleting data
        List<Category> testCategories = categoryService.findAll();

        for (Category current: testCategories)
            if (databaseService.findById(current.getId().toString()) != null)
                databaseService.delete(current.getId().toString());
    }

    @Test
    void createCategory() throws Exception {

        // Test data for creating user
        Category testCategory = new Category();

        testCategory.setName("TestCategory");


        // Converting data to JSON
        String testJson = mapper.writeValueAsString(testCategory);

        // Test for status by sending JSON to URL
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/category/new")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(testJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Response object (for get a result in JSON format)
        MvcResult response = resultActions.andReturn();

        // Convert JSON string from response to object
        Category resultCategory = mapper.readValue(response.getResponse().getContentAsString(), Category.class);

        // Add created user to array (for correct deleting in tearDown()
        testDataCategory.add(databaseService.findByName("TestCategory").get(0));

        // Test for content
        Assert.assertEquals(resultCategory.getName(), testCategory.getName());
    }


    @Test
    void showAllUsers() throws Exception {

        // Get test users data
        List<Category> result = categoryService.findAll();

        // Test for status by sending JSON to URL
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound());

        // Response object (for get a result in JSON format)
        MvcResult response = resultActions.andReturn();

        // Get JSON string and parsing it to List
        List<Category> categories = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<List<Category>>(){});

        // Check for containing
        List<UUID> allCategoriesId = new ArrayList<>();

        for (Category current : categories)
            allCategoriesId.add(current.getId());

        Assert.assertTrue(allCategoriesId.contains(result.get(0).getId()) && allCategoriesId.contains(result.get(1).getId()));

    }

    @Test
    void getUserById() throws Exception {

        Category testCategory = categoryService.findById(UUID.randomUUID().toString());

        String testJson = mapper.writeValueAsString(testCategory);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/category/" + testCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().json(testJson));
    }

    @Test
    void updateUser() throws Exception {
        Category testCategory = categoryService.findById(UUID.randomUUID().toString());

        testCategory.setName("NewCategory");

        String testJson = mapper.writeValueAsString(testCategory);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/category/update/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(testJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(testJson));
    }
}
