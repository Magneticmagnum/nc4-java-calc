package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ExpDialog extends JDialog implements ActionListener {

   private JTextField expressionfield;

   public static void main(String[] args) {
      JFrame frame = new JFrame("Test window");
      ExpDialogButton dbutton = new ExpDialogButton();
      dbutton.addActionListener(dbutton);
      frame.add(dbutton);
      frame.setVisible(true);
      frame.setSize(100, 150);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public ExpDialog() {

      setSize(100, 150);
      expressionfield = new JTextField();
      Object[] array = { "Enter an expression.", "ex. f(x)=x^3+2", expressionfield };
      Object[] options = { "Okay", "Cancel" };
      JOptionPane optionspane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null,
            options, options[0]);
      setContentPane(optionspane);
      setVisible(true);
      pack();
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if (e.getActionCommand().equals("OK")) {
         System.out.println(expressionfield.getText());
      } else if (e.getActionCommand().equals("Cancel")) {

      }
   }

}

class ExpDialogButton extends JButton implements ActionListener {
   public ExpDialogButton() {
      super();
      setSize(25, 25);
      setText("Expression...");
      setActionCommand("Expression");
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if (e.getActionCommand().equals("Expression")) {
         ExpDialog d = new ExpDialog();
         d.setSize(300, 200);
         d.setVisible(true);
      }

   }
}