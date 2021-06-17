package com.example.demo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
<<<<<<< HEAD
	Users findByUsersId(Integer users_id);

=======
	Users findByUsersId(Integer users_id);
>>>>>>> branch 'main' of git@github.com:TakumiMega/library.git
	Users findByUsersNameAndUsersBirthday(String usersName, Date usersBirthday);

}
