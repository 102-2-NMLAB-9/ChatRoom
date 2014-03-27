/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.GridLayout;  
import java.awt.Toolkit;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.BindException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.ArrayList;  
import java.util.StringTokenizer;  
  
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
  
public class Server {  
  
    private JFrame frame;  
    private JTextArea contentArea;  
    private JTextField txt_message;  
    private JTextField txt_max;  
    private JTextField txt_port;
    private JButton btn_start;  
    private JButton btn_stop;  
    private JButton btn_send;  
    private JPanel northPanel;  
    private JPanel southPanel;
    private JPanel westPanel;
    private JScrollPane rightPanel;  
    private JScrollPane leftPanel;
    private JScrollPane leftpanel;
    private JSplitPane centerSplit;  
    private JList userList;
    private JList roomList;
    private DefaultListModel listModel;
    private DefaultListModel listmodel;
  
    private ServerSocket serverSocket;  
    private ServerThread serverThread;  
    private ArrayList<ClientThread> clients;
    private ArrayList<RoomList> RoomList; 
  
    private boolean isStart = false;  
  
    // 主方法,程序执行入口  
    public static void main(String[] args) {  
        new Server();  
    }  
  
    // 执行消息发送  
    public void send() {  
        if (!isStart) 
        {  
            JOptionPane.showMessageDialog(null, "司服器尚未啟動，請不要猴急！", "Error",  
                    JOptionPane.ERROR_MESSAGE);  
        }  
        else if (clients.size() == 0) 
        {  
            JOptionPane.showMessageDialog(null, "沒有用戶連線，只能和空氣說話！", "Error",  
                    JOptionPane.ERROR_MESSAGE);  
        }  
        else {
                String message = txt_message.getText().trim();  
                if (message == null || message.equals("")) 
                {  
                    JOptionPane.showMessageDialog(null, "請不要亂發廢文！", "Error",  
                        JOptionPane.ERROR_MESSAGE);   
                }  
                else
                {
                    sendServerMessage(txt_message.getText());// 群发服务器消息  
                    contentArea.append("伺服器公告：" + txt_message.getText() + "\n");  
                    txt_message.setText(null);  
                }
            }
    }  
  
    // 构造放法  
    public Server() {  
        frame = new JFrame("伺服器");  
        // 更改JFrame的图标：  
        //frame.setIconImage(Toolkit.getDefaultToolkit().createImage(Client.class.getResource("qq.png")));  
        //frame.setIconImage(Toolkit.getDefaultToolkit().createImage(Server.class.getResource("qq.png")));  
        contentArea = new JTextArea();  
        contentArea.setEditable(false);  
        contentArea.setForeground(Color.red);  
        txt_message = new JTextField();  
        txt_max = new JTextField("30");  
        txt_port = new JTextField("5566");  
        btn_start = new JButton("啟動");  
        btn_stop = new JButton("終止");  
        btn_send = new JButton("send");  
        btn_stop.setEnabled(false);  
        listModel = new DefaultListModel();  
        listmodel = new DefaultListModel();
        userList = new JList(listModel);
        roomList = new JList(listmodel);
  
        southPanel = new JPanel(new BorderLayout());  
        southPanel.setBorder(new TitledBorder("公告"));  
        southPanel.add(txt_message, "Center");  
        southPanel.add(btn_send, "East");
        westPanel = new JPanel(new BorderLayout());
        leftpanel = new JScrollPane(roomList);
        leftpanel.setBorder(new TitledBorder("房間列表"));
        leftPanel = new JScrollPane(userList);  
        leftPanel.setBorder(new TitledBorder("在線用戶"));  
        westPanel.add(leftpanel, "North");
        westPanel.add(leftPanel, "Center");
  
        rightPanel = new JScrollPane(contentArea);  
        rightPanel.setBorder(new TitledBorder("訊息區"));  
  
        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westPanel,  
                rightPanel);  
        centerSplit.setDividerLocation(150);  
        northPanel = new JPanel(new GridLayout(1, 6));  
        northPanel.add(new JLabel("                        人數上限"));  
        northPanel.add(txt_max);  
        northPanel.add(new JLabel("                                  port"));  
        northPanel.add(txt_port);  
        northPanel.add(btn_start);  
        northPanel.add(btn_stop);  
        northPanel.setBorder(new TitledBorder("配置設定"));  
  
