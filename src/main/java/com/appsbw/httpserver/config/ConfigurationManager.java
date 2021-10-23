package com.appsbw.httpserver.config;

import com.appsbw.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager _configurationManager;
    private static Configuration _configuration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (_configurationManager == null)
            _configurationManager = new ConfigurationManager();
        return _configurationManager;
    }

    /**
     * Used to load a configuration file by the path provided
     *
     * @param filePath The path to the configuration file
     */
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                stringBuffer.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
        JsonNode configuration = null;
        try {
            configuration = Json.parse(stringBuffer.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration file", e);
        }
        try {
            _configuration = Json.fromJson(configuration, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration file, internal", e);
        }
    }

    /**
     * Returns the Current loaded Configuration
     */
    public Configuration getCurrentConfiguration() {
        if (_configuration == null) {
            throw new HttpConfigurationException("No Current Configuration Set");
        }
        return _configuration;
    }
}
