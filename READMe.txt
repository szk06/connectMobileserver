This Project is Created by:
Sami Kanafani & Hasan Shami

Brief Description:
The project is divided into 2 parts, the mobile application and the server side.
For the Mobile application, the android application will gather information about devices using the p2p (wifi direct) such as username and MAC address.

The information gathered by the mobile phone will be pushed to the server.
The server will recieve the username and the MAC address of the sender, and will recieve the information the sender gathered such as the MAC addresses of the mobile devices detected.


The server recieves these information as a POST from mobile application, it will store the information gathered in the mySQL database.

The mobile application can request a graph form the server, the server will create a graph. The devices are the nodes, and thos nodes are connected in the graph based on which device detected the other device.

Code:
Server:
The server is written in php
The database is MYSQL database 
arbor.js was used to draw the graph

Android: 
Java for backend, xml for layout 

Directories:
src: contains the code of the mobile application
conn: contains the code of server that inserts the information recieved from the server into the database
project_451: contains the code of the graph

Thank you for reading
