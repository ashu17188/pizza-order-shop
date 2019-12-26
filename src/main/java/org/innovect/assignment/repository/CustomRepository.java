package org.innovect.assignment.repository;

import java.util.List;

import org.innovect.assignment.model.AdditionalStuffInfo;

public interface CustomRepository {

	List<String> getPizzaAvailability();

	List<String> getAdditionalStuffAvailability();

	List<AdditionalStuffInfo> findByStuffNameList(List<String> stuffNameList);

}
