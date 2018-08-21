package com.ap.fmcgr;

public class DataHolder {
    private static String userName;
    private static String groupName;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        DataHolder.userName = userName;
    }

    public static String getGroupName() {
        return groupName;
    }

    public static void setGroupName(String groupName) {
        DataHolder.groupName = groupName;
    }
}
