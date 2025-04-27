package simpledb.kvstore.core;

import simpledb.core.DB;

import java.util.concurrent.ConcurrentHashMap;

public class KVDB extends DB {

    ConcurrentHashMap<String,Object> db ;

    public KVDB(String name) {
        super(name);
        db = new ConcurrentHashMap<>();
    }

    public Object getByKey(String key){
        return db.get(key);
    }

    public void put(String key, Object value){
        db.putIfAbsent(key, value);
    }


}
