package com.blurdel.sdjpajdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.blurdel.sdjpajdbc.domain.Author;
import com.blurdel.sdjpajdbc.domain.Book;

public class AuthorMapper implements RowMapper<Author> {

	@Override
	public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
		
//		rs.next();
		
		Author author = new Author();
		author.setId(rs.getLong("author_id"));
		author.setFirstName(rs.getString("first_name"));
		author.setLastName(rs.getString("last_name"));
		
		try {
			
			if (rs.getString("isbn") != null) {
				author.setBooks(new ArrayList<>());
				author.getBooks().add(mapBook(rs));
				
				while (rs.next()) {
					author.getBooks().add(mapBook(rs));	
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return author;
	}

	private Book mapBook(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setId(rs.getLong("book_id"));
		book.setIsbn(rs.getString("isbn"));
		book.setTitle(rs.getString("title"));
		book.setPublisher(rs.getString("publisher"));
		book.setAuthorId(rs.getLong("author_id"));
		return book;		
	}
	
}
