package com.luv2code.spring_boot_library.utils;

import java.util.Base64;
import java.util.HashMap;

public class ExtractJWT {
    public static String payloadJWTExtraction(String token,String filter){
        token.replace("Bearer ","");
        String arr[] = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(arr[1]));
        String entries[] = payload.split(",");
        HashMap<String,String> map = new HashMap<>();

        for(String s : entries){
            String keyVal[] = s.split(":");
            if(keyVal[0].equals(filter)){
                int remove = 1;
                if(keyVal[1].endsWith("}")){
                    remove = 2;
                }
                keyVal[1] = keyVal[1].substring(0,keyVal[1].length() - remove);
                keyVal[1] = keyVal[1].substring(1);
                map.put(keyVal[0],keyVal[1]);
            }
        }
        if(map.containsKey(filter)){
            return map.get(filter);
        }
        return null;
    }
}
