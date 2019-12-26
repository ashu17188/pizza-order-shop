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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(value = "This controller provides all pizza ordering, addition, updation, verify order Api.")
public class PizzaInfoController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PizzaFactory pizzaFactory;

	@GetMapping("/dashboard")
	public ResponseEntity<CustomerDashboardInfoDTO> getPizzaAndExtraInfo() {
		return new ResponseEntity<>(pizzaFactory.getPizzaAndExtraInfo(), HttpStatus.OK);
	}

	@PostMapping("/verifyOrder")
	public ResponseEntity<String> verifyOrder(@RequestBody SubmitOrderPostDTO submitOrderPostDTO) {
		try {
			pizzaFactory.verifyOrder(submitOrderPostDTO);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return new ResponseEntity<>(PizzaShopUtils.SUCCESSFUL_OPERATION, HttpStatus.OK);
	}

	@PostMapping("/submitOrder")
	public ResponseEntity<String> submitOrder(@RequestBody SubmitOrderPostDTO submitOrderPostDTO) {
		String response = "";
		try {
			response = pizzaFactory.submitOrder(submitOrderPostDTO);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/addUpdatePizza")
	public ResponseEntity<String> addPizzaInfoInventory(@RequestBody List<PizzaInfoDTO> pizzaInfoDTOList) {
		String response = "";
		try {
			response = pizzaFactory.addPizzaInfoInventory(pizzaInfoDTOList);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/addUpdateStuff")
	public ResponseEntity<String> addAdditionalStuffInventory(
			@RequestBody List<AdditionalStuffInfoDTO> additionalStuffInfoDTOList) {
		String response = "";
		try {
			response = pizzaFactory.addAdditionalStuffInventory(additionalStuffInfoDTOList);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/allPizzaInfo")
	public ResponseEntity<List<PizzaInfoDTO>> getAllPizzInfoList() {
		return new ResponseEntity<>(pizzaFactory.getAllPizzInfoList(), HttpStatus.OK);
	}

	@GetMapping("/allStuffInfo")
	public ResponseEntity<List<AdditionalStuffInfoDTO>> getAllStuffInfo() {
		return new ResponseEntity<>(pizzaFactory.getAllStuffInfo(), HttpStatus.OK);
	}
}
