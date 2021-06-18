package com.example.demo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	Users findByUsersId(Integer users_id);

	Users findByUsersNameAndUsersBirthday(String usersName, Date usersBirthday);

}
