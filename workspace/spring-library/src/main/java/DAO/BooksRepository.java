package DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import Bean.Books;

public interface BooksRepository extends JpaRepository<Books, Integer> {

}
