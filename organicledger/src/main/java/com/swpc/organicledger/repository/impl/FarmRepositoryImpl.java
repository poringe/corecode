package com.swpc.organicledger.repository.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpc.organicledger.emodel.OperationType;
import com.swpc.organicledger.model.Farm;
import com.swpc.organicledger.model.Farm_;
import com.swpc.organicledger.repository.FarmRepository;

@Repository
public class FarmRepositoryImpl implements FarmRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Farm find(String farmCode) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farm> query = builder.createQuery(Farm.class);

		Root<Farm> root = query.from(Farm.class);

		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(Farm_.farmCode), farmCode));
		predicates.add(builder.equal(root.get(Farm_.enabled), true));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<Farm> findAll(Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farm> query = builder.createQuery(Farm.class);
		Root<Farm> root = query.from(Farm.class);
		query.select(root).distinct(true);
		TypedQuery<Farm> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<Farm> findByFarmProfileCode(String farmProfileCode, Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farm> query = builder.createQuery(Farm.class);
		Root<Farm> root = query.from(Farm.class);
		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(Farm_.farmProfileCode), farmProfileCode));
		predicates.add(builder.equal(root.get(Farm_.enabled), true));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));
		TypedQuery<Farm> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<Farm> filter(String searchText, String farmProfileCode, Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farm> query = builder.createQuery(Farm.class);
		Root<Farm> root = query.from(Farm.class);
		query.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchText != null && !searchText.isEmpty()) {
			try {
				searchText = URLDecoder.decode(searchText, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Can't decode query string");
			}
			predicates.add(builder.like(root.get(Farm_.farmCode), searchText + "%"));
			predicates.add(builder.like(root.get(Farm_.desc), searchText + "%"));
		}

		if (predicates.isEmpty()) {
			query.where(builder.equal(root.get(Farm_.farmProfileCode), farmProfileCode),
					builder.equal(root.get(Farm_.enabled), true));
		} else {
			query.where(builder.and(builder.or(predicates.toArray(new Predicate[] {}))),
					builder.equal(root.get(Farm_.farmProfileCode), farmProfileCode),
					builder.equal(root.get(Farm_.enabled), true));
		}

		TypedQuery<Farm> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public void create(Farm farm) {
		entityManager.persist(farm);
	}

	@Override
	public Farm update(Farm farm) {
		farm.setOperationType(OperationType.MODIFIED);
		farm.setEnabled(true);
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		disableStatus(builder, farm.getFarmCode());
		entityManager.persist(farm);
		return farm;
	}

	@Override
	public void delete(String farmCode) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farm> query = builder.createQuery(Farm.class);

		Root<Farm> root = query.from(Farm.class);

		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(Farm_.farmCode), farmCode));
		predicates.add(builder.equal(root.get(Farm_.enabled), true));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));

		Farm farm = DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
		// disable all status
		disableStatus(builder, farmCode);
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			Farm deepCopy = objectMapper.readValue(objectMapper.writeValueAsString(farm), Farm.class);
			deepCopy.setId(null);
			deepCopy.setOperationType(OperationType.DELETED);
			deepCopy.setEnabled(false);

			entityManager.persist(deepCopy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String codeGenerator(String farmProfileCode) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		String generator = "";
		String prefix = farmProfileCode;
		CriteriaQuery<String> query = builder.createQuery(String.class);
		Root<Farm> root = query.from(Farm.class);
		query.select(builder.max((Expression) root.get(Farm_.farmCode)));
		String maxFarmCode = entityManager.createQuery(query).getSingleResult();
		if (maxFarmCode == null || maxFarmCode.isEmpty()) {
			generator = prefix + "0001";
		} else {
			try {
				int count = Integer.parseInt(maxFarmCode.replaceAll(prefix, ""));
				count++;
				if (count < 10) {
					generator = prefix + "000" + count;
				} else if (count < 100) {
					generator = prefix + "00" + count;
				} else if (count < 1000) {
					generator = prefix + "0" + count;
				} else if (count < 10000) {
					generator = prefix + count;
				}
			} catch (Exception e) {
				generator = prefix + "0001";
			}
		}
		return generator;
	}

	private void disableStatus(CriteriaBuilder builder, String farmCode) {

		// create update
		CriteriaUpdate<Farm> update = builder.createCriteriaUpdate(Farm.class);

		// set the root class
		Root<Farm> root = update.from(Farm.class);

		// set update and where clause
		update.set(Farm_.enabled, false);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(Farm_.farmCode), farmCode));
		update.where(builder.and(predicates.toArray(new Predicate[] {})));

		// perform update
		entityManager.createQuery(update).executeUpdate();
	}
}
