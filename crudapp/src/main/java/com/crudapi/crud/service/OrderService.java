package com.crudapi.crud.service;

import com.crudapi.crud.dto.order.CreateOrderDTO;
import com.crudapi.crud.dto.order.OrderFilterDTO;
import com.crudapi.crud.dto.order.OrderResponseDTO;
import com.crudapi.crud.dto.order.UpdateOrderDTO;
import com.common.common.enums.sort.OrderSortField;
import com.common.common.enums.sort.SortDirection;
import com.crudapi.crud.exceptions.NotFoundException;
import com.crudapi.crud.mapper.entityMapper.OrderMapper;
import com.crudapi.crud.model.Order;
import com.crudapi.crud.specification.OrderSpecification;
import com.crudapi.crud.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderResponseDTO createOrder(CreateOrderDTO dto) {
        log.info("Создание заказа");
        log.debug("Детали входного DTO: {}", dto);

        Order order = orderMapper.mapToEntity(dto);
        order.setCreationDateTime(LocalDateTime.now());
        log.debug("Entity после маппинга: {}", order);

        Order savedOrder = orderRepository.save(order);
        log.info("Заказ создан с id={}", savedOrder.getId());

        return orderMapper.mapToDTO(savedOrder);
    }

    public OrderResponseDTO updateOrder(Long id, UpdateOrderDTO dto) {
        log.info("Обновление заказа id={}", id);
        log.debug("Детали UpdateOrderDTO: {}", dto);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Заказ не найден: id={}", id);
                    return new NotFoundException("Order not found");
                });

        order.setStatus(dto.getStatus());
        log.debug("Новый статус заказа: {}", dto.getStatus());

        Order updatedOrder = orderRepository.save(order);
        log.info("Заказ id={} успешно обновлён", updatedOrder.getId());

        return orderMapper.mapToDTO(updatedOrder);
    }

    public void deleteOrder(Long id) {
        log.info("Удаление заказа id={}", id);

        if (!orderRepository.existsById(id)) {
            log.error("Попытка удалить несуществующий заказ id={}", id);
            throw new NotFoundException("Order not found");
        }

        orderRepository.deleteById(id);
        log.info("Заказ id={} успешно удалён", id);
    }

    public OrderResponseDTO findById(Long id) {
        log.info("Получение заказа по id={}", id);

        return orderRepository.findById(id)
                .map(order -> {
                    log.debug("Найден заказ: {}", order);
                    return orderMapper.mapToDTO(order);
                })
                .orElseThrow(() -> {
                    log.error("Заказ с id={} не найден", id);
                    return new NotFoundException("Order not found");
                });
    }

    public Page<OrderResponseDTO> getAllOrders(OrderFilterDTO filter) {
        log.info("Получение списка заказов с фильтром: {}", filter);
        try {
            OrderSortField sortField = filter.getSortField() != null ? filter.getSortField() : OrderSortField.CREATION_DATE_TIME;
            Sort sort = Sort.by(sortField.getSortBy());
            sort = filter.getSortDirection() == SortDirection.DESC ? sort.descending() : sort.ascending();

            int page = filter.getPage() != null ? filter.getPage() : 0;
            int size = filter.getSize() != null ? filter.getSize() : 10;
            Pageable pageable = PageRequest.of(page, size, sort);

            log.debug("Параметры пагинации: page={}, size={}, sort={}", page, size, sort);

            Page<OrderResponseDTO> result = orderRepository.findAll(
                    OrderSpecification.filterOrder(
                            filter.getStartDate(),
                            filter.getEndDate(),
                            filter.getStatus()
                    ),
                    pageable
            ).map(orderMapper::mapToDTO);

            log.info("Найдено заказов: {}", result.getTotalElements());
            return result;
        } catch (Exception e) {
            // TODO: переписать правильную обработку ошибок при ошибке в фильтре
            log.error("Ошибка при получении заказов с фильтром {}: {}", filter, e.getMessage(), e);
            throw new IllegalArgumentException("Invalid filter: " + e.getMessage());
        }
    }
}
