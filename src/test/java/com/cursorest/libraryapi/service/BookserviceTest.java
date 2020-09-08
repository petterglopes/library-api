package com.cursorest.libraryapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cursorest.libraryapi.api.exception.BusinessException;
import com.cursorest.libraryapi.api.model.entity.Book;
import com.cursorest.libraryapi.model.repository.BookReposytory;
import com.cursorest.libraryapi.service.impl.BookServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookserviceTest {
	
	BookService service;
	
	@MockBean
	BookReposytory repository;
	
	@BeforeEach
	public void setUp() {
		this.service = new BookServiceImpl(repository);
	}
	
	@Test
	@DisplayName("Deve salvar um livro")
	public void saveBookTest() {
		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
		Mockito.when( repository.save(book)).thenReturn(
				Book.builder().id(1l)
								.isbn("123")
								.author("Fulano")
								.title("As aventuras").build());
		
		Book savedBook = service.save(book);
		
		assertThat(savedBook.getId()).isNotNull();
		assertThat(savedBook.getIsbn()).isEqualTo("123");
		assertThat(savedBook.getTitle()).isEqualTo("As aventuras");
		assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
	}

	@Test
	@DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado")
	public void ShouldNotSaveBookWithDuplicateIsbn() {
		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
		
		Throwable exception = catchThrowable(() -> service.save(book));
		
		assertThat(exception)
				.isInstanceOf(BusinessException.class)
				.hasMessage("Isbn já cadastrado.");
		
		Mockito.verify(repository, Mockito.never()).save(book);
	
	}

	private Book createValidBook() {
		return Book.builder().isbn("123").author("Fulano").title("As aventuras").build();
	}
	
}
