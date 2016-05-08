/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Fernando Barillas (FBis251)
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

package com.fernandobarillas.albumparser.vidble.model;

import com.fernandobarillas.albumparser.vidble.exception.InvalidVidbleUrlException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

/**
 * Java representation of the Vidble album JSON response
 */
@Generated("org.jsonschema2pojo")
public class VidbleResponse {

    @SerializedName("pics")
    @Expose
    private List<String> pics = new ArrayList<String>();

    /**
     * Parses the pics returned by the Vidble API into valid List of url Strings
     *
     * @param originalQuality True to return the url to the original quality image, false for medium quality
     * @return A List of validated and parsed url Strings to the images in the album
     */
    public List<String> getPics(boolean originalQuality) {
        List<String> result = new ArrayList<>();
        for (String picUrl : pics) {
            try {
                result.add(new VidbleUrl(picUrl).getUrl(originalQuality));
            } catch (InvalidVidbleUrlException e) {
                continue;
            }
        }

        return result;
    }
}
