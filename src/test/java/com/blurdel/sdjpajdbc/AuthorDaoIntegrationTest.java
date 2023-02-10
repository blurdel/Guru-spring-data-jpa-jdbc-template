package com.blurdel.sdjpajdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import com.blurdel.sdjpajdbc.dao.AuthorDao;
import com.blurdel.sdjpajdbc.dao.AuthorDaoImpl;
import com.blurdel.sdjpajdbc.domain.Author;

@ActiveProfiles("mysql")
@DataJpaTest
//@ComponentScan(basePackages = {"com.blurdel.sdjpajdbc.dao"})
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoIntegrationTest {

	@Autowired
	AuthorDao authorDao;
	
	
	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setFirstName("john");
		author.setLastName("t");
		
		Author saved = authorDao.saveNew(author);
		
		authorDao.delete(saved.getId());
		
		// Verify an exception is thrown
		assertThrows(EmptyResultDataAccessException.class, () -> {
			authorDao.getById(saved.getId());
		});
		
	}
	
	@Test
	void testUpdateAuthor() {
		Author author = new Author();
		author.setFirstName("john");
		author.setLastName("t");
		
		Author saved = authorDao.saveNew(author);
		
		saved.setLastName("Thompson");
		Author updated = authorDao.update(saved);
		
		assertThat(updated.getLastName()).isEqualTo("Thompson");
	}
	
	@Test
	void testSaveAuthor() {
		Author author = new Author();
		author.setFirstName("John");
		author.setLastName("thompson");
		Author saved = authorDao.saveNew(author);
		
		assertThat(saved).isNotNull();
	}
	
	@Test
	void testGetAuthorByName() {
		Author author = null;
		
		author = authorDao.getByName("Craig", "Walls");
		assertThat(author).isNotNull();
		
		assertThrows(EmptyResultDataAccessException.class, () -> {
			authorDao.getByName("David", "Anderson");
		});
	}
	
	@Test
	void testGetAuthorById() {
		Author author = authorDao.getById(1L);
		assertThat(author).isNotNull();
	}

}
