/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.FlowLayout;
import java.awt.GridLayout;  
import java.awt.Toolkit;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;
import java.awt.Frame;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.Socket;  
import java.util.HashMap;  
import java.util.Map;  
import java.util.StringTokenizer;  
import java.util.ArrayList; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.net.ServerSocket;
  
import javax.swing.DefaultListModel;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JList;  
import javax.swing.JOptionPane;  
import javax.swing.JPanel;  
import javax.swing.JScrollPane;  
import javax.swing.JSplitPane;  
import javax.swing.JTextArea;  
import javax.swing.JTextField;  
import javax.swing.border.TitledBorder;  
  
public class Client{  

    private Client client;
    // public for chatromm.
    public JFrame frame;  
    private JList userList;
    private JList roomList;
    private JTextArea textArea;  
    private JTextField textField;  
    private JTextField txt_port;  
    private JTextField txt_hostIp;  
    private JTextField txt_name;  
    private JButton btn_start;  
    private JButton btn_stop;  
    private JButton btn_send;  
    private JButton btn_room;
    private JPanel westPanel;
    private JPanel northPanel;  
    private JPanel southPanel;  
    private JScrollPane rightScroll;
    private JScrollPane leftscroll;
    private JScrollPane leftScroll;  
    private JSplitPane centerSplit;  
  
    private DefaultListModel listModel;
    private DefaultListModel listmodel;
    private boolean isConnected = false;  
  
    private Socket socket;  
    private PrintWriter writer;  
    private BufferedReader reader;  
    private MessageThread messageThread;// 负责接收消息的线程  
    //public for chatroom
    public ArrayList<String> onLineUsers = new ArrayList<String>();// 所有在线用户
    public ArrayList<String> chatRooms = new ArrayList<String>();
    public ArrayList<ChatRoom> objChatRooms = new ArrayList<ChatRoom>();
    private ServerSocket serSock;
    private VoiceThread voicethread;
  
    // 主方法,程序入口  
    public static void main(String[] args) {  
        new Client();
    }  
  
    // 执行发送  
    public void send() {  
        if (!isConnected) {  
            JOptionPane.showMessageDialog(null, "還未連線，不能發送訊息！", "Error",  
                    JOptionPane.ERROR_MESSAGE);  
            return;  
        }  
        String message = textField.getText().trim();  
        if (message == null || message.equals("")) {  
            JOptionPane.showMessageDialog(null, "禁止發廢文！", "Error",  
                    JOptionPane.ERROR_MESSAGE);  
            return;  
        }  
        sendMessage(frame.getTitle() + "@" + "ALL" + "@" + message);  
        textField.setText(null);  
    }
    
    public String getName() {
        String name = txt_name.getText().trim();
        return name;
    }
    
    public void sendFile(String src, String dest) {
        sendMessage( "FILE@"+src+"@"+dest );
        Thread fsthd = new Thread( new FileSend() );
        fsthd.start();
    }
  
    // 构造方法  
    public Client() {
        client = this;
        textArea = new JTextArea();  
        textArea.setEditable(false);  
        textArea.setForeground(Color.blue);  
        textField = new JTextField();  
        txt_port = new JTextField("5566");  
        txt_hostIp = new JTextField("127.0.0.1");  
        txt_name = new JTextField("xiaoqiang");
        btn_start = new JButton("連線");  
        btn_stop = new JButton("斷開"); 
        btn_stop.setEnabled(false);
        btn_send = new JButton("send");  
        btn_room = new JButton("開房間");
        listModel = new DefaultListModel();  
        listmodel = new DefaultListModel();
        userList = new JList(listModel);
        roomList = new JList(listmodel);
  
        northPanel = new JPanel();  
        northPanel.setLayout(new GridLayout(1, 10));  
        northPanel.add(new JLabel("                       port"));  
        northPanel.add(txt_port);  
        northPanel.add(new JLabel("              server IP"));  
        northPanel.add(txt_hostIp);  
        northPanel.add(new JLabel("                      暱稱"));  
        northPanel.add(txt_name);  
        northPanel.add(btn_start);  
        northPanel.add(btn_stop);  
        northPanel.setBorder(new TitledBorder("配置設定"));  
  
        rightScroll = new JScrollPane(textArea);  
        rightScroll.setBorder(new TitledBorder("訊息區"));
        westPanel = new JPanel(new BorderLayout());
        leftscroll = new JScrollPane(roomList);
        leftscroll.setBorder(new TitledBorder("房間列表"));
        leftScroll = new JScrollPane(userList);  
        leftScroll.setBorder(new TitledBorder("在線用戶"));
        westPanel.add(leftscroll, "North");
        westPanel.add(leftScroll, "Center");
        southPanel = new JPanel(new BorderLayout());   
        southPanel.add(btn_room, "West");
        southPanel.add(btn_send, "East");
        southPanel.add(textField, "Center"); 
        southPanel.setBorder(new TitledBorder("對話框"));  
  
        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westPanel,  
                rightScroll);  
        centerSplit.setDividerLocation(150);  
  
