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
import romanav.analizermoc.utils.JsonInputGenerator;
import romanav.analizermoc.utils.ResourceReader;

import java.io.IOException;
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
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void fetchTwoLastEntriesInTimeDescendingOrder() throws Exception {

        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        Date date4 = new Date();

        addTestData("publisher_1", date1, Arrays.asList(1, 13, 192, 7, 8, 99, 1014, 4));
        addTestData("publisher_1", date2, Arrays.asList(1, 2, 3, 4, 5, 6));
        addTestData("publisher_1", date3, Arrays.asList(1, 13, 192, 7, 8, 99, 1014, 4));
        addTestData("Ignored Publisher", date4, Arrays.asList(1,2,3));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/server/getMedians")
                .content("{\"publisher\" : \"publisher_1\", \"entryCount\": 2}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertThat2ValuesReturnedInDescendingOrder(date2, date3, result);
   }

   @Test
   public void requestZeroOrLessEntities() throws Exception {
       addTestData("publisher_1", new Date(), Arrays.asList(1, 13, 192, 7, 8, 99, 1014, 4));
       MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/server/getMedians")
               .content("{\"publisher\" : \"publisher_1\", \"entryCount\": 0}")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn();
       assertEmptyListReturned(result);

       result = mvc.perform(MockMvcRequestBuilders.get("/server/getMedians")
               .content("{\"publisher\" : \"publisher_1\", \"entryCount\": -1}")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn();
       assertEmptyListReturned(result);
   }

   @Test
   public void requestNotExistingPublisher() throws Exception {
       MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/server/getMedians")
               .content(ResourceReader.read("request_not_existing.json"))
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn();

       assertEmptyListReturned(result);
   }

    private void assertEmptyListReturned(MvcResult result) throws IOException {
        JsonNode resultValues = mapper.readTree(result.getResponse().getContentAsString());
        assertThat(resultValues.size()).isEqualTo(0);
    }


    private void assertThat2ValuesReturnedInDescendingOrder(Date date2, Date date3, MvcResult result) throws IOException {
        JsonNode resultValues = mapper.readTree(result.getResponse().getContentAsString());
        assertThat(resultValues.size()).isEqualTo(2);

        assertThat(resultValues.get(0).get("time").asText()).isEqualTo(simpleDateFormat.format(date2));
        assertThat(resultValues.get(0).get("median").asDouble()).isEqualTo(10.5);
        assertThat(resultValues.get(0).get("publisher").asText()).isEqualTo("publisher_1");

        assertThat(resultValues.get(1).get("time").asText()).isEqualTo(simpleDateFormat.format(date3));
        assertThat(resultValues.get(1).get("publisher").asText()).isEqualTo("publisher_1");
    }

    private void addTestData(String publisher, Date date,  List<Integer> inputData) throws Exception {
        ObjectNode node = JsonInputGenerator.getTestDataNode(publisher, date,  inputData);

        mvc.perform(MockMvcRequestBuilders.post("/input/addEntry")
                .content(node.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



}

