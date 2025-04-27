package simpledb.relational.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.TreeMap;

@Getter
@RequiredArgsConstructor
public class Row {
    private final Long id;
    private final TreeMap<Long, String> data = new TreeMap<>();

    public String getValue(Long columnId) {
        return data.get(columnId);
    }

    public TreeMap<Long, String> getValues() {
        return new TreeMap<>(data);
    }

    public void addValue(Long columnId, String value) {
        this.data.put(columnId, value);
    }
}
