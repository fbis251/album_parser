package com.example;

/**
 * Created by fb on 5/3/16.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting");
        System.out.println(MediaTest.SEPARATOR + "GFYCAT TEST" + MediaTest.SEPARATOR);
        GfycatTest.gfycatTest();
        System.out.println(MediaTest.SEPARATOR + "IMGUR TEST" + MediaTest.SEPARATOR);
        ImgurTest.imgurTest();
        System.out.println(MediaTest.SEPARATOR + "STREAMABLE TEST" + MediaTest.SEPARATOR);
        StreamableTest.streamableTest();
        System.out.println(MediaTest.SEPARATOR + "VIDBLE TEST" + MediaTest.SEPARATOR);
        VidbleTest.vidbleTest();
        System.out.println(MediaTest.SEPARATOR + "VIDME TEST" + MediaTest.SEPARATOR);
        VidmeTest.vidmeTest();
        System.out.println(MediaTest.SEPARATOR);
        System.out.println("Done");
    }
}
