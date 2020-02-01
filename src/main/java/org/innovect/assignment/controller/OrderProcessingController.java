package org.innovect.assignment.controller;

import java.util.List;

import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.service.PizzaFactory;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/processing")
@Api(value = "This controller provides all pizza ordering, addition, updation, verify order Api.")
public class OrderProcessingController {

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
		return PizzaShopConstants.SUCCESSFUL_OPERATION;
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


	


	
}
