package se.trixon.toolbox.core;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public interface ToolProvider {

    String getCategory();

    String getCopyright();

    String getDescription();

    String getLicense();

    String getName();

    String getOptionsPath();

    String getVersion();
}
