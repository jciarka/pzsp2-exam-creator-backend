package com.PZSP2.PFIMJ.repositories;

import java.util.List;
import java.util.Optional;

import com.PZSP2.PFIMJ.db.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository extends JpaRepository<User, Long> {
    public Optional<User> findById(@Param("id") Long id);

    @Query(value = "SELECT u FROM User u WHERE u.id = :id")
    public Optional<User> myFindById(@Param("id") Long id);

    public List<User> findUsersByRolesId(Long rolesId);

    public Optional<User> findByEmail(String email);

    public List<User> findByEmailContaining(String email);
}