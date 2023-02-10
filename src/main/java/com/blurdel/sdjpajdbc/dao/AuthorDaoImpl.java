package com.blurdel.sdjpajdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.blurdel.sdjpajdbc.domain.Author;

@Component
public class AuthorDaoImpl implements AuthorDao {

	private final JdbcTemplate template;

	
	public AuthorDaoImpl(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public Author getById(Long id) {
		return template.queryForObject("select * from author where id=?", getRowMapper(), id);
	}

	@Override
	public Author getByName(String firstName, String lastName) {
		return template.queryForObject("select * from author where first_name=? and last_name=?", 
				getRowMapper(), firstName, lastName);
	}

	@Override
	public Author saveNew(Author author) {
		return null;
	}

	@Override
	public Author update(Author author) {
		return null;
	}

	@Override
	public void delete(Long id) {
	}

	private RowMapper<Author> getRowMapper() {
		return new AuthorMapper();
	}
}
