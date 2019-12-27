package org.innovect.assignment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.data.AdditionalStuffInfoListData;
import org.innovect.assignment.data.NormalOrderData;
import org.innovect.assignment.data.PizzaInfoListData;
import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.Order;
import org.innovect.assignment.model.OrderPizza;
import org.innovect.assignment.model.OrderSides;
import org.innovect.assignment.service.PizzaFactory;
import org.innovect.assignment.utils.PizzaShopUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This class contains only unit test cases. For detailed test scenarios and
 * business rules validation, please visits PizzaFactoryServiceTest.java and
 * PizzaFactoryServiceUnitTest.java
 * 
 * @author Ashutosh Shukla
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PizzaInfoControllerUnitTest {

	private MockMvc mockMvc;

	@Autowired
	private PizzaInfoController pizzaInfoController;

	@MockBean
	private PizzaFactory pizzaFactory;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.pizzaInfoController).build();
	}

	@Test
	public void getAvailableOptionForPizzaTest() throws Exception {
		List<PizzaInfoDTO> pizzaInfoDTOList = new Gson().fromJson(
				new Gson().toJson(PizzaInfoListData.createPizzaInfoList()), new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType());
		List<AdditionalStuffInfoDTO> stuffDTOList = new Gson().fromJson(
				new Gson().toJson(AdditionalStuffInfoListData.createAdditionalStuffInfoList()),
				new TypeToken<ArrayList<AdditionalStuffInfoDTO>>() {
				}.getType());
		CustomerDashboardInfoDTO customerDashboardInfoDTO = new CustomerDashboardInfoDTO();
		customerDashboardInfoDTO.setAdditionalStuffDTOList(stuffDTOList);
		customerDashboardInfoDTO.setPizzaInfoDTOList(pizzaInfoDTOList);

		when(pizzaFactory.getPizzaAndExtraInfo()).thenReturn(customerDashboardInfoDTO);
		mockMvc.perform(get("/api/dashboard").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

	@Test
	public void verifyOrderTest() throws Exception {
		SubmitOrderPostDTO submitOrderPostDTO = NormalOrderData.createSubmitOrderPostDTOObject();
		List<OrderPizza> orderPizzaList = new Gson().fromJson(
				new Gson().toJson(submitOrderPostDTO.getOrderPizzaDTOList()), new TypeToken<ArrayList<OrderPizza>>() {
				}.getType());
		List<OrderSides> orderSidesList = new Gson().fromJson(new Gson().toJson(submitOrderPostDTO.getSideOrderList()),
				new TypeToken<ArrayList<OrderSides>>() {
				}.getType());
		Order order = new Order();

		order.setCustName(submitOrderPostDTO.getCustName());
		order.setContactNumber(submitOrderPostDTO.getContactNumber());
		order.setDeliveryAddress(submitOrderPostDTO.getDeliveryAddress());
		order.setPizzaList(orderPizzaList);
		order.setSideOrderList(orderSidesList);
		when(pizzaFactory.verifyOrder(submitOrderPostDTO)).thenReturn(order);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/verifyOrder").content(new Gson().toJson(submitOrderPostDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void submitNormalOrderTest() throws Exception {
		SubmitOrderPostDTO submitOrderPostDTO = NormalOrderData.createSubmitOrderPostDTOObject();
		List<OrderPizza> orderPizzaList = new Gson().fromJson(
				new Gson().toJson(submitOrderPostDTO.getOrderPizzaDTOList()), new TypeToken<ArrayList<OrderPizza>>() {
				}.getType());
		List<OrderSides> orderSidesList = new Gson().fromJson(new Gson().toJson(submitOrderPostDTO.getSideOrderList()),
				new TypeToken<ArrayList<OrderSides>>() {
				}.getType());
		Order order = new Order();

		order.setCustName(submitOrderPostDTO.getCustName());
		order.setContactNumber(submitOrderPostDTO.getContactNumber());
		order.setDeliveryAddress(submitOrderPostDTO.getDeliveryAddress());
		order.setPizzaList(orderPizzaList);
		order.setSideOrderList(orderSidesList);

		when(pizzaFactory.submitOrder(submitOrderPostDTO)).thenReturn(PizzaShopUtils.SUCCESSFUL_ORDER_SUBMIT_OPERATION);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/submitOrder").content(new Gson().toJson(submitOrderPostDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void addPizzaInfoTest() throws Exception {

		List<PizzaInfoDTO> pizzaInfoDTOList = new Gson().fromJson(
				new Gson().toJson(PizzaInfoListData.createPizzaInfoList()), new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType());
		when(pizzaFactory.addPizzaInfoInventory(pizzaInfoDTOList)).thenReturn(PizzaShopUtils.SUCCESSFUL_OPERATION);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/addUpdatePizza").content(new Gson().toJson(pizzaInfoDTOList))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void addPizzaInfoErrorTest() throws Exception {

		List<PizzaInfoDTO> pizzaInfoDTOList = null;
		when(pizzaFactory.addPizzaInfoInventory(pizzaInfoDTOList)).thenThrow(
				new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Pizza list can not be empty."));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/addUpdatePizza").content(new Gson().toJson(pizzaInfoDTOList))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	public void addPizzaInfoEmptyErrorTest() throws Exception {

		List<PizzaInfoDTO> pizzaInfoDTOList = new ArrayList<>();
		when(pizzaFactory.addPizzaInfoInventory(pizzaInfoDTOList)).thenThrow(
				new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Pizza list can not be empty."));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/addUpdatePizza").content(new Gson().toJson(pizzaInfoDTOList))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andDo(print());
	}

	@Test
	public void addAdditionalStuffTest() throws Exception {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new Gson().fromJson(
				new Gson().toJson(AdditionalStuffInfoListData.createAdditionalStuffInfoList()),
				new TypeToken<ArrayList<AdditionalStuffInfoDTO>>() {
				}.getType());
		when(pizzaFactory.addAdditionalStuffInventory(additionalStuffDTOList))
				.thenReturn(PizzaShopUtils.SUCCESSFUL_OPERATION);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/addUpdateStuff").content(new Gson().toJson(additionalStuffDTOList))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void getAllPizzInfoListTest() throws Exception {
		List<PizzaInfoDTO> pizzaInfoDTOList = new Gson().fromJson(
				new Gson().toJson(PizzaInfoListData.createPizzaInfoList()), new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType());
		when(pizzaFactory.getAllPizzInfoList()).thenReturn(pizzaInfoDTOList);
		
		mockMvc.perform(get("/api/allPizzaInfo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

	@Test
	public void getAllStuffInfoTest() throws Exception {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new Gson().fromJson(
				new Gson().toJson(AdditionalStuffInfoListData.createAdditionalStuffInfoList()),
				new TypeToken<ArrayList<AdditionalStuffInfoDTO>>() {
				}.getType());

		when(pizzaFactory.getAllStuffInfo()).thenReturn(additionalStuffDTOList);
		
		mockMvc.perform(get("/api/allStuffInfo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}
}
