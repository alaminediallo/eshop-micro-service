package com.lamine.isi.orderservice.controller;

import com.lamine.isi.orderservice.client.IUserClient;
import com.lamine.isi.orderservice.dto.OrderMapper;
import com.lamine.isi.orderservice.dto.OrderDTO;
import com.lamine.isi.orderservice.dto.UserDTO;
import com.lamine.isi.orderservice.model.Order;
import com.lamine.isi.orderservice.service.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final IUserClient userClient;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAll();
        List<OrderDTO> orderDTOs = OrderMapper.toOrderDTOList(orders);
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        OrderDTO orderDTO = OrderMapper.toOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        // Définir la date de commande à maintenant si elle n'est pas fournie
        if (orderDTO.getOrderDate() == null) {
            orderDTO.setOrderDate(LocalDateTime.now());
        }

        // Vérifier que le client existe avant de créer la commande
        if (orderDTO.getCustomerId() != null) {
            UserDTO user = userClient.getUserById(orderDTO.getCustomerId());
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
        }

        Order order = OrderMapper.toOrder(orderDTO);
        Order savedOrder = orderService.save(order);
        OrderDTO savedOrderDTO = OrderMapper.toOrderDTO(savedOrder);
        return new ResponseEntity<>(savedOrderDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id,
                                                      @RequestBody Map<String, String> statusUpdate) {
        // Extraction du statut à partir du payload
        String newStatus = statusUpdate.get("status");
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Récupération de la commande existante
        Order existingOrder = orderService.getById(id);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }

        // Mise à jour du statut
        existingOrder.setStatus(newStatus);

        // Sauvegarde des modifications
        Order updatedOrder = orderService.save(existingOrder);
        OrderDTO updatedOrderDTO = OrderMapper.toOrderDTO(updatedOrder);

        return ResponseEntity.ok(updatedOrderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        // Vérification que la commande existe
        Order existingOrder = orderService.getById(id);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }

        orderService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // recupere les commande d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getByUserId(userId);
        List<OrderDTO> orderDTOs = OrderMapper.toOrderDTOList(orders);
        return ResponseEntity.ok(orderDTOs);
    }
}