package com.launch.kit;


public class StringKit {

    public static boolean isNotBlank(String...strings){

        for (String s : strings){
            if (s == null && s.trim().equals("")){
                return false;
            }
        }
        return true;
    }

}
