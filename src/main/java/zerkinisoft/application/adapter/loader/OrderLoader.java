package zerkinisoft.application.adapter.loader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import zerkinisoft.application.domain.record.Order;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderLoader implements CSVLoader<Order> {

    private static final String[] HEADERS = {"Date", "Quantity", "Product", "Price", "Currency"};
    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader(HEADERS)
            .setSkipHeaderRecord(true)
            .build();

    @Override
    public List<Order> loadFromCSV(String[] args) {
        return Arrays.stream(args)
                .flatMap(csvFileName -> {
                    try {
                        return readCsvFile(csvFileName).stream();
                    } catch (IOException e) {
                        System.out.println("Error when reading CSV File");
                        return Stream.of();
                    }
                })
                .collect(Collectors.toList());
    }

    private static List<Order> readCsvFile(String csvFileName) throws IOException {
        List<Order> orders = new ArrayList<>();

        try (CSVParser parser = new CSVParser(new FileReader(csvFileName), CSV_FORMAT)) {

            for (CSVRecord csvRecord : parser) {
                Order order = new Order(
                        csvRecord.get("Date"),
                        Integer.parseInt(csvRecord.get("Quantity")),
                        csvRecord.get("Product"),
                        new BigDecimal(csvRecord.get("Price")),
                        csvRecord.get("Currency")
                );
                orders.add(order);
            }
        }

        return orders;
    }
}
