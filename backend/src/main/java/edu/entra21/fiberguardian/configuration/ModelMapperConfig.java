package edu.entra21.fiberguardian.configuration;

import edu.entra21.fiberguardian.dto.FornecedorCnpjDto;
import edu.entra21.fiberguardian.model.Fornecedor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    Mapper mapper() {
    	final  ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(Fornecedor.class, FornecedorCnpjDto.class)
                .addMappings(mapper -> mapper.map(Fornecedor::getCnpj, FornecedorCnpjDto::setCnpj));


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

