package demo.service;

import demo.model.Message;

public interface Service {

    void sendMessage(Message message) throws ServiceException;
}
