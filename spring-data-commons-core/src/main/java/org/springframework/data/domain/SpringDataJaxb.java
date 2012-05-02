/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.hateoas.AbstractResource;
import org.springframework.hateoas.Link;

public class SpringDataJaxb {

	public static final String NAMESPACE = "http://spring-foo/xsd";

	static class PageRequestAdapter extends XmlAdapter<PageRequestDto, Pageable> {

		@Override
		public PageRequestDto marshal(Pageable request) throws Exception {

			SortDto sortDto = SortAdapter.INSTANCE.marshal(request.getSort());

			PageRequestDto dto = new PageRequestDto();
			dto.orders = sortDto == null ? Collections.<OrderDto> emptyList() : sortDto.orders;
			dto.page = request.getPageNumber();
			dto.size = request.getPageSize();

			return dto;
		}

		@Override
		public Pageable unmarshal(PageRequestDto v) throws Exception {

			if (v.orders.isEmpty()) {
				return new PageRequest(v.page, v.size);
			}

			SortDto sortDto = new SortDto();
			sortDto.orders = v.orders;
			Sort sort = SortAdapter.INSTANCE.unmarshal(sortDto);

			return new PageRequest(v.page, v.size, sort);
		}
	}

	static class SortAdapter extends XmlAdapter<SortDto, Sort> {

		private static final SortAdapter INSTANCE = new SortAdapter();

		@Override
		public SortDto marshal(Sort source) throws Exception {

			if (source == null) {
				return null;
			}

			SortDto dto = new SortDto();
			dto.orders = SpringDataJaxb.marshal(source, OrderAdapter.INSTANCE);

			return dto;
		}

		@Override
		public Sort unmarshal(SortDto source) throws Exception {
			return source == null ? null : new Sort(SpringDataJaxb.unmarshal(source.orders, OrderAdapter.INSTANCE));
		}
	}

	static class OrderAdapter extends XmlAdapter<OrderDto, Order> {

		private static final OrderAdapter INSTANCE = new OrderAdapter();

		/* (non-Javadoc)
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

		/* (non-Javadoc)
		 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
		 */
		@Override
		public Order unmarshal(OrderDto source) throws Exception {
			return source == null ? null : new Order(source.direction, source.property);
		}

	}

	public static class PageAdapter extends XmlAdapter<PageDto, Page<Object>> {

		@Override
		public PageDto marshal(Page<Object> source) throws Exception {

			if (source == null) {
				return null;
			}

			PageDto dto = new PageDto();
			dto.content = source.getContent();
			dto.addAll(getLinks(source));

			return dto;
		}

		/* 
		 * (non-Javadoc)
		 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
		 */
		@Override
		public Page<Object> unmarshal(PageDto v) throws Exception {

			return null;
		}

		protected List<Link> getLinks(Page<?> source) {
			return Collections.emptyList();
		}
	}

	@XmlType(namespace = SpringDataJaxb.NAMESPACE)
	public static class PageRequestDto {

		@XmlAttribute
		int page, size;

		@XmlElement(name = "order")
		List<OrderDto> orders = new ArrayList<OrderDto>();
	}

	@XmlType(namespace = SpringDataJaxb.NAMESPACE)
	public static class SortDto {

		@XmlElement(name = "order", namespace = SpringDataJaxb.NAMESPACE)
		List<OrderDto> orders = new ArrayList<OrderDto>();
	}

	public static class OrderDto {

		@XmlAttribute
		String property;
		@XmlAttribute
		Direction direction;
	}

	@XmlType(namespace = SpringDataJaxb.NAMESPACE)
	public static class PageDto extends AbstractResource {

		@XmlAnyElement
		@XmlElementWrapper(name = "content")
		List<Object> content;
	}

	static <T, S> List<T> unmarshal(Collection<S> source, XmlAdapter<S, T> adapter) throws Exception {

		if (source == null || source.isEmpty()) {
			return Collections.emptyList();
		}

		List<T> result = new ArrayList<T>(source.size());
		for (S element : source) {
			result.add(adapter.unmarshal(element));
		}
		return result;
	}

	static <T, S> List<S> marshal(Iterable<T> source, XmlAdapter<S, T> adapter) throws Exception {

		if (source == null) {
			return Collections.emptyList();
		}

		List<S> result = new ArrayList<S>();
		for (T element : source) {
			result.add(adapter.marshal(element));
		}
		return result;
	}
}
