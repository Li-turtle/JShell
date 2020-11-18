package fs;

import java.util.Set;

/**
 * The interface that define the behavior of file system.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.7
 * @since 1.7
 */
public interface FileSystem {

  /**
   * Return the content of file given path, and throw exception if file does not exist, filename is
   * invalid or path does not represent a regular file.
   *
   * @param path The path of the regular file
   * @return The content of the given path
   * @throws Exception When path is invalid path name or file does not exist or file is not a
   *                   regular file
   */
  String getFileContent(String path) throws Exception;

  /**
   * Return the root directory of the file system.
   *
   * @return The root of the file system
   */
  Directory getRoot();

  /**
   * Set the working directory of the file system to path, and throw exception if path is invalid or
   * path does not exist or path is not a directory.
   *
   * @param path The path of the directory you want to change to
   * @throws Exception When path does not exist or is not a directory
   */
  void setWorkingDirectory(String path) throws Exception;

  /**
   * Return the current working directory in string format.
   *
   * @return The working directory in string format
   */
  String getWorkingDirectory();

  /**
   * Return the path names of the files in current working directory.
   *
   * @return The path names of the files (including regular files and directories) in current
   *     working directory
   */
  Set<String> getDirectoryContent();

  /**
   * Return the path names of the files in directory path, and throw exception if path is invalid,
   * path does not exist or path does not represent directory.
   *
   * @param path The target path name
   * @return The contents in path (including regular files and directories)
   * @throws Exception When path is invalid, path does not exist, or path does not represent
   *                   directory
   */
  Set<String> getDirectoryContent(String path) throws Exception;

  /**
   * Return the absolute paths of contents in the current working directory (including regular files
   * and directories).
   *
   * @return The absolute paths of contents in current working directory
   */
  Set<String> getDirectoryContentFullPath();

  /**
   * Return the full paths of contents in path, and throw error if path is invalid, path does not
   * exist or path does not represent directory.
   *
   * @param path The path of target directory
   * @return The full path of contents in path, including regular files and directories
   * @throws Exception When path is invalid, path does not exist, or path does not represent
   *                   directory
   */
  Set<String> getDirectoryContentFullPath(String path) throws Exception;

  /**
   * Check if path represents a regular file.
   *
   * @param path The path
   * @return True of path represents a regular file
   */
  boolean isRegularFile(String path);

  /**
   * Check if path represents a directory.
   *
   * @param path The path to check
   * @return True if path represents a directory
   */
  boolean isDirectory(String path);

  /**
   * Create a path, and throw error when parent of path does not exist, path is a invalid name, or
   * path already exist.
   *
   * @param path The path to create
   * @throws Exception When parent of path does not exist, path is a invalid name, or path already
   *                   exist
   */
  void createDirectory(String path) throws Exception;

  /**
   * Write content to regular file with path path. If path does not exist, create one. Throw error
   * when file does not exist and cannot be created, or path is a directory
   *
   * @param path    The path of regular file
   * @param content The content that writes to file
   * @throws Exception When files does not exist and cannot be created, or path is a directory
   */
  void writeToFile(String path, String content) throws Exception;

  /**
   * Append content to regular file with path path. If path does not exist, create one. Throw error
   * when file does not exist and cannot be created, or path is a directory
   *
   * @param path    The path of regular file
   * @param content The content that writes to file
   * @throws Exception When files does not exist and cannot be created, or path is a directory
   */
  void appendToFile(String path, String content) throws Exception;

  /**
   * Make a new copy of regular file at old path to new path. Throw exception when old path is
   * invalid, new path is invalid, old path does not exist, old path is not regular file or new file
   * cannot be created
   *
   * @param oldPath The path of the regular file to be duplicated
   * @param newPath The new path of the regular file
   * @throws Exception When old path is invalid, new path is invalid, old path does not exist, old
   *                   path is not regular file or new file cannot be created
   */
  void duplicateRegularFile(String oldPath, String newPath) throws Exception;

  /**
   * Make a new copy of directory at old path to new path. Throw exception when old path is invalid,
   * new path is invalid, old path does not exist, old path is not directory or new path cannot be
   * created
   *
   * @param oldPath The path of the directory to be duplicated
   * @param newPath The new path of the directory
   * @throws Exception When old path is invalid, new path is invalid, old path does not exist, old
   *                   path is not directory or new path cannot be created
   */
  void duplicateDirectory(String oldPath, String newPath) throws Exception;

  /**
   * Remove the file that path represents. Throw exception when path is invalid, path does not
   * exist
   *
   * @param path The path of the file
   * @throws Exception When path is invalid, path does not exist
   */
  void removeFile(String path) throws Exception;

  /**
   * Check if path can be remove.
   *
   * @param path The path to check
   * @return True if path can be removed
   * @throws Exception When path cannot be removed
   */
  boolean removable(String path) throws Exception;

  /**
   * Check if path is valid.
   *
   * @param path The path to check
   * @throws Exception When path is invalid or path does not exist
   */
  void verifyPath(String path) throws Exception;
}
