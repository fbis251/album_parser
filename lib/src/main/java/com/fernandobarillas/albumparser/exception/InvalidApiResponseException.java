package com.fernandobarillas.albumparser.exception;

import java.net.URL;

/**
 * Created by fb on 9/21/16.
 */
public class InvalidApiResponseException extends IllegalStateException {
    private static final String message = "The API did not return a valid response";

    public InvalidApiResponseException(URL url) {
        super(message + ": url = [" + url + "]");
    }

    public InvalidApiResponseException(URL url, String errorMessage) {
        super(message + ": url = [" + url + "], errorMessage = [" + errorMessage + "]");
    }
}
