# JShell Design Doc

File -> Directory, RegularFile

Directory ~> File
<!-- Directory would contain Directory, RegularFile, aka File -->

File ->> Contain the content of the file

WorkingDirectory ~> Directory (contain a current directory & root irectory)
<!-- WorkingDirectory ->> has a method to return the File object given filename (relative or absolute) -->

CommandHistory

DirectoryStack

GetFile ~> WorkingDirectory
GetFile ->> Get file given filename & working directory

Validator ->>
FileValidator, (given a name, check if it is a real file)
DirectoryValidator, (given a file object, check if it is a directory)
RegularFileValidator, (given a file object, check if it is a regularfile)
AbsolutePathValidator, (given a name, check if it is possibly the name of a absolute path)
RelativePathValidator, (given a name, check if it is possibly the name of a relative path)

Validator ~> WorkingDirectory

<!-- because if you want to validate if a string is the name of a file, you have to know the current directory (or working directory) -->

Command ~~> has a method called getDoc()

Command -> Mkdir, ...
Command(WorkingDirectory)
run(String[] args) {
}

JShell ~> WorkingDirectory
<!-- JShell need a current directory -->

<!-- 
class WorkingDirectory {
    private Directory directory
} -->