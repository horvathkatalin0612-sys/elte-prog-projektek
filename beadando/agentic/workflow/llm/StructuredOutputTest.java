package agentic.workflow.llm;

import agentic.workflow.llm.*;
import agentic.workflow.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StructuredOutputTest{
    @Test
    public void testContainsExistingType(){
        StructuredOutput o = new StructuredOutput(new SchemaType[]{SchemaType.INT});
        assertEquals(true, o.contains(SchemaType.INT));
    }
    @Test
    public void testContainsMissingType(){
        StructuredOutput o = new StructuredOutput(new SchemaType[]{SchemaType.INT});
        assertEquals(false, o.contains(SchemaType.STRING));
    }
    @Test
    public void testSize(){
        StructuredOutput o = new StructuredOutput(new SchemaType[]{SchemaType.INT});
        assertEquals(1,o.size());
    }

}
