# Prime Product Profiler

## Description

Loads CSV files provided in program arguments. The CSV files should be in the following format:

```
Date,Quantity,Product,Price,Currency
2023-01-01,100,Item1,25.50,USD
2023-01-02,2,Item2,12.75,EUR
2023-01-01,100,Item1,25.50,USD
2023-01-03,8,Item3,18.99,GBP
```
Then groups orders by Product, multiplying the quantity of the order by the product price and converts the value to CHF. 
The orders are sorted by value, and the top 10 most valuable orders are printed:

```
Product | Total Quantity | Currency | Value
Item1,100,CHF,2514.81
Item3,8,CHF,199.66
Item2,2,CHF,29.42
```

## Shortcomings 

### Exceptions thrown by the Moneta library
The Moneta library code throws a `SocketTimeoutException` when trying to load the currency conversion rates and only after
a few failed attempts loads the rates from a different source. This is another reason to find an alternative to the Moneta
library.

### Hardcoded conversion to CHF
Right now the program is only able to convert the values to CHF. I considered different approaches to choosing a currency
to convert to. One of them would be to choose the currency dynamically in some way, like converting to the currency with the
highest order value, but I decided that the usefulness of such a solution is limited. 
Another would be to add another argument to the program which would allow to choose the currency to convert to, defaulting
to CHF if no argument for currency is passed. 

### Validation
The data from CSV files isn't validated. If the file has data not matching the format, for example, a string for a price,
the program will crash. Robust validation would be a worthwhile addition.

