package simpledb.relational.core.model;

import simpledb.relational.core.constant.Keys;

import java.util.*;

public class Database extends DBObject {
    private Map<String,Table> tables;

    public Database(String name) {
        super(name);
        this.tables = new HashMap<>();
        logger.info("Database created: {} " , name);
    }

    public Map<String,Table> getTables() {
        return tables;
    }

    public void addTable(Table table) {
        this.tables.put(table.getName(),table);
        addMetadata(Keys.TABLES_COUNT, String.valueOf(tables.size()));
    }

}
