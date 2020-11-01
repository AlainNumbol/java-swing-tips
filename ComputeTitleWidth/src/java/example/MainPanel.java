// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());
    JDesktopPane desktop = new JDesktopPane();
    desktop.setDesktopManager(new DefaultDesktopManager() {
      @Override public void iconifyFrame(JInternalFrame f) {
        Rectangle r = this.getBoundsForIconOf(f);
        r.width = f.getDesktopIcon().getPreferredSize().width;
        f.getDesktopIcon().setBounds(r);
        super.iconifyFrame(f);
      }
    });
    desktop.add(createFrame("looooooooooooong title #", 1));
    desktop.add(createFrame("#", 0));

    AtomicInteger idx = new AtomicInteger(2);
    JButton button = new JButton("add");
    button.addActionListener(e -> {
      JInternalFrame f = createFrame("#", idx.getAndIncrement());
      desktop.add(f);
      desktop.getDesktopManager().activateFrame(f);
    });

    add(desktop);
    add(button, BorderLayout.SOUTH);
    setPreferredSize(new Dimension(320, 240));
  }

  public JInternalFrame createFrame(String t, int i) {
    JInternalFrame f = new JInternalFrame(t + i, true, true, true, true);
    f.setDesktopIcon(new JInternalFrame.JDesktopIcon(f) {
      @Override public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        String title = f.getTitle();
        Font font = getFont();
        if (Objects.nonNull(font)) {
          testWidth();

          // @see javax/swing/plaf/basic/BasicInternalFrameTitlePane.java Handler#minimumLayoutSize(Container)
          // Calculate width.
          int buttonsW = 22;
          if (f.isClosable()) {
            buttonsW += 19;
          }
          if (f.isMaximizable()) {
            buttonsW += 19;
          }
          if (f.isIconifiable()) {
            buttonsW += 19;
          }
          // buttonsW = Math.max(buttonsW, buttonsW2);

          FontMetrics fm = getFontMetrics(font);
          int titleW = SwingUtilities.computeStringWidth(fm, title);
          Insets i = getInsets();
          d.width = buttonsW + i.left + i.right + titleW + 2 + 2 + 2; // 2: Magic number of gap between icons
          d.height = Math.min(27, d.height); // 27: Magic number for NimbusLookAndFeel
          System.out.println("BasicInternalFrameTitlePane: " + d.width);
        }
        return d;
      }

      private void testWidth() {
        Dimension dim = getLayout().minimumLayoutSize(this);
        System.out.println("minimumLayoutSize: " + dim.width);

        int buttonsW = SwingUtils.descendants(this)
            .filter(AbstractButton.class::isInstance)
            .mapToInt(c -> c.getPreferredSize().width)
            .sum();
        System.out.println("Total width of all buttons: " + buttonsW);
      }
    });
    f.setSize(200, 100);
    f.setVisible(true);
    f.setLocation(5 + 40 * i, 5 + 50 * i);
    return f;
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(MainPanel::createAndShowGui);
  }

  private static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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

final class SwingUtils {
  private SwingUtils() {
    /* Singleton */
  }

  public static Stream<Component> descendants(Container parent) {
    return Stream.of(parent.getComponents())
        .filter(Container.class::isInstance).map(Container.class::cast)
        .flatMap(c -> Stream.concat(Stream.of(c), descendants(c)));
  }
}
