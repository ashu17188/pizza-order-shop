package org.innovect.assignment.controller;

import java.util.List;

import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.service.PizzaFactory;
import org.innovect.assignment.utils.PizzaShopUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Api(value = "This controller provides all pizza ordering, addition, updation, verify order Api.")
public class PizzaInfoController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PizzaFactory pizzaFactory;

	@ApiOperation(value = "Provides information of Pizza and related items.", response = List.class)
	@GetMapping("/dashboard")
	public CustomerDashboardInfoDTO getPizzaAndExtraInfo() {
		return pizzaFactory.getPizzaAndExtraInfo();
	}

	@ApiOperation(value = "Validates pizza order", response = String.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Successfully verified order."),
			  @ApiResponse(code = 400, message = "Specifies system generated error.") })
	@PostMapping("/verify")
	public String verifyOrder(@RequestBody SubmitOrderPostDTO submitOrderPostDTO) {
		try {
			pizzaFactory.verifyOrder(submitOrderPostDTO);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
		}
		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	@ApiOperation(value = "Submit pizza order of end user.", response = String.class)
	@ApiResponses(value = {
			 @ApiResponse(code = 200, message = "Successfull order submit."),
			 @ApiResponse(code = 400, message = "Specifies system generated error.") })
	@PostMapping("/submit")
	public String submitOrder(@RequestBody SubmitOrderPostDTO submitOrderPostDTO) {
		String response = "";
		try {
			response = pizzaFactory.submitOrder(submitOrderPostDTO);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
		}
		return response;
	}

	@ApiOperation(value = "Add new Pizza information.", response = String.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "New Pizza additional successful."),
			@ApiResponse(code = 500, message = "Specifies system generated error.") })
	@PostMapping("/pizza")
	public String addPizzaInfoInventory(@RequestBody List<PizzaInfoDTO> pizzaInfoDTOList) {
		String response = "";
		try {
			response = pizzaFactory.addPizzaInfoInventory(pizzaInfoDTOList);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return response;
	}

	@ApiOperation(value = "Update Pizza information to inventory", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Information of Pizza updated."),
			@ApiResponse(code = 500, message = "Specifies system generated error for not able to add pizza.") })
	@PutMapping("/pizza")
	public String updatePizzaInfoInventory(@RequestBody List<PizzaInfoDTO> pizzaInfoDTOList) {
		String response = "";
		try {
			response = pizzaFactory.addPizzaInfoInventory(pizzaInfoDTOList);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return response;
	}

	@ApiOperation(value = "Create additional stuff for a Pizza order", notes = "Returns SUCCESSFUL response.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful addition of additional stuff like."),
			@ApiResponse(code = 400, message = "Specifies system generated error.") })
	@PostMapping("/stuff")
	public String addAdditionalStuffInventory(@RequestBody List<AdditionalStuffInfoDTO> additionalStuffInfoDTOList) {
		String response = "";
		try {
			response = pizzaFactory.addAdditionalStuffInventory(additionalStuffInfoDTOList);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return response;
	}

	@ApiOperation(value = "Create additional stuff for a Pizza order", notes = "Returns SUCCESSFUL response.")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Specifies system generated error.") })
	@PutMapping("/stuff")
	public String updateAdditionalStuffInventory(@RequestBody List<AdditionalStuffInfoDTO> additionalStuffInfoDTOList) {
		String response = "";
		try {
			response = pizzaFactory.addAdditionalStuffInventory(additionalStuffInfoDTOList);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return response;
	}

	@ApiOperation(value = "Api used to get Pizza information only.", notes = "Returns Pizzas available in inventory.")
	@GetMapping("/pizzas")
	public List<PizzaInfoDTO> getAllPizzInfoList() {
		return pizzaFactory.getAllPizzInfoList();
	}

	@ApiOperation(value = "Api used to get Additional Stuff information only.", notes = "Returns Additional Stuffs available in inventory.")
	@GetMapping("/stuffs")
	public List<AdditionalStuffInfoDTO> getAllStuffInfo() {
		return pizzaFactory.getAllStuffInfo();
	}
}
