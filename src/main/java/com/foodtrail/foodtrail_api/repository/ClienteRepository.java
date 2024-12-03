package com.foodtrail.foodtrail_api.repository;

import com.foodtrail.foodtrail_api.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c WHERE c.activo = true")
    Page<Cliente> findClientesActivos(Pageable pageable);
}
