package com.planittesting.automation.tests.dataProviders;

import com.planittesting.automation.model.data.ContactData;
import com.planittesting.automation.model.data.LoginData;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class CsvLoginDataProvider implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
            throws ArgumentsAggregationException {
        return new LoginData(
            accessor.getString(0) == null ? "" : accessor.getString(0),
            accessor.getString(1) == null ? "" : accessor.getString(1)
            );
    }    
}
