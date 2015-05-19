package de.gemo.permconfig.core;

import javax.swing.*;

/**
 * Created by GeMo on 16.05.2015.
 */

public class StartUp {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't use system look and feel.");
        }
        new MainFrame();

    }

}
