package com.tfkfan.hibernate.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractDao<T> {
	private final static Logger log = Logger.getLogger(AbstractDao.class.getName());

	@Autowired
	protected HibernateTemplate hTemplate;

	protected final Class<T> clazz;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void save(T object) {
		hTemplate.save(object);
	}

	public T get(Long id) {
		return hTemplate.get(clazz, id);
	}

	public List<T> listAll() {
		return hTemplate.loadAll(clazz);
	}

	public void update(T obj) {
		hTemplate.merge(obj);
	}

	protected HibernateTemplate getTemplate() {
		return hTemplate;
	}

	protected void setTemplate(HibernateTemplate hTemplate) {
		this.hTemplate = hTemplate;
	}

	public void delete(T obj) {
		hTemplate.delete(obj);
	}

	@SuppressWarnings("unchecked")
	public List<T> listAllOrderBy(String orderField, boolean asc) {
		return (List<T>) hTemplate
				.find("FROM " + clazz.getSimpleName() + " ORDER BY " + orderField + " " + (asc ? "ASC" : "DESC"));
	}

	@SuppressWarnings("unchecked")
	public List<T> listAllOrderByWithParam(List<String> conditions, String orderField, boolean asc) {
		String query = "FROM " + clazz.getSimpleName();
		if (conditions.size() != 0)
			query += " WHERE ";
		for (int i = 0; i < conditions.size(); i++) {
			String entry = conditions.get(i);
				query += entry + (i == conditions.size() - 1 ? "" : " AND ");
		}

		query += " ORDER BY " + orderField + " " + (asc ? "ASC" : "DESC");
		return (List<T>) hTemplate.find(query);
	}

	@SuppressWarnings("unchecked")
	public List<T> listAllWithParam(List<String> conditions) {
		String query = "FROM " + clazz.getSimpleName();
		if (conditions.size() != 0)
			query += " WHERE ";
		for (int i = 0; i < conditions.size(); i++) {
			String entry = conditions.get(i);
				query += entry + (i == conditions.size() - 1 ? "" : " AND ");
		}

		return (List<T>) hTemplate.find(query);
	}

	@SuppressWarnings("unchecked")
	public List<T> listAllOrderByWithParam(String condition, String orderField, boolean asc) {
		return (List<T>) hTemplate.find("FROM " + clazz.getSimpleName() + " WHERE " + condition + " ORDER BY "
				+ orderField + " " + (asc ? "ASC" : "DESC"));
	}

	@SuppressWarnings("unchecked")
	public List<T> listAllWithParam(String condition) {
		return (List<T>) hTemplate.find("FROM " + clazz.getSimpleName() + " WHERE " + condition);
	}

}
