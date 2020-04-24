# Spring boot starter Versioning [![Build Status](https://travis-ci.org/github/ganchix/versioning-spring-boot-starter.svg?branch=master)](https://travis-ci.org/github/ganchix/versioning-spring-boot-starter) [![codecov](https://codecov.io/gh/ganchix/versioning-spring-boot-starter/branch/master/graph/badge.svg)](https://codecov.io/gh/ganchix/versioning-spring-boot-starter) [![Join the chat at https://gitter.im/ganchix/versioning-spring-boot-starter](https://badges.gitter.im/ganchix/versioning-spring-boot-starter.svg)](https://gitter.im/ganchix/versioning-spring-boot-starter?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.ganchix/versioning-spring-boot-parent/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/io.github.ganchix/versioning-spring-boot-parent) [![GitHub stars](https://img.shields.io/github/stars/badges/shields.svg?style=social&label=Star)](https://github.com/ganchix/versioning-spring-boot-starter)

Spring boot starter using for versioning rest easily.

# Table of Contents
 
- [Overview](#overview)
- [Getting started](#getting-started)
- [License](#license)


### Overview

This implementation is based on the idea of this [StackOverflow](http://stackoverflow.com/questions/20198275/how-to-manage-rest-api-versioning-with-spring) thread.
For this approach we think that versioning with accept headers is the most RestFull option.
 

### Getting started
#### Code example

An example from the server side:

```java
import io.github.ganchix.microservice.starter.annotation.VersionedResource;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
    
@RestController
@RequestMapping("/test")
@VersionedResource(media = "application/vnd.app.resource")
class TestController {
    
    @RequestMapping
    @VersionedResource(from = "1.0", to = "2.0")
    public String testVersion1And2() {
        return "version-1.0-2.0";
    }
    
    @RequestMapping
    @VersionedResource(from = "3.0", to = "4.0")
    public String testVersion3And4() {
        return "version-3.0-4.0";
    }
    
    @RequestMapping
    @VersionedResource(from = "6.0")
    public String testVersion6ToInfinite() {
        return "version-6-Infinite";
    }
}
```

An example from the client side:

```java
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
 
public class DemoClient {
    
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        //Version 1
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.app.resource-v1.0+json");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/test", HttpMethod.GET, request, String.class);
        if(!response.getBody().equals("version-1.0-2.0") ){
            throw new RuntimeException("Error in version ");
        }
        //Version 3
        headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.app.resource-v3.0+json");
        request = new HttpEntity<>(headers);
        response = restTemplate.exchange("http://localhost:8080/test", HttpMethod.GET, request, String.class);
        
        if(!response.getBody().equals("version-3.0-4.0") ){
            throw new RuntimeException("Error in version ");
        }
    }
}
```


#### Integration using `@SpringBootApplication` or `@EnableAutoConfiguration` 

Only add Maven dependency:

```xml
<dependency>
    <groupId>io.github.ganchix</groupId>
    <artifactId>versioning-spring-boot-starter</artifactId>
    <version>LATEST_VERSION</version>
</dependency>

```

#### Without `@SpringBootApplication` or `@EnableAutoConfiguration` 

Add Maven dependency:

```xml
<dependency>
    <groupId>io.github.ganchix</groupId>
    <artifactId>versioning-spring-boot</artifactId>
    <version>LATEST_VERSION</version>
</dependency>

```

Configure manually `RequestMappingHandlerMapping` in your configuration

```java
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
 
@Configuration
public class WebVersionAutoConfig extends WebMvcConfigurationSupport {
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        VersionRequestMappingHandlerMapping handlerMapping = new VersionRequestMappingHandlerMapping();
        handlerMapping.setOrder(1);
        return handlerMapping;
    }
}
```


### License

Spring boot starter versioning is licensed under the MIT License. See [LICENSE](LICENSE.md) for details.

Copyright (c) 2020 Rafael Ríos Moya.