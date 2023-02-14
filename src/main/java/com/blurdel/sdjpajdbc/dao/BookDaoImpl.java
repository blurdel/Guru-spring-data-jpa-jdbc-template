package com.blurdel.sdjpajdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.blurdel.sdjpajdbc.domain.Book;

@Component
public class BookDaoImpl implements BookDao {

	private final JdbcTemplate template;
	
	
	public BookDaoImpl(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public Book getById(Long id) {
		return template.queryForObject("select * from book where id=?",
				getRowMapper(), id);
	}

	@Override
	public Book getByTitle(String title) {
		return template.queryForObject("select * from book where title=?",
				getRowMapper(), title);
	}

	@Override
	public Book saveNew(Book book) {
		template.update("insert into book (isbn, publisher, title, author_id) values (?,?,?,?)",
				book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId());
		
		Long lastId = template.queryForObject("select LAST_INSERT_ID()", Long.class);
		
		return this.getById(lastId);
	}

	@Override
	public Book update(Book book) {
		template.update("update book set isbn=?, publisher=?, title=?, author_id=? where id=?",
				book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId(), book.getId());
		
		return this.getById(book.getId());
	}

	@Override
	public void delete(Long id) {
		template.update("delete from book where id=?", id);
	}

	private RowMapper<Book> getRowMapper() {
		return new BookMapper();
	}
}
