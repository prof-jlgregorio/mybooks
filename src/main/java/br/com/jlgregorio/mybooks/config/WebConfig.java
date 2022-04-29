package br.com.jlgregorio.mybooks.config;

import br.com.jlgregorio.mybooks.serialization.converter.YamlJackson2HttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
//                .allowedOrigins("http://localhost:8081", "http://www.jlgregorio.com.br");
//    }

    private static final MediaType MEDIA_TYPE_YAML = MediaType.valueOf("application/x-yaml");

    public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
        converters.add(new YamlJackson2HttpMessageConverter());
    }

/*    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
        configurer.favorParameter(true)
                  .parameterName("mediaType")
                  .ignoreAcceptHeader(false)
                  .useRegisteredExtensionsOnly(false)
                  .defaultContentType(MediaType.APPLICATION_JSON)
                  .mediaType("xml", MediaType.APPLICATION_XML)
                  .mediaType("json", MediaType.APPLICATION_JSON)
                  .mediaType("x-yaml", MEDIA_TYPE_YAML);
    }*/

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
        configurer.favorParameter(true)
                .ignoreAcceptHeader(false)  //.. remove parameterName method and don't ignore Accept Header
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("x-yaml", MEDIA_TYPE_YAML);
    }


}
