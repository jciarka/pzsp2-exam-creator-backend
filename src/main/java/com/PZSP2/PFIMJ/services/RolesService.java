package com.PZSP2.PFIMJ.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.PZSP2.PFIMJ.db.entities.Role;
import com.PZSP2.PFIMJ.repositories.IRolesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

	@Autowired
	IRolesRepository repo;

	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		repo.findAll().forEach(roles::add);
		return roles;
	}

	public Role get(Long id) {
		return repo.getById(id);
	}

	public Role add(Role role) {
		if (role.getId() == 0L) {
			return null;
		}
		return repo.save(role);
	}

	public void delete(Long id) {
		Role toDelete = repo.getById(id);
		if (toDelete != null) {
			repo.delete(toDelete);
		}
	}

	public void delete(String name) {
		Optional<Role> toDelete = repo.findByName(name);
		if (!toDelete.isEmpty()) {
			repo.delete(toDelete.get());
		}
	}

}
