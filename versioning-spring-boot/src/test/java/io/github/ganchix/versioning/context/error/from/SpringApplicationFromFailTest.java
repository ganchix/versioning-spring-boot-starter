package io.github.ganchix.versioning.context.error.from;

import io.github.ganchix.versioning.annotation.VersionedResource;
import io.github.ganchix.versioning.context.WebContextConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@ImportAutoConfiguration(WebContextConfiguration.class)
public class SpringApplicationFromFailTest {

}

@RestController
@RequestMapping("/fromFailTest")
@VersionedResource(media = "application/vnd.app.resource")
class TestControllerFail {

    @RequestMapping
    @VersionedResource(to = "2.0")
    public String testVersion() {
        return "version-1.0-2.0";
    }



}
