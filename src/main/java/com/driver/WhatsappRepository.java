package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
   // private HashMap<String, Integer>messageIdMap;
    private HashMap<Group, User> adminMap;
  //  private HashMap<String, User>userMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
       // this.messageIdMap = new HashMap<String, Integer>();
       // this.userMap = new HashMap<String, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }
    public String createUser(String name, String mobile) throws Exception {
        if(userMobile.contains(mobile))
            throw new Exception("User already exists");
        User user = new User(name, mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users){
        if(users.size()<2)
            return null;
        if(users.size()==2){
            Group group = new Group(users.get(1).getName(), 2);
            groupUserMap.put(group, users);
            adminMap.put(group, users.get(0));
            return group;
        }
        customGroupCount++;
        Group group = new Group("Group "+customGroupCount, users.size());
        groupUserMap.put(group, users);
        adminMap.put(group, users.get(0));
        return group;
    }

    public int createMessage(String content){
        messageId++;
        Message msg = new Message(messageId, content);
        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        if(groupUserMap.containsKey(group))
            throw new Exception("Group does not exist");
        List<User> grpMember = groupUserMap.get(group);
        if(!grpMember.contains(sender))
            throw new Exception("You are not allowed to send message");
        //List<Message> grpMsg=groupMessageMap.get(group);
        senderMap.put(message, sender);
        return message.getId();
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(groupUserMap.containsKey(group))
            throw new Exception("Group does not exist");
        List<User> grpMember = groupUserMap.get(group);
        if(adminMap.get(group)!=approver)
            throw new Exception("Approver does not have rights");
        if(!grpMember.contains(user))
            throw new Exception("User is not a participant");
        adminMap.put(group, user);
        return "SUCCESS";
    }
}