        frame = new JFrame("Client");  
        // 更改JFrame的图标：  
        //frame.setIconImage(Toolkit.getDefaultToolkit().createImage(Client.class.getResource("qq.png")));  
        frame.setLayout(new BorderLayout());  
        frame.add(northPanel, "North");  
        frame.add(centerSplit, "Center");  
        frame.add(southPanel, "South");  
        frame.setSize(800, 600);  
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;  
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;  
        frame.setLocation((screen_width - frame.getWidth()) / 2,  
                (screen_height - frame.getHeight()) / 2);  
        frame.setVisible(true);
        
        try
        {
            serSock = new ServerSocket(6000);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        voicethread = new VoiceThread(serSock);
        voicethread.start();
  
        // 写消息的文本框中按回车键时事件  
        textField.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent arg0) {  
                send();  
            }  
        });  
  
        // 单击发送按钮时事件  
        btn_send.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                send();  
            }  
        });  
  
        // 单击连接按钮时事件  
        btn_start.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                int port;  
                if (isConnected) {
                    JOptionPane.showMessageDialog(null, "已連線，請不要再連一次!",  
                            "Error", JOptionPane.ERROR_MESSAGE);  
                    return;  
                }  
                try {  
                    try {  
                        port = Integer.parseInt(txt_port.getText());  
                    } catch (NumberFormatException e2) {  
                        throw new Exception("port請輸入正整數!");  
                    }  
                   if (port <= 0) {  
                        throw new Exception("port請輸入正整數！");  
                    }  
                    String hostIp = txt_hostIp.getText().trim();  
                    String name = txt_name.getText().trim();  
                    if (name.equals("") || hostIp.equals("")) {  
                        throw new Exception("有欄位未填!");  
                    }  
                    boolean flag = connectServer(port, hostIp, name);  

                    if (flag == false) {  
                        throw new Exception("連線失敗QQ");  
                    }  
                    else{
                        frame.setTitle(name);  
                        btn_start.setEnabled(false);  
                        txt_name.setEnabled(false); 
                        txt_port.setEnabled(false);
                        txt_hostIp.setEnabled(false);
                        btn_stop.setEnabled(true);
                   }
                } catch (Exception exc) {  
                    JOptionPane.showMessageDialog(null, exc.getMessage(),  
                            "Error", JOptionPane.ERROR_MESSAGE);  
                }  
            }  
        });  
  
        // 单击断开按钮时事件  
        btn_stop.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                if (!isConnected) {  
                    JOptionPane.showMessageDialog(null, "已斷開魂結，勿重複斷開!",  
                            "Error", JOptionPane.ERROR_MESSAGE);  
                    return;  
                }  
                try {  
                    boolean flag = closeConnection();// 断开连接  
                    if (flag == false) {  
                        throw new Exception("無法斷開魂結！");  
                    }  
                    else {
                        JOptionPane.showMessageDialog(null, "你下線囉!" , "通知" , JOptionPane.PLAIN_MESSAGE); 
                        btn_start.setEnabled(true);  
                        txt_name.setEnabled(true); 
                        txt_port.setEnabled(true);
                        txt_hostIp.setEnabled(true);
                        btn_stop.setEnabled(false);  
                    }
                } catch (Exception exc) {  
                    JOptionPane.showMessageDialog(frame, exc.getMessage(),  
                            "Error", JOptionPane.ERROR_MESSAGE);  
                }  
            }  
        });  
        btn_room.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isConnected) {
                    JOptionPane.showMessageDialog(null, "尚未連線!",  
                            "Error", JOptionPane.ERROR_MESSAGE);  
                }
                else {
                    int size = chatRooms.size();
                    chatRoomQuestion question = new chatRoomQuestion(frame, true, client);
                    if ( size != chatRooms.size() )
                    {
                        ChatRoom temp = new ChatRoom(chatRooms.get(chatRooms.size() - 1), client);
                        objChatRooms.add(temp);
                        sendMessage("ADDROOM@" + chatRooms.get(chatRooms.size() - 1) + "@" + frame.getTitle());
                    }
                }
            }
        });
  
        // 关闭窗口时事件  
        frame.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                if (isConnected) {  
                    closeConnection();// 关闭连接  
                }
                voicethread.stop();
                try
                {
                    serSock.close();
                }
                catch (IOException a)
                {
                    a.printStackTrace();
                }
                
                System.exit(0);// 退出程序  
            }  
        });  
        
        //雙擊房間名稱會加入房間
        roomList.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) 
            {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) 
                {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) 
                    {
                        Object o = theList.getModel().getElementAt(index);
                        boolean jump = true;
                        for ( int i = objChatRooms.size() - 1; i >= 0; i-- )
                        {
                            if (objChatRooms.get(i).returnRoomId().equals(o.toString()))
                            {
                                jump = false;
                                objChatRooms.get(i).setVisible(true);
                                objChatRooms.get(i).setState(JFrame.NORMAL);
                            }
                        }
                        if (jump)
                        {
                            chatRooms.add(o.toString());
                            ChatRoom temp = new ChatRoom(o.toString(), client);
                            objChatRooms.add(temp);
                            sendMessage("ADDINROOM@" + o.toString() + "@" + frame.getTitle());
                        }
                    }
                }
            }
        });
        
        //雙擊使用者會開私房
        userList.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) 
            {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) 
                {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) 
                    {
                        Object o = theList.getModel().getElementAt(index);
                        String st = o + "和" + frame.getTitle() + "的小天地";
                        boolean jump = true;
                        for ( int i = objChatRooms.size() - 1; i >= 0; i-- )
                        {
                            if (objChatRooms.get(i).returnRoomId().equals(st))
                            {
                                jump = false;
                                objChatRooms.get(i).setVisible(true);
                                objChatRooms.get(i).setState(JFrame.NORMAL);
                            }
                        }
                        if (jump)
                        {
                            chatRooms.add(st);
                            ChatRoom temp = new ChatRoom(st, client);
                            objChatRooms.add(temp);
                            sendMessage("ADDROOMPRIVATE@" + st + "@" + frame.getTitle());
                            sendMessage("INVITE@" + o + "@" + st + "@" + "1" + "@" + frame.getTitle());
                        }
                    }
                }
            }
        });
    }  
  
    /**  
     * 连接服务器  
     *   
     * @param port  
     * @param hostIp  
     * @param name  
     */  
    public boolean connectServer(int port, String hostIp, String name) {
        // 连接服务器  
        try {  
            socket = new Socket(hostIp, port);// 根据端口号和服务器ip建立连接  
            writer = new PrintWriter(socket.getOutputStream());  
            reader = new BufferedReader(new InputStreamReader(socket  
                    .getInputStream()));  
            // 发送客户端用户基本信息(用户名和ip地址)  
            sendMessage(name + "@" + socket.getLocalAddress().toString());
            // 开启接收消息的线程  
            messageThread = new MessageThread(reader, textArea);  
            isConnected = true;// 已经连接上了  
            messageThread.start();  
            
            return true;  
        } catch (Exception e) {  
            textArea.append("port：" + port + "    IP：" + hostIp  
                    + "   的伺服器連線失敗QQ" + "\r\n");  
            isConnected = false;// 未连接上  
            return false;  
        }  
    }  
    
    public String getIP() {
        return socket.getLocalAddress().toString();
    }
  
    /**  
     * 发送消息  
     *   
     * @param message  
     */  
    public void sendMessage(String message) {  
        writer.println(message);  
        writer.flush();  
    }  
  
    /**  
     * 客户端主动关闭连接  
     */  
    @SuppressWarnings("deprecation")  
    public synchronized boolean closeConnection() {  
        try {  
            listModel.removeAllElements();
            listmodel.removeAllElements();
            onLineUsers.clear();
            chatRooms.clear();
            objChatRooms.clear();
            frame.setTitle("Client");
            sendMessage("CLOSE");// 发送断开连接命令给服务器 
            messageThread.stop();// 停止接受消息线程  
            // 释放资源  
            if (reader != null) {  
                reader.close();  
            }  
            if (writer != null) {  
                writer.close();  
            }  
            if (socket != null) {  
                socket.close();  
            }  
            isConnected = false;  
            return true;  
        } catch (IOException e1) {  
            e1.printStackTrace();  
            isConnected = true;  
            return false;  
        }  
    }  
  
    // 不断接收消息的线程 
    class VoiceThread extends Thread
    {
        private ServerSocket serversocket;
        
        public VoiceThread( ServerSocket serversocket )
        {
            this.serversocket = serversocket;
        }
        
        public void run()
        {
            try
            {
                Socket cli=serversocket.accept();
                Playback player=new Playback(cli);
                player.start();
            }
            catch( IOException e )
            {
                e.printStackTrace();
            }
        }
    }
    
    class MessageThread extends Thread {
        private BufferedReader reader;  
        private JTextArea textArea;  
  
        // 接收消息线程的构造方法  
        public MessageThread(BufferedReader reader, JTextArea textArea) {
            this.reader = reader;  
            this.textArea = textArea;  
        }  
  
        // 被动的关闭连接  
        public synchronized void closeCon() throws Exception {
            // 清空用户列表  
            sleep(3000);
            listModel.removeAllElements();
            listmodel.removeAllElements();
            onLineUsers.clear();
            chatRooms.clear(); 
            objChatRooms.clear();
            btn_start.setEnabled(true);  
            txt_name.setEnabled(true); 
            txt_port.setEnabled(true);
            txt_hostIp.setEnabled(true);
            btn_stop.setEnabled(false);
            frame.setTitle("Client");
            // 被动的关闭连接释放资源  
            if (reader != null) {  
                reader.close();  
            }  
            if (writer != null) {  
                writer.close();  
            }  
            if (socket != null) {  
                socket.close();  
            }  
            isConnected = false;// 修改状态为断开  
        }  
  
        public void run() 
        {
            String message = "";  
            while (true) {  
                try {  
                    message = reader.readLine();  
                    StringTokenizer stringTokenizer = new StringTokenizer(  
                            message, "/@");  
                    String command = stringTokenizer.nextToken();// 命令  
                    if (command.equals("CLOSE"))// 服务器已关闭命令  
                    {  
                        textArea.append("伺服器維修關閉!\r\n");  
                        closeCon();// 被动的关闭连接   
                        return;// 结束线程  
                    } 
                    else if (command.equals("ADD")) 
                    {   // 有用户上线更新在线列表  
                        String username = "";  
                        String userIp = "";  
                        if ((username = stringTokenizer.nextToken()) != null  
                                && (userIp = stringTokenizer.nextToken()) != null) {   
                            onLineUsers.add(username);  
                            listModel.addElement(username);
                            for ( int i = objChatRooms.size() - 1; i >= 0; i--)
                            {
                                objChatRooms.get(i).addComboBox(username);
                            }
                        }  
                    } 
                    else if (command.equals("DELETE"))
                    {   // 有用户下线更新在线列表  
                        String username = stringTokenizer.nextToken();   
                        onLineUsers.remove(username);  
                        listModel.removeElement(username);  
                        for (int i = objChatRooms.size() - 1; i >= 0; i--)
                        {
                            objChatRooms.get(i).deleteComboBox(username);
                            objChatRooms.get(i).deleteList(username);
                            /*
                            if (objChatRooms.get(i).usernames.size() == 0)
                            {
                                listmodel.removeElement(objChatRooms.get(i).returnRoomId());
                                objChatRooms.remove(i);
                            }
                            */
                        }
                    } 
                    else if (command.equals("USERLIST"))
                    {   // 加载在线用户列表  
                        int size = Integer  
                                .parseInt(stringTokenizer.nextToken());  
                        String username = null;  
                        String userIp = null;  
                        for (int i = 0; i < size; i++) {  
                            username = stringTokenizer.nextToken();  
                            userIp = stringTokenizer.nextToken();    
                            onLineUsers.add(username);  
                            listModel.addElement(username);  
                        }  
                    } 
                    else if (command.equals("MAX")) 
                    {   // 人数已达上限  
                        textArea.append(stringTokenizer.nextToken()  
                                + stringTokenizer.nextToken() + "\r\n");  
                        closeCon();// 被动的关闭连接  
                        JOptionPane.showMessageDialog(null, "上線人數過多，請稍後再嘗試！", "Error",  
                                JOptionPane.ERROR_MESSAGE);  
                        return;// 结束线程  
                    } 
                    else if (command.equals("USED")) 
                    {   //使用者名稱重複
                        textArea.append(stringTokenizer.nextToken()  
                                + stringTokenizer.nextToken() + "\r\n");  
                        closeCon();// 被动的关闭连接  
                        JOptionPane.showMessageDialog(null, "暱稱已有人使用，請換一個！", "Error",  
                                JOptionPane.ERROR_MESSAGE);  
                        return;// 结束线程                          
                    }  
                    else if (command.equals("ADDROOM")) 
                    {   //開房間
                        String roomId = stringTokenizer.nextToken();  
                        chatRooms.add(roomId);  
                        listmodel.addElement(roomId); 
                    }
                    else if (command.equals("ROOMLIST"))
                    {   //更新房間列表                        
                        int size = Integer  
                        .parseInt(stringTokenizer.nextToken());  
                        String roomId = null;    
                        for (int i = 0; i < size; i++) {  
                            roomId = stringTokenizer.nextToken();    
                            chatRooms.add(roomId);  
                            listmodel.addElement(roomId); 

                        }
                    }
                    else if (command.equals("INVITE"))
                    {   //邀請別人
                        String username = stringTokenizer.nextToken();
                        String roomId = stringTokenizer.nextToken();
                        boolean jump = true;
                        for ( int i = objChatRooms.size() - 1; i >= 0; i--)
                        {
                            if (objChatRooms.get(i).returnRoomId().equals(roomId))
                            {
                                jump = false;
                                objChatRooms.get(i).addList(username);
                                
                            }
                        }
                        if (jump && frame.getTitle().equals(username))
                        {
                            ChatRoom temp = new ChatRoom(roomId, client);
                            objChatRooms.add(temp);
                            int size = Integer  
                                .parseInt(stringTokenizer.nextToken());
                            for (int i = 0; i < size; i++) 
                            {  
                                username = stringTokenizer.nextToken();  
                                temp.addList(username);
                            }
                        }
                    }
                    else if (command.equals("ADDINROOM"))
                    {   //主動加入房間
                        String roomId = stringTokenizer.nextToken();
                        int size = Integer  
                                .parseInt(stringTokenizer.nextToken());
                        for ( int i = objChatRooms.size() - 1; i >= 0; i-- )
                        {
                            if (objChatRooms.get(i).returnRoomId().equals(roomId))
                            {
                                for ( int j = 0; j < size; j++ )
                                {
                                    String username = stringTokenizer.nextToken();
                                    objChatRooms.get(i).addList(username);
                                }
                            }
                        }
                    }
                    else if (command.equals("ROOMINFORM"))
                    {
                        String roomId = stringTokenizer.nextToken();   
                        String name = stringTokenizer.nextToken();
                        for ( int i = objChatRooms.size() - 1; i >= 0; i-- )
                        {
                            if (objChatRooms.get(i).returnRoomId().equals(roomId) && !name.equals(frame.getTitle()))
                            {
                                objChatRooms.get(i).addList(name);
                                objChatRooms.get(i).setVisible(true);
                                objChatRooms.get(i).setState(JFrame.NORMAL);
                            }
                        }
                    }
                    else if (command.equals("ROOMCHAT"))
                    {
                        String roomId = stringTokenizer.nextToken();
                        String text = stringTokenizer.nextToken();
                        String speaker = stringTokenizer.nextToken();
                        for ( int i = objChatRooms.size() - 1; i >= 0; i-- )
                        {
                            if (objChatRooms.get(i).returnRoomId().equals(roomId))
                            {
                                String temp = speaker + "說:" + text;
                                objChatRooms.get(i).addText(temp);
                            }
                        }
                    }
                    else if (command.equals("DING"))
                    {
                        String roomId = stringTokenizer.nextToken();
                        String text = stringTokenizer.nextToken();
                        String speaker = stringTokenizer.nextToken();
                        for ( int i = objChatRooms.size() - 1; i >= 0; i-- )
                        {
                            if (objChatRooms.get(i).returnRoomId().equals(roomId))
                            {
                                objChatRooms.get(i).ding();
                            }
                        }
                    }                    
                    else if ( command.equals("FILE") )
                    {
                        String addr = stringTokenizer.nextToken();
                        String name = stringTokenizer.nextToken();
                        
                        Thread recvThd = new Thread( new FileRecv( addr, name ) );
                        recvThd.start();
                    }
                    else if ( command.equals("VOICE") )
                    {
                        String me = stringTokenizer.nextToken();
                        String dest = stringTokenizer.nextToken();
                        sendMessage("VOICEIP@" + client.getIP() + "@" + dest );
                        
                        String IP = stringTokenizer.nextToken();
                        try   
                        {   
                            Socket cli=new Socket(IP,6000);   
                            Capture cap=new Capture(cli);   
                            cap.start();
                        }   
                        catch(Exception e)   
                        {
                            e.printStackTrace();
                        }
                    }
                    else if ( command.equals("VOICEIP") )
                    {         
                        String IP = stringTokenizer.nextToken();
                        try   
                        {   
                            Socket cli=new Socket(IP,6000);   
                            Capture cap=new Capture(cli);   
                            cap.start();   
                        }   
                        catch(Exception e)   
                        {
                            e.printStackTrace();
                        }
                        
                    }
                    else 
                    {   // 普通消息  
                        textArea.append(message + "\r\n");  
                    }  
                }
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            }
        }
    }  
}  
