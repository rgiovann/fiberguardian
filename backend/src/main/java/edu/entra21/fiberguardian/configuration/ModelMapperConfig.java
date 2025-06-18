package edu.entra21.fiberguardian.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Configurações adicionais podem ser adicionadas aqui, se necessário
        return modelMapper;
    }
}
