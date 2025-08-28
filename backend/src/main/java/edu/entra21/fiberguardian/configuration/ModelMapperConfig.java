package edu.entra21.fiberguardian.configuration;

import edu.entra21.fiberguardian.dto.FornecedorCnpjDto;
import edu.entra21.fiberguardian.dto.NotaFiscalListagemPagedDto;
import edu.entra21.fiberguardian.input.NotaFiscalInput;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.NotaFiscal;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import edu.entra21.fiberguardian.model.Usuario;

@Configuration
public class ModelMapperConfig {

    @Bean
    Mapper mapper() {
        final  ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);

        // ---------- Fornecedor -> FornecedorCnpjDto ----------
        modelMapper.createTypeMap(Fornecedor.class, FornecedorCnpjDto.class)
                .addMappings(mapper -> mapper.map(Fornecedor::getCnpj, FornecedorCnpjDto::setCnpj));

        // Converter para Fornecedor
        Converter<String, Fornecedor> cnpjToFornecedor = (MappingContext<String, Fornecedor> ctx) -> {
            Fornecedor f = new Fornecedor();
            f.setCnpj(ctx.getSource());
            return f;
        };

        // Converter para Usuario
        Converter<String, Usuario> emailToUsuario = (MappingContext<String, Usuario> ctx) -> {
            Usuario u = new Usuario();
            u.setEmail(ctx.getSource());
            return u;
        };

        modelMapper.createTypeMap(NotaFiscalInput.class, NotaFiscal.class)
                .addMappings(m -> {
                    m.using(cnpjToFornecedor).map(NotaFiscalInput::getCnpj, NotaFiscal::setFornecedor);
                    m.using(emailToUsuario).map(NotaFiscalInput::getRecebidoPor, NotaFiscal::setRecebidoPor);
                });

        // Mapeamento de NotaFiscal -> NotaFiscalListagemPagedDto
        modelMapper.addMappings(new PropertyMap<NotaFiscal, NotaFiscalListagemPagedDto>() {
            @Override
            protected void configure() {
                // Fornecedor
                map().setNomeFornecedor(source.getFornecedor().getNomeFornecedor());
                map().setCnpjFornecedor(source.getFornecedor().getCnpj());

                // Usuario
                map().setEmailUsuario(source.getRecebidoPor().getEmail());
            }
        });

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