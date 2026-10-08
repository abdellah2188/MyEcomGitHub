package com.hamch.orderserviceb.security;

public class JwtContextHolder {
    private static final ThreadLocal<String> JWT_TOKEN_HOLDER = new ThreadLocal<>();

    public static void setToken(String token) {
        JWT_TOKEN_HOLDER.set(token);
    }

    public static String getToken() {
        return JWT_TOKEN_HOLDER.get();
    }

    public static void clear() {
        JWT_TOKEN_HOLDER.remove();
    }
}