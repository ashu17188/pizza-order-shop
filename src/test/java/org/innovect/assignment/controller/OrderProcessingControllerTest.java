package org.innovect.assignment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.innovect.assignment.data.NormalOrderData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

/**
 * This class contains basic test cases. For detailed test scenarios and
 * business rules validation, please visits PizzaFactoryServiceTest.java
 * 
 * @author Ashutosh Shukla
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderProcessingControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private OrderProcessingController orderProcessingController;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.orderProcessingController).build();
	}

	@Test
	public void getAvailableUriTest() throws Exception {
		mockMvc.perform(get("/api/orders/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent())
				.andDo(print()).andReturn();
	}
	
	@Test
	public void getAvailableOptionForPizzaTest() throws Exception {
		mockMvc.perform(get("/api/orders/dashboard").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

	@Test
	public void verifyOrderTest() throws Exception {
		String payload = new Gson().toJson(NormalOrderData.createSubmitOrderPostDTOObject());
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/orders/verify").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void submitNormalOrderTest() throws Exception {
		String payload = new Gson().toJson(NormalOrderData.createSubmitOrderPostDTOObject());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/submit").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}
	
	@Test(expected = RuntimeException.class)
	public void submitNullOrderTest() throws Exception {
		String payload = null;
		mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/submit").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}
}
