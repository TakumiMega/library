package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {
	Books findByBooksNameAndBooksAuthor(String booksName,String booksAuthor);
	Books findByBooksId(int booksId);
}
