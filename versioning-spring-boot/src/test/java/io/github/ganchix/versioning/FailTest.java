package io.github.ganchix.versioning;

import io.github.ganchix.versioning.context.error.accept.SpringApplicationAcceptFailTest;
import io.github.ganchix.versioning.context.error.collision.SpringApplicationCollisionFailTest;
import io.github.ganchix.versioning.context.error.from.SpringApplicationFromFailTest;
import io.github.ganchix.versioning.context.error.media.SpringApplicationAcceptDifferentFailTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by rafa on 26/01/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class FailTest {

    @Test(expected = Exception.class)
    public void testWhenVersionInSamePathCollision_ShouldReturnAException() throws Exception {

        AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext(SpringApplicationCollisionFailTest.class);
    }

    @Test(expected = Exception.class)
    public void testWhenVersionWithoutFrom_ShouldReturnAException() throws Exception {

        AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext(SpringApplicationFromFailTest.class);
    }

    @Test(expected = Exception.class)
    public void testWhenVersionWithoutAccept_ShouldReturnAException() throws Exception {

        AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext(SpringApplicationAcceptFailTest.class);
    }

    @Test(expected = Exception.class)
    public void testWhenVersionWithDifferentAccept_ShouldReturnAException() throws Exception {
        AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext(SpringApplicationAcceptDifferentFailTest.class);
    }

}
