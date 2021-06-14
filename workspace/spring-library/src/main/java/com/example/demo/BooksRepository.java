package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Integer> {
	Books findByBooksNameAndBooksAuthor(String books_id,String books_author);

}
