package com.secutix.DemoGraphQL.resolver;

import com.secutix.DemoGraphQL.model.Author;
import com.secutix.DemoGraphQL.model.Book;
import com.secutix.DemoGraphQL.repository.AuthorRepository;

import graphql.kickstart.tools.GraphQLResolver;

public class BookResolver implements GraphQLResolver<Book> {
	private AuthorRepository authorRepository;

	public BookResolver(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public Author getAuthor(Book book) {
		return authorRepository.findById(book.getAuthor().getId()).orElse(null);
	}
}
