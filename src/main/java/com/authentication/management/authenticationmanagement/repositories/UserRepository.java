package com.authentication.management.authenticationmanagement.repositories;

import com.authentication.management.authenticationmanagement.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmailId(String emailId);
}
