package com.company.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.apirest.models.entity.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
	public Usuario findByUsername(String username);
}
