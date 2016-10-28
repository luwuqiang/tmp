/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leederedu.qsearch.handler.component;

import com.google.common.base.Objects;
import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.core.highlight.SolrHighlighter;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * TODO!
 *
 *
 * @since solr 1.3
 */
public class HighlightComponent extends SearchComponent
{
  public static final String COMPONENT_NAME = "highlight";
  private SolrHighlighter highlighter;


  @Override
  public void prepare(ResponseBuilder rb) throws IOException {

  }

  @Override
  public void process(ResponseBuilder rb) throws IOException {

  }

  @Override
  public void modifyRequest(ResponseBuilder rb, SearchComponent who, ShardRequest sreq) {

  }

  @Override
  public void handleResponses(ResponseBuilder rb, ShardRequest sreq) {
  }

  @Override
  public void finishStage(ResponseBuilder rb) {

  }

  public SolrHighlighter getHighlighter() {
    return highlighter;
  }

}