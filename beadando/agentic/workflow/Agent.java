package agentic.workflow;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import agentic.workflow.llm.*;

public class Agent{
    private String name;
    private final List<WorkflowStep> steps = new ArrayList<>();

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public List<WorkflowStep> getSteps(){
    return Collections.unmodifiableList(steps);}

    public Agent(String name){
        if(name == null || name.trim().isEmpty() )
        {
            throw new IllegalArgumentException("a név nem lehet `null`, üres vagy csak szóközökből álló.");
        }
        this.name = name;
    }

    public void addStep(WorkflowStep step){
        if (step == null){
            throw new IllegalArgumentException("a lépés nem lehet `null`, és nem létezhet már másik lépés ugyanazzal a névvel.");
        }
        for (WorkflowStep s : steps){
            if(s.getName().equals(step.getName())){
                throw new IllegalArgumentException("a lépés nem lehet `null`, és nem létezhet már másik lépés ugyanazzal a névvel.");
            }
        }
        steps.add(step);
    }

    public int getStepCount(){
        return steps.size();
    }

    public WorkflowStep findStepByName(String stepName){
        if (stepName == null || stepName.trim().isEmpty()){
            throw new IllegalArgumentException("a lépés neve nem lehet `null`, üres vagy csak szóközökből álló.");
        }
        WorkflowStep ret = null;
        stepName = stepName.trim();
        for (WorkflowStep step : steps){
            if (step.getName().equals(stepName)){
                ret = step;
            }
        }
        return ret;
    }

    public void run(){
        for (WorkflowStep step : steps){
            System.out.println(step.getName());
        }
    }

    public static Agent loadAgent(String filename) throws IOException, WorkflowFormatException{
        if (filename == null || filename.isBlank()){
            throw new IllegalArgumentException("a fájlnév nem lehet `null`, üres vagy csak szóközökből álló.");
        }
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;

        while ((line = reader.readLine())!=null && line.isBlank());
    
        if (line == null || !line.startsWith("AGENT:")){
            throw new WorkflowFormatException("ha a fájl tartalma hibás formátumú.");
        }
        String agentName = line.substring(6).trim();
        Agent agent = new Agent(agentName);
         // Ezt a nevet olvassuk be a fájlból

        if (agentName.isEmpty()) { // Itt 'agentName'-t írj, ne csak 'name'-et!
            throw new WorkflowFormatException("hiba");
        }
        while ((line=reader.readLine()) != null){
            line = line.trim();

            if(line.equals("STEP")){
                WorkflowStep step = parseStep(reader);
                try {
                agent.addStep(step);}
                catch (IllegalArgumentException e){
                    throw new WorkflowFormatException("ha a fájl tartalma hibás formátumú.");
                }}
                
            else{
                    throw new WorkflowFormatException("ha a fájl tartalma hibás formátumú.");
                }
            
            
        }
        return agent;
    }
    }

    private static WorkflowStep parseStep(BufferedReader reader) throws IOException, WorkflowFormatException{
        String name = null;
        String prompt = null;
        String systemPrompt = null;
        agentic.workflow.llm.StructuredOutput output = null;
        String line;

        while ((line = reader.readLine()) != null){
            line = line.trim();
            if (line.equals("ENDSTEP")){
                break;
            }
            if (line.isEmpty()){
                continue;
            }

            String[] parts = line.split(":", 2);
            if (parts.length < 2){
                throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
            }

            String key = parts[0].trim();
            String value = parts[1].trim();

            switch(key) {
                case "name" :
                    name = value;
                    break;
                case "prompt" :
                    prompt = value;
                    break;
                case "systemPrompt" :
                    systemPrompt = value;
                    break;
                case "output" :
                    try{
                        SchemaType type = SchemaType.valueOf(value);
                        output = new StructuredOutput(new SchemaType[]{type});
                    }
                    catch (IllegalArgumentException e){
                        throw new WorkflowFormatException("az output értéke nem felel meg a SchemaType enum elemeinek.");
                    }
                    break;
                default:
                    throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
                }

            }
            if ( name == null || prompt == null || systemPrompt == null || output == null){
                throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
            }

            return new WorkflowStep(name,prompt,systemPrompt,output);
        }
        
    }

