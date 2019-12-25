package org.innovect.assignment.repository;

import org.innovect.assignment.model.AdditionalStuffInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalStuffRepository extends JpaRepository<AdditionalStuffInfo,String>{

	@Modifying
	@Query("update AdditionalStuffInfo asi set asi.stockQuantity = ?1 where asi.stuffName = ?2")
	void updateStockQuantity(long stockQuantity, String stuffName);

	AdditionalStuffInfo findByStuffName(String stuffName);
}
