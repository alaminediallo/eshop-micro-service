package com.lamine.isi.orderservice.service;

import com.lamine.isi.orderservice.client.IProductClient;
import com.lamine.isi.orderservice.client.IUserClient;
import com.lamine.isi.orderservice.dto.ProductDTO;
import com.lamine.isi.orderservice.dto.UserDTO;
import com.lamine.isi.orderservice.exception.NotFoundException;
import com.lamine.isi.orderservice.model.Order;
import com.lamine.isi.orderservice.model.OrderItem;
import com.lamine.isi.orderservice.repository.IOrderItemRepository;
import com.lamine.isi.orderservice.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IOrderItemRepository orderItemRepository;
    private final IUserClient userClient;
    private final IProductClient productClient;

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Commande non trouvée avec l'ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByUserId(Long userId) {
        if (userClient.getUserById(userId) == null) {
            throw new NotFoundException("Utilisateur non trouvé avec l'ID: " + userId);
        }
        return orderRepository.findByCustomer(userId);
    }

    @Override
    public Order save(Order order) {
        if (order.getCustomer() != null) {
            UserDTO user = userClient.getUserById(order.getCustomer());
            if (userClient.getUserById(user.getId()) == null) {
                throw new NotFoundException("Utilisateur non trouvé avec l'ID: " + user.getId());
            }
        }
        return orderRepository.save(order);
    }

    @Override
    public Order update(Long id, Order order) {
        if (!orderRepository.existsById(id)) {
            throw new NotFoundException("Commande non trouvée avec l'ID: " + id);
        }
        order.setId(id);
        return orderRepository.save(order);
    }

    @Override
    public Order partialUpdate(Long id, Map<String, Object> updates) {
        Order order = getById(id);

        if (updates.containsKey("status")) {
            order.setStatus((String) updates.get("status"));
        }
        if (updates.containsKey("customerId")) {
            UserDTO userDTO = userClient.getUserById((Long) updates.get("customerId"));
            if (userDTO.getId() == null) {
                throw new NotFoundException(
                        "Utilisateur non trouvé avec l'ID: " + updates.get("customerId"));
            }
            order.setCustomer(userDTO.getId());
        }

        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new NotFoundException("Commande non trouvée avec l'ID: " + id);
        }
        // Supprimer d'abord les articles de commande associés
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(id);
        orderItemRepository.deleteAll(orderItems);

        // Puis supprimer la commande
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderItem> getOrderItems(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Commande non trouvée avec l'ID: " + orderId);
        }
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public OrderItem addOrderItem(Long orderId, OrderItem orderItem) {
        Order order = getById(orderId);

        if (orderItem.getProduct() != null) {
            ProductDTO product = productClient.getProductById(orderItem.getProduct());
            if (product.getId() == null) {
                throw new NotFoundException(
                        "Produit non trouvé avec l'ID: " + orderItem.getProduct());
            }

            orderItem.setProduct(product.getId());
//            orderItem.getUnitPrice();
        }

        orderItem.setOrder(order);
        OrderItem savedItem = orderItemRepository.save(orderItem);

        orderRepository.save(order);

        return savedItem;
    }

    @Override
    public void removeOrderItem(Long orderId, Long orderItemId) {
        Order order = getById(orderId);

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Article de commande non trouvé avec l'ID: " + orderItemId));

        if (!orderItem.getOrder().getId().equals(orderId)) {
            throw new IllegalArgumentException("L'article de commande n'appartient pas à cette commande");
        }

        orderItemRepository.delete(orderItem);

        orderRepository.save(order);
    }
}