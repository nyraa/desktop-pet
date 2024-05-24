rmdir /s /q bin/desktoppet
cd bin
mkdir desktoppet
cd ..

set MAIN_SRC=src\desktoppet\main\DesktopPet.java

javac -sourcepath src -d bin %MAIN_SRC%