package example;
// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
// import javax.swing.event.*;

public final class MainPanel extends JPanel {
    private MainPanel() {
        super(new BorderLayout());

        JButton button = new JButton("showOptionDialog");
        button.addActionListener(e -> {
            JComponent c = (JComponent) e.getSource();
            String info = "<html>FORWARD_TRAVERSAL_KEYS : TAB, RIGHT, DOWN"
                        + "<br>BACKWARD_TRAVERSAL_KEYS: SHIFT+TAB, LEFT, UP</html>";
            int retValue = JOptionPane.showConfirmDialog(c.getRootPane(), info);
            if (retValue == JOptionPane.YES_OPTION) {
                System.out.println("YES_OPTION");
            } else if (retValue == JOptionPane.NO_OPTION) {
                System.out.println("NO_OPTION");
            } else if (retValue == JOptionPane.CANCEL_OPTION) {
                System.out.println("CANCEL_OPTION");
            }
        });
        EventQueue.invokeLater(() -> getRootPane().setDefaultButton(button));

        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(new JButton("111"));
        box.add(new JButton("222"));
        box.add(button);
        box.add(new JButton("333"));
        KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

        // Set<AWTKeyStroke> forwardKeys = new HashSet<>(frame.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        Set<AWTKeyStroke> forwardKeys = new HashSet<>(focusManager.getDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
        forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
        // frame.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
        focusManager.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

        // Set<AWTKeyStroke> backwardKeys = new HashSet<>(frame.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        Set<AWTKeyStroke> backwardKeys = new HashSet<>(focusManager.getDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        backwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
        backwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
        // frame.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);
        focusManager.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

        setFocusCycleRoot(true);
        setFocusTraversalPolicy(new LayoutFocusTraversalPolicy() {
            @Override protected boolean accept(Component c) {
                // return !Objects.equals(c, textarea) && super.accept(c);
                return !JTextArea.class.isInstance(c) && super.accept(c);
            }
            @Override public Component getDefaultComponent(Container container) {
                // return button;
                return getRootPane().getDefaultButton();
            }
        });

        add(box, BorderLayout.SOUTH);
        add(new JScrollPane(new JTextArea("FORWARD_TRAVERSAL_KEYS: TAB, RIGHT, DOWN\nBACKWARD_TRAVERSAL_KEYS: SHIFT+TAB, LEFT, UP")));
        setPreferredSize(new Dimension(320, 240));
    }

    // // %JAVA_HOME%/src_b23/javax/swing/JOptionPane.java
    // public static int showOptionDialog(
    //         Component parent, Object message, String title, int optionType, int messageType, Icon icon, Object[] options, Object initialValue) throws HeadlessException {
    //     JOptionPane pane = new JOptionPane(message, messageType, optionType, icon, options, initialValue);
    //     pane.setInitialValue(initialValue);
    //     pane.setComponentOrientation((parent == null ? JOptionPane.getRootFrame() : parent).getComponentOrientation());
    //
    //     // int style = JOptionPane.styleFromMessageType(messageType);
    //     // JDialog dialog = pane.createDialog(parent, title, style);
    //     JDialog dialog = pane.createDialog(parent, title);
    //
    //     Set<AWTKeyStroke> forwardKeys = new HashSet<>(dialog.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
    //     forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
    //     forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
    //     dialog.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
    //
    //     Set<AWTKeyStroke> backwardKeys = new HashSet<>(dialog.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
    //     backwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
    //     backwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
    //     dialog.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);
    //
    //     pane.selectInitialValue();
    //     // dialog.show();
    //     dialog.setVisible(true);
    //     dialog.dispose();
    //
    //     Object selectedValue = pane.getValue();
    //     if (selectedValue == null)
    //         return JOptionPane.CLOSED_OPTION;
    //     if (options == null) {
    //         if (selectedValue instanceof Integer)
    //           return ((Integer) selectedValue).intValue();
    //         return JOptionPane.CLOSED_OPTION;
    //     }
    //     for (int counter = 0, maxCounter = options.length;
    //         counter < maxCounter; counter++) {
    //         if (options[counter].equals(selectedValue))
    //           return counter;
    //     }
    //     return JOptionPane.CLOSED_OPTION;
    // }

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
