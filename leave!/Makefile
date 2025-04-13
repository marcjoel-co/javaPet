# Makefile for Java project

# Variables
JAVAC = javac
JAVA = java
SRC = $(wildcard *.java)
CLASS = $(SRC:.java=.class)
MAIN = Main  # Replace with your main class name

# Default target
all: compile run

# Compile Java files
compile: $(CLASS)

%.class: %.java
	$(JAVAC) $<

# Run the project
run: $(CLASS)
	$(JAVA) $(MAIN)

# Clean up .class files
clean:
	rm -f *.class

.PHONY: all compile run clean