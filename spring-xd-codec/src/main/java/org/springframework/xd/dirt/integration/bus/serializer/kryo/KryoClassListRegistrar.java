/*
 * Copyright 2015 the original author or authors.
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

package org.springframework.xd.dirt.integration.bus.serializer.kryo;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import reactor.util.CollectionUtils;

import org.springframework.util.Assert;

/**
 * A {@link KryoRegistrar} used to register a
 * list of Java classes. This assigns a sequential registration ID starting with an initial value (50 by default), but
 * may be configured. This is easiest to set up but requires that every server node be configured with the identical
 * list in the same order.
 * @author David Turanski
 * @since 1.1
 */
public class KryoClassListRegistrar extends AbstractKryoRegistrar {

	private final List<Class> registeredClasses;

	private int initialValue = 50;

	/**
	 * @param classes the list of classes to register
	 */
	public KryoClassListRegistrar(List<Class> classes) {
		this.registeredClasses = classes;
	}

	/**
	 * Set the inital ID value. Classes in the list will be sequentially assigned an ID starting with this value
	 * (default is 50).
	 * @param initialValue the initial value
	 */
	public void setInitialValue(int initialValue) {
		Assert.isTrue(initialValue >= MIN_REGISTRATION_VALUE, "'initialValue' must be >= " +
				MIN_REGISTRATION_VALUE);
		this.initialValue = initialValue;
	}


	@Override
	public List<Registration> getRegistrations() {
		List<Registration> registrations = new ArrayList<>();
		if (!CollectionUtils.isEmpty(registeredClasses)) {
			for (int i = 0; i < registeredClasses.size(); i++) {
				registrations.add(new Registration(registeredClasses.get(i), kryo.getSerializer(registeredClasses.get
						(i)), i + initialValue));
			}
		}
		return registrations;
	}

}
