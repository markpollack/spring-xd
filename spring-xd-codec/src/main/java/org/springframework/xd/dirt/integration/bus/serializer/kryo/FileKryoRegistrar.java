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

import java.util.Collections;
import java.util.List;

import com.esotericsoftware.kryo.Registration;

/**
 * A {@link KryoRegistrar} used to register a File serializer.
 * @author David Turanski
 * @since 1.2
 */
public class FileKryoRegistrar extends AbstractKryoRegistrar {

	private final static int FILE_REGISTRATION_ID = 40;

	private final FileSerializer fileSerializer = new FileSerializer();

	@Override
	public List<Registration> getRegistrations() {
		return Collections.singletonList(new Registration(java.io.File.class, fileSerializer, FILE_REGISTRATION_ID));
	}
}
