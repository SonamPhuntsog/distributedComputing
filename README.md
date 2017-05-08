# distributedComputing
                          Distributed Computing of Matrix Operations

This project intents to solve relatively large computational problems by taking advantage of
spare processing power of many sparsely used computes. By doing so we want the effectively
emulate a high-end computer.

At the server system the user perceives the collection of autonomous processors as a unit
and becomes equipped to solving a large computational problem. When the server side receives
a computational problem, firstly it breaks the problem into subparts and sends the smaller
problems to several clients. All the client sides computes their subproblems and sends the
result to the central server, upon receiving all the answers to subproblems the server combines
the solutions to form the final solution which will be presented to the main user.
Applications of this can be numerous as many scientific and engineering fields uses
linear algebra extensively and most operations on matrixes and vectors can be divided smaller tasks,
each of which can solved by one or more computers.

Java's RMI(Remote Methon Invocaition) is used for distributed computing and TCP Socket is used to manage Volunteer subscriptions.
=====================================================================================

The main user can select operands form file and an operator for computation and the result 
will be saved in Result file chooser. List of online volunteers are shown in text 
area and gets updated automatically when a volunteer is added or removed.

![Main GUI](https://github.com/SonamPhuntsog/distributedComputing/blob/master/img/MainGUI.png)

This window opens when Operator1/Operator2 button is pressed, 
browse through your file system and select a file.

![Computation Successful](https://github.com/SonamPhuntsog/distributedComputing/blob/master/img/FileSelection.png)

File selected must have the following format                               
\# name: A                                                          
\# type: matrix                                                          
\# rows: 2                                                          
\# columns: 2                                                           
1.1 2.3                                                           
44.3 35                                                          
When the computation is finished a pop up will appear.
![Computation Successful](https://github.com/SonamPhuntsog/distributedComputing/blob/master/img/Success.png)


Volunteer machines have to subscribe to a main machine by entering ip and port.
![Volunteer GUI](https://github.com/SonamPhuntsog/distributedComputing/blob/master/img/VolunteerGUI.png)

When done contributing, volunteers can uncubscribe.                             
![Unsubscribe](https://github.com/SonamPhuntsog/distributedComputing/blob/master/img/Unsubscribe.png)




