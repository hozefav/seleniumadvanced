package com.planittesting.automation.tests.dataProviders;

import java.util.List;
import java.util.stream.Stream;

import com.planittesting.automation.model.data.ContactData;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class DBContactDataProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        // HERE Connect to DB
        List<List<String>> results = List.of(
            List.of("juan","","a@b.com","","hello"), 
            List.of("florez","","c@d.com","","world"));

        return results.stream().map(row -> new ContactData(row.get(0), 
            row.get(1), 
            row.get(2), 
            row.get(3), 
            row.get(4))).map(Arguments::of);
    }
    
}
