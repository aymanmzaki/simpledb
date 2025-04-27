package simpledb.core;

import simpledb.relational.core.constant.Keys;
import simpledb.relational.core.model.RelDB;
import simpledb.relational.core.model.Table;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Schema extends DBObject {
    private ConcurrentHashMap<String, DB> databases;

    public Schema(String name) {
        super(name);
        databases = new ConcurrentHashMap<>();
        logger.info("Schema created: {} " , name);
    }

    public Map<String, DB> getDatabases() {
        return databases;
    }

    public void addDB(DB database){
        this.databases.put(database.getName(), database);
        addMetadata(Keys.DATABASES_COUNT, String.valueOf(databases.size()));
    }
    public void getSchemaInfo(){
        System.out.println("ObjectId\t:\tObjectName\t:\tObjectType");
        System.out.println(this.getId() + "\t:\t" +this.getName()+"\t:\tSCHEMA");
        for (DB database: databases.values()) {
            System.out.println(database.getId()+"\t:\t"+database.getName()+"\t:\tDATABASE");
        }
    }

}
