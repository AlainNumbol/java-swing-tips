// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import javax.swing.*;
// import javax.swing.plaf.basic.BasicButtonUI;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());
    // toolbar.setUI(new BasicToolBarUI() {
    //   @Override public boolean canDock(Component c, Point p) {
    //     return super.canDock(c, p) && isHorizontalDockingConstraint(c, p);
    //   }
    //
    //   private boolean isHorizontalDockingConstraint(Component c, Point p) {
    //     if (!c.contains(p)) {
    //       return false;
    //     }
    //     int iv = toolBar.getOrientation() == JToolBar.HORIZONTAL
    //         ? toolBar.getSize().height
    //         : toolBar.getSize().width;
    //     return p.x < c.getWidth() - iv && p.x >= iv;
    //   }
    // });

    add(Box.createRigidArea(new Dimension()), BorderLayout.WEST);
    add(Box.createRigidArea(new Dimension()), BorderLayout.EAST);

    JToolBar toolbar = new JToolBar("toolbar");
    toolbar.add(new JLabel("label"));
    toolbar.add(Box.createRigidArea(new Dimension(5, 5)));

    JButton button = new JButton("button");
    button.setFocusable(false);
    toolbar.add(button);
    toolbar.add(Box.createRigidArea(new Dimension(5, 5)));

    JComboBox<String> combo = new JComboBox<>(makeModel());
    toolbar.add(combo);
    toolbar.add(Box.createGlue());

    JLabel label = new JLabel();
    label.setText("<html>dockable: NORTH, SOUTH<br>undockable: EAST, WEST");
    label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(toolbar, BorderLayout.NORTH);
    add(label);
    setPreferredSize(new Dimension(320, 240));
  }

  private static ComboBoxModel<String> makeModel() {
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    model.addElement("1111111");
    model.addElement("22222");
    model.addElement("3333333333333333");
    model.addElement("44444444444");
    return model;
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
