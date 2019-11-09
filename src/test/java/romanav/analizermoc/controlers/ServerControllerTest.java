package romanav.analizermoc.controlers;


import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServerControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void fetchTwoLastEntriesInTimeDescendingOrder() throws Exception {


        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();

        addTestData(date1, Arrays.asList(1, 13, 192, 7, 8, 99, 1014, 4));
        addTestData(date2, Arrays.asList(1, 2, 3, 4, 5, 6));
        addTestData(date3, Arrays.asList(1, 13, 192, 7, 8, 99, 1014, 4));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/server/getMedians")
                .content("{\"publisher\" : \"publisher_1\", \"entryCount\": 2}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        JsonNode resultValues = mapper.readTree(result.getResponse().getContentAsString());
        assertThat(resultValues.size()).isEqualTo(2);

        assertThat(resultValues.get(0).get("time").asText()).isEqualTo(simpleDateFormat.format(date2));
        assertThat(resultValues.get(0).get("median").asDouble()).isEqualTo(10.5);

        assertThat(resultValues.get(1).get("time").asText()).isEqualTo(simpleDateFormat.format(date3));
        assertThat(resultValues.get(1).get("median").asDouble()).isEqualTo(3.5);

   }

    private void addTestData(Date date,  List<Integer> inputData) throws Exception {
        ObjectNode node = getTestDataNode(date,  inputData);


        mvc.perform(MockMvcRequestBuilders.post("/input/addEntry")
                .content(node.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private ObjectNode getTestDataNode(Date date, List<Integer> inputData) {


        ObjectNode node = mapper.createObjectNode();

        node.put("publisher", "publisher_1");
        node.put("time", simpleDateFormat.format(date));

        ArrayNode arrayNode = mapper.createArrayNode();
        for (Integer i : inputData){
            arrayNode.add(i);
        }

        node.putPOJO("readings", arrayNode);
        return node;
    }

}

