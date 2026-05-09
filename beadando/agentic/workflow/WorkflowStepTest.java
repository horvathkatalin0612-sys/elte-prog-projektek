package agentic.workflow;


import agentic.workflow.llm.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WorkflowStepTest{
    @Test
    public void testExpectsStructuredOutput(){
        StructuredOutput output = new StructuredOutput(new SchemaType[]{SchemaType.INT});
        WorkflowStep w = new WorkflowStep("name", "prompt", "system", output);
        assertEquals(true, w.expectsStructuredOutput());
    }
}