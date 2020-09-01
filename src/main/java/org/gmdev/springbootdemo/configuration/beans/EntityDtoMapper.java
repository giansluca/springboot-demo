package org.gmdev.springbootdemo.configuration.beans;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
