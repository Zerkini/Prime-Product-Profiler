package zerkinisoft.application.adapter.loader;

import zerkinisoft.application.domain.record.CSVRecord;

import java.util.List;

public interface CSVLoader<T extends CSVRecord> {

    List<T> loadFromCSV(String[] args);

}
