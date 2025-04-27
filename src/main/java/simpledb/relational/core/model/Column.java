package simpledb.relational.core.model;

import lombok.Builder;
import lombok.Value;
import simpledb.relational.core.constant.ColumnType;

@Value
@Builder
public class Column {
    Long id;
    String name;
    ColumnType type;
    @Builder.Default
    String description = "";
    String defaultValu;
    @Builder.Default
    boolean nullable = true;
    @Builder.Default
    boolean unique = false;
    @Builder.Default
    boolean primaryKey = false;
    @Builder.Default
    int size = 0;
    @Builder.Default
    int precision = 0;

    public static class Builder {
        private Long id;
        private String name;
        private ColumnType type;
        private String description = "";
        private String defaultValue;
        private boolean nullable = true;
        private boolean unique = false;
        private boolean primaryKey = false;
        private int size = 0;
        private int precision = 0;

        public Builder(String name, ColumnType type) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Column name cannot be null or empty");
            }
            if (type == null) {
                throw new IllegalArgumentException("Column type cannot be null");
            }
            this.name = name;
            this.type = type;
            this.size = type.getMaxSize();
            this.precision = type.getMaxPrecision();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder defaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder nullable(boolean nullable) {
            this.nullable = nullable;
            return this;
        }

        public Builder unique(boolean unique) {
            this.unique = unique;
            return this;
        }

        public Builder primaryKey(boolean primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder precision(int precision) {
            this.precision = precision;
            return this;
        }

        public Column build() {
            if (this.primaryKey && this.nullable) {
                throw new IllegalStateException("Primary key columns cannot be nullable");
            }
            if (!this.type.validateSize(this.size)) {
                throw new IllegalStateException("Invalid size for column type " + this.type);
            }
            if (!this.type.validatePrecision(this.precision)) {
                throw new IllegalStateException("Invalid precision for column type " + this.type);
            }
            return new Column(this.id, this.name, this.type, this.description, 
                            this.defaultValue, this.nullable, this.unique, 
                            this.primaryKey, this.size, this.precision);
        }
    }

    // Validation method
    public void validateValue(String value) {
        if (value == null) {
            if (!nullable) {
                throw new IllegalArgumentException("Column " + name + " does not allow null values");
            }
            return;
        }

        if (!type.validateValue(value)) {
            throw new IllegalArgumentException("Invalid value for column " + name + " of type " + type);
        }
    }

    public String formatValue(String value) {
        return type.formatValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return name.equals(column.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

