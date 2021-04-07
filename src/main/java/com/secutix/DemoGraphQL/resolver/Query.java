package com.secutix.DemoGraphQL.resolver;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;

import com.secutix.DemoGraphQL.model.Author;
import com.secutix.DemoGraphQL.model.Book;
import com.secutix.DemoGraphQL.repository.AuthorRepository;
import com.secutix.DemoGraphQL.repository.BookRepository;

import graphql.kickstart.tools.GraphQLQueryResolver;

public class Query implements GraphQLQueryResolver {
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;

	public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}

	public Iterable<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	public Iterable<Book> findAllBooks(Long id) {
		return id == null ? bookRepository.findAll() : bookRepository.findAllById(Collections.singleton(id));
	}

	public Iterable<Author> findAllAuthors() {
		return authorRepository.findAll();
	}

	public Iterable<Author> findAllAuthors(Long id, String firstName) {
		final Iterable<Author> authors;
		if (id != null) {
			authors = authorRepository.findAllById(Collections.singleton(id));
		} else {
			authors = authorRepository.findAll();
		}

		return StreamSupport.stream(authors.spliterator(), false)
				.filter(author -> StringUtils.isEmpty(firstName) || StringUtils
						.equals(firstName, author.getFirstName())).collect(Collectors.toList());
	}

	public long countBooks() {
		return bookRepository.count();
	}

	public long countAuthors() {
		return authorRepository.count();
	}
}
