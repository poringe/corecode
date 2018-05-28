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

import com.swpc.organicledger.model.ProductCategory;
import com.swpc.organicledger.model.ProductCategory_;
import com.swpc.organicledger.repository.ProductCategoryRepository;

@Repository
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ProductCategory find(Long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductCategory> query = builder.createQuery(ProductCategory.class);

		Root<ProductCategory> root = query.from(ProductCategory.class);

		query.select(root).distinct(true);
		Predicate idPredicate = builder.equal(root.get(ProductCategory_.id), id);
		query.where(builder.and(idPredicate));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<ProductCategory> findAll(Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductCategory> query = builder.createQuery(ProductCategory.class);
		Root<ProductCategory> root = query.from(ProductCategory.class);
		query.select(root).distinct(true);
		TypedQuery<ProductCategory> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<ProductCategory> search(String searchText, Integer firstResult, Integer maxResult) {
		try {
			searchText = URLDecoder.decode(searchText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Can't decode query string");
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductCategory> query = builder.createQuery(ProductCategory.class);
		Root<ProductCategory> root = query.from(ProductCategory.class);
		query.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchText != null && !searchText.isEmpty()) {
			predicates.add(builder.like(root.get(ProductCategory_.name), searchText + "%"));
		}
		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<ProductCategory> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public void create(ProductCategory productCategory) {
		entityManager.persist(productCategory);
	}

	@Override
	public ProductCategory update(ProductCategory productCategory) {
		return entityManager.merge(productCategory);
	}

	@Override
	public void delete(Long id) {
		ProductCategory productCategory = entityManager.find(ProductCategory.class, id);
		delete(productCategory);
	}

	@Override
	public void delete(ProductCategory productCategory) {
		entityManager.remove(productCategory);
	}
}
