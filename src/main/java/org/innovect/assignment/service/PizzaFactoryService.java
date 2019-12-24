package org.innovect.assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.AdditionalStuffInfo;
import org.innovect.assignment.model.OrderPizza;
import org.innovect.assignment.model.OrderSides;
import org.innovect.assignment.model.PizzaInfo;
import org.innovect.assignment.repository.AdditionalStuffRepository;
import org.innovect.assignment.repository.PizzaInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Ashutosh Shukla This class provides various methods for handling
 *         operations for Pizza management software.
 */

@Service
public class PizzaFactoryService implements PizzaFactory {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PizzaInfoRepository pizzaInfoRepository;

	@Autowired
	private AdditionalStuffRepository additionalStuffRepository;

	/**
	 * Method returns required information needed by Customer to its
	 * preferences.Customers can select one or more Pizzas from the menu and then
	 * customize them with available options.
	 * @return Object containing List of available pizza and other additional stuff.
	 */
	@Override
	public CustomerDashboardInfoDTO getPizzaAndExtraInfo() {
		CustomerDashboardInfoDTO customerDashboardInfoDTO = new CustomerDashboardInfoDTO();
		List<PizzaInfo> pizzaInfoList = pizzaInfoRepository.findAll();
		List<AdditionalStuffInfo> additionalStuffList = additionalStuffRepository.findAll();

		customerDashboardInfoDTO.setPizzaInfoDTOList(
				new Gson().fromJson(new Gson().toJson(pizzaInfoList), new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType()));
		customerDashboardInfoDTO.setAdditionalStuffDTOList(new Gson().fromJson(new Gson().toJson(additionalStuffList),
				new TypeToken<ArrayList<AdditionalStuffInfoDTO>>() {
				}.getType()));
		log.info("Dashboard information for customer:{}",customerDashboardInfoDTO.toString());
		return customerDashboardInfoDTO;
	}

	public boolean verifyOrder(SubmitOrderPostDTO submitOrderPostDTO) {
		List<OrderPizza> orderPizzaList = new Gson().fromJson(new Gson().toJson(submitOrderPostDTO.getOrderPizzaDTOList()),new TypeToken<ArrayList<OrderPizza>>() {
		}.getType());
		List<OrderSides> orderSidesList =new Gson().fromJson(new Gson().toJson(submitOrderPostDTO.getSideOrderList()),new TypeToken<ArrayList<OrderPizza>>() {
		}.getType());
		
		return false;
	}
	
}
