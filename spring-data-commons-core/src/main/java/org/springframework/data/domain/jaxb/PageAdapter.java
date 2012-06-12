package org.springframework.data.domain.jaxb;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.hateoas.Link;

public class PageAdapter extends XmlAdapter<PageDto, Page<Object>> {

	/*
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public PageDto marshal(Page<Object> source) throws Exception {

		if (source == null) {
			return null;
		}

		PageDto dto = new PageDto();
		dto.content = source.getContent();
		dto.add(getLinks(source));

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
