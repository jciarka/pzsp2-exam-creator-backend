package com.PZSP2.PFIMJ.repositories;

import java.util.List;
import java.util.Optional;

import com.PZSP2.PFIMJ.db.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolesRepository extends JpaRepository<Role, Long> {
    public List<Role> findRolesByUsersId(Long usersId);
    public Optional<Role> findByName(String name);
}
