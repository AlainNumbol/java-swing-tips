package example;
// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

import java.awt.*;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public final class MainPanel extends JPanel {
  private final String[] columnNames = {"family", "name", "postscript name"};
  private final DefaultTableModel model = new DefaultTableModel(null, columnNames) {
    @Override public boolean isCellEditable(int row, int column) {
      return false;
    }

    @Override public Class<?> getColumnClass(int column) {
      return String.class;
    }
  };
  private final JTable table = new JTable(model);

  private MainPanel() {
    super(new BorderLayout());
    table.setAutoCreateRowSorter(true);
    Stream.of(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts())
      .map(f -> new String[] {f.getFamily(), f.getName(), f.getPSName()})
      .forEach(model::addRow);
    add(new JScrollPane(table));
    setPreferredSize(new Dimension(320, 240));
  }

  public static void main(String... args) {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        createAndShowGui();
      }
    });
  }

  public static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException
         | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
    }
    JFrame frame = new JFrame("@title@");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MainPanel());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
