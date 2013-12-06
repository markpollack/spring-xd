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

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * 
 * @author Mark Pollack
 */
public class DirtClusterTests {


	@Test
	public void simpleClusterTests() {
		DirtCluster cluster = new DirtCluster();
		cluster.addNode(ClusterUtils.createNode("alphaNode"));
		cluster.addNode(ClusterUtils.createNode("betaNode"));
		assertEquals(2, cluster.getNodes().size());
	}

	@Test
	public void groupingClusterTests() {
		DirtGroupNameResolver groupResolver = new SpelDirtGroupNameResolver("['groupName'] == 'pepsi'", "pepsi");
		DirtCluster cluster = new DirtCluster(groupResolver);
		cluster.addNode(ClusterUtils.createNode("alphaNode", "groupName", "pepsi"));
		cluster.addNode(ClusterUtils.createNode("betaNode", "groupName", "pepsi"));
		cluster.addNode(ClusterUtils.createNode("gammaNode", "groupName", "coke"));
		cluster.addNode(ClusterUtils.createNode("deltaNode", "groupName", "coke"));
		assertEquals(4, cluster.getNodes().size());
		// rule is only for pepsi
		assertEquals(1, cluster.getGroups().size());


	}
}
