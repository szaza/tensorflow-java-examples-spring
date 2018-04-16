#!/bin/bash
java -jar build/libs/java-tensorflow-service-0.1.0.jar -Xms1024m -Xmx1024m -Xss1M -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8