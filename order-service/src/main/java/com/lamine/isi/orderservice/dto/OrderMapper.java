package com.lamine.isi.orderservice.dto;

import com.lamine.isi.orderservice.model.Order;
import com.lamine.isi.orderservice.model.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    // Conversion de Order en OrderDTO
    public static OrderDTO toOrderDTO(Order order) {
        if (order == null) {
            return null;
        }

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());

        // Définir l'ID du client au lieu de l'objet client complet
        if (order.getCustomer() != null) {
            dto.setCustomerId(order.getCustomer());
        }

        // Ajouter les items de la commande
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            dto.setItems(toOrderItemDTOList(order.getItems()));
        }

        return dto;
    }

    // Conversion de OrderItem en OrderItemDTO
    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());

        if (orderItem.getOrder() != null) {
            dto.setOrderId(orderItem.getOrder().getId());
        }

        if (orderItem.getProduct() != null) {
            dto.setProductId(orderItem.getProduct());
        }

        return dto;
    }

    // Conversion de OrderDTO en Order
    public static Order toOrder(OrderDTO dto) {
        if (dto == null) {
            return null;
        }

        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());

        // Si un customerId est fourni, créer un objet User avec cet ID
        if (dto.getCustomerId() != null) {
            UserDTO customer = new UserDTO();
            customer.setId(dto.getCustomerId());
            order.setCustomer(customer.getId());
        }

        // Convertir les items DTO en entités OrderItem
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<OrderItem> items = new ArrayList<>();
            for (OrderItemDTO itemDTO : dto.getItems()) {
                OrderItem item = toOrderItem(itemDTO);
                item.setOrder(order);

                // Si un productId est fourni, créer un objet Product avec cet ID
                if (itemDTO.getProductId() != null) {
                    ProductDTO product = new ProductDTO();
                    product.setId(itemDTO.getProductId());
                    item.setProduct(product.getId());
                }

                items.add(item);
            }
            order.setItems(items);
        }

        return order;
    }

    // Conversion de OrderItemDTO en OrderItem
    public static OrderItem toOrderItem(OrderItemDTO dto) {
        if (dto == null) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());

        if (dto.getQuantity() != null) {
            orderItem.setQuantity(dto.getQuantity());
        }

        if (dto.getUnitPrice() != null) {
            orderItem.setUnitPrice(dto.getUnitPrice());
        }

        return orderItem;
    }

    // Conversion d'une liste de Order en liste de OrderDTO
    public static List<OrderDTO> toOrderDTOList(List<Order> orders) {
        if (orders == null) {
            return List.of();
        }
        return orders.stream()
                .map(OrderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    // Conversion d'une liste de OrderItem en liste de OrderItemDTO
    public static List<OrderItemDTO> toOrderItemDTOList(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return List.of();
        }
        return orderItems.stream()
                .map(OrderMapper::toOrderItemDTO)
                .collect(Collectors.toList());
    }
}