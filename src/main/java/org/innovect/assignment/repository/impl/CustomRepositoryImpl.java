package org.innovect.assignment.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.innovect.assignment.model.AdditionalStuffInfo;
import org.innovect.assignment.repository.CustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRepositoryImpl implements CustomRepository{

	@Autowired
	EntityManager em;
	
	Logger log = LoggerFactory.getLogger(CustomRepositoryImpl.class);
	
	@Override
	public List<String> getPizzaAvailability(){
		try {
			Query query = em.createQuery(
					"select distinct pi.pizzaName from PizzaInfo pi where pi.stockQuantity <= 0");
			return (List<String>) query.getResultList();
		} catch (RuntimeException nre) {
			log.error("No matching record found in database. ");
			throw nre;
		}
	}

	@Override
	public List<String> getAdditionalStuffAvailability(){
		try {
			Query query = em.createQuery(
					"select distinct asi.stuffName from AdditionalStuffInfo asi where asi.stockQuantity <= 0");
			return (List<String>) query.getResultList();
		} catch (RuntimeException nre) {
			log.error("No matching record found in database. ");
			throw nre;
		}
	}
	
	@Override
	public List<AdditionalStuffInfo> findByStuffNameList(List<String> stuffNameList) {
		try {
			Query query = em.createQuery(
					"from AdditionalStuffInfo asi where asi.stuffName in (:stuffNameList)");
			query.setParameter("stuffNameList", stuffNameList);
			return (List<AdditionalStuffInfo>) query.getResultList();
		} catch (RuntimeException nre) {
			log.error("No matching record found in database. ");
			throw nre;
		}
	} 

}
