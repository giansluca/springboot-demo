package org.gmdev.setup;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

public class TestContainerImageResolver {

    private static final Properties PROPERTIES = loadProperties();

    private static Properties loadProperties() {
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setResources(new ClassPathResource("application.yaml"));
        Properties props = yamlFactory.getObject();
        if (props == null) {
            throw new IllegalStateException("Failed to load application.yaml");
        }
        return props;
    }

    public static String getMongoImage() {
        return buildImageName("container.mongo.image", "container.mongo.tag");
    }

    public static String getPostgresImage() {
        return buildImageName("container.postgres.image", "container.postgres.tag");
    }

    private static String buildImageName(String imageKey, String tagKey) {
        String image = PROPERTIES.getProperty(imageKey);
        String tag = PROPERTIES.getProperty(tagKey);
        if (image == null || tag == null) {
            throw new IllegalStateException("Missing properties for imageKey or tagKey");
        }

        return String.format("%s:%s", image, tag);
    }

}

