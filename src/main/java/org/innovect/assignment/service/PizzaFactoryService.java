package org.innovect.assignment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
import org.innovect.assignment.utils.PizzaShopConstants;
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
		if (StringUtils.isEmpty(submitOrderPostDTO.getOrderPizzaDTOList())) {
			throw new RuntimeException("Ordered number of Pizza can not be empty");
		} else if (submitOrderPostDTO.getOrderPizzaDTOList().size() == 0) {
			throw new RuntimeException("Ordered number of Pizza can not be empty");
		}

		List<OrderPizza> orderPizzaList = new Gson().fromJson(
				new Gson().toJson(submitOrderPostDTO.getOrderPizzaDTOList()), new TypeToken<ArrayList<OrderPizza>>() {
				}.getType());
		List<OrderSides> orderSidesList = new Gson().fromJson(new Gson().toJson(submitOrderPostDTO.getSideOrderList()),
				new TypeToken<ArrayList<OrderSides>>() {
				}.getType());
		Order order = new Order();
		order.setCustName(submitOrderPostDTO.getCustName());
		order.setContactNumber(submitOrderPostDTO.getContactNumber());
		order.setDeliveryAddress(submitOrderPostDTO.getDeliveryAddress());
		try {
			List<String> unavailablePizzaNameList = customRepository.getPizzaAvailability();
			List<String> unavailableStuffNameList = customRepository.getAdditionalStuffAvailability();

			for (OrderPizza orderPizza : orderPizzaList) {
				if (null != unavailablePizzaNameList && unavailablePizzaNameList.size() != 0
						&& unavailablePizzaNameList.contains(orderPizza.getPizzaName())) {
					throw new RuntimeException(orderPizza.getPizzaName() + " is out of stock.");
				}
				order.addPizza(orderPizza, unavailableStuffNameList);
			}
			if (!StringUtils.isEmpty(orderSidesList)) {
				for (OrderSides orderSides : orderSidesList) {
					if (null != unavailableStuffNameList && unavailableStuffNameList.size() != 0
							&& unavailableStuffNameList.contains(orderSides.getSideName())) {
						throw new RuntimeException(orderSides.getSideName() + " is out of stock.");

					}
					order.addSideBars(orderSides);
				}
			}
		} catch (RuntimeException re) {
			log.error("Error occurred in verifyOrder()", re.getMessage());
			throw re;
		}
		return order;
	}

	@Override
	@Transactional
	public SubmitOrderPostDTO getOrderById(String orderId) {
		Order order = orderRepository.findById(orderId).orElse(null);
		return new Gson().fromJson(new Gson().toJson(order), SubmitOrderPostDTO.class);
	}
	
	@Override
	@Transactional
	public SubmitOrderPostDTO submitOrder(SubmitOrderPostDTO submitOrderPostDTO) {
		Order order = new Order();
		try {
			order = this.verifyOrder(submitOrderPostDTO);
			synchronized (lockObject) {
				// update PizzaInfo and Additional Stuff stock quantity before saving to order
				// table.
				this.updateStockPizzaInfoInventory(order.getPizzaList());
				this.updateStockAdditionalStuffInventory(null, order.getSideOrderList());

				order = orderRepository.save(order);
			}

		} catch (RuntimeException e) {
			throw e;
		}
		return new Gson().fromJson(new Gson().toJson(order), SubmitOrderPostDTO.class);
	}

	private String updateStockPizzaInfoInventory(List<OrderPizza> orderPizzaList) {
		List<PizzaInfo> pizzaInfoList = new ArrayList<>();

		for (OrderPizza orderPizza : orderPizzaList) {
			PizzaInfo pizzaInfoFrmDB = pizzaInfoRepository.findByPizzaNameAndPizzaSize(orderPizza.getPizzaName(),
					orderPizza.getPizzaSize());
			if (null != pizzaInfoFrmDB) {
				pizzaInfoFrmDB.setStockQuantity(pizzaInfoFrmDB.getStockQuantity() - 1);
				pizzaInfoList.add(pizzaInfoFrmDB);
				this.updateStockAdditionalStuffInventory(orderPizza.getOrderAdditionalStuffList(), null);
			}
		}
		pizzaInfoRepository.saveAll(pizzaInfoList);

		return PizzaShopConstants.SUCCESSFUL_OPERATION;
	}

	/**
	 * This method is
	 * 
	 * @param orderAdditionalStuffList additional stuff passed during order submit.
	 * @param orderSidesList           side list passed during order submit.
	 * @return SUCCESSFUL if after order processing inventory is successfully
	 *         updated.
	 */
	private String updateStockAdditionalStuffInventory(List<OrderAdditionalStuff> orderAdditionalStuffList,
			List<OrderSides> orderSidesList) {
		if (StringUtils.isEmpty(orderAdditionalStuffList) && StringUtils.isEmpty(orderSidesList)) {
			return "";
		}
		List<String> stuffNameList = new ArrayList<>();
		Map<String, Object> addtionalStuffMap = new HashMap<>();

		if (!StringUtils.isEmpty(orderAdditionalStuffList)) {
			List<String> additionalStuffNameList = orderAdditionalStuffList.stream().map(stuff -> stuff.getStuffName())
					.collect(Collectors.toList());
			stuffNameList.addAll(additionalStuffNameList);

			orderAdditionalStuffList.stream().forEach(stuff -> {
				addtionalStuffMap.put(stuff.getStuffName(), stuff);
			});
		}
		if (!StringUtils.isEmpty(orderSidesList)) {
			List<String> orderSidesNameList = orderSidesList.stream().map(side -> side.getSideName())
					.collect(Collectors.toList());
			stuffNameList.addAll(orderSidesNameList);

			orderSidesList.stream().forEach(sides -> {
				addtionalStuffMap.put(sides.getSideName(), sides);
			});
		}

		List<AdditionalStuffInfo> additionalStuffInfoList = customRepository.findByStuffNameList(stuffNameList);

		for (AdditionalStuffInfo additionalStuffInfo : additionalStuffInfoList) {
			if (additionalStuffInfo.getStockQuantity() <= 0) {
				throw new RuntimeException(additionalStuffInfo.getStuffName() + " is out of stock.");
			}

			if (null != addtionalStuffMap.get(additionalStuffInfo.getStuffName())) {
				Object obj = addtionalStuffMap.get(additionalStuffInfo.getStuffName());
				if (obj instanceof OrderAdditionalStuff) {
					OrderAdditionalStuff orderAdditionalStuff = (OrderAdditionalStuff) obj;
					if (additionalStuffInfo.getStockQuantity() - orderAdditionalStuff.getOrderedQuantity() < 0) {
						throw new RuntimeException(orderAdditionalStuff.getStuffName() + ", "
								+ orderAdditionalStuff.getStuffCategory() + " stock is not sufficient.");
					}
					additionalStuffInfo.setStockQuantity(
							additionalStuffInfo.getStockQuantity() - orderAdditionalStuff.getOrderedQuantity());

				}
				if (obj instanceof OrderSides) {
					OrderSides orderSides = (OrderSides) obj;
					if (additionalStuffInfo.getStockQuantity() - orderSides.getOrderedQuantity() < 0) {
						throw new RuntimeException(orderSides.getSideName() + " stock is not sufficient.");
					}
					additionalStuffInfo
							.setStockQuantity(additionalStuffInfo.getStockQuantity() - orderSides.getOrderedQuantity());
				}
			}
		}

		additionalStuffRepository.saveAll(additionalStuffInfoList);

		return PizzaShopConstants.SUCCESSFUL_OPERATION;
	}

}
