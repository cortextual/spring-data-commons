/**
 * Central domain abstractions especially to be used in combination with the {@link org.springframework.data.repository.Repository} abstraction.
 *
 * @see org.springframework.data.repository.Repository
 */
@XmlSchema(xmlns = { @XmlNs(prefix = "spring-data", namespaceURI = org.springframework.data.domain.SpringDataJaxb.NAMESPACE) }, elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)
@XmlJavaTypeAdapters({
		@XmlJavaTypeAdapter(value = org.springframework.data.domain.SpringDataJaxb.PageRequestAdapter.class, type = Pageable.class),
		@XmlJavaTypeAdapter(value = org.springframework.data.domain.SpringDataJaxb.SortAdapter.class, type = Sort.class),
		@XmlJavaTypeAdapter(value = org.springframework.data.domain.SpringDataJaxb.PageAdapter.class, type = Page.class) })
package org.springframework.data.domain;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

