package com.swpc.organicledger.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import com.swpc.organicledger.model.FarmRecord;
import com.swpc.organicledger.model.FarmRecord_;
import com.swpc.organicledger.repository.FarmRecordRepository;

@Repository
public class FarmRecordRepositoryImpl implements FarmRecordRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public FarmRecord find(Long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FarmRecord> query = builder.createQuery(FarmRecord.class);

		Root<FarmRecord> root = query.from(FarmRecord.class);

		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(FarmRecord_.id), id));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<FarmRecord> findAll(Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FarmRecord> query = builder.createQuery(FarmRecord.class);
		Root<FarmRecord> root = query.from(FarmRecord.class);
		query.select(root).distinct(true);
		TypedQuery<FarmRecord> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<FarmRecord> findByFarmingCode(String farmingCode, Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FarmRecord> query = builder.createQuery(FarmRecord.class);
		Root<FarmRecord> root = query.from(FarmRecord.class);
		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(FarmRecord_.farmingCode), farmingCode));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));
		TypedQuery<FarmRecord> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}


	@Override
	public void create(FarmRecord farmRecord) {
		entityManager.persist(farmRecord);
	}

}
