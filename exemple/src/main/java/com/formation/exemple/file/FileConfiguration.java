package com.formation.exemple.file;

import java.util.ArrayList;
import java.util.List;

import com.formation.exemple.model.InputProduct;
import com.formation.exemple.model.OutputProduct;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class FileConfiguration {

    @Autowired
    private ProductProcessor productProcessor;

    @Bean
    public FlatFileItemReader<InputProduct> productReader() {
        FlatFileItemReader<InputProduct> reader = new FlatFileItemReader<InputProduct>();
        // Positionner la ressource d’entrée avec FileSystemResource ou
        // ClassPathResource
        reader.setResource(new FileSystemResource("exemple/src/main/resources/products.csv"));
        // Nombre de lignes d’entête à ignorer
        reader.setLinesToSkip(1);
        // définition d'un LineMapper
        // utilisation de l'implémentation par défaut : découpage en tableau de String
        // (LineTokenizer) et transformation en objet Java (FielSetMapper)
        reader.setLineMapper(new DefaultLineMapper<InputProduct>() {
            {
                // les colonnes de chaque ligne dans le bon ordre
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "id", "availability", "description", "hauteur", "largeur", "longueur",
                                "nom", "prix_unitaire", "reference", "fournisseur_id" });
                    }
                });
                // Mapping automatique avec l'objet Java correspondant
                setFieldSetMapper(new BeanWrapperFieldSetMapper<InputProduct>() {
                    {
                        setTargetType(InputProduct.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public FlatFileItemWriter<OutputProduct> productWriter() {
        return new FlatFileItemWriterBuilder<OutputProduct>().name("productWriter")//
                .resource(new FileSystemResource("output.txt"))// définition du nom du fichier
                // définition du lineAggregator delimited
                .delimited().delimiter(";") // ; à la place de la , en séparateur
                .names(new String[] { "reference", "nom", "hauteur", "instant" }) // colonnes à écrire dans le fichier
                .headerCallback(writer -> writer.write("LIGNE DE TITRE"))// écriture d'un en-tête de fichier
                .shouldDeleteIfExists(true) // suppression du fichier s'il existe
                .build();
    }

    @Bean
    public CompositeItemProcessor<InputProduct, OutputProduct> compositeItemProcessor() throws Exception {
        CompositeItemProcessor<InputProduct, OutputProduct> processor = new CompositeItemProcessor<>();
        List<ItemProcessor<InputProduct, ? extends Object>> itemProcessors = new ArrayList<>(2);
        itemProcessors.add(validatingProcessor());
        itemProcessors.add(productProcessor);
        processor.setDelegates(itemProcessors);
        return processor;
    }

    @Bean
    public BeanValidatingItemProcessor<InputProduct> validatingProcessor() throws Exception {
        BeanValidatingItemProcessor<InputProduct> ret = new BeanValidatingItemProcessor<>();
        ret.setFilter(true); // false pour faire la BeanValidation, true pour ne pas la faire
        ret.afterPropertiesSet();
        return ret;
    }

    @Bean
    public JsonItemReader<InputProduct> jsonProductReader() {
        return new JsonItemReaderBuilder<InputProduct>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(InputProduct.class))
                .resource(new ClassPathResource("products.json")).name("jsonProductReader").build();
    }

    @Bean
    public StaxEventItemWriter<OutputProduct> xmlProductWriter() throws Exception {
        return new StaxEventItemWriterBuilder<OutputProduct>().name("productXmlWriter")
                .marshaller(outputProductMarshaller()).resource(new FileSystemResource("output.xml"))
                .rootTagName("output-product").overwriteOutput(true).build();
    }

    @Bean
    public Jaxb2Marshaller outputProductMarshaller() throws Exception {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(OutputProduct.class);
        marshaller.afterPropertiesSet();
        return marshaller;
    }

}