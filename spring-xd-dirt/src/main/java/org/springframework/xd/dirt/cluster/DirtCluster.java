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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.data.grid.support.AbstractContainerGridGroups;
import org.springframework.util.Assert;


/**
 * Represents all the nodes in the cluster and is responsible for groups nodes into groups using a
 * DirtGroupNameResolver.
 * 
 * @author Mark Pollack
 */
public class DirtCluster extends AbstractContainerGridGroups<String, String, DirtContainerNode, DirtContainerGroup>
		implements Cluster {

	private final Log logger = LogFactory.getLog(this.getClass());

	private DirtGroupNameResolver groupResolver;

	public DirtCluster() {

	}

	public DirtCluster(DirtGroupNameResolver groupResolver) {
		Assert.notNull(groupResolver, "groupResolver cannot be null.");
		this.groupResolver = groupResolver;
	}

	@Override
	public boolean addNode(DirtContainerNode node) {
		if (groupResolver != null) {
			String groupName = groupResolver.resolve(node);

			if (groupName != null) {
				DirtContainerGroup group = this.getGroup(groupName);
				if (group == null) {
					DirtContainerGroup newGroup = new DirtContainerGroup(groupName);
					newGroup.addNode(node);
					notifyNodeAdded(newGroup, node);
					this.addGroup(newGroup);
				}
				else {
					group.addNode(node);
					notifyNodeAdded(group, node);
				}
			}
		}
		return super.addNode(node);

	}

	@Override
	public boolean removeNode(String id) {
		DirtContainerNode node = null;
		for (DirtContainerGroup group : getGroups()) {
			if ((node = group.removeNode(id)) != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Removed member " + node);
				}
				notifyNodeRemoved(group, node);
				break;
			}
		}
		return super.removeNode(id);
	}

}
