package com.rain.ordermanagement.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rain.ordermanagement.dto.CodesDto;
import com.rain.ordermanagement.dto.OrderDto;
import com.rain.ordermanagement.model.ItemSummary;
import com.rain.ordermanagement.model.ShopOrder;
import com.rain.ordermanagement.model.Item;
import com.rain.ordermanagement.model.OrderStatus;
import com.rain.ordermanagement.model.Summary;
import com.rain.ordermanagement.model.User;
import com.rain.ordermanagement.repository.OrderItemRepository;
import com.rain.ordermanagement.repository.OrderRepository;
import com.rain.ordermanagement.repository.UserRepository;

@Service
public class OrderService {
	private OrderRepository orderRepository;
	private UserRepository userRepository;
	private OrderItemRepository orderItemRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository, UserRepository userRepository,
			OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.userRepository = userRepository;
	}

	public OrderDto addOrder(OrderDto order) {
		ShopOrder created = null;
		User user = userRepository.findByUsername(order.getOwnerUsername());
		if (user == null) {
			throw new IllegalArgumentException(String
					.format("Cannot place order for not existent user with username: %s", order.getOwnerUsername()));
		}
		Map<String, Integer> codes;
		codes = listToMap(Arrays.asList(order.getItemCodes()));
		String usernameOrderRefPart = order.getOwnerUsername().length() > 6 ? order.getOwnerUsername().substring(0, 5)
				: order.getOwnerUsername().substring(0, order.getOwnerUsername().length() - 1);
		String timeOrderRefPart = Long.toString(Date.from(Instant.now()).getTime());
		Set<Item> items = orderItemRepository.findByCodeIn(codes.keySet());
		if (items == null || items.size() != order.getItemCodes().length) {
			List<String> notfoundCodes = null;
			if (items != null) {
				notfoundCodes = codes.keySet().stream()
						.filter(code -> !items.stream().map(Item::getCode).collect(Collectors.toList()).contains(code))
						.collect(Collectors.toList());
			}
			throw new IllegalArgumentException(String
					.format("Cannot place order, some items in order basket do not exist, codes: %s", notfoundCodes));
		}
		Set<ItemSummary> itemSummaries = new HashSet<>();
		for (Item item : items) {
			itemSummaries.add(new ItemSummary(item, codes.get(item.getCode())));
		}
		Summary summary = new Summary(itemSummaries);
		ShopOrder newOrder = new ShopOrder(user.getAccount(), usernameOrderRefPart + timeOrderRefPart,
				OrderStatus.CREATED);
		newOrder.getSummaries().add(summary);
		created = orderRepository.save(newOrder);
		return toDto(created);

	}

	private Map<String, Integer> listToMap(List<CodesDto> list) {
		Map<String, Integer> map = new HashMap<>();
		for (CodesDto code : list) {
			if (map.containsKey(code.getCode())) {
				map.put(code.getCode(), map.get(code.getCode()) + code.getQuantity());
			} else {
				map.put(code.getCode(), code.getQuantity());
			}

		}
		return map;
	}

	public OrderDto updateOrder(OrderDto orderDto) {
		ShopOrder order = fromDto(orderDto);
		order.setStatus(orderDto.getStatus());
		return toDto(order);
	}

	public OrderDto findOrder(Long orderId) {
		Optional<ShopOrder> orderOption = orderRepository.findById(orderId);
		if (!orderOption.isPresent()) {
			throw new IllegalArgumentException(String.format("Order with ID: %s, not found. ", orderId));
		}
		return toDto(orderOption.get());
	}

	private OrderDto toDto(ShopOrder order) {
		return new OrderDto(order.getDate(), order.getReference(), order.getAccount().getOwner().getUsername(),
				order.getStatus(), order.getAccount().getId(), order.getAccount().getOwner().getId());
	}

	private ShopOrder fromDto(OrderDto dto) {
		return orderRepository.findByReferenceAndAccountOwnerUsername(dto.getReference(), dto.getOwnerUsername());
	}

	public OrderDto findByReference(String orderReference) {
		if(!orderRepository.existsByReference(orderReference)) {
			throw new IllegalArgumentException(String.format("Order with Ref: %s, not found. ", orderReference));
		}
		return toDto(orderRepository.findByReference(orderReference));
	}
}
