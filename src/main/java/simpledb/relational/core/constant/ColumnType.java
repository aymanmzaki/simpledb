package simpledb.relational.core.constant;

import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
public enum ColumnType {
    NUMERIC(8, 38, 0, 38),  // precision, scale
    TEXT(1, 65535, 0, 0),   // min length, max length
    BOOLEAN(1, 1, 0, 0),    // fixed size
    DATE(10, 10, 0, 0),     // YYYY-MM-DD
    TIME(8, 8, 0, 0),       // HH:MM:SS
    TIMESTAMP(19, 19, 0, 0);

    private final int minSize;
    private final int maxSize;
    private final int minPrecision;
    private final int maxPrecision;

    ColumnType(int minSize, int maxSize, int minPrecision, int maxPrecision) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minPrecision = minPrecision;
        this.maxPrecision = maxPrecision;
    }

    public boolean validateSize(int size) {
        return size >= minSize && size <= maxSize;
    }

    public boolean validatePrecision(int precision) {
        return precision >= minPrecision && precision <= maxPrecision;
    }

    public boolean validateValue(String value) {
        if (value == null) {
            return true;
        }

        try {
            switch (this) {
                case NUMERIC:
                    // Check if it's a valid number and within precision/scale limits
                    String[] parts = value.split("\\.");
                    if (parts.length > 2) {
                        return false;
                    }
                    if (parts[0].length() > maxPrecision) {
                        return false;
                    }
                    if (parts.length == 2 && parts[1].length() > maxPrecision) {
                        return false;
                    }
                    Double.parseDouble(value);
                    return true;

                case TEXT:
                    return value.length() <= maxSize;

                case BOOLEAN:
                    return value.equalsIgnoreCase("true") || 
                           value.equalsIgnoreCase("false") || 
                           value.equals("0") || 
                           value.equals("1");

                case DATE:
                    LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
                    return true;

                case TIME:
                    LocalTime.parse(value, DateTimeFormatter.ISO_TIME);
                    return true;

                case TIMESTAMP:
                    LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
                    return true;

                default:
                    return false;
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            return false;
        }
    }

    public String getDefaultValue() {
        switch (this) {
            case NUMERIC:
                return "0";
            case TEXT:
                return "";
            case BOOLEAN:
                return "false";
            case DATE:
                return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
            case TIME:
                return LocalTime.now().format(DateTimeFormatter.ISO_TIME);
            case TIMESTAMP:
                return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
            default:
                return null;
        }
    }

    public String formatValue(String value) {
        if (value == null) {
            return getDefaultValue();
        }

        switch (this) {
            case NUMERIC:
                return String.format("%." + maxPrecision + "f", Double.parseDouble(value));
            case TEXT:
                return value.length() > maxSize ? value.substring(0, maxSize) : value;
            case BOOLEAN:
                return Boolean.parseBoolean(value) ? "true" : "false";
            case DATE:
                return LocalDate.parse(value, DateTimeFormatter.ISO_DATE)
                        .format(DateTimeFormatter.ISO_DATE);
            case TIME:
                return LocalTime.parse(value, DateTimeFormatter.ISO_TIME)
                        .format(DateTimeFormatter.ISO_TIME);
            case TIMESTAMP:
                return LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
                        .format(DateTimeFormatter.ISO_DATE_TIME);
            default:
                return value;
        }
    }
}
