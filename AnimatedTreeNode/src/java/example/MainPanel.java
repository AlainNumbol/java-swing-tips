// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.util.Objects;
import java.util.Optional;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;


public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());
    ImageIcon icon = new ImageIcon(getClass().getResource("restore_to_background_color.gif"));

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
    DefaultMutableTreeNode s0 = new DefaultMutableTreeNode(new NodeObject("default", icon));
    DefaultMutableTreeNode s1 = new DefaultMutableTreeNode(new NodeObject("setImageObserver", icon));
    root.add(s0);
    root.add(s1);
    JTree tree = new JTree(new DefaultTreeModel(root)) {
      @Override public void updateUI() {
        setCellRenderer(null);
        super.updateUI();
        TreeCellRenderer r = getCellRenderer();
        setCellRenderer((tree, value, selected, expanded, leaf, row, hasFocus) -> {
          JLabel l = (JLabel) r.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
          Object v = Optional.ofNullable(value)
              .filter(DefaultMutableTreeNode.class::isInstance).map(DefaultMutableTreeNode.class::cast)
              .map(DefaultMutableTreeNode::getUserObject).orElse(null);
          if (v instanceof NodeObject) {
            NodeObject uo = (NodeObject) v;
            l.setText(Objects.toString(uo.title, ""));
            l.setIcon(uo.icon);
          } else {
            l.setText(Objects.toString(value, ""));
            l.setIcon(null);
          }
          return l;
        });
      }
    };
    TreePath path = new TreePath(s1.getPath());
    // Wastefulness: icon.setImageObserver((ImageObserver) tree);
    icon.setImageObserver((img, infoflags, x, y, w, h) -> {
      if (!tree.isShowing()) {
        return false;
      }
      Rectangle cellRect = tree.getPathBounds(path);
      if ((infoflags & (FRAMEBITS | ALLBITS)) != 0 && Objects.nonNull(cellRect)) {
        tree.repaint(cellRect);
      }
      return (infoflags & (ALLBITS | ABORT)) == 0;
    });
    tree.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(new JScrollPane(tree));
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

class NodeObject {
  public final Icon icon;
  public final String title;

  // protected NodeObject(String title) {
  //   this(title, null);
  // }

  protected NodeObject(String title, Icon icon) {
    this.title = title;
    this.icon = icon;
  }
}
