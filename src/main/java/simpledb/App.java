package simpledb;

import simpledb.relational.core.constant.ColumnType;
import simpledb.relational.core.model.Column;
import simpledb.relational.core.model.RelDB;
import simpledb.core.Schema;
import simpledb.relational.core.model.Table;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Schema schema = new Schema("newSchema");
        RelDB db = new RelDB("test");
        schema.addDB(db);
        Table table = new Table("testTable");
        db.addTable(table);

        table.addColumn(new Column.Builder("id", ColumnType.NUMERIC).build());
        table.addColumn(new Column.Builder("name", ColumnType.TEXT).build());
        table.addColumn(new Column.Builder("age", ColumnType.NUMERIC).build());
        table.addColumn(new Column.Builder("email", ColumnType.TEXT).build());
        table.addColumn(new Column.Builder("phone", ColumnType.TEXT).build());
        table.addColumn(new Column.Builder("address", ColumnType.TEXT).build());

        table.addRow("1", "John", "25", "john@example.com", "1234567890", "123 Main St");
        table.addRow("2", "Jane", "30", "jane@example.com", "0987654321", "456 Elm St");
        table.addRow("3", "Bob", "40", "bob@example.com", "5555555555", "789 Oak St");
        table.addRow("4", "Alice", "50", "alice@example.com", "1111111111", "321 Pine St");
        table.addRow("5", "Tom", "60", "tom@example.com", "2222222222", "654 Maple St");

        schema.getSchemaInfo();
    }
}
