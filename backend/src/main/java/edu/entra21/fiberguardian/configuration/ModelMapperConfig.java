package edu.entra21.fiberguardian.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    Mapper mapper() {
    	final  ModelMapper modelMapper = new ModelMapper(); 

    	return new Mapper() {
            @Override
            public <D> D map(Object source, Class<D> destinationType) {
                return modelMapper.map(source, destinationType);
            }

            @Override
            public void map(Object source, Object destination) {
                modelMapper.map(source, destination);
            }
        };     
    }
    	
    }
}
