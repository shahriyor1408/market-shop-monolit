package com.company.telegrambotapp.repository.auth;

import com.company.telegrambotapp.domains.auth.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {
}
