package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import math.graph.GraphDims;
import math.graph.GraphDimsSubscriber;


public class JGraphDims extends JPanel implements GraphDimsSubscriber {

   private static final long serialVersionUID = -1651134176661053483L;

   private GraphDims         dims;

   private JLabel            minXLabel;
   private DoubleTextField   minXField;

   private JLabel            maxXLabel;
   private DoubleTextField   maxXField;

   private JLabel            minYLabel;
   private DoubleTextField   minYField;

   private JLabel            maxYLabel;
   private DoubleTextField   maxYField;


   public JGraphDims(GraphDims dims) {
      this.dims = dims;
      dims.addSubscriber(this);

      minXLabel = new JLabel("Min X");
      minXField = new DoubleTextField(dims.getMinX(), 20);

      maxXLabel = new JLabel("Max X");
      maxXField = new DoubleTextField(dims.getMaxX(), 20);

      minYLabel = new JLabel("Min Y");
      minYField = new DoubleTextField(dims.getMinY(), 20);

      maxYLabel = new JLabel("Max Y");
      maxYField = new DoubleTextField(dims.getMaxY(), 20);

      buildLayout();
   }

   public JGraphDims() {
      this(new GraphDims(-10, 10, -10, 10));
   }

   private void buildLayout() {
      GroupLayout layout = new GroupLayout(this);

      layout.setAutoCreateGaps(true);
      layout.setAutoCreateContainerGaps(true);

      GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
      hGroup.addGroup(layout.createParallelGroup().addComponent(minXLabel).addComponent(maxXLabel).addComponent(
            minYLabel).addComponent(maxYLabel));
      hGroup.addGroup(layout.createParallelGroup().addComponent(minXField).addComponent(maxXField).addComponent(
            minYField).addComponent(maxYField));
      // hGroup.addGap(0, Short.MAX_VALUE, Short.MAX_VALUE);
      layout.setHorizontalGroup(hGroup);


      GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
      vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(minXLabel).addComponent(minXField));
      vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(maxXLabel).addComponent(maxXField));
      vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(minYLabel).addComponent(minYField));
      vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(maxYLabel).addComponent(maxYField));
      layout.setVerticalGroup(vGroup);

      this.setLayout(layout);
   }

   @Override
   public void updateGraphDims() {
      minXField.setValue(dims.getMinX());
      maxXField.setValue(dims.getMaxX());
      minYField.setValue(dims.getMinY());
      maxYField.setValue(dims.getMaxY());
   }

   private class DoubleTextField extends JTextField implements ActionListener, FocusListener {

      private static final long   serialVersionUID = 7092604735271985943L;
      private static final String badchars         = "`~!@#$%^&*()_+=\\|\"':;?/><, ";

      public DoubleTextField(Double d, int width) {
         super(String.valueOf(d), width);
         addActionListener(this);
         addFocusListener(this);
      }

      @Override
      public void processKeyEvent(KeyEvent ev) {
         char c = ev.getKeyChar();
         if ((Character.isLetter(c) && !ev.isAltDown()) || badchars.indexOf(c) > -1) {
            ev.consume();
         } else {
            super.processKeyEvent(ev);
         }
      }

      public Double getValue() {
         return Double.valueOf(super.getText());
      }

      public void setValue(Double d) {
         super.setText(String.valueOf(d));
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         dims.set(minXField.getValue(), maxXField.getValue(), minYField.getValue(), maxYField.getValue());
         selectAll();
      }

      @Override
      public void focusGained(FocusEvent e) {
         selectAll();
      }

      @Override
      public void focusLost(FocusEvent e) {
         dims.set(minXField.getValue(), maxXField.getValue(), minYField.getValue(), maxYField.getValue());
      }

   }

}
