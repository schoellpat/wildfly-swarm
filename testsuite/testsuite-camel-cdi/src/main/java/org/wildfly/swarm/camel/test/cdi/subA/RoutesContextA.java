/*
 * #%L
 * Camel CDI :: Tests
 * %%
 * Copyright (C) 2016 RedHat
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.swarm.camel.test.cdi.subA;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;
import org.apache.camel.component.mock.MockEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses contextA with explicit context names on all Camel annotations
 */
@ContextName("contextA")
public class RoutesContextA extends RouteBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(RoutesContextA.class);

    @EndpointInject(uri = "mock:A.b", context = "contextA")
    public MockEndpoint b;

    @ContextName("contextA")
    @Inject @Uri(value = "seda:A.a")
    Endpoint a;

    @ContextName("contextA")
    @Inject @Uri(value = "seda:A.a")
    ProducerTemplate producer;

    @Override
    public void configure() throws Exception {
        LOG.info("Adding route from " + a + " to " + b);
        from(a).to(b);
    }

    public void sendMessages() {
        for (Object expectedBody : Constants.EXPECTED_BODIES_A) {
            LOG.info("Sending " + expectedBody + " to " + producer.getDefaultEndpoint());
            producer.sendBody(expectedBody);
        }
    }
}
