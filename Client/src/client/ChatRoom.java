/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.WindowConstants;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

/**
 *
 * @author nmlab212
 */
public class ChatRoom extends javax.swing.JFrame {
    private ChatRoom chatRoom = this;
    private String roomId;
    //need to pass client in.
    private Client client;
    private Vector <Integer> pos = new Vector <Integer>();
    private Vector <String> pic = new Vector <String>();
    public ArrayList<String> usernames = new ArrayList<String>();
    private DefaultListModel model = new DefaultListModel();

    /**
     * Creates new form ChatRoom
     */
    public ChatRoom(String text, Client Client) {
        initComponents();
        this.roomId = text;
        this.setTitle(text);
        this.client = Client;
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;  
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;  
        this.setLocation((screen_width - this.getWidth()) / 2,  
                (screen_height - this.getHeight()) / 2);
        myList.setModel(model);
        usernames.add(client.frame.getTitle().toString());
        model.addElement(client.frame.getTitle().toString() + "                                       ");
        for ( int i = client.onLineUsers.size() - 1; i >= 0; i-- )
        {
            invite.addItem(client.onLineUsers.get(i));
        }
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        myList = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        invite = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        myList.setBorder(javax.swing.BorderFactory.createTitledBorder("使用者列表"));
        jScrollPane4.setViewportView(myList);

        jScrollPane3.setViewportView(jScrollPane4);

        jTextPane2.setEditable(false);
        jTextPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("訊息欄"));
        jScrollPane5.setViewportView(jTextPane2);

        jButton1.setText("send");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/1.jpg"))); // NOI18N
        jLabel1.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/2.jpg"))); // NOI18N
        jLabel3.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel3.setOpaque(true);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setToolTipText("");
        jLabel4.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel4.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setToolTipText("");
        jLabel6.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel6.setOpaque(true);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setToolTipText("");
        jLabel7.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel7.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setToolTipText("");
        jLabel8.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel8.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel9.setOpaque(true);

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel10.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel11.setOpaque(true);

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel12.setOpaque(true);

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel13.setOpaque(true);

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel14.setOpaque(true);

