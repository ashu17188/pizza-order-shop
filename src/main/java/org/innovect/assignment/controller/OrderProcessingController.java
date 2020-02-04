package org.innovect.assignment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.hateoas.event.ResourceCreatedEvent;
import org.innovect.assignment.service.PizzaFactory;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/orders")
@Api(value = "This controller provides all pizza ordering, addition, updation, verify order Api.")
public class OrderProcessingController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PizzaFactory pizzaFactory;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@ApiOperation(value = "Provides information of Pizza and related items.", response = List.class)
	@GetMapping("/dashboard")
	public CustomerDashboardInfoDTO getPizzaAndExtraInfo() {
		CustomerDashboardInfoDTO objFromDB = pizzaFactory.getPizzaAndExtraInfo();
		log.info("{} Pizza and {} Ingredient fetched successfully.", objFromDB.getPizzaInfoDTOList().size(),
				objFromDB.getAdditionalStuffDTOList().size());
		return objFromDB;
	}

	@ApiOperation(value = "Validates pizza order", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully verified order."),
			@ApiResponse(code = 400, message = "Specifies system generated error.") })
	@PatchMapping("/verify")
	public String verifyOrder(@RequestBody @Valid SubmitOrderPostDTO submitOrderPostDTO) {
		Preconditions.checkNotNull(submitOrderPostDTO);
		pizzaFactory.verifyOrder(submitOrderPostDTO);
		return PizzaShopConstants.SUCCESSFUL_OPERATION;
	}

	@ApiOperation(value = "Fetch order of end user using unique order id.", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfull order submit."),
			@ApiResponse(code = 500, message = "Specifies system generated error.") })
	@GetMapping("/submit")
	public SubmitOrderPostDTO getOrder(@PathVariable String orderId) {
		Preconditions.checkNotNull(orderId);
		SubmitOrderPostDTO orderObj = pizzaFactory.getOrderById(orderId);
		log.info("Order successfully fetched.", orderObj.toString());
		return orderObj;
	}

	@ApiOperation(value = "Submit pizza order of end user.", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfull order submit."),
			@ApiResponse(code = 400, message = "Specifies system generated error.") })
	@PostMapping("/submit")
	public SubmitOrderPostDTO submitOrder(@RequestBody @Valid SubmitOrderPostDTO submitOrderPostDTO,
			final HttpServletResponse response) {
		Preconditions.checkNotNull(submitOrderPostDTO);
		SubmitOrderPostDTO savedObj = pizzaFactory.submitOrder(submitOrderPostDTO);
		final String idOfCreatedResource = savedObj.getOrderId();
		eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, idOfCreatedResource));
		return savedObj;
	}

}
