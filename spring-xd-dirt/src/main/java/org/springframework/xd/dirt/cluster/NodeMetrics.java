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


/**
 * Represents attributes of a node that are frequently changing such as memory, thread, CPU usage exposed by
 * java.lang.management.MemoryMXBean and ThreadMXBEan and OperatingSystemMXBean.
 * 
 * More detailed information related to the machine can be exposed using the Hyperic SIGAR project
 * 
 * https://github.com/hyperic/sigar
 * 
 * Note this class contains just one property now as a spike placeholder.
 * 
 * @author Mark Pollack
 */
public class NodeMetrics {

	// TODO this will need to scale up with a copy ctor with metric information over-ridden from
	// an instance of NodeMetrics send via node heartbeat messages.

	private double systemLoadAverage;

	public NodeMetrics() {

	}

	public NodeMetrics(double systemLoadAverge) {
		this.systemLoadAverage = systemLoadAverge;
	}

	public double getSystemLoadAverage() {
		return this.systemLoadAverage;
	}

}
