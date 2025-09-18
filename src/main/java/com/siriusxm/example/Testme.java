package com.siriusxm.example;

import org.aspectj.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class Testme {

    public static void main(String[] args) throws IOException {
        String strings = FileUtil.readAsString(new File("c:\\temp\\detail.html"));
        String[] string = strings.split("100\">");
        for (int i = 1; i < string.length; i++) {
            String a = string[i];
            a = a .replace(" ", "_");
            System.out.println(a.substring(0, a.indexOf("<")) +",");
        }
        System.out.println(string.length);
    }
}
