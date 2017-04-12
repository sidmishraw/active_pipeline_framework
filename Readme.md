## Active Pipeline framework
[Using lecture notes from Dr. Pearce](http://www.cs.sjsu.edu/faculty/pearce/modules/projects/pipes/index.htm)

A pipe is a message queue. A message can be anything. A filter is a process, thread, or other component that perpetually reads messages from an input pipe, one at a time, processes each message, then writes the result to an output pipe. Thus, it is possible to form pipelines of filters connected by pipes.

This uses active filters. Each filter has a run method that perpetually reads messages from its input pipe, processes them, then writes the processed message to its output pipe. Instead of a single message, a pipe has a message queue. A filter must wait for data if its input pipe is empty. Pipes must be monitors with synchronized read and write operations.


### Background
#### Filter Classification

There are four types of filters: *producers*, *consumers*, *transformers*, and *testers*. 

* A *producer* is a producer of messages. It has no input pipe. It generates a message into its output pipe. 

* A *consumer* is a consumer of messages. It has no output pipe. It eats messages taken from its input pipe. 

* A *transformer* reads a message from its input pipe, modulates it, then writes the result to its output pipe. (This is what DOS and UNIX programmers call filters.) 

* A *tester* reads a message from its input pipe, then tests it. If the message passes the test, it is written, unaltered, to the output pipe; otherwise, it is discarded. (This is what signal processing engineers call filters).


Filters can also be classified as *active* or *passive*.
 
* An active filter has a control loop that runs in its own process or thread. It perpetually reads messages from its input pipe, processes them, then writes the results to its output pipe. An active filter needs to be derived from a thread class provided by the operating system:

	```java
	class Filter extends Thread { ... }
	```

A passive filter has a control loop function. Here's a simplified version that assumes the filter is a transformer:


	void controlLoop()
	{
	   while(true)
	   {
	      Message val = inPipe.read();
	      val = transform(val); // do something to val
	      outPipe.write(val);
	   }
	}


When activated, a passive filter reads a single message from its input pipe, processes it, then writes the result to its output pipe:

	void activate()
	{
	   Message val = inPipe.read();
	   val = transform(val); // do something to val
	   outPipe.write(val);
	}

There are two types of passive filters. 

* A *data-driven filter* is activated when another filter writes a message into its input pipe. 
* A *demand-driven filter* is activated when another filter attempts to read a message from its empty output pipe.


### Full scope
After completing the framework, use the framework to implement:
* GarBage Band:
GarBage Band is a digital music studio that processes streams of musical notes. Each note has frequency, amplitude, and duration. (These can be floating point numbers.) GB provides the following configurable filters:

** Amplifiers: Increases or decreases the volume of each note
** Players: Play the notes through the computer's sound system.
** Noise Filters: Remove noise notes from the stream. A noise note can be a note that doesn't make sense: too loud, too quiet, too short, inaudible frequency, or a note that's suspiciously different from its predecessor (this might be static or a pop).
** Digital Composer: Generates a random sequence of <= 100 notes.


* [Airline Performance Analyzer:](http://www.cs.sjsu.edu/faculty/pearce/modules/projects/streams/index.htm)