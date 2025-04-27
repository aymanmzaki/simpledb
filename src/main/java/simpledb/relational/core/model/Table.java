package simpledb.relational.core.model;

import lombok.Getter;
import simpledb.relational.core.constant.Keys;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Table extends DBObject {
    private Long rowId = 0L;
    private Long columnId = 0L;
    private final List<Column> columns;
    private final List<Row> rows;
    private final Map<String, Column> columnMap;
    private final Set<Long> primaryKeyValues;

    public Table(String tableName) {
        super(tableName);
        logger.info("Creating table: {} " ,tableName);
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.columnMap = new HashMap<>();
        this.primaryKeyValues = new HashSet<>();
        logger.info("Table created: {}" ,tableName);
    }

    public void addColumn(Column column) {
        if (columnMap.containsKey(column.getName())) {
            throw new IllegalArgumentException("Column with name " + column.getName() + " already exists");
        }
        
        Column newColumn = Column.builder()
                .id(columnId++)
                .name(column.getName())
                .type(column.getType())
                .description(column.getDescription())
                .defaultValue(column.getDefaultValue())
                .nullable(column.isNullable())
                .unique(column.isUnique())
                .primaryKey(column.isPrimaryKey())
                .build();

        this.columns.add(newColumn);
        this.columnMap.put(newColumn.getName(), newColumn);
        addMetadata(Keys.COLUMN_COUNT, String.valueOf(columns.size()));
    }

    public void addRow(String... values) {
        if (values.length != columns.size()) {
            throw new IllegalArgumentException("Expected " + columns.size() + " values, got " + values.length);
        }

        // Validate all values first
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).validateValue(values[i]);
        }

        // Check unique constraints
        for (Column column : columns) {
            if (column.isUnique()) {
                boolean isDuplicate = rows.stream()
                        .anyMatch(row -> values[column.getId().intValue()].equals(row.getValue(column.getId())));
                if (isDuplicate) {
                    throw new IllegalArgumentException("Duplicate value for unique column " + column.getName());
                }
            }
        }

        // Check primary key constraints
        for (Column column : columns) {
            if (column.isPrimaryKey()) {
                Long value = Long.parseLong(values[column.getId().intValue()]);
                if (primaryKeyValues.contains(value)) {
                    throw new IllegalArgumentException("Duplicate primary key value: " + value);
                }
                primaryKeyValues.add(value);
            }
        }

        Row row = new Row(rowId++);
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            String value = values[i];
            if (value == null && column.getDefaultValue() != null) {
                value = column.getDefaultValue();
            }
            row.addValue(column.getId(), value);
        }
        
        this.rows.add(row);
        addMetadata(Keys.ROWS_COUNT, String.valueOf(rows.size()));
    }

    public List<Row> query(String columnName, String value) {
        Column column = columnMap.get(columnName);
        if (column == null) {
            throw new IllegalArgumentException("Column " + columnName + " does not exist");
        }
        return rows.stream()
                .filter(row -> value.equals(row.getValue(column.getId())))
                .collect(Collectors.toList());
    }

    public List<Row> getAllRows() {
        return new ArrayList<>(rows);
    }

    public List<Column> getColumns() {
        return new ArrayList<>(columns);
    }

    public Column getColumn(String name) {
        return columnMap.get(name);
    }
}

