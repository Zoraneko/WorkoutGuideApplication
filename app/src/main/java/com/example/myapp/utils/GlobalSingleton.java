package com.example.myapp.utils;

public class GlobalSingleton {
    private static GlobalSingleton instance;
    private String globalVariable;

    private GlobalSingleton() {
        // Initialize the global variable if needed
        globalVariable = "Initial Value";
    }

    public static synchronized GlobalSingleton getInstance() {
        if (instance == null) {
            instance = new GlobalSingleton();
        }
        return instance;
    }

    public String getGlobalVariable() {
        return globalVariable;
    }

    public void setGlobalVariable(String globalVariable) {
        this.globalVariable = globalVariable;
    }
}
