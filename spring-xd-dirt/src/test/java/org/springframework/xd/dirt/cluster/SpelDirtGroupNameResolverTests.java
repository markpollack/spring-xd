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
import static org.junit.Assert.assertNull;

import org.junit.Test;


/**
 * Tests for SpelDirtGroupNameResolver
 * 
 * @author Mark Pollack
 */
public class SpelDirtGroupNameResolverTests {

	/**
	 * This test should result in no groups being matched for the provided node.
	 */
	@Test
	public void testEmptyExpression() {
		DirtContainerNode node = ClusterUtils.createNode("alphaNode", "groupName", "pepsi");
		SpelDirtGroupNameResolver resolver = new SpelDirtGroupNameResolver("", "emptyGroup");
		String groupName = resolver.resolve(node);
		assertNull(groupName);
	}

	@Test
	public void testValidEpression() {
		DirtContainerNode node = ClusterUtils.createNode("alphaNode", "groupName", "pepsi");
		SpelDirtGroupNameResolver resolver = new SpelDirtGroupNameResolver("['groupName'] == 'pepsi'", "pepsiGroup");
		String groupName = resolver.resolve(node);
		assertEquals("pepsiGroup", groupName);
	}

}
