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

package org.springframework.xd.dirt.module.transformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;


/**
 * Encapsulates the manifest data relating to colocate streams so they execute on the same node.
 * 
 * @author Mark Pollack
 */
public class StreamColocationSpec {

	private Map<String, List<String>> colocationManifestMap = new HashMap<String, List<String>>();

	public void add(String colocationGroupName, List<String> modulesToColocate) {
		Assert.notNull(colocationGroupName, "colocationGroupName cannot be null");
		Assert.notEmpty(modulesToColocate, "modulesToColocate list cannot be null");
		colocationManifestMap.put(colocationGroupName, modulesToColocate);
	}

	public Map<String, List<String>> getColocationManifestMap() {
		Map<String, List<String>> shallowCopy = new HashMap<String, List<String>>();
		shallowCopy.putAll(colocationManifestMap);
		return shallowCopy;
	}
}
