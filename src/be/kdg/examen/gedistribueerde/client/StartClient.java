package be.kdg.examen.gedistribueerde.client;

import be.kdg.examen.gedistribueerde.client.communication.NetworkAddress;
import be.kdg.examen.gedistribueerde.server.ServerStub;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class StartClient {
    public static void main(String[] args) {
        // args:127.0.0.1 33825
        if (args.length != 2) {
            System.err.println("Usage: java Client <contactsIP> <contactsPort>");
            System.exit(1);
        }
        int port = Integer.parseInt(args[1]);
        NetworkAddress contactsAddress = new NetworkAddress(args[0], port);
        ServerStub serverStub = new ServerStub(contactsAddress);
        DocumentImpl document = new DocumentImpl();
        Client client = new Client(serverStub,document);
        client.run();
    }
}
