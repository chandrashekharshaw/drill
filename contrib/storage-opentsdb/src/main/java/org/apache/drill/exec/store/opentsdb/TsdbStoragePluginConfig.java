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
package org.apache.drill.exec.store.opentsdb;

import java.io.IOException;

import org.apache.drill.common.logical.StoragePluginConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

//import net.opentsdb.utils.Config;

@JsonTypeName(TsdbStoragePluginConfig.NAME)
public class TsdbStoragePluginConfig extends StoragePluginConfig {
  private static final Logger LOG = 
      LoggerFactory.getLogger(TsdbStoragePluginConfig.class);
  
  public static final String NAME = "opentsdb";
  
  //private Config config;
  
//  @JsonCreator
//  public TsdbStoragePluginConfig() {
//    LOG.info("INSTANTIATED TSD STORAGE PLUGIN CONF");
////    try {
////      config = new Config(true);
////    } catch (IOException e) {
////      LOG.error("Failed loading TSDB config", e);
////      throw new RuntimeException("Failure loading TSDB config", e);
////    }
//  }
//
//  @JsonCreator
//  public TsdbStoragePluginConfig(@JsonProperty("config_file") String configFile) {
////    try {
////      config = new Config(configFile);
////    } catch (IOException e) {
////      LOG.error("Failed loading TSDB config: " + configFile, e);
////      throw new RuntimeException("Failure loading TSDB config: " + configFile, e);
////    }
//  }
//  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TsdbStoragePluginConfig that = (TsdbStoragePluginConfig) o;
    //return config.equals(that.config);
    return true;
  }

  @Override
  public int hashCode() {
    return 0;//return config != null ? config.hashCode() : 0;
  }

//  public Config getTSDConfig() {
//    return config;
//  }
}
