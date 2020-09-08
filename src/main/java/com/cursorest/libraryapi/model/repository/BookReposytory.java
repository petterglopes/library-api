package com.cursorest.libraryapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursorest.libraryapi.api.model.entity.Book;

public interface BookReposytory extends JpaRepository<Book, Long>{

	boolean existsByIsbn(String isbn);

}
