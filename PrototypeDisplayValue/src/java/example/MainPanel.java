// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.util.Objects;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private static final String TITLE = "MMMMMMMMMMMMMMMMMMMM";

  private MainPanel() {
    super();
    ComboBoxModel<String> model1 = new DefaultComboBoxModel<>(new String[] {"a", "b", "c"});
    JComboBox<String> combo1 = new JComboBox<>(model1);
    combo1.setEditable(false);
    // ((JTextField) combo1.getEditor().getEditorComponent()).setColumns(20);

    JComboBox<String> combo2 = new JComboBox<>(model1);
    combo2.setPrototypeDisplayValue(TITLE);

    JComboBox<String> combo3 = new JComboBox<>(model1);
    combo3.setPrototypeDisplayValue(TITLE);
    combo3.setEditable(true);

    ComboBoxModel<WebSite> model2 = new DefaultComboBoxModel<>(new WebSite[] {
        new WebSite("a", new ColorIcon(Color.RED)),
        new WebSite("b", new ColorIcon(Color.GREEN)),
        new WebSite("c", new ColorIcon(Color.BLUE))});
    JComboBox<WebSite> combo4 = new JComboBox<>(model2);
    combo4.setRenderer(new SiteListCellRenderer<>());

    JComboBox<WebSite> combo5 = new JComboBox<>(model2);
    combo5.setRenderer(new SiteListCellRenderer<>());
    combo5.setPrototypeDisplayValue(new WebSite(TITLE, new ColorIcon(Color.GRAY)));

    JComboBox<WebSite> combo6 = new JComboBox<>();
    combo6.setRenderer(new SiteListCellRenderer<>());
    combo6.setPrototypeDisplayValue(new WebSite(TITLE, new ColorIcon(Color.GRAY)));

    SpringLayout layout = new SpringLayout();
    setLayout(layout);
    layout.putConstraint(SpringLayout.WEST, combo1, 10, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.WEST, combo2, 10, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.WEST, combo3, 10, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.WEST, combo4, 10, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.WEST, combo5, 10, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.WEST, combo6, 10, SpringLayout.WEST, this);

    layout.putConstraint(SpringLayout.NORTH, combo1, 10, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.NORTH, combo2, 10, SpringLayout.SOUTH, combo1);
    layout.putConstraint(SpringLayout.NORTH, combo3, 10, SpringLayout.SOUTH, combo2);
    layout.putConstraint(SpringLayout.NORTH, combo4, 10, SpringLayout.SOUTH, combo3);
    layout.putConstraint(SpringLayout.NORTH, combo5, 10, SpringLayout.SOUTH, combo4);
    layout.putConstraint(SpringLayout.NORTH, combo6, 10, SpringLayout.SOUTH, combo5);

    add(combo1);
    add(combo2);
    add(combo3);
    add(combo4);
    add(combo5);
    add(combo6);
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

class WebSite {
  public final Icon favicon;
  public final String title;

  protected WebSite(String title, Icon favicon) {
    this.title = title;
    this.favicon = favicon;
  }
}

class ColorIcon implements Icon {
  private final Color color;

  protected ColorIcon(Color color) {
    this.color = color;
  }

  @Override public void paintIcon(Component c, Graphics g, int x, int y) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.translate(x, y);
    g2.setPaint(color);
    g2.fillOval(4, 4, getIconWidth() - 8, getIconHeight() - 8);
    g2.dispose();
  }

  @Override public int getIconWidth() {
    return 24;
  }

  @Override public int getIconHeight() {
    return 24;
  }
}

class SiteListCellRenderer<E extends WebSite> extends JLabel implements ListCellRenderer<E> {
  @Override public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
    setOpaque(index >= 0);
    setEnabled(list.isEnabled());
    setFont(list.getFont());
    if (Objects.nonNull(value)) {
      setText(value.title);
      setIcon(value.favicon);
    }
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    return this;
  }
}
