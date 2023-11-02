### Client-Server with multi-threading
This is an example of how to make a multi-threaded server that implements Runnable.
It will be able to accept as many clients as there are threads that are available to the server.
I want to try and keep this entire thing thread safe, this way each client gets a thread
and won't have any interference from other clients on other threads.

The client side of this will be single threaded and interact with the server through
a terminal. The server will save very basic data on a per username basis.