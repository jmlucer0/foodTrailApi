package com.foodtrail.foodtrail_api.repository;

import com.foodtrail.foodtrail_api.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p WHERE p.enviado = true")
    Page<Pedido> findByPedidosEnviados(Pageable pageable);

    @Query("SELECT p FROM Pedido p WHERE p.enviado = false")
    Page<Pedido> findPedidosNoEnviados(Pageable pageable);
}
