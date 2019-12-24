package org.innovect.assignment.repository;

import org.innovect.assignment.model.Toppings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToppingsRepository extends JpaRepository<Toppings,String>{

}
