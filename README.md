PoC on hz queues and executing python code

Designed to be run alongside a hazelcast image container. Running on localhost:5701.

args for processorService:

--spring.profiles.active=dev

Currently the processor service will just take Tasks from the hz task queue and run them.

PopulateTaskQueue will put some Tasks in the hz queue.

Requires that the pyRunner package is installed to whatever python you specify in the application-dev yaml.