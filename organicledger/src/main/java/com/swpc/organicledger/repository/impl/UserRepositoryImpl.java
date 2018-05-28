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

import com.swpc.organicledger.model.User;
import com.swpc.organicledger.model.User_;
import com.swpc.organicledger.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public User find(Long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);

		Root<User> root = query.from(User.class);
		root.fetch(User_.authorities, JoinType.LEFT);

		query.select(root).distinct(true);
		Predicate idPredicate = builder.equal(root.get(User_.id), id);
		query.where(builder.and(idPredicate));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public User findByUsername(String username) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);

		Root<User> root = query.from(User.class);
		root.fetch(User_.authorities, JoinType.LEFT);

		query.select(root).distinct(true);
		Predicate usernamePredicate = builder.equal(root.get(User_.username), username);
		query.where(builder.and(usernamePredicate));

		return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<User> findAll(Integer firstResult, Integer maxResult) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root).distinct(true);
		TypedQuery<User> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}

	@Override
	public List<User> filter(String searchText, Integer firstResult, Integer maxResult) {
		try {
			searchText = URLDecoder.decode(searchText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Can't decode query string");
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		root.fetch(User_.authorities, JoinType.LEFT);
		query.select(root).distinct(true);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchText != null && !searchText.isEmpty()) {
			predicates.add(builder.like(root.get(User_.farmProfileCode), searchText + "%"));
			predicates.add(builder.like(root.get(User_.username), searchText + "%"));
			predicates.add(builder.like(root.get(User_.firstName), searchText + "%"));
			predicates.add(builder.like(root.get(User_.lastName), searchText + "%"));
			predicates.add(builder.like(root.get(User_.emailAddress), searchText + "%"));
		}
		query.where(builder.or(predicates.toArray(new Predicate[] {})));

		TypedQuery<User> allQuery = entityManager.createQuery(query);
		allQuery.setFirstResult(firstResult);
		allQuery.setMaxResults(maxResult);
		return allQuery.getResultList();
	}
	
	@Override
	public void create(User user) {
		entityManager.persist(user);
	}

	@Override
	public User update(User user) {
		return entityManager.merge(user);
	}

	@Override
	public void delete(Long id) {
		User user = entityManager.find(User.class, id);
		delete(user);
	}

	@Override
	public void delete(User user) {
		entityManager.remove(user);
	}
}
