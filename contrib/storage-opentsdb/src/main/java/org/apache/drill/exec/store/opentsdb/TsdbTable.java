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

import java.util.ArrayList;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.drill.exec.planner.logical.DrillTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import net.opentsdb.core.TSDB;

public class TsdbTable extends DrillTable {
  private static final Logger LOG = 
      LoggerFactory.getLogger(TsdbTable.class);
  
  // TODO - dunno yet if this is where we instante the TSD. Only one per JVM.
  //private final TSDB tsdb;

  public TsdbTable(final String storageEngineName, final TsdbStoragePlugin plugin,
      final Object selection) {
    super(storageEngineName, plugin, selection);
    //tsdb = new TSDB(((TsdbStoragePluginConfig)plugin.getConfig()).getTSDConfig());
    LOG.info("INSTANTIATED TSDB TABLE");
  }

  @Override
  public RelDataType getRowType(final RelDataTypeFactory factory) {
    ArrayList<RelDataType> typeList = new ArrayList<>();
    ArrayList<String> fieldNameList = new ArrayList<>();
    
    fieldNameList.add("metric");
    typeList.add(factory.createSqlType(SqlTypeName.VARCHAR));
    
    fieldNameList.add("timestamp");
    typeList.add(factory.createSqlType(SqlTypeName.BIGINT));
    
    // TODO - integers too
    fieldNameList.add("value");
    typeList.add(factory.createSqlType(SqlTypeName.DOUBLE));
    
    // TODO - tags? How do we handle those?
    
    return factory.createStructType(typeList, fieldNameList);
  }

//  @JsonIgnore
//  public TSDB getTSDB() {
//    return tsdb;
//  }
}
