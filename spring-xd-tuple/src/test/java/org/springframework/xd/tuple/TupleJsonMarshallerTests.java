/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.springframework.xd.tuple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author David Turanski
 * 
 */
public class TupleJsonMarshallerTests extends AbstractTupleMarshallerTests {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.xd.tuple.AbstractTupleMarshallerTests#getMarshaller()
	 */
	@Override
	protected TupleJsonMarshaller getMarshaller() {
		return new TupleJsonMarshaller();
	}

	@Test
	public void testComplexJson() throws IOException {
		Resource jsonFile = new ClassPathResource("/tweet.json");
		assertTrue(jsonFile.exists());

		String source = readJson(jsonFile);
		TupleJsonMarshaller marshaller = getMarshaller();
		Tuple tuple = marshaller.toTuple(source);
		assertEquals("Gabriel", tuple.getTuple("user").getString("name"));
		List<?> mentions = (List<?>) tuple.getTuple("entities").getValue("user_mentions");
		assertEquals(2, mentions.size());
		assertTrue(mentions.get(0) instanceof Tuple);
		Tuple t = (Tuple) mentions.get(0);
		assertEquals("someoneFollowed", t.getString("screen_name"));
	}

	@Test
	public void testJsonWithArrays() throws IOException {
		Resource jsonFile = new ClassPathResource("/jsonWithArrays.json");
		assertTrue(jsonFile.exists());
		String json = readJson(jsonFile);


		TupleJsonMarshaller marshaller = getMarshaller();
		Tuple tuple = marshaller.toTuple(json);
		List<Tuple> body = (List<Tuple>) tuple.getValue("body");
		Tuple t2 = body.get(0).getTuple("har");
		Tuple t3 = t2.getTuple("log");
		List<?> pages = (List<?>)t3.getValue("pages");
		assertEquals(2, pages.size());

		//Convert back to json.
		String convertedJson = prettyPrintJson(marshaller.fromTuple(tuple));
		assertEquals(json, convertedJson);
	}

	private String prettyPrintJson(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		JsonNode jsonNode = mapper.readTree(json);
		StringWriter sw = new StringWriter();
		mapper.writeValue(sw, jsonNode);
		sw.append(System.lineSeparator());
		return sw.toString();
	}
}
