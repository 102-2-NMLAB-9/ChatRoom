/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.ArrayList;

/**
 *
 * @author nmlab212
 */
public class RoomList {
    //public for server access easily
    public String name;
    public ArrayList<String> members = new ArrayList<String>();
    
    public RoomList(String name, String username)
    {
        this.name = name;
        members.add(username);
    }
    
}
