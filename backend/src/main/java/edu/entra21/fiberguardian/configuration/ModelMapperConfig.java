package edu.entra21.fiberguardian.configuration;

import edu.entra21.fiberguardian.dto.FornecedorCnpjDto;
import edu.entra21.fiberguardian.dto.LaboratorioDto;
import edu.entra21.fiberguardian.dto.NotaFiscalListagemPagedDto;
import edu.entra21.fiberguardian.input.LaboratorioInput;
import edu.entra21.fiberguardian.input.NotaFiscalInput;
import edu.entra21.fiberguardian.model.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

        // Conversor para Item Nota Fiscal
        Converter<LaboratorioInput, ItemNotaFiscal> laboratorioToItemNotaFiscal =
                (MappingContext<LaboratorioInput, ItemNotaFiscal> ctx) -> {
                    LaboratorioInput src = ctx.getSource();

                    // Montar NotaFiscal com fornecedor (cnpj + codigoNf)
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setCnpj(src.getCnpj());

                    NotaFiscal nf = new NotaFiscal();
                    nf.setFornecedor(fornecedor);
                    nf.setCodigoNf(src.getCodigoNf());

                    // Montar Produto com fornecedor (cnpj + codProduto)
                    Produto produto = new Produto();
                    produto.setFornecedor(fornecedor);
                    produto.setCodigo(src.getCodProduto());

                    // Criar ItemNotaFiscal "stub"
                    ItemNotaFiscal item = new ItemNotaFiscal();
                    item.setNotaFiscal(nf);
                    item.setProduto(produto);

                    return item;
                };

        modelMapper.createTypeMap(LaboratorioInput.class, Laboratorio.class)
                .addMappings(m -> {
                      // Item Nota Fiscal
                    m.using(laboratorioToItemNotaFiscal)
                            .map(src -> src, Laboratorio::setItemNotaFiscal);
                    // Depois, mapeia explicitamente
                    m.using(emailToUsuario)
                            .map(LaboratorioInput::getEmailLaudoLab, Laboratorio::setLiberadoPor);
                });

        // Mapeamento de NotaFiscal -> NotaFiscalInput
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

        // Mapeamento de Laboratorio -> LaboratorioDto (usando TypeMap explícito)
        TypeMap<Laboratorio, LaboratorioDto> laboratorioTypeMap =
                modelMapper.createTypeMap(Laboratorio.class, LaboratorioDto.class);

        laboratorioTypeMap.addMappings(m -> {

            // Fornecedor
            m.map(src -> src.getItemNotaFiscal()
                            .getNotaFiscal()
                            .getFornecedor()
                            .getCnpj(),
                    LaboratorioDto::setCnpj);

            // Nota Fiscal
            m.map(src -> src.getItemNotaFiscal()
                            .getNotaFiscal()
                            .getCodigoNf(),
                    LaboratorioDto::setCodigoNf);

            // Produto
            m.map(src -> src.getItemNotaFiscal()
                            .getProduto()
                            .getCodigo(),
                    LaboratorioDto::setCodProduto);

            // Usuario
            m.map(src -> src.getLiberadoPor().getEmail(),
                    LaboratorioDto::setEmailLaudoLab);
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