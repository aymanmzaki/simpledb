package simpledb.relational.core.model;

import simpledb.core.DB;
import simpledb.core.DBObject;
import simpledb.relational.core.constant.Keys;

import java.util.HashMap;
import java.util.Map;

public class RelDB extends DB{
    private Map<String,Table> tables;

    public RelDB(String name) {
        super(name);
        this.tables = new HashMap<>();
        logger.info("RelDB created: {} " , name);
    }

    public Map<String,Table> getTables() {
        return tables;
    }

    public void addTable(Table table) {
        this.tables.put(table.getName(),table);
        addMetadata(Keys.TABLES_COUNT, String.valueOf(tables.size()));
    }

}
