rmdir /s /q bin
mkdir bin

set MAIN_SRC=src\desktoppet\main\DesktopPet.java

javac -sourcepath src -cp "lib/*" -d bin %MAIN_SRC%