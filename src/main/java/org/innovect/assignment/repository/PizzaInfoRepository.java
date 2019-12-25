package org.innovect.assignment.repository;

import org.innovect.assignment.model.PizzaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaInfoRepository extends JpaRepository<PizzaInfo, Integer> {

	@Modifying
	@Query("update PizzaInfo pi set pi.stockQuantity = ?1 where pi.pizzaName = ?2")
	void updateStockQuantity(int stockQuantity, String pizzaName);

	/*
	 * @Query("SELECT * FROM PizzaInfo pi WHERE pi.pizzaName in ?1") List<PizzaInfo>
	 * findByPizzaNameList(List<String> pizzaNameList);
	 */
	PizzaInfo findByPizzaNameAndPizzaSize(String pizzaName, String pizzaSize);
}
