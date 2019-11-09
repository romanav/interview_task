# Intoruction
I had several concerns how to implement that project:

1) I'm not familiar how to use Kafaka APIs ( I know concepts but never made hands on) - so cannot do it in time constrains that were set.
1) For E2E Automation, I recommend to do black box testing, but I don't have relevant system (Description of E2E test that should be done, below). We do black box testing because intervetion to the system work can cause for escaping bugs,  we can mock inputs and run databases with predefined data.

In that case we can generate mock for "publisher" and we can set database with predefined data, then write tests that will operate that data.

# Solution
In reason of described constrains I decide to write micro service that can read data and store in MongoDB in requested format (No kafka involved).This Solution contains one micro service, but it should be split to 2 micro-services. One read data, second fetch data. I put it in one project because, to make think more visible and simple. 

In reason we have micro services only, that not connected to the working system, it's meaningless to do black box E2E testing. Micro-service testing (component testing) better to do in level of unit tests and component test, because unit testing and component testing catch failures when code is compiling and developer get fast response about code quality. 

* In addition, I have implimented project in with TDD approach (I know it's important for you :) )

To compile project, fist need to start MongoDB service, run mongo_start.yml with docker compose from root directory of the project. 
```
docker-compose.exe -f mongo_start.yml up
```

Run "mvn install" from the root. Project  will compile and run unit tests that cover the functionality of the service.
```
mvn install
```

## Service implimentation description
Service expose following API:
1) "POST /input/addEntry" to write new entry with body in requested json format.
2) "GET /server/getMedians" to get specific publisher medians, with body format: 
```
{
  "publisher" : "value tracker 1",
  "entryCount": 1
}
```
In current solution I use mongoDb as dependancy for compilation that is not best way to do that, to run unit tests we can also MOC DB too, and compile code without running mongo. We can do it with several libraries that we have online. Also adapter desing pattern can be used here to avoid dependancy on specific DB

 

# Required E2E Solution
## System configuration
I see the entire working system in following way:

"Publisher" ---write---> "Kafka" <---read---- "Workers"  ----write---> "DB" <---read--- "Server" <---read----"UI"

1) We have data publishers that write data To Kafka.
1) "Worker" micor-service pull data from Kafka, calculate Median and put it in DB.
   To provide fast responce and high avialability it's better to use several workers 
1) "Server" pull data data from DB. 
    To provide high avialiability we can use load balancing approach (Ngnix or Traefik) to run several "Servers".
1) "UI micro-service" work directly with Server and request for relevant data for relevant worker
   

## E2E automation configuration
1) E2E test connect to workers or mimic their behaviour. Then publish data to the system (we can populate data as predifined set of values stored in file/db or generated in random way)
1) API tests can request for data and see if data returned in correct way.
1) In that system it's  better to add "Chaos Monkey" that will delete "Worker" and "Server" micro-services, and in that way we will able to see if the system can tolerate failures.
1) We need add load testing and populate data in very fast manner to check if system can handle it, without loosing data and without micro-service hangs or crashes
1) We need to check how system handle the big data. Connect database with huge amount of data (amount of data based on customers perspective) and check if system will handle it and will respond fast
1) Need to run UI test to validate that system behave in correct way (also can be done as component tests of UI micro-service)

