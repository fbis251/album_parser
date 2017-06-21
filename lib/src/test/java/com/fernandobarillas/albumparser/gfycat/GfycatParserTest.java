/*
 * The MIT License (MIT)
 * Copyright (c) 2017 Fernando Barillas (FBis251)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fernandobarillas.albumparser.gfycat;

import com.fernandobarillas.albumparser.exception.InvalidApiResponseException;
import com.fernandobarillas.albumparser.parser.IParserTest;
import com.fernandobarillas.albumparser.util.ExpectedHash;
import com.fernandobarillas.albumparser.util.TestUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.fernandobarillas.albumparser.util.ParseUtils.getUrlObject;
import static com.fernandobarillas.albumparser.util.TestUtils.API_CALL_TIMEOUT_MS;
import static org.junit.Assert.assertNotNull;

/**
 * Created by fb on 6/12/17.
 */
public class GfycatParserTest implements IParserTest {

    private GfycatParser mGfycatParser;

    @Test(expected = InvalidApiResponseException.class, timeout = API_CALL_TIMEOUT_MS)
    @Override
    public void testApi404Error() throws IOException, RuntimeException {
        URL url = getUrlObject("https://gfycat.com/UnconsciousTalkativeBluejay");
        assertNotNull(url);
        mGfycatParser.parse(url);
    }

    @Override
    public void testApiUsesHttps() {

    }

    @Test
    @Override
    public void testCanParseAndGetHash() {
        List<ExpectedHash> validHashes = new ArrayList<>();

        validHashes.add(new ExpectedHash("IndelibleMerryBuck",
                "https://gfycat.com/IndelibleMerryBuck"));
        validHashes.add(new ExpectedHash("IndelibleMerryBuck",
                "https://gfycat.com/gifs/detail/IndelibleMerryBuck"));
        validHashes.add(new ExpectedHash("IndelibleMerryBuck",
                "https://gfycat.com/IndelibleMerryBuck"));
        validHashes.add(new ExpectedHash("PotableLeftAbalone",
                "https://fat.gfycat.com/PotableLeftAbalone.webm"));
        validHashes.add(new ExpectedHash("PotableLeftAbalone",
                "https://fat.gfycat.com/PotableLeftAbalone.mp4"));
        validHashes.add(new ExpectedHash("PotableLeftAbalone",
                "https://thumbs.gfycat.com/PotableLeftAbalone-mobile.mp4"));

        TestUtils.validateCanParseAndHashes(mGfycatParser, validHashes, false);
    }

    @Override
    public void testInvalidUrls() {

    }

    @Before
    public void setUp() throws Exception {
        mGfycatParser = new GfycatParser(TestUtils.getOkHttpClient());
    }
}
