I had several concerns how to implement that project:

1) I'm not familiar how to use Kafaka APIs(I do know concepts but have time issue to learn it right now)
1) For E2E Automation I do recommend to do black box testing, because intervention to micro-service work can hide bugs(escaping bugs)
1) In black box testing we can replace some parts. 
In that case we can generate mock for "publisher" and we can set database with predefined data, but I don't have that system


In reason of described constrains I decide to write micro service that knows to populate DB with data and also can provide median.
Micro-service testing (component testing) better to do in level of unit tests and component test.
Unit tests and component test failures we want to catch when code is compiling, because we need to get fast response during development.
In that reason all micro-service testing implemented as unit tests in that project.

 

   