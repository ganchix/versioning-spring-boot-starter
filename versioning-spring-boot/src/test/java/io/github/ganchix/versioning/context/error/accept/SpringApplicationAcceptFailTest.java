package io.github.ganchix.versioning.context.error.accept;

import io.github.ganchix.versioning.annotation.VersionedResource;
import io.github.ganchix.versioning.context.WebContextConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rafael Ríos on 2/04/17.
 */

@SpringBootApplication
@ImportAutoConfiguration(WebContextConfiguration.class)
public class SpringApplicationAcceptFailTest {

}

@RestController
@RequestMapping("/acceptFailTest")
class TestControllerFail {

    @RequestMapping
    @VersionedResource(from = "1.0", to = "2.0")
    public String testVersion() {
        return "version-1.0-2.0";
    }



}