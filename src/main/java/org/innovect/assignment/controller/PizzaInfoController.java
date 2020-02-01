package org.innovect.assignment.controller;

import java.util.List;

import javax.validation.Valid;

import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.service.PizzaInventory;
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
@Api(value = "This controller provides all pizza fetching, addition, updation functionalities from inventory.")
public class PizzaInfoController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PizzaInventory pizzaInventory;

	@ApiOperation(value = "Api used to get Pizza information only.", notes = "Returns Pizzas available in inventory.")
	@GetMapping("/pizzas")
	public List<PizzaInfoDTO> getAllPizza() {
		return pizzaInventory.getAllPizza();
	}

	@ApiOperation(value = "Add Pizza to inventory", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Information of Pizza saved."),
			@ApiResponse(code = 500, message = "Specifies system generated error for not able to add pizza.") })
	@PostMapping("/pizzas")
	public PizzaInfoDTO addPizza(@RequestBody @Valid PizzaInfoDTO pizzaInfoDTO) {
		PizzaInfoDTO response = null;
		try {
			response = pizzaInventory.saveAndUpdatePizza(pizzaInfoDTO);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return response;
	}

	@ApiOperation(value = "Update Pizza present in inventory", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Information of Pizza updated."),
			@ApiResponse(code = 500, message = "Specifies system generated error for not able to add pizza.") })
	@PutMapping("/pizzas")
	public PizzaInfoDTO updatePizzaInfoInventory(@RequestBody @Valid PizzaInfoDTO pizzaInfoDTO) {
		PizzaInfoDTO response = null;
		try {
			response = pizzaInventory.saveAndUpdatePizza(pizzaInfoDTO);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return response;
	}

	@ApiOperation(value = "Add or update list of pizza in batch.", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "New Pizza additional successful."),
			@ApiResponse(code = 500, message = "Specifies system generated error.") })
	@PostMapping("/pizzas/batch")
	public String addAndUpdatePizzaBatch(@RequestBody @Valid List<PizzaInfoDTO> pizzaInfoDTOList) {
		String response = "";
		try {
			response = pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoDTOList);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		return response;
	}
}
