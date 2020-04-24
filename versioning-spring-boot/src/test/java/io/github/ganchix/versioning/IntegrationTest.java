package io.github.ganchix.versioning;

import io.github.ganchix.versioning.context.normal.SpringApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by rafa on 26/01/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringApplicationTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IntegrationTest {

    private final String url = "/test";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSeveralApiVersion_ShouldReturnDistinctResultPerVersion() throws Exception {


        MvcResult result = mockMvc.perform(get(url).accept("application/vnd.app.resource-v1.0+json"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), is("version-1.0-2.0"));

        result = mockMvc.perform(get(url).accept("application/vnd.app.resource-v3.0+json"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), is("version-3.0-4.0"));


    }

    @Test
    public void testNonExistsApiVersion_ShouldReturnNotFound() throws Exception {


        mockMvc.perform(get(url).accept("application/vnd.app.resource-v5.0+json"))
                .andExpect(status().isNotFound())
                .andReturn();


    }

    @Test
    public void testInfiniteApiVersion_ShouldReturnTheLastApiVersion() throws Exception {


        MvcResult result = mockMvc.perform(get(url).accept("application/vnd.app.resource-v50.0+json"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), is("version-6-Infinite"));


    }

    @Test
    public void testIWithoutMediaType_ShouldReturnNotFound() throws Exception {

        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andReturn();


    }


}
