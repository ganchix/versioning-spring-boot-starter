package io.github.ganchix.versioning.context.error.media;

import io.github.ganchix.versioning.annotation.VersionedResource;
import io.github.ganchix.versioning.context.WebContextConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rafael Ríos on 3/04/17.
 */
@SpringBootApplication
@ImportAutoConfiguration(WebContextConfiguration.class)
public class SpringApplicationAcceptDifferentFailTest {
}

@RestController
@VersionedResource(media = "application/vnd.app.resource")
@RequestMapping("/acceptDiferentFailTest")
class TestControllerAcceptDiferentFailTest {

    @RequestMapping
    @VersionedResource(media = "application/vnd.app.resource1", from = "1.0", to = "2.0")
    public String testVersion() {
        return "version-1.0-2.0";
    }




}