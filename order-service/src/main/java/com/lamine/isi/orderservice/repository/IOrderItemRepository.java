package com.lamine.isi.orderservice.repository;

import com.lamine.isi.orderservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

//    OrderItem findByOrder_Customer_Id(Long orderCustomerId);
}