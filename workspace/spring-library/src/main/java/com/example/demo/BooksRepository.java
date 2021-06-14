package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Integer> {
	Books findByBooksNameAndBooksAuthor(String books_name,String books_author);

}
