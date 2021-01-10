package be.kdg.examen.gedistribueerde.server;

import be.kdg.examen.gedistribueerde.client.Document;
import be.kdg.examen.gedistribueerde.client.DocumentImpl;
import be.kdg.examen.gedistribueerde.client.communication.MessageManager;
import be.kdg.examen.gedistribueerde.client.communication.MethodCallMessage;
import be.kdg.examen.gedistribueerde.client.communication.NetworkAddress;


public class ServerStub implements Server{

    private final NetworkAddress contactsAddress;
    private final MessageManager messageManager;

    
    public ServerStub(NetworkAddress contactsAddress) {
        this.contactsAddress = contactsAddress;
        this.messageManager = new MessageManager();
    }


    private void checkEmptyReply() {
        String value = "";
        while (!"Ok".equals(value)) {
            MethodCallMessage reply = messageManager.wReceive();
            if (!"result".equals(reply.getMethodName())) {
                continue;
            }
            value = reply.getParameter("result");
        }
    }

    @Override
    public void log(Document document) {

        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "log");

        message.setParameter("text", document.getText());
        messageManager.send(message, contactsAddress);
        checkEmptyReply();


    }

    @Override
    public Document create(String s) {
        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "create");
        message.setParameter("text", s);
        messageManager.send(message, contactsAddress);

        MethodCallMessage reply = messageManager.wReceive();

        return new DocumentImpl(reply.getParameter("text"));


    }

    @Override
    public void toUpper(Document document) {

        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "toUpper");

        message.setParameter("text", document.getText());
        messageManager.send(message, contactsAddress);

        String value = "";
        while (!"Ok".equals(value)) {
            MethodCallMessage reply = messageManager.wReceive();
            if ("resultToUpper".equals(reply.getMethodName())) {
                document.setText(reply.getParameter("text"));
                value = reply.getParameter("result");
            }

        }

    }

    @Override
    public void toLower(Document document) {

        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "toLower");

        message.setParameter("text", document.getText());
        messageManager.send(message, contactsAddress);

        String value = "";
        while (!"Ok".equals(value)) {
            MethodCallMessage reply = messageManager.wReceive();
            if ("resultToLower".equals(reply.getMethodName())) {
                document.setText(reply.getParameter("text"));
                value = reply.getParameter("result");
            }

        }


    }

    @Override
    public void type(Document document, String textToAppend) {

        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "type");

        message.setParameter("text", document.getText());
        message.setParameter("textToAppend", textToAppend);
        messageManager.send(message, contactsAddress);
        String value = "";
        while (!"Ok".equals(value)) {
            MethodCallMessage reply = messageManager.wReceive();
            if ("resultType".equals(reply.getMethodName())) {
                if("Typing".equals(reply.getParameter("result"))){
                    document.setText(reply.getParameter("text"));
                }

                value = reply.getParameter("result");
            }

        }


    }
}
