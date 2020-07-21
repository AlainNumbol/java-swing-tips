// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());
    try {
      Thread.sleep(5000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
      UIManager.getLookAndFeel().provideErrorFeedback(this);
      Thread.currentThread().interrupt();
    }
    add(new JScrollPane(new JTree()));
    setPreferredSize(new Dimension(320, 240));
  }

  public static void main(String[] args) {
    System.out.println("main start / EDT: " + EventQueue.isDispatchThread());
    createAndShowGui();
    System.out.println("main end");
  }

  private static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
      Toolkit.getDefaultToolkit().beep();
    }
    JWindow splashScreen = new JWindow();
    EventQueue.invokeLater(() -> {
      System.out.println("splashScreen show start / EDT: " + EventQueue.isDispatchThread());
      ImageIcon img = new ImageIcon(MainPanel.class.getResource("splash.png"));
      splashScreen.getContentPane().add(new JLabel(img));
      splashScreen.pack();
      splashScreen.setLocationRelativeTo(null);
      splashScreen.setVisible(true);
      System.out.println("splashScreen show end");
    });

    System.out.println("createGUI start / EDT: " + EventQueue.isDispatchThread());
    JFrame frame = new JFrame("@title@");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MainPanel()); // new MainPanel() take long time
    frame.pack();
    frame.setLocationRelativeTo(null);
    System.out.println("createGUI end");

    EventQueue.invokeLater(() -> {
      System.out.println("  splashScreen dispose start / EDT: " + EventQueue.isDispatchThread());
      // splashScreen.setVisible(false);
      splashScreen.dispose();
      System.out.println("  splashScreen dispose end");

      System.out.println("  frame show start / EDT: " + EventQueue.isDispatchThread());
      frame.setVisible(true);
      System.out.println("  frame show end");
    });
  }
}

//   public static void main(String[] args) {
//     System.out.println("main start / EDT: " + EventQueue.isDispatchThread());
//     EventQueue.invokeLater(MainPanel::createAndShowGui);
//     System.out.println("main end");
//   }
//
//   println static void createAndShowGui() {
//     try {
//       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//     } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//       ex.printStackTrace();
//     }
//     System.out.println("splashScreen show start / EDT: " + EventQueue.isDispatchThread());
//     JWindow splashScreen = new JWindow();
//     ImageIcon img = new ImageIcon(MainPanel.class.getResource("splash.png"));
//     splashScreen.getContentPane().add(new JLabel(img));
//     splashScreen.pack();
//     splashScreen.setLocationRelativeTo(null);
//     splashScreen.setVisible(true);
//     System.out.println("splashScreen show end");
//
//     JFrame frame = new JFrame("@title@");
//     frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//     new SwingWorker() {
//       @Override protected Object doInBackground() {
//         System.out.println("frame make start / EDT: " + EventQueue.isDispatchThread());
//         frame.getContentPane().add(new MainPanel()); // new MainPanel() take long time
//         System.out.println("frame make end");
//         return "Done";
//       }
//
//       @Override protected void done() {
//         System.out.println("splashScreen dispose start / EDT: " + EventQueue.isDispatchThread());
//         splashScreen.dispose();
//         System.out.println("splashScreen dispose end");
//         System.out.println("frame show start / EDT: " + EventQueue.isDispatchThread());
//         frame.pack();
//         frame.setLocationRelativeTo(null);
//         frame.setVisible(true);
//         System.out.println("frame show end");
//       }
//     }.execute();
//   }
// }
