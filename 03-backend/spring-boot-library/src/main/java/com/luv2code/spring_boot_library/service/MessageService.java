package com.luv2code.spring_boot_library.service;

import com.luv2code.spring_boot_library.dao.MessageRepository;
import com.luv2code.spring_boot_library.entity.Message;
import com.luv2code.spring_boot_library.requestmodels.AdminQuestionRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public void postMessage(Message messageRequest,String userEmail){
        Message message = new Message(messageRequest.getTitle(),messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        this.messageRepository.save(message);
    }

    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception{
        Optional<Message> message = this.messageRepository.findById(adminQuestionRequest.getId());
        if(!message.isPresent()){
            throw new Exception("Message not found!");
        }
        message.get().setAdminEmail(userEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);
        this.messageRepository.save(message.get());
    }
}
