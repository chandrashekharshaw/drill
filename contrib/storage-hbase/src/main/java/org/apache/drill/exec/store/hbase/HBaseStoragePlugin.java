/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.exec.store.hbase;

import java.io.IOException;
import java.util.Set;

import org.apache.calcite.schema.SchemaPlus;

import org.apache.drill.common.JSONOptions;
import org.apache.drill.exec.ops.OptimizerRulesContext;
import org.apache.drill.exec.server.DrillbitContext;
import org.apache.drill.exec.store.AbstractStoragePlugin;
import org.apache.drill.exec.store.SchemaConfig;
import org.apache.drill.exec.store.StoragePluginOptimizerRule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;

public class HBaseStoragePlugin extends AbstractStoragePlugin {
  static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HBaseStoragePlugin.class);

  private final DrillbitContext context;
  private final HBaseStoragePluginConfig engineConfig;
  private final HBaseSchemaFactory schemaFactory;

  @SuppressWarnings("unused")
  private final String name;

  public HBaseStoragePlugin(HBaseStoragePluginConfig configuration, DrillbitContext context, String name)
      throws IOException {
    
    logger.info("INSTANTIATED HBASE PLUGIN!  Name: " + name);
    logger.info("Starting with context: " + context);
    this.context = context;
    this.schemaFactory = new HBaseSchemaFactory(this, name);
    this.engineConfig = configuration;
    this.name = name;
  }

  public DrillbitContext getContext() {
    return this.context;
  }

  @Override
  public boolean supportsRead() {
    return true;
  }

  @Override
  public HBaseGroupScan getPhysicalScan(String userName, JSONOptions selection) throws IOException {
    
    logger.info("GETTING PHYSICAL SCAN FROM PLUGIN FOR user: " + userName);
    
    
    HBaseScanSpec scanSpec = selection.getListWith(new ObjectMapper(), new TypeReference<HBaseScanSpec>() {});
    logger.info("   Got scan spec: " + scanSpec); // needs flattening
    return new HBaseGroupScan(userName, this, scanSpec, null);
  }

  @Override
  public void registerSchemas(SchemaConfig schemaConfig, SchemaPlus parent) throws IOException {
    logger.info("Registering schema with config: " + schemaConfig);
    logger.info("Registering schema with parent: " + parent);
    schemaFactory.registerSchemas(schemaConfig, parent);
  }

  @Override
  public HBaseStoragePluginConfig getConfig() {
    return engineConfig;
  }

  @Override
  public Set<StoragePluginOptimizerRule> getPhysicalOptimizerRules(OptimizerRulesContext optimizerRulesContext) {
    return ImmutableSet.of(HBasePushFilterIntoScan.FILTER_ON_SCAN, HBasePushFilterIntoScan.FILTER_ON_PROJECT);
  }
}