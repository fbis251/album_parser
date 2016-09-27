package com.fernandobarillas.albumparser.parser;

import java.io.IOException;

/**
 * Interface for parser tests.
 */
public interface IParserTest {
    void testApi404Error() throws IOException, RuntimeException;

    void testApiUsesHttps();

    void testCanParseAndGetHash();

    void testInvalidUrls();
}
