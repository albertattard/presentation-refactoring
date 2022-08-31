# Proxy

A simple project showing how a proxy can be used to capture requests coming into
service (that will be replaced) and going out from the service.  The responses
and requests produced by the _old-service_ and the _new-service_ can be compared
to ensure that the _new-service_ is behaving as expected, before fully switching
to the _new-service_.
