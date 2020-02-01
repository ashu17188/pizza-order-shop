package org.innovect.assignment.controller;

import java.util.List;

import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.service.IngredientInventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Api(value = "This controller provides all ingredient fetching, addition, updation functionalities from inventory.")
public class IngredientContoller {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private IngredientInventory ingredientInventory;

	@ApiOperation(value = "Api used to get All Ingredients.", response = List.class)
	@GetMapping("/ingredients")
	public List<AdditionalStuffInfoDTO> getAllIngredients() {
		return ingredientInventory.getAllIngredient();
	}

	@ApiOperation(value = "Api used to get Ingredient by ingredient name.", response = AdditionalStuffInfoDTO.class)
	@GetMapping("/ingredients/{name}")
	public AdditionalStuffInfoDTO getIngredientById(@PathVariable(value = "name") String name) {
		return ingredientInventory.getIngredientById(name);
	}

	@ApiOperation(value = "Api used to save Ingredient.", response = AdditionalStuffInfoDTO.class)
	@PostMapping("/ingredients")
	public AdditionalStuffInfoDTO saveIngredient(@RequestBody AdditionalStuffInfoDTO additionalStuffInfoDTO) {
		return ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO);
	}

	@ApiOperation(value = "Api used to Ingredient in repository.", response = AdditionalStuffInfoDTO.class)
	@PutMapping("/ingredients/{name}")
	public AdditionalStuffInfoDTO updateIngredient(@RequestBody AdditionalStuffInfoDTO additionalStuffInfoDTO) {
		return ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO);
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
		return response;
	}
}
