package com.kk.app2;

import java.util.StringTokenizer;

/**
 * @author Kuriakose V
 * @since 18/11/19.
 */
public class Test {
    public static void main(String[] args) {
        String callbackUrl = "http://localhost:8080/abdcirotjdndfdtest=123&kjfdkj=245@mobile";

        StringTokenizer stringTokenizer = new StringTokenizer(callbackUrl, "@");

        if(stringTokenizer.countTokens()>1) {
            System.out.println("Its mobile");
            String token = stringTokenizer.nextToken("@");
            System.out.println("Value = "+token);
            System.out.println("Value 2nd ="+stringTokenizer.nextToken("@"));
        }
        while(stringTokenizer.hasMoreElements()){
            System.out.println(stringTokenizer.nextToken());
        }
    }
}
