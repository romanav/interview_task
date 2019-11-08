package romanav.analizermoc.controlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import romanav.analizermoc.utils.ResourceReader;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void writeEntry() throws Exception {


        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();

        addTestData(date1);
        addTestData(date2);
        addTestData(date3);


        mvc.perform(MockMvcRequestBuilders.get("/server/getMedians")
                .content(ResourceReader.read("publisher_read.json"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    private void addTestData(Date date1) throws Exception {
        ObjectNode node = getTestDataNode(date1);


        mvc.perform(MockMvcRequestBuilders.post("/input/addEntry")
                .content(node.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private ObjectNode getTestDataNode( Date date1) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("publisher", "publisher_1");
        node.put("time", format.format(date1));

        ArrayNode arrayNode = mapper.createArrayNode();
        for (Integer i : Arrays.asList(1, 13, 192, 7, 8, 99, 1014, 4)){
            arrayNode.add(i);
        }

        node.putPOJO("readings", arrayNode);
        return node;
    }

}
