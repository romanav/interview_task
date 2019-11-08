package romanav.analizermoc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ResourceReader {

    public static String read(String relativePath){

        InputStream stream = ResourceReader.class.getClassLoader().getResourceAsStream(relativePath);
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readTree(stream).toString();
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

    }
}
