#Intoruction
I had several concerns how to implement that project:

1) I'm not familiar how to use Kafaka APIs(I do know concepts but have time issue to learn it right now)
1) For E2E Automation I do recommend to do black box testing, but I don't have relevant system. We do black box testing because intervetion to the system work can cause to escaping bugs, for sure we can mock inputs and run databases with predefined data.

In that case we can generate mock for "publisher" and we can set database with predefined data, then write tests that will operate that data.

#Solution
In reason of described constrains I decide to write micro service that knows how to populate DB with data (without Kafka) and also can provide median.
Micro-service testing (component testing) better to do in level of unit tests and component test.
Reason to do unit testing and component testing is to catch failures when code is compiling. In that case developer will get fast response.

To compile project, fist need to start MongoDB service, run mongo_start.yml with docker compose from root directory of the project. 
```
docker-compose.exe -f mongo_start.yml up
```

Run "mvn install" from the root. Project  will compile and run unit tests that cover the functionality of the service.
```
mvn install
```

##Service implimentation description
Service expose to APIs 
"POST /input/addEntry" to write new entry with body in requested json format
"GET /server/getMedians" to get specific publisher medians. Request body format: 
```
{
  "publisher" : "value tracker 1",
  "entryCount": 1
}
```


 

   
