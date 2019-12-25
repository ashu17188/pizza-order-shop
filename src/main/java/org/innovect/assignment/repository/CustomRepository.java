package org.innovect.assignment.repository;

import java.util.List;

import org.innovect.assignment.model.AdditionalStuffInfo;
import org.innovect.assignment.model.PizzaInfo;

public interface CustomRepository {

	List<String> getPizzaAvailability();

	List<String> getAdditionalStuffAvailability();

	List<PizzaInfo> findByPizzaNameList(List<String> pizzaNameList);

	List<AdditionalStuffInfo> findByStuffNameList(List<String> stuffNameList);

}
