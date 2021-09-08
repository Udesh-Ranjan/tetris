build:
	cd src && javac -Xlint -cp :/home/dev/Downloads/jarfiles/javafx-sdk-16/lib/\*:/home/dev/Downloads/jarfiles/guava/\* tetris/application/GameApplication.java -d ../out && cd -

launch:
	cd out && java --module-path /home/dev/Downloads/jarfiles/javafx-sdk-16/lib --add-modules javafx.controls,javafx.graphics -cp :/home/dev/Downloads/jarfiles/javafx-sdk-16/lib/\*:/home/dev/Downloads/jarfiles/guava/\* tetris.application.GameApplication && cd -

clean: 
	rm -rf *.class

.PHONY: clean

tetris: clean build launch
