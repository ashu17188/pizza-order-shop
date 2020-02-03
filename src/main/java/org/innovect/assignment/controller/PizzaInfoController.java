package org.innovect.assignment.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.hateoas.event.ResourceCreatedEvent;
import org.innovect.assignment.service.PizzaInventory;
import org.innovect.assignment.utils.RestPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@EnableHypermediaSupport(type = HypermediaType.HAL)
@Api(value = "This controller provides all pizza fetching, addition, updation functionalities from inventory.")
public class PizzaInfoController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private PizzaInventory pizzaInventory;

	@ApiOperation(value = "Api used to get Pizza information only.", notes = "Returns Pizzas available in inventory.")
	@GetMapping(value = "/pizzas", produces = { "application/hal+json" })
	public Resources<PizzaInfoDTO> getAllPizza() {
		List<PizzaInfoDTO> pizzaList = pizzaInventory.getAllPizza();
		for (final PizzaInfoDTO pizzaDTO : pizzaList) {
			final Link selfLink = linkTo(methodOn(getClass()).getPizzaInventoryById(pizzaDTO.getPizzaInfoId()))
					.withSelfRel();
			final Link addLink = linkTo(methodOn(getClass()).addPizza(pizzaDTO, null)).withRel("linkRels/pizzas/add");
			final Link updateLink = linkTo(methodOn(getClass()).updatePizzaInfoInventory(pizzaDTO))
					.withRel("linkRels/pizzas/update");
			final Link deleteLink = linkTo(methodOn(getClass()).deletePizzaInfoInventory(pizzaDTO.getPizzaInfoId()))
					.withRel("linkRels/pizzas/delete");
			pizzaDTO.add(selfLink);
			pizzaDTO.add(addLink);
			pizzaDTO.add(updateLink);
			pizzaDTO.add(deleteLink);
		}

		Resources<PizzaInfoDTO> result = new Resources<>(pizzaList);
		log.info("{} count of pizza fetched successfully.", pizzaList.size());
		return result;
	}

	@ApiOperation(value = "Fetch Pizza present in inventory", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Information of Pizza updated."),
			@ApiResponse(code = 500, message = "Specifies system generated error for not able to add pizza.") })
	@GetMapping("/pizzas/{pizzaId}")
	public PizzaInfoDTO getPizzaInventoryById(@PathVariable Integer pizzaId) {
		RestPreconditions.check(pizzaId, null, "Pizza id can not be empty.");
		PizzaInfoDTO pizzaDTO = pizzaInventory.getPizzaById(pizzaId);
		RestPreconditions.check(pizzaDTO, null, null);
		log.info("Pizza object fetched successfully:{}", pizzaDTO.toString());
		return pizzaDTO;
	}

	@ApiOperation(value = "Add Pizza to inventory", response = PizzaInfoDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Information of Pizza saved."),
			@ApiResponse(code = 500, message = "Specifies system generated error for not able to add pizza.") })
	@PostMapping("/pizzas")
	@ResponseStatus(HttpStatus.CREATED)
	public PizzaInfoDTO addPizza(@RequestBody @Valid PizzaInfoDTO pizzaInfoDTO, final HttpServletResponse response) {
		Preconditions.checkNotNull(pizzaInfoDTO);
		PizzaInfoDTO savedObj = pizzaInventory.saveAndUpdatePizza(pizzaInfoDTO);
		final long idOfCreatedResource = savedObj.getPizzaInfoId();
		eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, idOfCreatedResource));
		log.info("New Pizza with id= {} created successfully.", savedObj.getPizzaInfoId());
		return savedObj;
	}

	@ApiOperation(value = "Update Pizza present in inventory", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Information of Pizza updated."),
			@ApiResponse(code = 500, message = "Specifies system generated error for not able to add pizza.") })
	@PutMapping("/pizzas")
	public PizzaInfoDTO updatePizzaInfoInventory(@RequestBody @Valid PizzaInfoDTO pizzaInfoDTO) {
		RestPreconditions.check(pizzaInfoDTO, null, "resouce can not be null.");
		return pizzaInventory.saveAndUpdatePizza(pizzaInfoDTO);
	}

	@ApiOperation(value = "Update Pizza present in inventory", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Information of Pizza updated."),
			@ApiResponse(code = 500, message = "Specifies system generated error for not able to add pizza.") })
	@DeleteMapping("/pizzas/{pizzaId}")
	public ResponseEntity<?> deletePizzaInfoInventory(@PathVariable Integer pizzaId) {
		RestPreconditions.check(pizzaId, null, "pizza id can not be null.");
		pizzaInventory.deletePizza(pizzaId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Add or update list of pizza in batch.", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "New Pizza additional successful."),
			@ApiResponse(code = 500, message = "Specifies system generated error.") })
	@PostMapping("/pizzas/batch")
	public String addAndUpdatePizzaBatch(@RequestBody @Valid List<PizzaInfoDTO> pizzaInfoDTOList) {
		RestPreconditions.check(pizzaInfoDTOList, null, "pizza list can not be null");
		return pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoDTOList);
	}
}
