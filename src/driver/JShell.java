// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: zhuan103
// UT Student #: 1005721934
// Author: Ziheng Zhuang
//
// Student2:
// UTORID user_name: chensih4
// UT Student #: 1005719590
// Author: Sihao Chen
//
// Student3:
// UTORID user_name:lujiayu4
// UT Student #:1005706960
// Author:Jiayu Lu
//
// Student4:
// UTORID user_name: wang1399
// UT Student #: 1005723175
// Author: Yining Wang
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

package driver;

import command.Cat;
import command.Tree;
import command.Find;
import command.Pushd;
import command.Echo;
import command.Save;
import command.Cd;
import command.Cp;
import command.Curl;
import command.Exit;
import command.History;
import command.Load;
import command.Ls;
import command.Man;
import command.Mkdir;
import command.Mv;
import command.Pwd;
import command.Rm;
import command.Popd;
import command.Speak;
import command.Command;
import exception.InvalidFormatException;
import fs.FileSystem;
import fs.JFS;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import util.ArgParser;
import util.CommandHistory;
import util.DirectoryStack;
import util.FileNavigator;
import util.IO;
import util.JFN;

/**
 * The class that encapsulates all features of a Shell.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.7
 * @since 1.0
 */
public class JShell implements Serializable {

  /**
   * Map that maps command name to command object.
   */
  private Map<String, Command> dict;

  /**
   * Object that stores working directory.
   */
  public FileSystem fs;

  /**
   * Object that stores command history.
   */
  public CommandHistory commandHistory;

  /**
   * Object that stores directory stack.
   */
  public DirectoryStack directoryStack;

  /**
   * The start log of JShell.
   */
  private String startLog
      = "\n"
      + "                 ------                ___    __\n"
      + "        ------ /   __   \\             |   |  |  |\n"
      + "       |      /  /    \\  \\            |   |  |  |\n"
      + "        --   |  |      |__|           |   |  |  |\n"
      + "          |  |  |                     |   |  |  |\n"
      + "          |  |  |       __    __      |   |  |  |\n"
      + "          |   \\  \\ ____|  |  |  |     |   |  |  |\n"
      + "          |   |\\ _____   \\|  |  | ____|   |  |  |\n"
      + "   ___    |   |        \\  \\__|  /  __  \\  |  |  |   __\n"
      + "  |   |   |   |        |  |__  |  /__\\  | |  |  |  |  |\n"
      + "  |   \\___/   |\\       /  |  | |  ______| |  |  |__/  |\n"
      + "   \\          / \\ ___ /  /|  | |  \\__/  /  ------ ___ /\n"
      + "    \\ ______ /\\ _______ /_|  |__\\ ____ /_________/\n"
      + "\n"
      + "Welcome to JShell 1.7\n";

  /**
   * Initialize instance variables for JShell.
   */
  public JShell() {
    dict = new HashMap<>();
    FileNavigator fn = new JFN();
    fs = new JFS(fn);
    commandHistory = new CommandHistory();
    directoryStack = new DirectoryStack();
    registerCommand();
  }
  
  /**
   * set JShell fields.
   * 
   * @param fs FileSystem class
   * @param commandHistory CommandHistory class
   * @param directoryStack DirectoryStack class
   */
  
  public void setJShell(FileSystem fs, CommandHistory commandHistory, 
      DirectoryStack directoryStack) {
    this.fs = fs;
    this.commandHistory = commandHistory;
    this.directoryStack = directoryStack;
    FileNavigator savedFn = new JFN();
    fs = new JFS(savedFn);
    registerCommand();
  }

  /**
   * Register commands for JShell.
   */
  private void registerCommand() {
    dict.put("mkdir", new Mkdir(fs));
    dict.put("exit", new Exit(fs));
    dict.put("speak", new Speak());
    dict.put("cd", new Cd(fs));
    dict.put("pwd", new Pwd(fs));
    dict.put("pushd", new Pushd(directoryStack, fs));
    dict.put("popd", new Popd(directoryStack, fs));
    dict.put("ls", new Ls(fs));
    dict.put("history", new History(commandHistory));
    dict.put("cat", new Cat(fs));
    dict.put("echo", new Echo(fs));
    dict.put("man", new Man(dict));
    dict.put("mv", new Mv(fs));
    dict.put("rm", new Rm(fs));
    dict.put("save", new Save(this));
    dict.put("load", new Load(this));
    dict.put("curl", new Curl(fs));
    dict.put("find", new Find(fs));
    dict.put("tree", new Tree(fs));
    dict.put("cp", new Cp(fs));
  }

  /**
   * Parse string input to array.
   *
   * @param input the argument as a string
   * @return the String array after parsed
   */
  private String[] parseArg(String input) {
    ArrayList<String> arr = new ArrayList<>();
    input = input.trim();
    String s = "";

    // Loop over input to generate arg list
    for (char ch : input.toCharArray()) {
      if (ch == ' ' && !s.startsWith("\"") && !s.isEmpty()) {
        arr.add(s.trim());
        s = "";
        continue;
      } else if (ch == '\"' && s.startsWith("\"")) {
        s += '\"';
        arr.add(s.trim());
        s = "";
        continue;
      }
      s += s.isEmpty() && ch == ' ' ? "" : ch;
    }

    // Add string to argList at the end if it is not empty
    if (!s.isEmpty()) {
      arr.add(s.trim());
    }

    return arr.toArray(new String[arr.size()]);
  }

  /**
   * Execute the command and redirect output to file.
   *
   * @param dict The dictionary of command list
   * @param io The io object
   * @param args The argument list
   * @param cmd The command to execute
   * @throws Exception When commands have errors
   */
  private void redirection(Map<String, Command> dict, IO io,
      String[] args, String cmd) throws Exception {
    if (!ArgParser.isValidRedirection(args)) {
      throw new InvalidFormatException("Invalid redirection");
    }
    dict.get(cmd).exec(ArgParser.getCommand(args), io);
    String target = ArgParser.getDirectedFilename(args);
    if (io.flush().isEmpty() || io.hasErr()) {
      return;
    }
    if (ArgParser.isAppend(args)) {
      fs.appendToFile(target, io.flush());
    } else {
      fs.writeToFile(target, io.flush());
    }
  }

  /**
   * Print the prompt, read the input then distribute arguments to commands.
   */
  private void prompt() {
    IO.write("jshell:" + fs.getWorkingDirectory() + "$ ");

    // Read from terminal & get the parsed arg list
    String input = IO.readLine();
    commandHistory.add(input);
    String[] args = parseArg(input);
    // to the next iteration if no input detected
    if (args.length == 0) {
      return;
    }

    // Command name
    String command = args[0];
    // If command does not exist
    if (!dict.containsKey(command)) {
      IO.writeLine(command + ": command not found");
      return;
    }

    try {
      // Execute command
      IO io = new IO();
      if (ArgParser.hasRedirection(args)) {
        redirection(dict, io, args, command);
      } else {
        dict.get(command).exec(args, io);
        IO.write(io.flush());
      }
    } catch (Exception e) {
      IO.writeLine(e.getMessage());
    }
  }

  /**
   * A loop that perform infinite read-evaluate feature of JShell.
   */
  public void run() {
    IO.writeLine(startLog);
    // Use for loop to do construct the shell
    for (; ; ) {
      prompt();
    }
  }

  /**
   * The function that executes JShell.
   *
   * @param args no meaning here
   */
  public static void main(String[] args) {
    JShell jshell = new JShell();
    jshell.run();
  }
}
