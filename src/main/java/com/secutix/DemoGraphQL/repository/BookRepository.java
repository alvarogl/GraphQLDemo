package com.secutix.DemoGraphQL.repository;

import org.springframework.data.repository.CrudRepository;

import com.secutix.DemoGraphQL.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}

