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
    private HashMap<Group, User> adminMap;
    private HashMap<String, User>user;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.user = new HashMap<String, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 1;
        this.messageId = 1;
    }
    public String createUser(String name, String mobile) throws Exception {
        if(userMobile.contains(mobile)){
            throw new Exception("User already exists");
        }
        else {
            userMobile.add(mobile);
            user.put(mobile, new User(name, mobile));
        }
        return "SUCCESS";
    }

    public Group createGroup(List<User> users){
        if(users.size()<2)
            return null;
        User admin = users.get(0);

        if(users.size()==2) {
            String chatName = users.get(1).getName();
            Group group = new Group(chatName, 2);
            groupUserMap.put(group, users);
            return null;
        }

        String groupName = "Group "+customGroupCount++;
        Group group = new Group(groupName, users.size());
        groupUserMap.put(group, users);
        return group;
    }

    public int createMessage(String content){
        Message msg = new Message(messageId, content);
        return messageId++;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        for(User user : groupUserMap.get(group)){
            if(user.equals(sender)){
                //return (groupMessageMap.get(group).size()-1);
                return groupMessageMap.get(group).get(groupMessageMap.get(group).size()-1).getId();
            }
        }
        throw new Exception("You are not allowed to send message");
        //return -1;
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        if(!groupUserMap.get(group).get(0).equals(approver)){
            throw new Exception("Approver does not have rights");
        }
        for(User isUser : groupUserMap.get(group)){
            if(isUser.equals(user)){
                List<User> users = groupUserMap.get(group);
                users.remove(user);
                users.add(0, user);
                return "SUCCESS";
            }
        }
        throw new Exception("User is not a participant");
    }
}