package agentic.workflow.llm;
import java.util.*;

public class StructuredOutput{
    private final SchemaType[] schemaTypes;
    public SchemaType[] getSchemaTypes(){return schemaTypes.clone();}

    public StructuredOutput(SchemaType[] schemaTypes){
        if (schemaTypes.length == 0){
            throw new IllegalArgumentException("legalább egy sématípust meg kell adni.");
        }
        for (SchemaType s : schemaTypes){
            if ( s == null){
                throw new NullPointerException("a megadott sématípusok között nem lehet `null`.");
            }
        }
        this.schemaTypes = schemaTypes;

    }

    public boolean contains(SchemaType schemaType){
        if (schemaType == null || schemaTypes == null){
            return false;
        }
        for (SchemaType t : schemaTypes){
            if (t == schemaType){
                return true;
            }
        }
        return false;
    }

    public int size(){
        return schemaTypes.length;
    }
}