package com.driver;

import java.util.Date;
import java.util.List;

public class WhatsappService {
    WhatsappRepository whatsappRepository = new WhatsappRepository();
    public String createUser(String name, String mobile) throws Exception {
        String user = whatsappRepository.createUser(name, mobile);
        return user;
    }

    public Group createGroup(List<User> users){
        return whatsappRepository.createGroup(users);
    }

    public int createMessage(String content){
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        return 0;
    }

    public String changeAdmin(User approver, User user, Group group) throws  Exception{
        return null;
    }

    public int removeUser(User user)throws  Exception{
        return 0;
    }

    public String findMessage(Date start, Date end, int K)throws  Exception{
        return null;
    }
}
