package romanav.analizermoc.controlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import romanav.analizermoc.utils.JsonInputGenerator;
import romanav.analizermoc.utils.ResourceReader;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InputControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void writeEntry() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/input/addEntry")
                .content(ResourceReader.read("request.json"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void trySendNull() throws Exception {
        ObjectNode json = JsonInputGenerator.getTestDataNode("publisher_1", new Date(), Arrays.asList(1, 2, 3, null, 5, 6));
        expectBadRequest(json);
    }

    @Test
    public void trySendEmptyData() throws Exception {
        ObjectNode json = JsonInputGenerator.getTestDataNode("publisher_1", new Date(), Collections.emptyList());
        expectBadRequest(json);
    }

    @Test
    public void trySendEmptyPublisher() throws Exception {
        ObjectNode json = JsonInputGenerator.getTestDataNode(null, new Date(), Collections.emptyList());
        expectBadRequest(json);
    }

    @Test
    public void trySendEmptyDate() throws Exception {

        ObjectNode node = mapper.createObjectNode();
        node.put("publisher", "somebody");
        node.put("time",  (String) null);

        ArrayNode arrayNode = mapper.createArrayNode();
        for (Integer i :  Arrays.asList(1, 2, 3, 4, 5, 6)){
            arrayNode.add(i);
        }

        node.putPOJO("readings", arrayNode);

        expectBadRequest(node);
    }

    private void expectBadRequest(ObjectNode json) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/input/addEntry")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
