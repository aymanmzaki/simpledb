package simpledb.relational.core.model;

import simpledb.relational.core.constant.Keys;

import java.util.HashMap;
import java.util.Map;

public class Schema extends DBObject {
    private Map<String,Database> databases;

    public Schema(String name) {
        super(name);
        databases = new HashMap<>();
        logger.info("Schema created: {} " , name);
    }

    public Map<String, Database> getDatabases() {
        return databases;
    }

    public void addDB(Database database){
        this.databases.put(database.getName(), database);
        addMetadata(Keys.DATABASES_COUNT, String.valueOf(databases.size()));
    }
    public void getSchemaInfo(){
        System.out.println("ObjectId\t:\tObjectName\t:\tObjectType");
        System.out.println(this.getId() + "\t:\t" +this.getName()+"\t:\tSCHEMA");
        for (Database database: databases.values()) {
            System.out.println(database.getId()+"\t:\t"+database.getName()+"\t:\tDATABASE");
            for (Table table: database.getTables().values()) {
                System.out.println(table.getId()+ "\t:\t"+ table.getName()+"\t:\tTABLE");
            }
        }
    }

}
