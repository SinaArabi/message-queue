# message-queue
Implementing message queue using semaphores to perform read and write operations

Here you can try some scenarios to see how the queue acts in different critical problem situations

**S.1**
Queue is empty and get_msg() is blocked till the first message is written into the queue;
**S.2**
Queue has some messages and threads with get_msg() and get_msg_nb() are called in unexpected orders;
**S.3**
Queue is full and send_msg() can not perform any actions;
**S.4**
Both producers(send_msg) and consumers(get_msg) are running and dont bother each others tasks;


