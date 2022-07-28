package com.veljkocerovic.timeformeal.utils;

import javax.servlet.http.HttpServletRequest;

public class Helpers {
    public static String createAppUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
