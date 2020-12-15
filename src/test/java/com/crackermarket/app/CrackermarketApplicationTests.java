package com.crackermarket.app;

import com.crackermarket.app.shop.entities.Category;
import com.crackermarket.app.shop.repository.CategoryDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrackermarketApplicationTests {

	@Autowired
	public MockMvc mockMvc;
	@Autowired
	private CategoryDAO categoryDAO;
	@Test
	public void loadEmptyCategory() throws Exception{
		mockMvc
				.perform(get("/category"))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	public void loadAllCategories() throws Exception {
		saveNewCategory();
		mockMvc
				.perform(get("/category"))
				.andDo(print())
				.andExpect(status().isFound());
	}

	public void saveNewCategory() throws Exception {
		Category category = new Category();
		category.setName("Main category");
		categoryDAO.save(category);

	}
}
