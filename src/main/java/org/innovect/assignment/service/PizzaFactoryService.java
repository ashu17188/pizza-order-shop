package org.innovect.assignment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.dialect.Dialect;
import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.AdditionalStuffInfo;
import org.innovect.assignment.model.Order;
import org.innovect.assignment.model.OrderAdditionalStuff;
import org.innovect.assignment.model.OrderPizza;
import org.innovect.assignment.model.OrderSides;
import org.innovect.assignment.model.PizzaInfo;
import org.innovect.assignment.repository.AdditionalStuffRepository;
import org.innovect.assignment.repository.CustomRepository;
import org.innovect.assignment.repository.OrderRepository;
import org.innovect.assignment.repository.PizzaInfoRepository;
import org.innovect.assignment.utils.PizzaShopUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Ashutosh Shukla This class provides various methods for handling
 *         operations for Pizza management software.
 */

@Service
public class PizzaFactoryService implements PizzaFactory {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PizzaInfoRepository pizzaInfoRepository;

	@Autowired
	private AdditionalStuffRepository additionalStuffRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomRepository customRepository;

	private final Object lockObject = new Object();

	@Override
	public CustomerDashboardInfoDTO getPizzaAndExtraInfo() {
		CustomerDashboardInfoDTO customerDashboardInfoDTO = new CustomerDashboardInfoDTO();
		List<PizzaInfo> pizzaInfoList = pizzaInfoRepository.findAll();
		List<AdditionalStuffInfo> additionalStuffList = additionalStuffRepository.findAll();

		customerDashboardInfoDTO.setPizzaInfoDTOList(
				new Gson().fromJson(new Gson().toJson(pizzaInfoList), new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType()));
		customerDashboardInfoDTO.setAdditionalStuffDTOList(new Gson().fromJson(new Gson().toJson(additionalStuffList),
				new TypeToken<ArrayList<AdditionalStuffInfoDTO>>() {
				}.getType()));
		log.info("Dashboard information for customer:{}", customerDashboardInfoDTO.toString());
		return customerDashboardInfoDTO;
	}

	@Override
	public Order verifyOrder(SubmitOrderPostDTO submitOrderPostDTO) {
		List<OrderPizza> orderPizzaList = new Gson().fromJson(
				new Gson().toJson(submitOrderPostDTO.getOrderPizzaDTOList()), new TypeToken<ArrayList<OrderPizza>>() {
				}.getType());
		List<OrderSides> orderSidesList = new Gson().fromJson(new Gson().toJson(submitOrderPostDTO.getSideOrderList()),
				new TypeToken<ArrayList<OrderPizza>>() {
				}.getType());
		Order order = new Order();

		try {
			List<String> unavailablePizzaNameList = customRepository.getPizzaAvailability();
			List<String> unavailableStuffNameList = customRepository.getAdditionalStuffAvailability();

			for (OrderPizza orderPizza : orderPizzaList) {
				if (unavailablePizzaNameList.contains(orderPizza.getPizzaName())) {
					throw new RuntimeException(orderPizza.getPizzaName() + " Pizza is out of stock.");
				}
				order.addPizza(orderPizza, unavailableStuffNameList);
			}
			for (OrderSides orderSides : orderSidesList) {
				if (unavailableStuffNameList.contains(orderSides.getSideName())) {
					throw new RuntimeException(orderSides.getSideName() + " is out of stock.");

				}
				order.addSideBars(orderSides);
			}
		} catch (RuntimeException re) {
			log.error("Error occurred in {}:: verifyOrder()", log.getClass(), re.getLocalizedMessage());
			throw re;
		}
		return order;
	}

	@Override
	@Transactional
	public String submitOrder(SubmitOrderPostDTO submitOrderPostDTO) {
		if (submitOrderPostDTO.getOrderPizzaDTOList().size() == 0) {
			throw new RuntimeException("Ordered number of Pizza can not be empty");
		}
		Order order = new Order();
		try {
			order = this.verifyOrder(submitOrderPostDTO);
			synchronized (lockObject) {
				order = orderRepository.save(order);

				// update PizzaInfo and Additional Stuff stock quantity before saving to order
				// table.
				this.updateStockPizzaInfoInventory(order.getPizzaList());
				this.updateStockAdditionalStuffInventory(null, order.getSideOrderList());

			}

		} catch (RuntimeException e) {
			throw e;
		}
		return PizzaShopUtils.SUCCESSFUL_ORDER_SUBMIT_OPERATION;
	}

