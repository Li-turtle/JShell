class File {
    private String fileName;
    public File(String fileName);
    public String getName();
    public void setName();
}

class Directory extends File {
    private HashMap<String, File> fileList;
    public Directory(String name, Directory parent);
    public boolean hasFile(String name);
    public void addFile(String name, File file);
    public void removeFile(String name);
    public void renameFile(String oldName, String newName);
    public File getFile(String name);
    public File getParentDirectory();
    public Directory getRoot();
}

class RegularFile extends File {
    private String content;
    public RegularFile(String fileName);
    public void write(String content);
    public void append(String content);
    public String read();
}

class WorkingDirectory {
    private Directory curDir;
    public Directory getCurDir();
    public void setCurDir(Directory directory);
}

class CommandHistory {
    private ArrayList<String> history;
    public ArrayList<String> getAllHistory();
    public ArrayList<String> getLatestHistory(int limit);
}

class DirectoryStack {
    private Stack<Directory> directoryStack;
    public void push(Directory directory);
    public Directory pop();
}

// Deprecated
// class GetFile {
//     public static File getFile(String filename, WorkingDirectory workingdirectory);
//     public static File getRoot(WorkingDirectory workingDirectory);
// }

// Deprecated
// class Validator {
//     public static boolean fileValidator(String name, WorkingDirectory dir);
//     public static boolean directoryValidator(File file);
//     public static boolean regularFileValidator(File file);
//     public static boolean absolutePathValidator(String path);
//     public static boolean relativePathValidator(String path);
// }

class FileNavigator {
    // store the parsed path data
    // /a/b/c//////../.
    // becomes
    // {"/", "a", "b", "c", "..", "."}
    private ArrayList<String> path;
    // store the working directory
    private WorkingDirectory workingDirectory;
    // make
    // /.///../das/asd/dsa///
    // be
    // /./../das/asd/dsa
    // aka remove additional back slash & trailing back slash
    private String purifyPath(String pathName);
    // parse purified path string to an array
    // /./../das/asd/dsa
    // become
    // {"/", ".", "..", "das", "asd", "dsa"}
    private ArrayList<String> decomposePath(String pathName);
    // init
    public FileNavigator(String pathName, WorkingDirectory workingDirectory);
    // another init
    public FileNavigator(WorkingDirectory workingDirectory);
    public void setPath(String pathName);

    // return the purified string
    public String toString();

    // check if path indicates a file
    public boolean isFile();
    // return the file
    public File getFile();
    
    // check if it is regular file
    public boolean isRegularFile();
    // check if it is directory
    public boolean isDirectory();

    // check if the path is absolute (starts with /)
    public boolean isAbsolute();
    // reverse of isAbsolute
    public boolean isRelative();

    // see if the path has a parent
    public boolean hasParent();

    public FileNavigator getParent();

    public String getCur();
}

class Command {
    WorkingDirectory workingDirectory;
    public String getDoc();
    public void exec(String[] args) throw exception;
}

class JShell {
    private WorkingDirectory workingDirectory;
    private CommandHistory commandHistory;
    private DirectoryStack directoryStack;
    private void prompt();
    public void run() {
        while (true) {
            prompt();
        }
    }
}

class Driver {
    public static void main(String[] args) {
        JShell jshell = new JShell();
        jshell.run();
    }
}

class FileExistException extends Exception {
    ...
}

class FileNotExistException extends Exception {
    ...
}

class ArityMismatchException extends Exception {
    ...
}

...