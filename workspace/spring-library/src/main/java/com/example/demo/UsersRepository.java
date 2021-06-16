package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
public interface UsersRepository extends JpaRepository<Users, Integer>{
	Users findByUsersId(Integer users_id);
=======
public interface UsersRepository extends JpaRepository <Users, Integer> {

>>>>>>> branch 'main' of git@github.com:TakumiMega/library.git
}
