package agentic.workflow;
import agentic.workflow.llm.*;
import java.util.*;

public class WorkflowFormatException extends Exception{
    public WorkflowFormatException(String message){
        super(message);
    }

    public WorkflowFormatException(String message, Throwable cause){
        super(message, cause);
    }
}