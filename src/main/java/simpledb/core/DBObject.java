package simpledb.core;

import simpledb.relational.core.constant.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class DBObject {
    protected static final Logger logger = LoggerFactory.getLogger(DBObject.class);
    private UUID id;
    private String name;
    private Long creationTs;
    private Long lastModifiedTs;
    private Long removedTs;
    private Map<Keys,String> metadata;


    public DBObject(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.creationTs = System.currentTimeMillis();
        metadata = new HashMap<>();
    }
    
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCreationTs(){
        return creationTs;
    }

    public void addMetadata(Keys key, String value) {
        this.metadata.put(key, value);
    }

    public void remove() {
        this.removedTs = System.currentTimeMillis();
    }

}
