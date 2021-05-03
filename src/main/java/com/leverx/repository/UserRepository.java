package com.leverx.repository;

import com.leverx.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByApprovedIsTrue();

    List<User> findAllByApprovedIsNull();

    Optional<User> findByIdAndApprovedTrue(Integer id);

    Optional<User> findByIdAndApprovedIsNull(Integer id);

    Optional<User> findByEmail(String email);
}
