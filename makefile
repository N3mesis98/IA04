#!/usr/bin/make

COMPILER=javac
CFLAGS=-classpath lib/jade.jar:src -d bin
SRC=$(shell for file in `find src -name "*.java"`; do echo $$file; done)
OBJ=$(shell for file in `find src -name "*.java"`; do echo $$file | sed -e 's/^src/bin/' -e 's/\.java$$/\.class/'; done)
CONF=$(shell for file in `find src -name "*.conf"`; do echo $$file | sed -e 's/^src/bin/'; done)

all: $(OBJ) $(CONF)

bin/%.class: src/%.java
	$(COMPILER) $(CFLAGS) $(^)

bin/%.conf: src/%.conf
	cp $(^) $(@)

clean:
	rm -f $(OBJ)
