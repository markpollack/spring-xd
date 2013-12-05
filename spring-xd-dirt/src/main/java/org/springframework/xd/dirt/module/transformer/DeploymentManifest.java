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
import java.util.Map;

import org.springframework.util.Assert;


/**
 * Describes how the basic stream definition will be transformed to a physical deployment model taking into accout
 * co-location of modules, data partitioning, module instances, and module placement on nodes.
 * 
 * Intended to be mapped onto a YAML file with separate sections for individual jobs, steams.
 * 
 * @author Mark Pollack
 */
public class DeploymentManifest {

	private Map<String, StreamColocationSpec> streamCompositionSpecMap = new HashMap<String, StreamColocationSpec>();

	/*--- formatting messed this up...
	streams: 
	deployment1:
	myStream: 
	    coloate: 
	      - 
	        colocateBandC: 
	          - b
	          - c
	        colocateDandE: 
	          - d
	          - e
	 */

	public void addColocation(String streamName, StreamColocationSpec streamColocationSpec) {
		Assert.notNull(streamName, "streamName cannot be null");
		Assert.notNull(streamColocationSpec, "streamCompositionSpec cannot be null");
		streamCompositionSpecMap.put(streamName, streamColocationSpec);
	}

	public boolean hasEmptyStreamColocationSpecMap() {
		return streamCompositionSpecMap.isEmpty();
	}

	public Map<String, StreamColocationSpec> getStreamColocationSpecMap() {
		Map<String, StreamColocationSpec> shallowCopy = new HashMap<String, StreamColocationSpec>();
		shallowCopy.putAll(streamCompositionSpecMap);
		return shallowCopy;
	}


}
