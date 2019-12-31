package com.soybeany.properties_cipher.spring;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

/**
 * <br>Created by Soybeany on 2019/12/24.
 */
public class PropertyPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        CipherPropertyLoader.load(props);
    }
}
