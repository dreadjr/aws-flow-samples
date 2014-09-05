/*
 * Copyright 2012 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.services.simpleworkflow.flow.examples.imageprocessing;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.WorkflowReplayer;
import com.amazonaws.services.simpleworkflow.flow.examples.common.ConfigHelper;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecution;

/**
 * This is used for launching a Workflow instance of FileProcessingWorkflowExample
 */
public class WorkflowExecutionReplayer {
    
    private static AmazonSimpleWorkflow swfService;
    private static String domain;
    
    public static void main(String[] args) throws Exception {
    	
        // Load configuration
    	ConfigHelper configHelper = ConfigHelper.createConfig();

        // Create the client for Simple Workflow Service
        swfService = configHelper.createSWFClient();
        domain = configHelper.getDomain();

        if (args.length < 2) {
            System.out.println("Usage: WorkflowExecutionReplayer {runId} {workflowId}");
            System.exit(-1);
        }

        String runId = args[0];
        String workflowId = args[1];
        Class<ImageProcessingWorkflowImpl> workflowImplementationType = ImageProcessingWorkflowImpl.class;
        WorkflowExecution workflowExecution = new WorkflowExecution();
        workflowExecution.setWorkflowId(workflowId);
        workflowExecution.setRunId(runId);

        WorkflowReplayer<ImageProcessingWorkflowImpl> replayer = new WorkflowReplayer<ImageProcessingWorkflowImpl>(swfService, domain, workflowExecution, workflowImplementationType);

        System.out.println("Beginning workflow replay for " + workflowExecution);
        Object workflow = replayer.loadWorkflow();
        System.out.println("Workflow implementation object:");
        System.out.println(workflow);
        System.out.println("Done workflow replay for " + workflowExecution);
        System.exit(0);
    }    
}
