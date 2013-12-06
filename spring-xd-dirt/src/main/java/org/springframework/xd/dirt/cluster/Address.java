/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.xd.dirt.cluster;

import org.springframework.util.Assert;


/**
 * Represents an address to send a message to a specific node.
 * 
 * @author Mark Pollack
 */
public class Address {

	private String address;

	public Address(String address) {
		Assert.notNull(address, "address cannot be null.");
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}
}
