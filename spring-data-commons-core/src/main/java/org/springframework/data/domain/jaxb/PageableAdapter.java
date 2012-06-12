package org.springframework.data.domain.jaxb;

import java.util.Collections;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.data.domain.jaxb.SpringDataJaxb.SortDto;

class PageableAdapter extends XmlAdapter<PageRequestDto, Pageable> {

	/*
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public PageRequestDto marshal(Pageable request) throws Exception {

		SortDto sortDto = SortAdapter.INSTANCE.marshal(request.getSort());

		PageRequestDto dto = new PageRequestDto();
		dto.orders = sortDto == null ? Collections.<OrderDto> emptyList() : sortDto.orders;
		dto.page = request.getPageNumber();
		dto.size = request.getPageSize();

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
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
