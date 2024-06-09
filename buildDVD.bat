rmdir /s /q bin
mkdir bin

set MAIN_SRC=src\plugin\DVD\DVD.java

javac -sourcepath src -d bin %MAIN_SRC%