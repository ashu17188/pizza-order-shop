package org.innovect.assignment.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.model.PizzaInfo;
import org.innovect.assignment.repository.PizzaInfoRepository;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class PizzaInfoService implements PizzaInventory{
	
	@Autowired
	private PizzaInfoRepository pizzaInfoRepository;

	@Override
	public List<PizzaInfoDTO> getAllPizza() {
		return new Gson().fromJson(new Gson().toJson(pizzaInfoRepository.findAll()),
				new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType());
	}
	
	@Override
	public PizzaInfoDTO getPizzaById(int id) {
		return new Gson().fromJson(new Gson().toJson(pizzaInfoRepository.findById(id)), PizzaInfoDTO.class);

	}
	
	@Override
	public PizzaInfoDTO saveAndUpdatePizza(PizzaInfoDTO pizzaInfoDTO) {
		PizzaInfo pizzaObjFromDB = pizzaInfoRepository.save(new Gson().fromJson( new Gson().toJson(pizzaInfoDTO), PizzaInfo.class));
		return new Gson().fromJson(new Gson().toJson(pizzaObjFromDB),PizzaInfoDTO.class);
	}
	
	@Override
	@Transactional
	public String addAndUpdatePizzaBatch(List<PizzaInfoDTO> pizzaInfoDTOList) {
		if (pizzaInfoDTOList.size() == 0) {
			throw new IllegalArgumentException("Pizza list can not be empty.");
		}
		List<PizzaInfo> pizzaInfoList = new ArrayList<>();
		for (PizzaInfoDTO pizzaInfoDTO : pizzaInfoDTOList) {
			PizzaInfo pizzaInfoDB = pizzaInfoRepository.findByPizzaNameAndPizzaSize(pizzaInfoDTO.getPizzaName(),
					pizzaInfoDTO.getPizzaSize());
			if (!StringUtils.isEmpty(pizzaInfoDB)) {
				pizzaInfoDB.setStockQuantity(pizzaInfoDTO.getStockQuantity());
				pizzaInfoDB.setPrice(pizzaInfoDTO.getPrice());
				pizzaInfoDB.setPizzaCategory(pizzaInfoDTO.getPizzaCategory());
				pizzaInfoDB.setPizzaSize(pizzaInfoDTO.getPizzaSize());
				pizzaInfoDB.setPizzaName(pizzaInfoDTO.getPizzaName());
				pizzaInfoList.add(pizzaInfoDB);
			} else {
				PizzaInfo newPizzaObj = new Gson().fromJson(new Gson().toJson(pizzaInfoDTO), PizzaInfo.class);
				pizzaInfoList.add(newPizzaObj);
			}
		}
		pizzaInfoRepository.saveAll(pizzaInfoList);

		return PizzaShopConstants.SUCCESSFUL_OPERATION;
	}

	
}
