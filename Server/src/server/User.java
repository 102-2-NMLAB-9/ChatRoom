/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

/**
 *
 * @author nmlab212
 */

public class User {
    private String name;  
    private String ip;  
    private String roomId;
  
    public User(String name, String ip) {  
        this.name = name;  
        this.ip = ip;  
        this.roomId = "";
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public String getIp() {  
        return ip;  
    }  
  
    public void setIp(String ip) {  
        this.ip = ip;  
    }  
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public String getRoomId() {
        return roomId;
    }
}