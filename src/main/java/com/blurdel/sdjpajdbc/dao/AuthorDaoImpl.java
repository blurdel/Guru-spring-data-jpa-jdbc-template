package com.blurdel.sdjpajdbc.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.blurdel.sdjpajdbc.domain.Author;

@Component
public class AuthorDaoImpl implements AuthorDao {

//	private final DataSource source;

	@Override
	public Author getById(Long id) {
		return null;
	}

	@Override
	public Author getByName(String firstName, String lastName) {
		return null;
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
