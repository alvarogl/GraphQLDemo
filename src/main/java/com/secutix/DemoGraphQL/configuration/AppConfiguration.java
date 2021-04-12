package com.secutix.DemoGraphQL.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.secutix.DemoGraphQL.exception.GraphQLErrorAdapter;
import com.secutix.DemoGraphQL.model.Author;
import com.secutix.DemoGraphQL.model.Book;
import com.secutix.DemoGraphQL.repository.AuthorRepository;
import com.secutix.DemoGraphQL.repository.BookRepository;
import com.secutix.DemoGraphQL.resolver.BookResolver;
import com.secutix.DemoGraphQL.resolver.Mutation;
import com.secutix.DemoGraphQL.resolver.Query;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;

@Configuration
public class AppConfiguration {

	@Bean
	public BookResolver authorResolver(AuthorRepository authorRepository) {
		return new BookResolver(authorRepository);
	}

	@Bean
	public Query query(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Query(authorRepository, bookRepository);
	}

	@Bean
	public Mutation mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Mutation(authorRepository, bookRepository);
	}

	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors =
						errors.stream().filter(this::isClientError).collect(Collectors.toList());

				List<GraphQLError> serverErrors =
						errors.stream().filter(e -> !isClientError(e)).map(GraphQLErrorAdapter::new)
								.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}

	@Bean
	public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
		return args -> {
			final Author tolkien = new Author("J.R.R", "Tolkien");
			authorRepository.save(tolkien);

			bookRepository.save(new Book("The Fellowship of the Ring", "8439596200", 423, tolkien));
			bookRepository.save(new Book("The Two Towers", "8445071769", 352, tolkien));
			bookRepository.save(new Book("The Return of the King", "8439596243", 416, tolkien));

			final Author schildt = new Author("Herbert", "Schildt");
			authorRepository.save(schildt);

			bookRepository.save(new Book("Java: A Beginner's Guide, Sixth Edition", "0071809252", 728, schildt));
		};
	}
}
