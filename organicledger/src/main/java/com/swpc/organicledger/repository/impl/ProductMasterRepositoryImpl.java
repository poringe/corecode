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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import com.swpc.organicledger.model.ProductMaster;
import com.swpc.organicledger.model.ProductMaster_;
import com.swpc.organicledger.repository.ProductMasterRepository;

@Repository
public class ProductMasterRepositoryImpl implements ProductMasterRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ProductMaster find(Long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductMaster> query = builder.createQuery(ProductMaster.class);

		Root<ProductMaster> root = query.from(ProductMaster.class);

		query.select(root).distinct(true);
		Predicate idPredicate = builder.equal(root.get(ProductMaster_.id), id);
		query.where(builder.and(idPredicate));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<ProductMaster> findAll(Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductMaster> query = builder.createQuery(ProductMaster.class);
		Root<ProductMaster> root = query.from(ProductMaster.class);
		query.select(root).distinct(true);
		TypedQuery<ProductMaster> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<ProductMaster> search(String searchText, Integer firstResult, Integer maxResult) {
		try {
			searchText = URLDecoder.decode(searchText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Can't decode query string");
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductMaster> query = builder.createQuery(ProductMaster.class);
		Root<ProductMaster> root = query.from(ProductMaster.class);
		query.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchText != null && !searchText.isEmpty()) {
			predicates.add(builder.like(root.get(ProductMaster_.name), searchText + "%"));
		}
		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<ProductMaster> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public void create(ProductMaster productMaster) {
		entityManager.persist(productMaster);
	}

	@Override
	public ProductMaster update(ProductMaster productMaster) {
		return entityManager.merge(productMaster);
	}

	@Override
	public void delete(Long id) {
		ProductMaster productMaster = entityManager.find(ProductMaster.class, id);
		delete(productMaster);
	}

	@Override
	public void delete(ProductMaster productMaster) {
		entityManager.remove(productMaster);
	}
}
