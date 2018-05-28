package com.swpc.organicledger.repository.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import com.swpc.organicledger.model.Unit;
import com.swpc.organicledger.model.Unit_;
import com.swpc.organicledger.repository.UnitRepository;

@Repository
public class UnitRepositoryImpl implements UnitRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Unit find(Long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Unit> query = builder.createQuery(Unit.class);

		Root<Unit> root = query.from(Unit.class);
		root.fetch(Unit_.unitMeasures, JoinType.LEFT);

		query.select(root).distinct(true);
		Predicate idPredicate = builder.equal(root.get(Unit_.id), id);
		query.where(builder.and(idPredicate));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<Unit> findAll(Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Unit> query = builder.createQuery(Unit.class);
		Root<Unit> root = query.from(Unit.class);
		root.fetch(Unit_.unitMeasures, JoinType.LEFT);
		query.select(root).distinct(true);
		TypedQuery<Unit> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<Unit> search(String searchText, Integer firstResult, Integer maxResult) {
		try {
			searchText = URLDecoder.decode(searchText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Can't decode query string");
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Unit> query = builder.createQuery(Unit.class);
		Root<Unit> root = query.from(Unit.class);
		root.fetch(Unit_.unitMeasures, JoinType.LEFT);
		query.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchText != null && !searchText.isEmpty()) {
			predicates.add(builder.like(root.get(Unit_.name), searchText + "%"));
		}
		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Unit> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public void create(Unit unit) {
		entityManager.persist(unit);
	}

	@Override
	public Unit update(Unit unit) {
		return entityManager.merge(unit);
	}

	@Override
	public void delete(Long id) {
		Unit unit = entityManager.find(Unit.class, id);
		delete(unit);
	}

	@Override
	public void delete(Unit unit) {
		entityManager.remove(unit);
	}
}
