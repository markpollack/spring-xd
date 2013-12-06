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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;


/**
 * Represents the resources a node has to offer so a decision can be made if it should be assigned work.
 * 
 * Some of the attributes in the ResourceOffer are set when the node starts and would not be expected to change, though
 * they can if a node. These are accessed using the getAttribute method. Other attributes are expected to change at
 * runtime as they typically measure system information such as free heap memory or Node specific information such as
 * number of currently running modules.
 * 
 * TODO; Some well known attributes should have dedicated getters. See
 * http://research.cs.wisc.edu/htcondor/manual/v7.8/11_Appendix_A.html in the section for a potential list of useful
 * attributes that can be added.
 * 
 * @author Mark Pollack
 */
public class ResourceOffer implements MatchAttributes {


	private final Map<String, Object> nodeAttributes;

	private String requirement;

	private int rank;

	private NodeMetrics nodeMetrics = new NodeMetrics();

	public ResourceOffer(Map<String, Object> nodeAttributes) {
		Assert.notNull(nodeAttributes, "nodeAttributes cannot be null");
		this.nodeAttributes = nodeAttributes;
	}

	public Object getAttribute(String key) {
		return nodeAttributes.get(key);
	}

	public Object setAttribute(String key, Object value) {
		return nodeAttributes.put(key, value);
	}

	public Map<String, Object> getAttributes() {
		return new ConcurrentHashMap<String, Object>(nodeAttributes);
	}

	@Override
	public String getRequirements() {
		return this.requirement;
	}

	@Override
	public int getRank() {
		return this.rank;
	}

	public NodeMetrics getNodeMetrics() {
		return this.nodeMetrics;
	}
}