        frame.setLayout(new BorderLayout());  
        frame.add(northPanel, "North");  
        frame.add(centerSplit, "Center");  
        frame.add(southPanel, "South");  
        frame.setSize(800, 600);  
        //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());//设置全屏  
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;  
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;  
        frame.setLocation((screen_width - frame.getWidth()) / 2,  (screen_height - frame.getHeight()) / 2);  
        frame.setVisible(true);  
  
        // 关闭窗口时事件  
        frame.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                if (isStart) {  
                    closeServer();// 关闭服务器  
                }  
                System.exit(0);// 退出程序  
            }  
        });  
  
        // 文本框按回车键时事件  
        txt_message.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                send();  
            }  
        });  
  
        // 单击发送按钮时事件  
        btn_send.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent arg0) {  
                send();  
            }  
        });  
  
        // 单击启动服务器按钮时事件  
        btn_start.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                if (isStart) {  
                    JOptionPane.showMessageDialog(frame, "伺服器早已啟動！",  
                            "Error", JOptionPane.ERROR_MESSAGE);  
                    return;  
                }  
                int max;  
                int port;  
                try {  
                    try {  
                        max = Integer.parseInt(txt_max.getText());  
                    } catch (Exception e1) {  
                        throw new Exception("人數上限請輸入正整數！");  
                    }  
                    if (max <= 0) {  
                        throw new Exception("人數上限請輸入正整數！");  
                    }  
                    try {  
                        port = Integer.parseInt(txt_port.getText());  
                    } catch (Exception e1) {  
                        throw new Exception("port請輸入正整數！");  
                    }  
                    if (port <= 0) {  
                        throw new Exception("port請輸入正整數！");  
                    }  
                    serverStart(max, port);  
                    contentArea.append("伺服器成功啟動!  人數上限:" + max + " ,port:" + port  
                            + "\r\n");  
                    JOptionPane.showMessageDialog(null, "伺服器成功啟動!", "恭喜",  
                    JOptionPane.PLAIN_MESSAGE);
                    btn_start.setEnabled(false);  
                    txt_max.setEnabled(false);  
                    txt_port.setEnabled(false);  
                    btn_stop.setEnabled(true);  
                } catch (Exception exc) {  
                    JOptionPane.showMessageDialog(null, exc.getMessage(),  
                            "Error", JOptionPane.ERROR_MESSAGE);  
                }  
            }  
        });  
  
        // 单击停止服务器按钮时事件  
        btn_stop.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                if (!isStart) {  
                    JOptionPane.showMessageDialog(null, "伺服器尚未啟動！", "Error",  
                            JOptionPane.ERROR_MESSAGE);  
                    return;  
                }  
                try {  
                    closeServer();  
                    btn_start.setEnabled(true);  
                    txt_max.setEnabled(true);  
                    txt_port.setEnabled(true);  
                    btn_stop.setEnabled(false);  
                    contentArea.append("伺服器關閉!\r\n");  
                    JOptionPane.showMessageDialog(null, "伺服器關閉!", "通知",  
                    JOptionPane.PLAIN_MESSAGE);
                } catch (Exception exc) {  
                    JOptionPane.showMessageDialog(null, "無法終止伺服器！", "Error",  
                            JOptionPane.ERROR_MESSAGE);  
                }  
            }  
        });  
    }  
  
    // 启动服务器  
    public void serverStart(int max, int port) throws java.net.BindException {  
        try {  
            clients = new ArrayList<ClientThread>(); 
            RoomList = new ArrayList<RoomList>();
            serverSocket = new ServerSocket(port);  
            serverThread = new ServerThread(serverSocket, max);  
            serverThread.start();  
            isStart = true;  
        } catch (BindException e) {  
            isStart = false;  
            throw new BindException("此port已被佔用，請換一個！");  
        } catch (Exception e1) {  
            e1.printStackTrace();  
            isStart = false;  
            throw new BindException("啟動伺服器失敗");  
        }  
    }  
  
    // 关闭服务器  
    public void closeServer() {  
        try {  
            if (serverThread != null) {  
                serverThread.stop();// 停止服务器线程
            }  
            for (int i = clients.size() - 1; i >= 0; i--) {  
                // 给所有在线用户发送关闭命令  
                clients.get(i).getWriter().println("CLOSE");  
                clients.get(i).getWriter().flush();  
                // 释放资源  
                clients.get(i).reader.close();  
                clients.get(i).writer.close();  
                clients.get(i).socket.close();  
                clients.get(i).stop();// 停止此条为客户端服务的线程  
                clients.remove(i);  
            }  
            if (serverSocket != null) {  
                serverSocket.close();// 关闭服务器端连接  
            }  
            listModel.removeAllElements();// 清空用户列表  
            listmodel.removeAllElements();
            RoomList.clear();
            isStart = false;  
        } catch (IOException e) {  
            e.printStackTrace();  
            isStart = true;  
        }  
    }  
  
    // 群发服务器消息  
    public void sendServerMessage(String message) {  
        for (int i = clients.size() - 1; i >= 0; i--) {  
            clients.get(i).getWriter().println("伺服器公告：" + message + "(廣播)");  
            clients.get(i).getWriter().flush();  
        }  
    }  
  
    // 服务器线程  
    class ServerThread extends Thread {  
        private ServerSocket serverSocket;  
        private int max;// 人数上限  
  
        // 服务器线程的构造方法  
        public ServerThread(ServerSocket serverSocket, int max) {  
            this.serverSocket = serverSocket;  
            this.max = max;  
        }  
  
        public void run() {
            while (true) {// 不停的等待客户端的链接  
                try {
                    Socket socket = serverSocket.accept();
                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    PrintWriter w;
                    w = new PrintWriter(socket.getOutputStream());
                    String inf = r.readLine();                     
                    StringTokenizer st = new StringTokenizer(inf, "@");  
                    User user = new User(st.nextToken(), st.nextToken());
                    boolean NameCollision = false;

                    for(int i=clients.size()-1; i>=0; --i) {
                        if(user.getName().equals(clients.get(i).getUser().getName()) ) {
                            NameCollision = true; break;
                        }
                    }
                    if(NameCollision) {
                        w.println("USED@伺服器表示：對不起，" + user.getName()  
                                + user.getIp() + "，暱稱已重複，請換一個！");
                        w.flush();
                        r.close();
                        w.close();
                        socket.close();
                        continue;
                    }

                    if (clients.size() == max) {// 如果已达人数上限  
                        // 接收客户端的基本用户信息  
/*                        String inf = r.readLine();  
                        StringTokenizer st = new StringTokenizer(inf, "@");  

                        User user = new User(st.nextToken(), st.nextToken());  
                        // 反馈连接成功信息  

                        user = new User(st.nextToken(), st.nextToken());
*/                        // 反馈连接成功信息  
                        w.println("MAX@伺服器表示：對不起，" + user.getName()  
                                + user.getIp() + "，伺服器已爆滿，請稍候再嘗試連線！");  
                        w.flush();  
                        // 释放资源   
                        r.close();  
                        w.close(); 
                        socket.close();   
                    }else {
                        ClientThread client = new ClientThread(socket, user);  
                        client.start();// 开启对此客户端服务的线程  
                        clients.add(client);  
                        listModel.addElement(client.getUser().getName());// 更新在线列表  
                        contentArea.append(client.getUser().getName()  
                                + client.getUser().getIp() + "上線!\r\n");  
                    }
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }
        }  
    }  
  
    // 为一个客户端服务的线程  
    class ClientThread extends Thread {  
        private Socket socket;  
        private BufferedReader reader;  
        private PrintWriter writer;  
        private User user;  
  
        public BufferedReader getReader() {  
            return reader;  
        }  
  
        public PrintWriter getWriter() {  
            return writer;  
        }  
  
        public User getUser() {  
            return user;  
        }  
  
        // 客户端线程的构造方法  
        public ClientThread(Socket socket, User usr) {  
            try {
                this.socket = socket;  
                reader = new BufferedReader(new InputStreamReader(socket  
                        .getInputStream()));  
                writer = new PrintWriter(socket.getOutputStream());  
                // 接收客户端的基本用户信息  
/*                String inf = reader.readLine();  
                StringTokenizer st = new StringTokenizer(inf, "@");  
                user = new User(st.nextToken(), st.nextToken());  
*/                // 反馈连接成功信息  
                this.user = usr;
                writer.println(user.getName() + user.getIp() + "連線成功!");  
                writer.flush();  
                // 反馈当前在线用户信息  
                if (clients.size() > -1) {  
                    String temp = "";  
                    for (int i = clients.size() - 1; i >= 0; i--) {  
                        temp += (clients.get(i).getUser().getName() + "/" + clients  
                                .get(i).getUser().getIp())  
                                + "@";
                    }  
                    writer.println("USERLIST@" + clients.size() + "@" + temp);  
                    writer.flush();  
                    String temp1 = "";
                    for ( int i = RoomList.size() - 1; i >= 0; i-- )
                    {
                        temp1 += ( RoomList.get(i).name + "@" );
                    }
                    writer.println("ROOMLIST@" + RoomList.size() + "@" + temp1);
                    writer.flush();
                }  
                // 向所有在线用户发送该用户上线命令  
                for (int i = clients.size() - 1; i >= 0; i--) {  
                    clients.get(i).getWriter().println(  
                            "ADD@" + user.getName() + user.getIp());  
                    clients.get(i).getWriter().flush();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
        public void run() {// 不断接收客户端的消息，进行处理。  
            String message = null;  
            while (true) {  
                try {  
                    message = reader.readLine();// 接收客户端消息
                    StringTokenizer stringTokenizer = new StringTokenizer(  
                            message, "@");  
                    String command = stringTokenizer.nextToken();// 命令
                    if (command.equals("CLOSE"))// 下线命令  
                    {
                        contentArea.append(this.getUser().getName()  
                                + this.getUser().getIp() + "下線!\r\n");  
                        // 断开连接释放资源  
                        reader.close();  
                        writer.close();  
                        socket.close();  
  
                        // 向所有在线用户发送该用户的下线命令  
                        for (int i = clients.size() - 1; i >= 0; i--) {  
                            clients.get(i).getWriter().println(  
                                    "DELETE@" + user.getName());  
                            clients.get(i).getWriter().flush();  
                        } 
  
                        listModel.removeElement(user.getName());// 更新在线列表  
                        
                        for (int i = RoomList.size() - 1; i >= 0; i--)
                        {
                            RoomList.get(i).members.remove(user.getName());
                            /*
                            if (RoomList.get(i).members.size() == 0)
                            {
                                listmodel.removeElement(RoomList.get(i).name);
                                RoomList.remove(i);
                            }
                            */
                        }
                                                
                        // 删除此条客户端服务线程  
                        for (int i = clients.size() - 1; i >= 0; i--) {  
                            if (clients.get(i).getUser() == user) {  
                                ClientThread temp = clients.get(i);  
                                clients.remove(i);// 删除此用户的服务线程  
                                temp.stop();// 停止这条服务线程  
                                return;  
                            }  
                        }  
                    } 
                    else if (command.equals("ADDROOM"))//增加房間
                    {
                        String roomId = stringTokenizer.nextToken();   
                        String username = stringTokenizer.nextToken();
                        listmodel.addElement(roomId); 
                        RoomList.add(new RoomList(roomId, username));
                        for ( int i = clients.size() - 1; i >= 0; i-- )
                        {
                            clients.get(i).getWriter().println("ADDROOM@" + roomId);
                            clients.get(i).getWriter().flush();
                        }
                    }
                    else if (command.equals("INVITE"))//邀請別人
                    {                        
                        String name = stringTokenizer.nextToken();   
                        String roomId = stringTokenizer.nextToken();
                        for ( int i = RoomList.size() - 1; i >= 0; i-- )
                        {
                            if (RoomList.get(i).name.equals(roomId))
                            {
                                RoomList.get(i).members.add(name);
                            }
                        }
                        for ( int i = clients.size() - 1; i >= 0; i-- )
                        {
                            clients.get(i).getWriter().println(message);
                            clients.get(i).getWriter().flush();
                        }
                        
                    }
                    else if (command.equals("ADDINROOM"))//加入房間
                    {
                        //TODO:need to inform others.
                        String roomId = stringTokenizer.nextToken();   
                        String name = stringTokenizer.nextToken();
                        for ( int i = RoomList.size() - 1; i >= 0; i-- )
                        {
                            if ( RoomList.get(i).name.equals(roomId))
                            {
                                String temp = "ADDINROOM@" + roomId + "@" + RoomList.get(i).members.size() + "@";
                                for ( int j = RoomList.get(i).members.size() - 1; j >= 0; j-- )
                                {
                                    temp += (RoomList.get(i).members.get(j) + "@");
                                }
                                for ( int j = clients.size() - 1; j >= 0; j-- )
                                {
                                    if ( clients.get(j).getUser().getName().equals(name) )
                                    {
                                        clients.get(j).getWriter().println(temp);
                                        clients.get(j).getWriter().flush();
                                    }
                                }
                                String temp1 = "ROOMINFORM@" + roomId + "@" + name;
                                for ( int j = clients.size() - 1; j >= 0; j-- )
                                {
                                    clients.get(j).getWriter().println(temp1);
                                    clients.get(j).getWriter().flush();
                                }
                                RoomList.get(i).members.add(name);
                            }
                        }
                    }
                    else if (command.equals("ROOMCHAT"))
                    {
                        for ( int j = clients.size() - 1; j >= 0; j-- )
                        {
                            clients.get(j).getWriter().println(message);
                            clients.get(j).getWriter().flush();
                        }
                    }
                     else if (command.equals("DING"))
                    {
                        for ( int j = clients.size() - 1; j >= 0; j-- )
                        {
                            clients.get(j).getWriter().println(message);
                            clients.get(j).getWriter().flush();
                        }
                    }
                    else if ( command.equals("FILE"))
                    {
                        String destIP = stringTokenizer.nextToken();
                        String dest = stringTokenizer.nextToken();
                        String src = stringTokenizer.nextToken();
                        
                        for( int i = clients.size()-1; i >= 0; i-- ) {
                            if(clients.get(i).getUser().getName().equals(dest)) {
                                clients.get(i).getWriter().println(message);
                                clients.get(i).getWriter().flush();
                            }
                        }
                    }
                    else if (command.equals("ADDROOMPRIVATE"))
                    {
                        String roomId = stringTokenizer.nextToken();   
                        String username = stringTokenizer.nextToken();
                        listmodel.addElement(roomId); 
                        RoomList.add(new RoomList(roomId, username));
                    }
                    else if (command.equals("VOICE"))
                    {  
                        String dest = stringTokenizer.nextToken();
                        for( int i = clients.size()-1; i >= 0; i-- ) 
                        {
                            if(clients.get(i).getUser().getName().equals(dest)) 
                            {
                                clients.get(i).getWriter().println(message);
                                clients.get(i).getWriter().flush();
                            }
                        }
                    }
                    else if (command.equals("VOICEIP"))
                    {
                        String IP = stringTokenizer.nextToken();
                        String dest = stringTokenizer.nextToken();
                        for( int i = clients.size()-1; i >= 0; i-- ) 
                        {
                            if(clients.get(i).getUser().getName().equals(dest)) 
                            {
                                clients.get(i).getWriter().println(message);
                                clients.get(i).getWriter().flush();
                            }
                        }
                    }
                    else if (command.equals("VOICECANT"))
                    {
                        String dest = stringTokenizer.nextToken();
                        for( int i = clients.size()-1; i >= 0; i-- ) 
                        {
                            if(clients.get(i).getUser().getName().equals(dest)) 
                            {
                                clients.get(i).getWriter().println(message);
                                clients.get(i).getWriter().flush();
                            }
                        }
                    }
                    else if (command.equals("DISABLEVOICE"))
                    {
                        String dest = stringTokenizer.nextToken();
                        String me = stringTokenizer.nextToken();
                        for( int i = clients.size()-1; i >= 0; i-- ) 
                        {
                            if( clients.get(i).getUser().getName().equals(dest) || clients.get(i).getUser().getName().equals(me) ) 
                            {
                                clients.get(i).getWriter().println(message);
                                clients.get(i).getWriter().flush();
                            }
                        }
                    }
                    else 
                    {  
                        dispatcherMessage(message);// 转发消息  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } 
            }  
        }  
  
        // 转发消息  
        public void dispatcherMessage(String message) {  
            StringTokenizer stringTokenizer = new StringTokenizer(message, "@");  
            String source = stringTokenizer.nextToken();  
            String owner = stringTokenizer.nextToken();  
            String content = stringTokenizer.nextToken();  
            message = source + "說：" + content;  
            contentArea.append(message + "\r\n");  
            if (owner.equals("ALL")) {// 群发  
                for (int i = clients.size() - 1; i >= 0; i--) {  
                    clients.get(i).getWriter().println(message);  
                    clients.get(i).getWriter().flush();  
                }  
            }  
        }  
    }  
}
