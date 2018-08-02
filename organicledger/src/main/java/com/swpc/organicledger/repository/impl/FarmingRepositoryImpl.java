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
import com.swpc.organicledger.model.Farming;
import com.swpc.organicledger.model.Farming_;
import com.swpc.organicledger.repository.FarmingRepository;

@Repository
public class FarmingRepositoryImpl implements FarmingRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Farming find(String farmingCode) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farming> query = builder.createQuery(Farming.class);

		Root<Farming> root = query.from(Farming.class);

		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(Farming_.farmingCode), farmingCode));
		predicates.add(builder.equal(root.get(Farming_.enabled), true));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<Farming> findAll(Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farming> query = builder.createQuery(Farming.class);
		Root<Farming> root = query.from(Farming.class);
		query.select(root).distinct(true);
		TypedQuery<Farming> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<Farming> findByFarmingProfileCode(String farmingProfileCode, Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farming> query = builder.createQuery(Farming.class);
		Root<Farming> root = query.from(Farming.class);
		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(Farming_.farmProfileCode), farmingProfileCode));
		predicates.add(builder.equal(root.get(Farming_.enabled), true));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));
		TypedQuery<Farming> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<Farming> filter(String searchText, String farmingProfileCode, Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farming> query = builder.createQuery(Farming.class);
		Root<Farming> root = query.from(Farming.class);
		query.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchText != null && !searchText.isEmpty()) {
			try {
				searchText = URLDecoder.decode(searchText, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Can't decode query string");
			}
			predicates.add(builder.like(root.get(Farming_.farmingCode), searchText + "%"));
			predicates.add(builder.like(root.get(Farming_.farmCode), searchText + "%"));
			predicates.add(builder.like(root.get(Farming_.desc), searchText + "%"));
		}

		if (predicates.isEmpty()) {
			query.where(builder.equal(root.get(Farming_.farmProfileCode), farmingProfileCode),
					builder.equal(root.get(Farming_.enabled), true));
		} else {
			query.where(builder.and(builder.or(predicates.toArray(new Predicate[] {}))),
					builder.equal(root.get(Farming_.farmProfileCode), farmingProfileCode),
					builder.equal(root.get(Farming_.enabled), true));
		}

		TypedQuery<Farming> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public void create(Farming farming) {
		entityManager.persist(farming);
	}

	@Override
	public Farming update(Farming farming) {
		farming.setOperationType(OperationType.MODIFIED);
		farming.setEnabled(true);
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		disableStatus(builder, farming.getFarmingCode());
		entityManager.persist(farming);
		return farming;
	}

	@Override
	public void delete(String farmingCode) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farming> query = builder.createQuery(Farming.class);

		Root<Farming> root = query.from(Farming.class);

		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(Farming_.farmingCode), farmingCode));
		predicates.add(builder.equal(root.get(Farming_.enabled), true));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));

		Farming farming = DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
		// disable all status
		disableStatus(builder, farmingCode);
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			Farming deepCopy = objectMapper.readValue(objectMapper.writeValueAsString(farming), Farming.class);
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
	public String codeGenerator(String farmCode) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		String generator = "";
		String prefix = farmCode;
		CriteriaQuery<String> query = builder.createQuery(String.class);
		Root<Farming> root = query.from(Farming.class);
		query.select(builder.max((Expression) root.get(Farming_.farmingCode)));
		String maxFarmingCode = entityManager.createQuery(query).getSingleResult();
		if (maxFarmingCode == null || maxFarmingCode.isEmpty()) {
			generator = prefix + "0001";
		} else {
			try {
				int count = Integer.parseInt(maxFarmingCode.replaceAll(prefix, ""));
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

	private void disableStatus(CriteriaBuilder builder, String farmingCode) {

		// create update
		CriteriaUpdate<Farming> update = builder.createCriteriaUpdate(Farming.class);

		// set the root class
		Root<Farming> root = update.from(Farming.class);

		// set update and where clause
		update.set(Farming_.enabled, false);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(Farming_.farmingCode), farmingCode));
		update.where(builder.and(predicates.toArray(new Predicate[] {})));

		// perform update
		entityManager.createQuery(update).executeUpdate();
	}
}
