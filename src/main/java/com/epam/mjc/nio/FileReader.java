package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        final int bufferSize = 1024;
        StringBuilder sb = new StringBuilder();
        try (FileChannel inChannel = FileChannel.open(file.toPath())) {
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    sb.append((char) buffer.get());
                }
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] pairsOfData = sb.toString().split("\n");
        String name = getValueFromPair(pairsOfData[0]);
        int age = Integer.parseInt(getValueFromPair(pairsOfData[1]));
        String email = getValueFromPair(pairsOfData[2]);
        long phone = Long.parseLong(getValueFromPair(pairsOfData[3]));

        return new Profile(name, age, email, phone);
    }


    private String getValueFromPair(String pair) {
        return pair.split(":")[1].trim();
    }
}
