// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Highlighter;

public final class MainPanel extends JPanel {
  private final JTextArea textArea = new JTextArea("11111111111111\n22222\n3333");

  private MainPanel() {
    super(new BorderLayout());

    Caret hidingCaret = new DefaultCaret() {
      @Override public boolean isVisible() {
        return false;
      }
    };
    Caret defaultCaret = textArea.getCaret();
    Highlighter defaultHighlighter = textArea.getHighlighter();

    JCheckBox hidingCaretCheck = new JCheckBox("Hide Caret");
    hidingCaretCheck.addActionListener(e -> textArea.setCaret(isSelected(e) ? hidingCaret : defaultCaret));

    JCheckBox hidingHighlighterCheck = new JCheckBox("Hide Highlighter");
    hidingHighlighterCheck.addActionListener(e -> textArea.setHighlighter(isSelected(e) ? null : defaultHighlighter));

    JCheckBox editableCheck = new JCheckBox("Editable", true);
    editableCheck.addActionListener(e -> textArea.setEditable(isSelected(e)));

    JCheckBox focusableCheck = new JCheckBox("Focusable", true);
    focusableCheck.addActionListener(e -> textArea.setFocusable(isSelected(e)));

    JPanel p1 = new JPanel();
    p1.add(hidingCaretCheck);
    p1.add(hidingHighlighterCheck);

    JPanel p2 = new JPanel();
    p2.add(editableCheck);
    p2.add(focusableCheck);

    JPanel p = new JPanel(new BorderLayout(0, 0));
    p.add(p1, BorderLayout.NORTH);
    p.add(p2, BorderLayout.SOUTH);

    add(p, BorderLayout.NORTH);
    add(new JScrollPane(textArea));
    add(new JTextField(), BorderLayout.SOUTH);
    setPreferredSize(new Dimension(320, 240));
  }

  private static boolean isSelected(ActionEvent e) {
    return ((JCheckBox) e.getSource()).isSelected();
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
