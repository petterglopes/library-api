package com.cursorest.libraryapi.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cursorest.libraryapi.api.model.entity.Book;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	BookReposytory reposytory;
	
	@Test
	@DisplayName("Deve retornar vedadeiro quando existir o livro na base com o isbn informado")
	public void returnTrueWhenIsbnExists() {
		
		String isbn = "123";
		Book book = Book.builder().title("Aventuras").author("Fulano").isbn(isbn).build();
		entityManager.persist(book);
		
		boolean exists = reposytory.existsByIsbn(isbn);
		
		assertThat(exists).isTrue();
	}
	
	@Test
	@DisplayName("Deve retornar falso quando não existir o livro na base com o isbn informado")
	public void returnFalseWhenIsbnExists() {
		
		String isbn = "123";
		
		boolean exists = reposytory.existsByIsbn(isbn);
		
		assertThat(exists).isFalse();
	}
}