	@Override
	public List<PizzaInfoDTO> getAllPizzInfoList() {
		return new Gson().fromJson(new Gson().toJson(pizzaInfoRepository.findAll()),
				new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType());
	}
	@Override
	public String addPizzaInfoInventory(List<PizzaInfoDTO> pizzaInfoDTOList) {
		if (pizzaInfoDTOList.size() == 0) {
			throw new IllegalArgumentException("Pizza list can not be empty.");
		}
		List<PizzaInfo> pizzaInfoList = new ArrayList<>();
		for(PizzaInfoDTO pizzaInfoDTO: pizzaInfoDTOList) {
			PizzaInfo pizzaInfoDB =pizzaInfoRepository.findByPizzaNameAndPizzaSize(pizzaInfoDTO.getPizzaName(), pizzaInfoDTO.getPizzaSize());
			if(!StringUtils.isEmpty(pizzaInfoDB)) {
				pizzaInfoDB.setStockQuantity(pizzaInfoDTO.getStockQuantity());
				pizzaInfoDB.setPrice(pizzaInfoDTO.getPrice());
				pizzaInfoList.add(pizzaInfoDB);				
			}else {
				PizzaInfo newPizzaObj = new Gson().fromJson(new Gson().toJson(pizzaInfoDTO),PizzaInfo.class);
				pizzaInfoList.add(newPizzaObj);				
			}
		}
		pizzaInfoRepository.saveAll(pizzaInfoList);

		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	public String updateStockPizzaInfoInventory(List<OrderPizza> orderPizzaList) {
		Map<String, OrderPizza> orderPizzaMap = new HashMap<>();
		for (OrderPizza orderPizza : orderPizzaList) {
			orderPizzaMap.put(orderPizza.getPizzaName(), orderPizza);
		}
		List<String> pizzaNameList = orderPizzaList.stream().map(pizza -> pizza.getPizzaName())
				.collect(Collectors.toList());
		List<PizzaInfo> pizzaInfoList = customRepository.findByPizzaNameList(pizzaNameList);

		for (PizzaInfo pizzaInfo : pizzaInfoList) {
			long stockQty = pizzaInfo.getStockQuantity() - 1;
			pizzaInfo.setStockQuantity(stockQty);
		}
		pizzaInfoRepository.saveAll(pizzaInfoList);

		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	@Override
	public String addAdditionalStuffInventory(List<AdditionalStuffInfoDTO> additionalStuffInfoDTOList) {
		if (additionalStuffInfoDTOList.size() == 0) {
			throw new IllegalArgumentException("Stuff List can not be empty.");
		}
		List<AdditionalStuffInfo> additionalStuffInfoList = new ArrayList<>();
		for (AdditionalStuffInfoDTO additionalStuffInfoDTO : additionalStuffInfoDTOList) {
			AdditionalStuffInfo stuffObjFrmDB = additionalStuffRepository.findByStuffName(additionalStuffInfoDTO.getStuffName());
			if(!StringUtils.isEmpty(stuffObjFrmDB)) {
				stuffObjFrmDB.setPrice(additionalStuffInfoDTO.getPrice());
				stuffObjFrmDB.setStockQuantity(additionalStuffInfoDTO.getStockQuantity());
				additionalStuffInfoList.add(stuffObjFrmDB);
			}else {
				AdditionalStuffInfo additionalStuffInfo = new Gson().fromJson(new Gson().toJson(additionalStuffInfoDTO),
						AdditionalStuffInfo.class);
				additionalStuffInfoList.add(additionalStuffInfo);
			}
		}
		
		additionalStuffRepository.saveAll(additionalStuffInfoList);
		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	public String updateStockAdditionalStuffInventory(List<OrderAdditionalStuff> orderAdditionalStuffList,
			List<OrderSides> orderSidesList) {
		if (StringUtils.isEmpty(orderAdditionalStuffList) && StringUtils.isEmpty(orderSidesList)) {
			return "";
		}
		Map<String, Object> addtionalStuffMap = new HashMap<>();
		List<String> stuffNameList = new ArrayList<>();
		if (!StringUtils.isEmpty(orderAdditionalStuffList)) {
			for (OrderAdditionalStuff orderAdditionalStuff : orderAdditionalStuffList) {
				addtionalStuffMap.put(orderAdditionalStuff.getStuffName(), orderAdditionalStuff);
				stuffNameList.add(orderAdditionalStuff.getStuffName());
			}
		}
		if (!StringUtils.isEmpty(orderSidesList)) {
			for (OrderSides orderSides : orderSidesList) {
				addtionalStuffMap.put(orderSides.getSideName(), orderSides);
				stuffNameList.add(orderSides.getSideName());
			}
		}

		List<AdditionalStuffInfo> stuffList = customRepository.findByStuffNameList(stuffNameList);

		for (AdditionalStuffInfo stuffObj : stuffList) {
			Object mapObject = addtionalStuffMap.get(stuffObj.getStuffName());
			if (null != mapObject) {
				if (mapObject instanceof OrderAdditionalStuff) {
					OrderAdditionalStuff orderStuffFromMap = (OrderAdditionalStuff) mapObject;
					long qtyLeft = stuffObj.getStockQuantity() - orderStuffFromMap.getOrderedQuantity();
					stuffObj.setStockQuantity(qtyLeft);
				} else if (mapObject instanceof OrderSides) {
					OrderSides orderSidesFromMap = (OrderSides) mapObject;
					long qtyLeft = stuffObj.getStockQuantity() - orderSidesFromMap.getOrderedQuantity();
					stuffObj.setStockQuantity(qtyLeft);
				}

			}
		}
		additionalStuffRepository.saveAll(stuffList);

		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	protected int batchSize() {
		return Integer.valueOf(Dialect.DEFAULT_BATCH_SIZE);
	}
}
