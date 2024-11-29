package ru.romansib.otus.lesson21;

import java.util.Properties;

public class AppConfig {
    Properties configFile;

    public AppConfig() {
        configFile = new Properties();
        try {
            configFile.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getProperty(String propName) {
        return this.configFile.getProperty(propName);
    }

    public int getPort() {
        String portString = getProperty("port");
        if (portString == null || portString.isEmpty()) {
            return 8080;
        }
        return Integer.parseInt(portString);
    }

    public int getMaxRequestSize() {
        final int oneMb = 1024 * 1024;
        String sizeMb = getProperty("max-request-size-mb");
        if (sizeMb == null || sizeMb.isEmpty()) {
            return oneMb;
        }
        return Integer.parseInt(sizeMb) * oneMb;
    }

    public int getThreadsCount() {
        String threadsCount = getProperty("server-threads");
        if (threadsCount == null || threadsCount.isEmpty()) {
            return 1;
        }
        return Integer.parseInt(threadsCount);
    }
}
