package agentic.workflow;
import java.util.*;
import agentic.workflow.llm.*;

public class WorkflowStep{
    private String name;
    private String prompt;
    private String systemPrompt;
    private StructuredOutput structuredOutput;

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getPrompt(){return prompt;}
    public void setPrompt(String prompt){this.prompt = prompt;}

    public String getSystemPrompt(){return systemPrompt;}
    public void setSystemPrompt(String systemPrompt){this.systemPrompt = systemPrompt;}

    public agentic.workflow.llm.StructuredOutput getStructuredOutput(){return structuredOutput;}
    public void setStructuredOutput(agentic.workflow.llm.StructuredOutput structuredOutput){this.structuredOutput = structuredOutput;}

    public WorkflowStep(String name, String prompt, String systemPrompt, agentic.workflow.llm.StructuredOutput structuredOutput){

        if (name == null || name.isEmpty()
        || prompt == null || prompt.isEmpty()
        || systemPrompt == null || systemPrompt.isEmpty()
        || structuredOutput == null) {
    throw new IllegalArgumentException(
        "a `name`, `prompt` és `systemPrompt` nem lehet üres, a `structuredOutput` pedig nem lehet `null`."
    );
}

        this.name = name;
        this.systemPrompt = systemPrompt;
        this.prompt = prompt;
        this.structuredOutput = structuredOutput;
    }

    public boolean expectsStructuredOutput(){
        return this.structuredOutput != null;
    }

    public String simulateResponse(){
        SchemaType[] types = structuredOutput.getSchemaTypes();
        
        SchemaType primaryType = types[0];

        
        return switch (primaryType) {
            case INT -> "0";
            case STRING -> "sample";
            case BOOLEAN -> "true";
            case LIST_INT -> "[1,2,3]";
            case LIST_STRING -> "[\"a\",\"b\"]";
            case MAP_STRING_STRING -> "{\"kulcs\":\"érték\"}";
            default -> "";
        };
    
    }
}