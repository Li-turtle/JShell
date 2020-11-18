package command;


import util.IO;

/**
 * The base class for all commands.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.7
 * @since 1.0
 */
public interface Command {

  /**
   * Get Documentation for the command.
   *
   * @return The string of documentation
   */
  String getDoc();

  /**
   * Execute the command.
   *
   * @param args Arguments that passed to the command
   * @throws Exception various exceptions
   */
  void exec(String[] args, IO io) throws Exception;
}
