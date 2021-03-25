package com.planittesting.automation.tests.dataProviders;

import com.planittesting.automation.model.data.ContactData;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class CsvContactDataProvider implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
            throws ArgumentsAggregationException {
        return new ContactData(
            accessor.getString(0) == null ? "" : accessor.getString(0),
            accessor.getString(1) == null ? "" : accessor.getString(1),
            accessor.getString(2) == null ? "" : accessor.getString(2),
            accessor.getString(3) == null ? "" : accessor.getString(3),
            accessor.getString(4) == null ? "" : accessor.getString(4)
            );
    }
    
}
