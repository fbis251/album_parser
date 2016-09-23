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

import com.fernandobarillas.albumparser.media.BaseMediaAlbum;
import com.fernandobarillas.albumparser.media.IMedia;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fb on 5/11/16.
 */
public class VidbleAlbum extends BaseMediaAlbum {
    List<IMedia> mMediaList;

    public VidbleAlbum(List<String> responsePics) {
        mMediaList = new ArrayList<>();
        if (responsePics == null) return;
        for (String responsePic : responsePics) {
            mMediaList.add(new VidbleMedia(responsePic));
        }
    }

    @Override
    public List<IMedia> getAlbumMedia() {
        return mMediaList;
    }

    @Override
    public URL getPreviewUrl() {
        if (!isEmpty()) {
            // Return the first image as the preview
            return mMediaList.get(0).getPreviewUrl();
        }

        return null;
    }
}
