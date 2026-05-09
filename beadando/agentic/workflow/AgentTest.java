package agentic.workflow;

import agentic.workflow.llm.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgentTest{
    @Test
    public void testStepCount(){
        Agent agent = new Agent("name");
        StructuredOutput structuredOutput = new StructuredOutput(new SchemaType[]{SchemaType.STRING});
        WorkflowStep w = new WorkflowStep("step1", "prompt", "system",structuredOutput);

        assertEquals(0, agent.getStepCount());

        agent.addStep(w);

        assertEquals(1, agent.getStepCount());
    }

    @Test
    public void testAddDuplicateStepRejected(){
        Agent agent = new Agent("name");
        StructuredOutput structuredOutput = new StructuredOutput(new SchemaType[]{SchemaType.STRING});
        WorkflowStep w1 = new WorkflowStep("step1", "prompt", "system",structuredOutput);
        WorkflowStep w2 = new WorkflowStep("step1", "prompt2", "system2",structuredOutput);
        agent.addStep(w1);
        assertThrows(IllegalArgumentException.class, () -> agent.addStep(w2));
    }

    @Test
    public void findStepByName(){
        Agent agent = new Agent("name");
        StructuredOutput structuredOutput = new StructuredOutput(new SchemaType[]{SchemaType.STRING});
        WorkflowStep w = new WorkflowStep("step1", "prompt", "system",structuredOutput);
        agent.addStep(w);
        assertEquals(w, agent.findStepByName("step1"));
    }

    @Test
    public void findStepByNameMissing(){
        Agent agent = new Agent("name");
        StructuredOutput structuredOutput = new StructuredOutput(new SchemaType[]{SchemaType.STRING});
        WorkflowStep w = new WorkflowStep("step1", "prompt", "system",structuredOutput);
        agent.addStep(w);
        assertEquals(null, agent.findStepByName("step2"));
    }

    @Test
    public void testLoadAgentSuccess() throws Exception{
        Agent agent = Agent.loadAgent("agent.txt");
        assertEquals("alma", agent.getName());
        assertEquals(1, agent.getStepCount());
    }
    @Test
    public void testLoadAgentRejectsMissingHeader(){
        assertThrows(WorkflowFormatException.class, () -> Agent.loadAgent("agent2.txt"));
    }
    @Test
    public void testLoadAgentRejectsDuplicateStepNames(){
        assertThrows(WorkflowFormatException.class, () -> Agent.loadAgent("agent3.txt"));
    }

    }
