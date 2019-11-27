// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicToolBarUI;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());

    JToolBar toolbar = new JToolBar("ToolBar");
    toolbar.add(new JCheckBox("JCheckBox"));
    toolbar.add(new JTextField(10));

    EventQueue.invokeLater(() -> {
      Container w = getTopLevelAncestor();
      if (w instanceof Window) {
        // Setting a specific location for a floating JToolBar
        // https://stackoverflow.com/questions/41701664/setting-a-specific-location-for-a-floating-jtoolbar
        Point pt = w.getLocation();
        BasicToolBarUI ui = (BasicToolBarUI) toolbar.getUI();
        ui.setFloatingLocation(pt.x + 120, pt.y + 160);
        ui.setFloating(true, null);
      }
    });

    // // TEST: Here is another approach
    // EventQueue.invokeLater(() -> {
    //   Window w = (Window) getTopLevelAncestor();
    //   Point pt = w.getLocation();
    //   ((BasicToolBarUI) toolbar.getUI()).setFloating(true, null);
    //   Container c = toolbar.getTopLevelAncestor();
    //   if (c instanceof Window) {
    //     ((Window) c).setLocation(pt.x + 120, pt.y + 160);
    //   }
    // });

    add(toolbar, BorderLayout.NORTH);
    add(Box.createHorizontalStrut(0), BorderLayout.WEST);
    add(Box.createHorizontalStrut(0), BorderLayout.EAST);
    add(new JScrollPane(new JTree()));
    setPreferredSize(new Dimension(320, 240));
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(MainPanel::createAndShowGui);
  }

  private static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
      Toolkit.getDefaultToolkit().beep();
    }
    JFrame frame = new JFrame("@title@");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MainPanel());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
