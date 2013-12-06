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

import java.util.HashMap;
import java.util.Map;


/**
 * Utility class for creating nodes.
 * 
 * @author Mark Pollack
 */
public abstract class ClusterUtils {

	public static DirtContainerNode createNode(String nodeName) {
		Map<String, Object> nodeAttributes = new HashMap<String, Object>();
		ResourceOffer resourceOffer = new ResourceOffer(nodeAttributes);
		Address address = new Address("direct://my-exchange/routing-key");
		return new DirtContainerNode(nodeName, address, resourceOffer);
	}

	public static DirtContainerNode createNode(String nodeName, String nodeAttributeKey, String noteAttributeValue) {
		Map<String, Object> nodeAttributes = new HashMap<String, Object>();
		nodeAttributes.put(nodeAttributeKey, noteAttributeValue);
		ResourceOffer resourceOffer = new ResourceOffer(nodeAttributes);
		Address address = new Address("direct://my-exchange/routing-key");
		return new DirtContainerNode(nodeName, address, resourceOffer);
	}
}
