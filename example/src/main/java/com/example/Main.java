package com.example;

/**
 * Created by fb on 5/3/16.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting");
//        // Example to use a proxy server
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.2", 8080));
//        OkHttpClient client = new OkHttpClient.Builder().proxy(proxy).build();
//        AlbumParserTest.testAlbumParser(client);

        // Without a proxy
        AlbumParserTest.testAlbumParser(null);
        System.out.println("Done");
    }
}
