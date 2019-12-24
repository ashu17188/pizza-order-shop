package org.innovect.assignment.repository;

import org.innovect.assignment.model.AdditionalStuffInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalStuffRepository extends JpaRepository<AdditionalStuffInfo,String>{

}
