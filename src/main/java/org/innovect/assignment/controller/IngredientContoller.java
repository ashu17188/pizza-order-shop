package org.innovect.assignment.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.hateoas.event.ResourceCreatedEvent;
import org.innovect.assignment.service.IngredientInventory;
import org.innovect.assignment.utils.RestPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Api(value = "Ingredient operations", description = "This controller provides operations regarding fetching, addition, updation of Ingredients (i.e toppings, sides, crust etc) from inventory.")
public class IngredientContoller {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private IngredientInventory ingredientInventory;

	@ApiOperation(value = "Api used to get All Ingredients.", response = List.class)
	@GetMapping(value = "/ingredients", produces = { "application/hal+json" })
	public Resources<AdditionalStuffInfoDTO> getAllIngredients() {

		List<AdditionalStuffInfoDTO> ingredientList = ingredientInventory.getAllIngredient();

		for (final AdditionalStuffInfoDTO ingredientDTO : ingredientList) {
			final Link selfLink = linkTo(methodOn(getClass()).getIngredientById(ingredientDTO.getStuffName()))
					.withSelfRel();
			final Link addLink = linkTo(methodOn(getClass()).saveIngredient(ingredientDTO, null))
					.withRel("linkRels/ingredient/add");
			final Link updateLink = linkTo(methodOn(getClass()).updateIngredient(ingredientDTO))
					.withRel("linkRels/ingredient/update");
			final Link deleteLink = linkTo(methodOn(getClass()).deleteIngredient(ingredientDTO.getStuffName()))
					.withRel("linkRels/ingredient/delete");
			ingredientDTO.add(selfLink);
			ingredientDTO.add(addLink);
			ingredientDTO.add(updateLink);
			ingredientDTO.add(deleteLink);
		}

		Resources<AdditionalStuffInfoDTO> result = new Resources<>(ingredientList);
		log.info("Count of ingredient in inventory = {}", ingredientList.size());
		return result;
	}

	@ApiOperation(value = "Api used to get Ingredient by ingredient name.", response = AdditionalStuffInfoDTO.class)
	@GetMapping("/ingredients/{name}")
	public AdditionalStuffInfoDTO getIngredientById(
			@ApiParam("Unique name of ingredient like Black olive, Barbeque chicken etc.") @PathVariable(value = "name") String name) {
		Preconditions.checkArgument(!StringUtils.isEmpty(name),"Ingredient name is required.");
		AdditionalStuffInfoDTO additionalStuffInfoDTO = ingredientInventory.getIngredientById(name);
		RestPreconditions.check(additionalStuffInfoDTO, null, null);
		log.info("Ingredient fetched successfully. {}", additionalStuffInfoDTO.toString());
		return additionalStuffInfoDTO;
	}

	@ApiOperation(value = "Api used to save Ingredient.", response = AdditionalStuffInfoDTO.class)
	@PostMapping("/ingredients")
	@ResponseStatus(HttpStatus.CREATED)
	public AdditionalStuffInfoDTO saveIngredient(
			@ApiParam("Ingredient containing all required information for creating new Ingredient like "
					+ "name=Barbeque chicken, category=Non-­Veg Toppings/Veg Toppings, Price in Dollar=45.0, "
					+ "Quantity=10") @RequestBody @Valid AdditionalStuffInfoDTO additionalStuffInfoDTO,
			final HttpServletResponse response) {
		Preconditions.checkNotNull(additionalStuffInfoDTO);
		AdditionalStuffInfoDTO savedObj = ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO);
		final String idOfCreatedResource = savedObj.getStuffName();
		eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, idOfCreatedResource));

		log.info("New Ingredient with id= {} created successfully.", savedObj.getStuffName());
		return savedObj;
	}

	@ApiOperation(value = "Api used to update Ingredient information in inventory.", response = AdditionalStuffInfoDTO.class)
	@PutMapping("/ingredients")
	public AdditionalStuffInfoDTO updateIngredient(
			@ApiParam("Ingredient containing all required information for creating new Ingredient like "
					+ "name=Barbeque chicken, category=Non-­Veg Toppings/Veg Toppings, Price in Dollar=45.0, "
					+ "Quantity=10") @RequestBody @Valid AdditionalStuffInfoDTO additionalStuffInfoDTO) {
		Preconditions.checkNotNull(additionalStuffInfoDTO);
		AdditionalStuffInfoDTO updatedObj = ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO);
		log.info("Ingredient with id ={} updated successfully.", updatedObj.getStuffName());
		return updatedObj;

	}

	@ApiOperation(value = "Api used to delete Ingredient in repository.", response = AdditionalStuffInfoDTO.class)
	@DeleteMapping("/ingredients/{name}")
	public ResponseEntity<?> deleteIngredient(
			@ApiParam("Unique name of ingredient like Black olive") @PathVariable String name) {
		if (StringUtils.isEmpty(name))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingredient name is required");
		ingredientInventory.deleteIngredient(name);
		log.info("Ingredient with name :{} deleted successfully.", name);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Create and update number of ingredients in batch.", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful addition of additional stuff like."),
			@ApiResponse(code = 400, message = "Specifies system generated error.") })
	@PostMapping("/ingredients/batch")
	public String saveAndUpdateIngredientBatch(
			@ApiParam("List of ingredients for save or update in inventory.") @RequestBody List<AdditionalStuffInfoDTO> additionalStuffInfoDTOList) {
		Preconditions.checkNotNull(additionalStuffInfoDTOList);
		String response = ingredientInventory.saveAndUpdateIngredientBatch(additionalStuffInfoDTOList);
		log.info("Batch save and update of ingredients completed successfully.");
		return response;
	}
}
