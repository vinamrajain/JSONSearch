package com.mastercard.assignment;

import com.mastercard.assignment.config.ApplicationInitializer;
import com.mastercard.assignment.controller.SearchController;
import com.mastercard.assignment.service.KeywordSearchManager;
import com.mastercard.assignment.service.KeywordSearchManagerIterativeImpl;
import com.mastercard.assignment.service.KeywordSearchManagerRecursiveImpl;
import com.mastercard.assignment.view.ResponseCount;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentApplicationTests {

	@Autowired
	SearchController controller;

	@Autowired
	@Qualifier("recursive")
	KeywordSearchManager manager;

	@Autowired
	@Qualifier("jsonData")
	JSONArray jsonArray;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void contextload(){

	}

	@Test
	public void testBeanCreations() {

		assertThat(controller).isNotNull();
		assertThat(manager).isInstanceOf(KeywordSearchManagerRecursiveImpl.class);
		assertThat(manager).isNotInstanceOf(KeywordSearchManagerIterativeImpl.class);
		assertThat(jsonArray).isNotNull();

	}

	@Test
	public void testControllerReturnStatusOk() throws Exception {
		this.mockMvc.perform(get("/search/abc"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.searchTerm").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.movies").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.count").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.cast").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("$.year").doesNotExist());


	}

	@Test
	public void testControllerReturnSearchTerm() throws Exception {
		this.mockMvc.perform(get("/search/{keyword}" , "ABC"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.searchTerm").value("ABC"));
	}

}
