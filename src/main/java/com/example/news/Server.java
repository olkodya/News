package com.example.news;

import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.sql.SQLException;



public class Server {

    private static Server instance;
    private static int clientCount = 0;
    private static int id = 0;


    public static synchronized Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int port = 8000;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                MonoServer ech = new MonoServer(serverSocket.accept(), id++);
                // clientList.put(,ech);
                clientCount++;

                ech.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert serverSocket != null;
            stop(serverSocket);
        }
    }




    public static void stop(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class MonoServer extends Thread {
        private final Socket clientSocket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        DatabaseHandler DatabaseHandler = new DatabaseHandler();

        public MonoServer(Socket socket, int id) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                ois = new ObjectInputStream(clientSocket.getInputStream());
                oos = new ObjectOutputStream(clientSocket.getOutputStream());

                while (!clientSocket.isClosed() && !clientSocket.isOutputShutdown() && !clientSocket.isInputShutdown()) {
                    Object readObject = ois.readObject();
                    if (readObject instanceof String string) {
                        //sendLeaderboard();
                    } else {
                        UserPackage userPackage = (UserPackage) readObject;
                        if (userPackage.getMessage().equals("LOGIN")) {
                            if (DatabaseHandler.getUser(userPackage.username, userPackage.password)) {
                                oos.writeObject("correct");
                                oos.reset();
                            } else {
                                oos.writeObject("incorrect");
                                oos.reset();
                            }
                        } else if (userPackage.getMessage().equals("ROLE")) {
                             {
                                oos.writeObject(DatabaseHandler.getRole(userPackage.username));
                                oos.reset();
                            }
                        }
                        else if (userPackage.getMessage().equals("CHECK")) {
                            {
                                oos.writeObject(DatabaseHandler.CheckUser(userPackage.username));
                                oos.reset();
                            }
                        }

                        else if (userPackage.getMessage().equals("SIGN")) {
                            {
                                DatabaseHandler.signUpUser(userPackage.username,userPackage.password, userPackage.role);
                                oos.writeObject("SUCCESS");
                                oos.reset();
                            }
                        }

                        else if (userPackage.getMessage().equals("ADDNEWS")) {
                            {
                                DatabaseHandler.createNews(userPackage.title,userPackage.body);
                                oos.writeObject("SUCCESS");
                                oos.reset();
                            }
                        }

                        else if (userPackage.getMessage().equals("DELNEWS")) {
                            {
                                DatabaseHandler.DeleteNews(userPackage.title);
                                oos.writeObject("SUCCESS");
                                oos.reset();
                            }
                        }

                        else if (userPackage.getMessage().equals("EDNEWS")) {
                            {
                                DatabaseHandler.edNews(userPackage.starttitle,userPackage.title, userPackage.body);
                                oos.writeObject("SUCCESS");
                                oos.reset();
                            }
                        }

                        else if (userPackage.getMessage().equals("ID")) {
                            {
                                oos.writeObject(String.valueOf(DatabaseHandler.getID(userPackage.title)));
                                oos.reset();
                            }
                        }
                    }
                }
                oos.close();
                ois.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }
}