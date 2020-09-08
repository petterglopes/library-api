package com.cursorest.libraryapi.service.impl;

import org.springframework.stereotype.Service;

import com.cursorest.libraryapi.api.exception.BusinessException;
import com.cursorest.libraryapi.api.model.entity.Book;
import com.cursorest.libraryapi.model.repository.BookReposytory;
import com.cursorest.libraryapi.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private BookReposytory repository;

	public BookServiceImpl(BookReposytory repository) {
		this.repository = repository;
	}

	@Override
	public Book save(Book book) {
		if(repository.existsByIsbn(book.getIsbn())) {
			throw new BusinessException("Isbn já cadastrado.");
		}
		return repository.save(book);
	}

}
