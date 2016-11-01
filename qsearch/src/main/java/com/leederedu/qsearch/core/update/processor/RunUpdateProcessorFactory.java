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
package com.leederedu.qsearch.core.update.processor;

import com.leederedu.qsearch.core.QSearchCommand;
import com.leederedu.qsearch.core.common.SolrException;

import java.io.IOException;


/**
 *
 */
public class RunUpdateProcessorFactory extends UpdateRequestProcessorFactory 
{
  @Override
  public UpdateRequestProcessor getInstance(UpdateRequestProcessor next)
  {
    return new RunUpdateProcessor(req, next);
  }
}

class RunUpdateProcessor extends UpdateRequestProcessor 
{
  private final UpdateHandler updateHandler;

  private boolean changesSinceCommit = false;

  public RunUpdateProcessor(SolrQueryRequest req, UpdateRequestProcessor next) {
    super( next );
    this.updateHandler = req.getCore().getUpdateHandler();
  }

  @Override
  public void processAdd(QSearchCommand cmd) throws IOException {
    
    if (AtomicUpdateDocumentMerger.isAtomicUpdate(cmd)) {
      throw new SolrException
        (SolrException.ErrorCode.BAD_REQUEST,
         "RunUpdateProcessor has received an AddUpdateCommand containing a document that appears to still contain Atomic document update operations, most likely because DistributedUpdateProcessorFactory was explicitly disabled from this updateRequestProcessorChain");
    }

    updateHandler.addDoc(cmd);
    super.processAdd(cmd);
    changesSinceCommit = true;
  }

  @Override
  public void processDelete(QSearchCommand cmd) throws IOException {
    if( cmd.isDeleteById()) {
      updateHandler.delete(cmd);
    }
    else {
      updateHandler.deleteByQuery(cmd);
    }
    super.processDelete(cmd);
    changesSinceCommit = true;
  }

  @Override
  public void processMergeIndexes(QSearchCommand cmd) throws IOException {
    updateHandler.mergeIndexes(cmd);
    super.processMergeIndexes(cmd);
  }

  @Override
  public void processCommit(QSearchCommand cmd) throws IOException
  {
    updateHandler.commit(cmd);
    super.processCommit(cmd);
    if (!cmd.softCommit) {
      // a hard commit means we don't need to flush the transaction log
      changesSinceCommit = false;
    }
  }

  /**
   * @since Solr 1.4
   */
  @Override
  public void processRollback(QSearchCommand cmd) throws IOException
  {
    updateHandler.rollback(cmd);
    super.processRollback(cmd);
    changesSinceCommit = false;
  }


  @Override
  public void finish() throws IOException {
    if (changesSinceCommit && updateHandler.getUpdateLog() != null) {
      updateHandler.getUpdateLog().finish(null);
    }
    super.finish();
  }
}


