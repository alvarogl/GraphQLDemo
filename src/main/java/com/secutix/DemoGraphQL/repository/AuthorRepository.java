package com.secutix.DemoGraphQL.repository;

import org.springframework.data.repository.CrudRepository;

import com.secutix.DemoGraphQL.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
