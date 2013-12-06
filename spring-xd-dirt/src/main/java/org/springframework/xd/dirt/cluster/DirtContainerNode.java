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

import org.springframework.data.grid.ContainerNode;
import org.springframework.util.Assert;


/**
 * Represents a node (process) of the XD cluster.
 * 
 * @author Mark Pollack
 */
public class DirtContainerNode implements ContainerNode<String> {

	private final String id;

	private final Address address;

	private final ResourceOffer resourceOffer;

	public DirtContainerNode(String id, Address address, ResourceOffer resourceOffer) {
		Assert.notNull(id, "id can not be null");
		Assert.notNull(address, "address can not be null");
		Assert.notNull(resourceOffer, "resourceOffer cannot be null.");
		this.id = id;
		this.address = address;
		this.resourceOffer = resourceOffer;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public Address getAddress() {
		return this.address;
	}

	public ResourceOffer getResourceOffer() {
		return this.resourceOffer;
	}

}
