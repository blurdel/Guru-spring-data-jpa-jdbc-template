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
		String sql = "select author.id as author_id, first_name, last_name, book.id as book_id, book.isbn as isbn, book.publisher, book.title from author\n" +
                "left outer join book on author.id = book.author_id where author.id = ?";
		
//		return template.queryForObject("select * from author where id=?", getRowMapper(), id);
		
		return template.query(sql, new AuthorExtractor(), id);
	}

	@Override
	public Author getByName(String firstName, String lastName) {
		return template.queryForObject("select id as author_id, first_name, last_name, NULL as isbn from author where first_name=? and last_name=?", 
				getRowMapper(), firstName, lastName);
	}

	@Override
	public Author saveNew(Author author) {
		template.update("insert into author (first_name, last_name) values (?,?)",
				author.getFirstName(), author.getLastName());
		
		Long lastId = template.queryForObject("select LAST_INSERT_ID()", Long.class);

		return this.getById(lastId);
	}

	@Override
	public Author update(Author author) {
		template.update("update author set first_name=?, last_name=? where id=?",
				author.getFirstName(), author.getLastName(), author.getId());
		
		return this.getById(author.getId());
	}

	@Override
	public void delete(Long id) {
		template.update("delete from author where id=?", id);
	}

	private RowMapper<Author> getRowMapper() {
		return new AuthorMapper();
	}
}
