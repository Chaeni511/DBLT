package com.dopamines.backend.account.repository;

import com.dopamines.backend.account.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : Hunseong-Park
 * @date : 2022-07-04
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    boolean existsByName(String roleName);
}
