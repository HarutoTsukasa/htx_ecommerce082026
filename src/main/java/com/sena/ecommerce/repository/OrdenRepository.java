package com.sena.ecommerce.repository;

import com.sena.ecommerce.model.Orden;
import com.sena.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

	List<Orden> findByUsuarioOrderByFechacreacionDesc(Usuario usuario);
}
