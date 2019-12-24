package org.innovect.assignment.repository;

import org.innovect.assignment.model.OrderSides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SidesRepository extends JpaRepository<OrderSides, String> {

}
