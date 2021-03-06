/*
 * Copyright 2009 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.netty.channel;

import java.util.Map;
import java.util.Map.Entry;

import org.jboss.netty.buffer.ChannelBufferFactory;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.socket.SocketChannelConfig;
import org.jboss.netty.util.internal.ConversionUtil;

/**
 * The default {@link SocketChannelConfig} implementation.
 *
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 *
 * @version $Rev$, $Date$
 *
 */
public class DefaultChannelConfig implements ChannelConfig {

    private volatile ChannelBufferFactory bufferFactory = HeapChannelBufferFactory.getInstance();
    private volatile int connectTimeoutMillis = 10000; // 10 seconds

    /**
     * Creates a new instance.
     */
    public DefaultChannelConfig() {
        super();
    }

    @Override
    public void setOptions(Map<String, Object> options) {
        for (Entry<String, Object> e: options.entrySet()) {
            setOption(e.getKey(), e.getValue());
        }
    }

    @Override
    public boolean setOption(String key, Object value) {
        if (key.equals("pipelineFactory")) {
            setPipelineFactory((ChannelPipelineFactory) value);
        } else if (key.equals("connectTimeoutMillis")) {
            setConnectTimeoutMillis(ConversionUtil.toInt(value));
        } else if (key.equals("bufferFactory")) {
            setBufferFactory((ChannelBufferFactory) value);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    @Override
    public ChannelBufferFactory getBufferFactory() {
        return bufferFactory;
    }

    @Override
    public void setBufferFactory(ChannelBufferFactory bufferFactory) {
        if (bufferFactory == null) {
            throw new NullPointerException("bufferFactory");
        }
        this.bufferFactory = bufferFactory;
    }

    @Override
    public ChannelPipelineFactory getPipelineFactory() {
        return null;
    }

    @Override
    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
        if (connectTimeoutMillis < 0) {
            throw new IllegalArgumentException("connectTimeoutMillis: " + connectTimeoutMillis);
        }
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    @Override
    public void setPipelineFactory(ChannelPipelineFactory pipelineFactory) {
        // Unused
    }
}
