package org.innovect.assignment.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.model.AdditionalStuffInfo;
import org.innovect.assignment.repository.AdditionalStuffRepository;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class IngredientService implements IngredientInventory {

	@Autowired
	private AdditionalStuffRepository additionalStuffRepository;

	@Override
	public List<AdditionalStuffInfoDTO> getAllIngredient() {
		List<AdditionalStuffInfoDTO> stuffList = new Gson().fromJson(
				new Gson().toJson(additionalStuffRepository.findAll()),
				new TypeToken<ArrayList<AdditionalStuffInfoDTO>>() {
				}.getType());
		return stuffList;
	}

	@Override
	public AdditionalStuffInfoDTO getIngredientById(String ingredientName) {
		return new Gson().fromJson(
				new Gson().toJson(additionalStuffRepository.findById(ingredientName), AdditionalStuffInfo.class),
				AdditionalStuffInfoDTO.class);
	}

	@Override
	public AdditionalStuffInfoDTO saveAndUpdateIngredient(AdditionalStuffInfoDTO additionalStuffInfoDTO) {
		AdditionalStuffInfo objFromDB = additionalStuffRepository.save(new Gson().fromJson(
				new Gson().toJson(additionalStuffInfoDTO, AdditionalStuffInfoDTO.class), AdditionalStuffInfo.class));
		return new Gson().fromJson(new Gson().toJson(objFromDB, AdditionalStuffInfo.class),
				AdditionalStuffInfoDTO.class);
	}

	@Override
	@Transactional
	public String saveAndUpdateIngredientBatch(List<AdditionalStuffInfoDTO> additionalStuffInfoDTOList) {
		if (additionalStuffInfoDTOList.size() == 0) {
			throw new IllegalArgumentException("Stuff List can not be empty.");
		}
		List<AdditionalStuffInfo> additionalStuffInfoList = new ArrayList<>();
		for (AdditionalStuffInfoDTO additionalStuffInfoDTO : additionalStuffInfoDTOList) {
			AdditionalStuffInfo stuffObjFrmDB = additionalStuffRepository
					.findByStuffName(additionalStuffInfoDTO.getStuffName());
			if (!StringUtils.isEmpty(stuffObjFrmDB)) {
				stuffObjFrmDB.setPrice(additionalStuffInfoDTO.getPrice());
				stuffObjFrmDB.setStockQuantity(additionalStuffInfoDTO.getStockQuantity());
				stuffObjFrmDB.setStuffCategory(additionalStuffInfoDTO.getStuffCategory());
				additionalStuffInfoList.add(stuffObjFrmDB);
			} else {
				AdditionalStuffInfo additionalStuffInfo = new Gson().fromJson(new Gson().toJson(additionalStuffInfoDTO),
						AdditionalStuffInfo.class);
				additionalStuffInfoList.add(additionalStuffInfo);
			}
		}

		additionalStuffRepository.saveAll(additionalStuffInfoList);
		return PizzaShopConstants.SUCCESSFUL_OPERATION;
	}

}
