package org.innovect.assignment.repository;

import org.innovect.assignment.model.PizzaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaInfoRepository extends JpaRepository<PizzaInfo, Integer> {

}
