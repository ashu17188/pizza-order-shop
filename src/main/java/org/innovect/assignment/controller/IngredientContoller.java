package org.innovect.assignment.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Api(value = "This controller provides all ingredient fetching, addition, updation functionalities from inventory.")
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
			final Link addLink = linkTo(methodOn(getClass()).saveIngredient(ingredientDTO,null))
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
	public AdditionalStuffInfoDTO getIngredientById(@PathVariable(value = "name") String name) {
		RestPreconditions.check(name, null, null);
		AdditionalStuffInfoDTO additionalStuffInfoDTO = ingredientInventory.getIngredientById(name);
		RestPreconditions.check(additionalStuffInfoDTO, null, null);
		log.info("Ingredient fetched successfully. {}", additionalStuffInfoDTO.toString());
		return additionalStuffInfoDTO;
	}

	@ApiOperation(value = "Api used to save Ingredient.", response = AdditionalStuffInfoDTO.class)
	@PostMapping("/ingredients")
	@ResponseStatus(HttpStatus.CREATED)
	public AdditionalStuffInfoDTO saveIngredient(@RequestBody AdditionalStuffInfoDTO additionalStuffInfoDTO,
			final HttpServletResponse response) {
		Preconditions.checkNotNull(additionalStuffInfoDTO);
		AdditionalStuffInfoDTO savedObj = ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO);
		final String idOfCreatedResource = savedObj.getStuffName();
		eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, idOfCreatedResource));
		log.info("New Ingredient with id= {} created successfully.", savedObj.getStuffName());
		return savedObj;
	}

	@ApiOperation(value = "Api used to Ingredient in repository.", response = AdditionalStuffInfoDTO.class)
	@PutMapping("/ingredients")
	public AdditionalStuffInfoDTO updateIngredient(@RequestBody AdditionalStuffInfoDTO additionalStuffInfoDTO) {
		Preconditions.checkNotNull(additionalStuffInfoDTO);
		AdditionalStuffInfoDTO updatedObj = ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO);
		log.info("Ingredient with id ={} updated successfully.", updatedObj.getStuffName());
		return updatedObj;

	}

	@ApiOperation(value = "Api used to Ingredient in repository.", response = AdditionalStuffInfoDTO.class)
	@DeleteMapping("/ingredients/{name}")
	public ResponseEntity<?> deleteIngredient(@PathVariable String name) {
		ingredientInventory.deleteIngredient(name);
		log.info("Ingredient with name :{} deleted successfully.",name);
		return new ResponseEntity<>(HttpStatus.OK);	
	}

	@ApiOperation(value = "Create additional stuff for a Pizza order", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful addition of additional stuff like."),
			@ApiResponse(code = 400, message = "Specifies system generated error.") })
	@PostMapping("/ingredients/batch")
	public String saveAndUpdateIngredientBatch(@RequestBody List<AdditionalStuffInfoDTO> additionalStuffInfoDTOList) {
		String response = "";
		try {
			response = ingredientInventory.saveAndUpdateIngredientBatch(additionalStuffInfoDTOList);
		} catch (RuntimeException e) {
			log.error("Error occured:{}", e.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		log.info("Batch save and update of ingredients completed successfully.");
		return response;
	}
}
