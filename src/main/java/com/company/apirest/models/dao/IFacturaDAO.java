package com.company.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.apirest.models.entity.Factura;

public interface IFacturaDAO extends CrudRepository<Factura, Long>{
	
}
