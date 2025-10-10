package net.cyvfabric.event;

import net.cyvfabric.CyvFabric;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MacroFileInit {
    public static final String NAME = "macro.json";
    public static final String PATH = ConfigLoader.PATH + "macros/";
    public static final String FILEPATH = PATH + NAME;

    public static File macroFile;

    //initialises file
    public static void setupFile(String name) {
        File dir = new File(PATH);
        if (!dir.exists()) dir.mkdirs();
        dir = new File(PATH);
        macroFile = new File(PATH + name + ".json");

        try {
            if (!macroFile.exists()) {
                macroFile.createNewFile();
                FileWriter writer = new FileWriter(macroFile);
                writer.write("[]");
                writer.close();
            }

        } catch (IOException e) {
            CyvFabric.LOGGER.error(String.valueOf(e));
        }
    }

    //swaps or creates a new file
    public static void swapFile(String name) {
        File dir = new File(PATH);
        if (!dir.exists()) dir.mkdirs();
        dir = new File(PATH);
        macroFile = new File(PATH + name + ".json");

        try {
            if (!macroFile.exists()) {
                macroFile.createNewFile();
                FileWriter writer = new FileWriter(macroFile);
                writer.write("[]");
                writer.close();
            }

        } catch (IOException e) {
            CyvFabric.LOGGER.error(String.valueOf(e));
        }
    }
}