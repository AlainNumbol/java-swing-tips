// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private final JLabel label = new JLabel();
  private final URL u1 = getClass().getResource("i03-04.gif");
  private final URL u2 = getClass().getResource("i03-10.gif");
  private final ImageIcon i1 = new ImageIcon(u1);
  private final ImageIcon i2 = new ImageIcon(u2);
  private File file;

  private MainPanel() {
    super(new BorderLayout());
    label.setVerticalTextPosition(SwingConstants.BOTTOM);
    label.setVerticalAlignment(SwingConstants.CENTER);
    label.setHorizontalTextPosition(SwingConstants.CENTER);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setBorder(BorderFactory.createTitledBorder("Drag Source JLabel"));
    clearFile();

    // // JDK 1.5.0
    // DragSource.getDefaultDragSource()
    //     .createDefaultDragGestureRecognizer(label, DnDConstants.ACTION_MOVE, new DragGestureListener() {
    //   @Override public void dragGestureRecognized(DragGestureEvent dge) {
    //     File tmpFile = getFile();
    //     if (Objects.isNull(tmpFile)) {
    //       return;
    //     }
    //     DragSourceAdapter dsa = new DragSourceAdapter() {
    //       @Override public void dragDropEnd(DragSourceDropEvent dsde) {
    //         if (dsde.getDropSuccess()) {
    //           clearFile();
    //         }
    //       }
    //     };
    //     dge.startDrag(DragSource.DefaultMoveDrop, new TempFileTransferable(tmpFile), dsa);
    //   }
    // });

    label.setTransferHandler(new TransferHandler() {
      @Override public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
      }

      @Override protected Transferable createTransferable(JComponent c) {
        File tmpFile = getFile();
        if (Objects.nonNull(tmpFile)) {
          return new TempFileTransferable(tmpFile);
        } else {
          return null;
        }
      }

      @Override protected void exportDone(JComponent c, Transferable data, int action) {
        cleanup(c, action == TransferHandler.MOVE);
      }

      private void cleanup(JComponent c, boolean isMoved) {
        if (isMoved) {
          clearFile();
          c.repaint();
        }
      }
    });
    label.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        System.out.println(e);
        JComponent c = (JComponent) e.getComponent();
        c.getTransferHandler().exportAsDrag(c, e, TransferHandler.COPY);
      }
    });

    JButton button = new JButton("Create Temp File");
    button.addActionListener(e -> {
      File outfile;
      try {
        outfile = File.createTempFile("test", ".tmp");
        outfile.deleteOnExit();
      } catch (IOException ex) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(getRootPane(), "Could not create file.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
      setFile(outfile);
    });

    JButton clearButton = new JButton("Clear");
    clearButton.addActionListener(e -> {
      clearFile();
      repaint();
    });

    Box box = Box.createHorizontalBox();
    box.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    box.add(Box.createHorizontalGlue());
    box.add(button);
    box.add(Box.createHorizontalStrut(2));
    box.add(clearButton);
    add(label);
    add(box, BorderLayout.SOUTH);
    setPreferredSize(new Dimension(320, 240));
  }

  protected File getFile() {
    return file;
  }

  protected void setFile(File file) {
    this.file = file;
    label.setIcon(i2);
    label.setText("tmpFile#exists(): true(draggable)");
  }

  protected void clearFile() {
    file = null;
    label.setIcon(i1);
    label.setText("tmpFile#exists(): false");
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

class TempFileTransferable implements Transferable {
  private final File file;

  protected TempFileTransferable(File file) {
    this.file = file;
  }

  @Override public Object getTransferData(DataFlavor flavor) {
    return Collections.singletonList(file);
  }

  @Override public DataFlavor[] getTransferDataFlavors() {
    return new DataFlavor[] {DataFlavor.javaFileListFlavor};
  }

  @Override public boolean isDataFlavorSupported(DataFlavor flavor) {
    return flavor.equals(DataFlavor.javaFileListFlavor);
  }
}
