# Makefile to clear .class files, compile all .java files, and run the main class

# Variables
JAVAC = javac
JAVA = java
MAIN_CLASS = Main  # Replace 'Main' with the name of your main class
JAVA_FILES = $(wildcard *.java)
CLASS_FILES = $(JAVA_FILES:.java=.class)

# Targets
all: compile

compile: $(CLASS_FILES)

%.class: %.java
	$(JAVAC) $<

run: compile
	$(JAVA) $(MAIN_CLASS)

clean:
	rm -f *.class

.PHONY: all compile clean run