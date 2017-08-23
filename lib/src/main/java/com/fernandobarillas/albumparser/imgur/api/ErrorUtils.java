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

package com.fernandobarillas.albumparser.imgur.api;

import com.fernandobarillas.albumparser.imgur.model.ImgurApiError;
import com.fernandobarillas.albumparser.imgur.model.v3.ImgurErrorResponseV3;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ErrorUtils {
    public static ImgurApiError getApiError(Retrofit retrofit, Response<?> response) {

        Converter<ResponseBody, ImgurErrorResponseV3> converter =
                retrofit.responseBodyConverter(ImgurErrorResponseV3.class, new Annotation[0]);

        ImgurErrorResponseV3 apiError;

        String defaultMessage = "An unknown API error has occurred";

        try {
            apiError = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ImgurApiError(defaultMessage);
        }

        String message = defaultMessage;
        if(apiError.data != null && apiError.data.error != null) {
            message = apiError.data.error;
        }
        return new ImgurApiError(message);
    }
}
