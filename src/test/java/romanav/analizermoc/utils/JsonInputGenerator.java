package romanav.analizermoc.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JsonInputGenerator {
    private static ObjectMapper mapper = new ObjectMapper();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static ObjectNode getTestDataNode(String publisher, Date date, List<Integer> inputData) {

        ObjectNode node = mapper.createObjectNode();

        node.put("publisher", publisher);
        node.put("time", simpleDateFormat.format(date));

        ArrayNode arrayNode = mapper.createArrayNode();
        for (Integer i : inputData){
            arrayNode.add(i);
        }

        node.putPOJO("readings", arrayNode);
        return node;
    }
}