        invite.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "invite" }));
        invite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviteActionPerformed(evt);
            }
        });

        jTextPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("發送訊息"));
        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextPane1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTextPane1);

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setToolTipText("");
        jLabel16.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel16.setOpaque(true);

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/3.jpg"))); // NOI18N
        jLabel15.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel15.setOpaque(true);
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(invite, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(invite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.setDefaultCloseOperation(this.HIDE_ON_CLOSE);
    }//GEN-LAST:event_formWindowClosing

    private void inviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviteActionPerformed
        // TODO add your handling code here:
        JComboBox cb = (JComboBox) evt.getSource();
        String name = (String)cb.getSelectedItem();
        if ( name.equals("invite") )
        {
            //TODO:need to fix
            /*
            for ( int i = client.onLineUsers.size() - 1; i >=0; i-- )
            {           
                boolean jump = true;
                for ( int j = usernames.size() - 1; j >= 0; j-- )
                {
                    if (client.onLineUsers.get(i).equals(usernames.get(j)))
                    {
                        jump = false;
                    }
                }
                if (jump)
                {
                    sendComboBox(client.onLineUsers.get(i));
                }
            }
            for ( int i = client.onLineUsers.size() - 1; i >=0; i-- )
            {
                boolean jump = true;
                for ( int j = usernames.size() - 1; j >= 0; j-- )
                {
                    if (client.onLineUsers.get(i).equals(usernames.get(j)))
                    {
                        jump = false;
                    }
                }
                if (jump)
                {
                    usernames.add(client.onLineUsers.get(i));
                    model.addElement(client.onLineUsers.get(i));
                }
            }
            */
        }
        else
        {
            boolean jump = true;
            for ( int i = usernames.size() - 1; i >= 0; i-- )
            {
                if (usernames.get(i).equals(name))
                {
                    jump = false;
                }
            }
            if (jump)
            {
                sendComboBox(name);
                usernames.add(name);
                model.addElement(name);
            }
        }
    }//GEN-LAST:event_inviteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        client.sendMessage("ROOMCHAT@" + roomId + "@" + transfer(jTextPane1.getText()) + "@" + client.frame.getTitle());
        pos.removeAllElements();
        pic.removeAllElements();
        jTextPane1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            client.sendMessage("ROOMCHAT@" + roomId + "@" + transfer(jTextPane1.getText()) + "@" + client.frame.getTitle());
            pos.removeAllElements();
            pic.removeAllElements();
            jTextPane1.setText("");
            evt.consume();
        }
    }//GEN-LAST:event_jTextPane1KeyPressed

    private String transfer(String s) {
        String ts="";
        char[] chr = s.toCharArray();
        if(pos.isEmpty()) {
            return s;
        }
        else {
            for(int c=0; c<chr.length; c++) {    
                if(pos.indexOf(c) == -1)
                    ts += chr[c];
                else
                    ts += pic.elementAt(pos.indexOf(c));
            }  
            return ts;
        }          
    }
    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        jTextPane1.insertIcon(new ImageIcon(getClass().getResource("/picture/1.jpg")));       
        pos.add(jTextPane1.getText().toString().length()-1);
        pic.add("{");
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        jTextPane1.insertIcon(new ImageIcon(getClass().getResource("/picture/2.jpg")));       
        pos.add(jTextPane1.getText().toString().length()-1);
        pic.add("}");
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        jTextPane1.insertIcon(new ImageIcon(getClass().getResource("/picture/3.jpg")));       
        pos.add(jTextPane1.getText().toString().length()-1);
        pic.add("|");
    }//GEN-LAST:event_jLabel15MouseClicked

    //send message when inviting
    private void sendComboBox (String name)
    {
        String temp = ("INVITE@" + name + "@" + roomId);
        temp += ( "@" + usernames.size() );
        for ( int j = usernames.size() - 1; j >= 0; j-- )
        {
            temp += ( "@" + usernames.get(j) );
        }
        client.sendMessage(temp);
    }
    
    public void deleteComboBox(String name)
    {
        invite.removeItem(name);
    }
    
    //invite combobox function for client
    public void addComboBox (String name)
    {
        invite.addItem(name);
    }
    
    //userlist function for client
    public void addList (String name)
    {
        boolean jump = true;
        for ( int i = usernames.size() - 1; i >= 0; i--)
        {
            if (usernames.get(i).equals(name))
            {
                jump = false;
            }
        }
        if (jump)
        {
            usernames.add(name);
            model.addElement(name);
        }
    }
    
    public void deleteList (String name)
    {
        usernames.remove(name);
        model.removeElement(name);
    }
    
    //to inform client this roomId
    public String returnRoomId()
    {
        return roomId;
    }
    /**
     * @param args the command line arguments
     */

    public void addText(String text)
    {
        class temp implements Runnable
        {
            public temp (String text)
            {
                this.text = text;
            }
            private String text;
            
            @Override
            public void run()
            {
                try {
                    //String[] array = text.split("^_^");                   
                    /*for(int i = 0 ; i < array.length ; i ++) {
                         jTextPane2.getDocument().insertString(jTextPane2.getDocument().getLength(), array[i], null);
                         jTextPane2.insertIcon(new ImageIcon(getClass().getResource("/picture/wreck-it-ralph-vanellope.jpg"))); 
                    }*/
                    StringTokenizer st1= new StringTokenizer(text,"{",true);
                    while(st1.hasMoreTokens()){
                        String s1=st1.nextToken();
                        if(s1.equals("{")) {
                            jTextPane2.insertIcon(new ImageIcon(getClass().getResource("/picture/1.jpg")));  
                        }
                        else {
                            StringTokenizer st2= new StringTokenizer(s1,"}",true);
                            while(st2.hasMoreTokens()){
                                String s2=st2.nextToken();
                                if(s2.equals("}")) {
                                    jTextPane2.insertIcon(new ImageIcon(getClass().getResource("/picture/2.jpg")));  
                                }
                                else {
                                    StringTokenizer st3= new StringTokenizer(s2,"|",true);
                                    while(st3.hasMoreTokens()){
                                        String s3=st3.nextToken();
                                        if(s3.equals("|")) {
                                            jTextPane2.insertIcon(new ImageIcon(getClass().getResource("/picture/3.jpg")));  
                                        }
                                        else {
                                            jTextPane2.getDocument().insertString(jTextPane2.getDocument().getLength(),s3, null);                            
                                        }
                                    }                            
                                }
                            }                      
                        }
                    }
                    jTextPane2.getDocument().insertString(jTextPane2.getDocument().getLength(),"\r\n", null); 
                    chatRoom.setVisible(true);
                }
                catch (BadLocationException e) {
                }
            }
        }
        
        SwingUtilities.invokeLater(new temp(text));
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox invite;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JList myList;
    // End of variables declaration//GEN-END:variables
}
