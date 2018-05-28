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

import com.swpc.organicledger.model.ProductType;
import com.swpc.organicledger.model.ProductType_;
import com.swpc.organicledger.repository.ProductTypeRepository;

@Repository
public class ProductTypeRepositoryImpl implements ProductTypeRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ProductType find(Long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductType> query = builder.createQuery(ProductType.class);

		Root<ProductType> root = query.from(ProductType.class);
		root.fetch(ProductType_.productMasters, JoinType.LEFT);

		query.select(root).distinct(true);
		Predicate idPredicate = builder.equal(root.get(ProductType_.id), id);
		query.where(builder.and(idPredicate));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<ProductType> findAll(Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductType> query = builder.createQuery(ProductType.class);
		Root<ProductType> root = query.from(ProductType.class);
		root.fetch(ProductType_.productMasters, JoinType.LEFT);
		query.select(root).distinct(true);
		TypedQuery<ProductType> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<ProductType> search(String searchText, Integer firstResult, Integer maxResult) {
		try {
			searchText = URLDecoder.decode(searchText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Can't decode query string");
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductType> query = builder.createQuery(ProductType.class);
		Root<ProductType> root = query.from(ProductType.class);
		root.fetch(ProductType_.productMasters, JoinType.LEFT);
		query.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchText != null && !searchText.isEmpty()) {
			predicates.add(builder.like(root.get(ProductType_.name), searchText + "%"));
		}
		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<ProductType> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public void create(ProductType productType) {
		entityManager.persist(productType);
	}

	@Override
	public ProductType update(ProductType productType) {
		return entityManager.merge(productType);
	}

	@Override
	public void delete(Long id) {
		ProductType productType = entityManager.find(ProductType.class, id);
		delete(productType);
	}

	@Override
	public void delete(ProductType productType) {
		entityManager.remove(productType);
	}
}
