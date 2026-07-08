package com.hamch.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamch.notificationservice.entities.OrderEventEntity;
//import java.util.Optional;

//@CrossOrigin("*")

public interface OrderEventEntityRepository extends JpaRepository<OrderEventEntity, Long> {
  boolean existsByEventId(String eventId);
}
