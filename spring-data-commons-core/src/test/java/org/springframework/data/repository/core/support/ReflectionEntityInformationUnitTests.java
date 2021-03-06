/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may
import java.io.Serializable;
 not use this file except in compliance with the License.
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
package org.springframework.data.repository.core.support;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Test;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.core.EntityInformation;

/**
 * Unit tests for {@link ReflectionEntityInformation}.
 * 
 * @author Oliver Gierke
 */
public class ReflectionEntityInformationUnitTests {

	@Test
	public void discoversAnnotationOnField() {

		EntityInformation<Sample, Serializable> information = getEntityInformation(Sample.class);
		assertThat(information.getIdType(), is(typeCompatibleWith(String.class)));
	}

	/**
	 * @see DATACMNS-170
	 */
	@Test(expected = IllegalArgumentException.class)
	public void rejectsTypeWithoutAnnotatedField() {
		getEntityInformation(Unannotated.class);
	}

	private static <T> EntityInformation<T, Serializable> getEntityInformation(Class<T> type) {
		return new ReflectionEntityInformation<T, Serializable>(type, Id.class);
	}

	static class Sample {

		@Id
		String id;
	}

	static class Unannotated {

		String id;
	}
}
