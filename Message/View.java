package Message;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.io.File;

public class View {
    final JFrame frame = new JFrame();
    private final String workingDirectory = System.getProperty("user.dir") + "\\res\\";
    private final ImageIcon plusSign = new ImageIcon(workingDirectory + "plus_icon.png");
    private final ImageIcon minusSign = new ImageIcon(workingDirectory + "minus_icon.png");
    private final ImageIcon reloadSign = new ImageIcon(workingDirectory + "reload_icon.png");
    private final ImageIcon sendSign = new ImageIcon(workingDirectory + "arrow_icon.png");
    GridBagConstraints gbc = new GridBagConstraints();
    JComboBox<String> contactSelection = new JComboBox<>();
    JButton addContact = new JButton(plusSign);
    JButton deleteContact = new JButton(minusSign);
    JButton sendMessage = new JButton(sendSign);
    JButton reconnectToServer = new JButton(reloadSign);
    JTextArea messageDisplay = new JTextArea(19,58);
    JScrollPane messageDisplayScroll = new JScrollPane(messageDisplay);
    JTextField userInMessage = new JTextField(58);
    JLabel programName = new JLabel();
    JPanel panel = new JPanel();
    public View(){
        System.out.println(new File(workingDirectory + "plus_icon.png").exists());
        System.out.println(workingDirectory);
        System.out.println(workingDirectory);
        setUIManager("Nimbus");
        setColors();
        setDefaults();
        drawGUI();
    }

    private void setDefaults(){
        messageDisplay.setEditable(false);
        messageDisplay.setWrapStyleWord(true);
        messageDisplay.setLineWrap(true);
        messageDisplayScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        programName.setText("Message App");
        programName.setHorizontalAlignment(JLabel.CENTER);
    }

    private void setColors(){
        panel.setBackground(Color.LIGHT_GRAY);
    }

    private void drawGUI(){
        panel.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(programName, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(addContact, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(deleteContact, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        panel.add(contactSelection, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        panel.add(messageDisplayScroll, gbc);
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx= 2;
        gbc.gridy = 5;
        panel.add(userInMessage, gbc);
        gbc.gridwidth =1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(reconnectToServer, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(sendMessage, gbc);
        frame.add(panel);
        frame.setBounds(0,0, 975, 415);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void setUIManager(String lAndFName){//Sets look and feel from string
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (lAndFName.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    System.out.println("Using " + lAndFName);
                    return;
                }
            }
            System.out.println("The Look and Feel " + lAndFName + " could not be found. Using default");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
