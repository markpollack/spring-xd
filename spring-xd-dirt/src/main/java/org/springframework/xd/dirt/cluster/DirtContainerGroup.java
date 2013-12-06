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

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.grid.ContainerGroup;
import org.springframework.util.Assert;


/**
 * Represents a grouping of DirtContainer nodes. This would often represent a subset of all the nodes in the cluster.
 * 
 * @author Mark Pollack
 */
public class DirtContainerGroup implements ContainerGroup<String, String, DirtContainerNode> {

	private ConcurrentHashMap<String, DirtContainerNode> nodes = new ConcurrentHashMap<String, DirtContainerNode>();

	private final String id;

	public DirtContainerGroup(String id) {
		Assert.notNull(id, "id cannot be null.");
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public boolean hasNode(String id) {
		return nodes.containsKey(id);
	}

	@Override
	public boolean addNode(DirtContainerNode node) {
		Assert.notNull(node, "Node must not be null");
		if (nodes.putIfAbsent(node.getId(), node) == null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public DirtContainerNode removeNode(String id) {
		return nodes.remove(id);
	}

	@Override
	public DirtContainerNode getNode(String id) {
		return nodes.get(id);
	}

	@Override
	public Collection<DirtContainerNode> getNodes() {
		return nodes.values();
	}

}
