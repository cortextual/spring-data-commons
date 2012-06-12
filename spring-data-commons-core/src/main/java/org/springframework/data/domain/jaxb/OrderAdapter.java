package org.springframework.data.domain.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

public class OrderAdapter extends XmlAdapter<OrderDto, Order> {

	public static final OrderAdapter INSTANCE = new OrderAdapter();

	/* 
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public OrderDto marshal(Order order) throws Exception {

		if (order == null) {
			return null;
		}

		OrderDto dto = new OrderDto();
		dto.direction = order.getDirection();
		dto.property = order.getProperty();
		return dto;
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Order unmarshal(OrderDto source) throws Exception {
		return source == null ? null : new Order(source.direction, source.property);
	}
}
