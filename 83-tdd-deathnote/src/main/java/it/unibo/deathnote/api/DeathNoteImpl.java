package it.unibo.deathnote.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DeathNoteImpl implements DeathNote {
    
    private String lastwritename;
    private String lastCause;
    private long startTime;
    private long currentTime;
    private Map<String,Death> deaths = new HashMap<>();

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber > deaths.size()){
            throw new IllegalArgumentException("Rule index " + ruleNumber + " does not exist");
        } 
        else {
            return DeathNote.RULES.get(ruleNumber);
        }
    }

    @Override
    public void writeName(String name) {
       Objects.requireNonNull(name);
       this.lastwritename = name;
       this.deaths.put(name, new Death());
       this.startTime = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        Objects.requireNonNull(cause);
        this.lastCause = cause;
        if (this.deaths.size() == 0) {
            throw new IllegalStateException();
        }
        this.currentTime = System.currentTimeMillis();
        if((this.currentTime - this.startTime) >= 40){
        
                this.deaths.put(this.lastwritename, new Death(cause, ""));
                var equalscause = this.deaths.get(lastwritename);
                if (equalscause.GetCause().equals(cause)) {
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        this.currentTime = System.currentTimeMillis();
        if (this.deaths.size() == 0) {
            throw new IllegalStateException();
        }
        else if ((this.currentTime - this.startTime) >= 6040) {
            this.deaths.put(this.lastwritename,new Death(this.lastCause, details));
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(String name) {
        Death infoDeath = this.deaths.get(name);
        if (infoDeath == null) {
            throw new IllegalArgumentException();
        }
        return infoDeath.GetCause();
    }

    @Override
    public String getDeathDetails(String name) {
        Death infoDeath = this.deaths.get(name);
        if (infoDeath == null) {
            throw new IllegalArgumentException();
        }
        return infoDeath.GetDetails();
    }

    @Override
    public boolean isNameWritten(String name) {
       return this.deaths.containsKey(name);
    }

    private   class Death {
        private String cause;
        private String details;
        private static final String DEFAULT_CAUSE = "heart attack";

        public Death(String cause,String details) {

            this.cause = cause;
            this.details = details;
        }

        public Death() {
            this(DEFAULT_CAUSE,"");
        }

        public String GetCause() {
            return this.cause;
        }

        public String GetDetails() {
            return this.details;
        }

        
    }
    
}
