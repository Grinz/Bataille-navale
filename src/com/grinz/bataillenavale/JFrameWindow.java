package com.grinz.bataillenavale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JFrameWindow extends JFrame implements ActionListener{
    JPanel panel;
    JMenuBar menuBar;
    JMenu gameMenu;
    JMenu othersMenu;

    public JFrameWindow() {
        setVisible(true);
        setTitle("Bataille navale");
        setSize(1366, 768);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        panel = new JPanel();
        Container containerPanel = getContentPane();
        //TO DO : affichage tableaux
        containerPanel.add(panel);

        menuBar = new JMenuBar();
        gameMenu = new JMenu("Partie");
        JMenuItem menuItem1 = new JMenuItem("Nouvelle partie");
        menuItem1.addActionListener(this);
        JMenuItem menuItem2 = new JMenuItem("Charger une partie");
        menuItem2.addActionListener(this);
        JMenuItem menuItem3 = new JMenuItem("Sauvegarder la partie");
        menuItem2.addActionListener(this);
        gameMenu.add(menuItem1);
        gameMenu.add(menuItem2);
        gameMenu.add(menuItem3);

        othersMenu = new JMenu("Autre");
        menuItem1 = new JMenuItem("Paramètres");
        menuItem1.addActionListener(this);
        menuItem2 = new JMenuItem("Aide");
        menuItem2.addActionListener(this);
        othersMenu.add(menuItem1);
        othersMenu.add(menuItem2);

        menuBar.add(gameMenu);
        menuBar.add(othersMenu);
        setJMenuBar(menuBar);
    }

    public void actionPerformed(ActionEvent event) {

    }
}