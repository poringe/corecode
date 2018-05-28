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
import com.swpc.organicledger.model.FarmProfile;
import com.swpc.organicledger.model.FarmProfile_;
import com.swpc.organicledger.repository.FarmProfileRepository;

@Repository
public class FarmProfileRepositoryImpl implements FarmProfileRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public FarmProfile find(String farmCode) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FarmProfile> query = builder.createQuery(FarmProfile.class);

		Root<FarmProfile> root = query.from(FarmProfile.class);

		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(FarmProfile_.farmProfileCode), farmCode));
		predicates.add(builder.equal(root.get(FarmProfile_.enabled), true));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<FarmProfile> findAll(Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FarmProfile> query = builder.createQuery(FarmProfile.class);
		Root<FarmProfile> root = query.from(FarmProfile.class);
		query.select(root).distinct(true);
		TypedQuery<FarmProfile> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<FarmProfile> filter(String searchText, Integer firstResult, Integer maxResult) {
		try {
			searchText = URLDecoder.decode(searchText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Can't decode query string");
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FarmProfile> query = builder.createQuery(FarmProfile.class);
		Root<FarmProfile> root = query.from(FarmProfile.class);
		query.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchText != null && !searchText.isEmpty()) {
			predicates.add(builder.like(root.get(FarmProfile_.farmProfileCode), searchText + "%"));
			predicates.add(builder.like(root.get(FarmProfile_.name), searchText + "%"));
			predicates.add(builder.like(root.get(FarmProfile_.farmerName), searchText + "%"));
			predicates.add(builder.like(root.get(FarmProfile_.farmerContact), searchText + "%"));
			predicates.add(builder.like(root.get(FarmProfile_.description), searchText + "%"));
		}
		query.where(builder.and(builder.or(predicates.toArray(new Predicate[] {}))),
				builder.equal(root.get(FarmProfile_.enabled), true));

		TypedQuery<FarmProfile> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public void create(FarmProfile farmProfile) {
		entityManager.persist(farmProfile);
	}

	@Override
	public FarmProfile update(FarmProfile farmProfile) {
		farmProfile.setOperationType(OperationType.MODIFIED);
		farmProfile.setEnabled(true);
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		disableStatus(builder, farmProfile.getFarmProfileCode());
		entityManager.persist(farmProfile);
		return farmProfile;
	}

	@Override
	public void delete(String farmCode) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FarmProfile> query = builder.createQuery(FarmProfile.class);

		Root<FarmProfile> root = query.from(FarmProfile.class);

		query.select(root).distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(FarmProfile_.farmProfileCode), farmCode));
		predicates.add(builder.equal(root.get(FarmProfile_.enabled), true));
		query.where(builder.and(predicates.toArray(new Predicate[] {})));

		FarmProfile farmProfile = DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
		// disable all status
		disableStatus(builder, farmCode);
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			FarmProfile deepCopy = objectMapper.readValue(objectMapper.writeValueAsString(farmProfile),
					FarmProfile.class);
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
	public String codeGenerator() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		String generator = "";
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY", Locale.US);
		String prefix = "FP" + sdf.format(new Date());
		CriteriaQuery<String> query = builder.createQuery(String.class);
		Root<FarmProfile> root = query.from(FarmProfile.class);
		query.select(builder.max((Expression) root.get(FarmProfile_.farmProfileCode)));
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
		CriteriaUpdate<FarmProfile> update = builder.createCriteriaUpdate(FarmProfile.class);

		// set the root class
		Root<FarmProfile> root = update.from(FarmProfile.class);

		// set update and where clause
		update.set(FarmProfile_.enabled, false);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get(FarmProfile_.farmProfileCode), farmCode));
		update.where(builder.and(predicates.toArray(new Predicate[] {})));

		// perform update
		entityManager.createQuery(update).executeUpdate();
	}
}
