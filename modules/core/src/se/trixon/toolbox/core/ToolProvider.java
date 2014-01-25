package se.trixon.toolbox.core;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public interface ToolProvider {

    String getToolCategory();

    String getToolCopyright();

    String getToolDescription();

    String getToolLicense();

    String getToolName();

    String getToolOptionsPath();

    String getToolVersion();
}
