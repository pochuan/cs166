CLASSFILES = RMQDriver.class driver/RMQFactory.class rmq/PrecomputedRMQ.class rmq/SparseTableRMQ.class rmq/HybridRMQ.class rmq/FischerHeunRMQ.class

all: $(CLASSFILES)

RMQDriver.class: RMQDriver.java
	javac RMQDriver.java

driver/RMQFactory.class: driver/RMQFactory.java
	javac driver/RMQFactory.java

rmq/PrecomputedRMQ.class: rmq/PrecomputedRMQ.java
	javac rmq/PrecomputedRMQ.java

rmq/SparseTableRMQ.class: rmq/SparseTableRMQ.java
	javac rmq/SparseTableRMQ.java

rmq/HybridRMQ.class: rmq/HybridRMQ.java
	javac rmq/HybridRMQ.java

rmq/FischerHeunRMQ.class: rmq/FischerHeunRMQ.java
	javac rmq/FischerHeunRMQ.java

clean:
	rm -f *~ *.class
	rm -f rmq/*~ rmq/*.class
	rm -f driver/*~ driver/*.class
