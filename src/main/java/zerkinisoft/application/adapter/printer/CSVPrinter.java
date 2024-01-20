package zerkinisoft.application.adapter.printer;

import zerkinisoft.application.domain.record.CSVRecord;

import java.util.List;

public interface CSVPrinter<T extends CSVRecord> {

    void printCSV(List<T> records);

}
