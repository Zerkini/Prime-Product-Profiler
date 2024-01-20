package zerkinisoft.application;

import zerkinisoft.application.adapter.CurrencyConverterAdapter;
import zerkinisoft.application.adapter.processor.CSVProcessor;
import zerkinisoft.application.adapter.processor.OrderCSVProcessor;
import zerkinisoft.application.adapter.loader.OrderLoader;
import zerkinisoft.application.adapter.printer.ValueCalculatedPrinter;
import zerkinisoft.application.domain.handler.OrderValueCalculationHandler;

public class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: java Prime ProductProfiler <csvFileName1> <csvFileName2> ...");
            return;
        }

        CSVProcessor csvProcessor = new OrderCSVProcessor(
                new OrderValueCalculationHandler(new CurrencyConverterAdapter()),
                new OrderLoader(),
                new ValueCalculatedPrinter());
        csvProcessor.processCsv(args);
    }
}