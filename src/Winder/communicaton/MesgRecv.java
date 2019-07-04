package Winder.communicaton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Winder.main.MainFrame;
import Winder.windersearch.SearchFrame;
import Winder.windertalking.ChatFrame;

public class MesgRecv extends Thread {
    private Socket socket;
    public PrintWriter out;
    private boolean isConnecting;

    private int id;

    public MesgRecv (Socket s) {
        socket = s;
        id = -1;
        isConnecting = true;
    }

    // 通信状況を監視し，受信データによって動作する
    public void run()
    {
        try {
            InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
            BufferedReader    br   = new BufferedReader(sisr);
            out = new PrintWriter(socket.getOutputStream(), true);

            Initialize();

            while (isConnecting) {
                String inputLine = br.readLine(); // データを一行分だけ読み込んでみる
                MainFrame.mainController.PrintLog("[ServerClient] Receive : " + inputLine);
                if (inputLine != null) { // 読み込んだときにデータが読み込まれたかどうかをチェックする
                    String[] inputTokens = inputLine.split(" ");
                    String cmd = inputTokens[0];
                    
                    switch (cmd) {
                        case "LOGIN":
                        String name = inputTokens[1].replace("_", " ");
                        String birth = inputTokens[2];
                        String comment = inputTokens[3].replace("_", " ");
                        MainFrame.mainController.setStatus(name, birth, comment);
                        break;

                        case "SETID":
                        int set_id = Integer.parseInt(inputTokens[1]);
                        try {
                            File file = new File("dat/userdata.dat");
                            FileWriter fw = new FileWriter(file);
                            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
                        
                            pw.println(set_id);

                            pw.close();

                            File ct_dir = new File("dat/chat" + String.valueOf(set_id));
                            ct_dir.mkdir();
                            File ct_list = new File("dat/chat" + String.valueOf(set_id)+ "/chatlist.dat");
                            ct_list.createNewFile();

                            MainFrame.mainController.PrintLog("[MesgRecv] Set ID : " + String.valueOf(set_id));
                            id = set_id;
                            String msg = "LOGIN " + String.valueOf(id);
                            out.println(msg);
                            
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                        case "SEARCHINFO":
                        int search_id = Integer.parseInt(inputTokens[1]);
                        String search_name = inputTokens[2].replace("_", " ");
                        String search_birth = inputTokens[3].replace("_", " ");
                        String search_comment = inputTokens[4].replace("_", " ");
                        SearchFrame.searchController.SetStatus(search_id, search_name, search_birth, search_comment);
                        break;

                        case "NEWCHAT":
                        int new_id = Integer.parseInt(inputTokens[1]);
                        String new_name = inputTokens[2];
                        MainFrame.mainController.StartNewChat(new_id, new_name);
                        break;

                        case "MESSAGE":
                        int op_id = Integer.parseInt(inputTokens[1]);
                        String op_name = inputTokens[1];
                        String recv_msg = inputTokens[2];
                        MainFrame.mainController.ReceiveMessage(op_id, op_name, recv_msg);
                        break;

                        case "DISCONNECT":
                        isConnecting = false;
                        break;

                        default:
                        MainFrame.mainController.PrintLog("[MesgRecv] Wrong Command : " + cmd);
                        break;
                    }
                }
                
                
            }
            
            socket.close();  
        } catch (IOException e) {
            System.err.println("エラーが発生しました: " + e);
        }
    }

    private void Initialize () {
        MainFrame.mainController.PrintLog("[MesgRecv] Load Status");
        try{
            File file = new File("dat/userdata.dat");
            BufferedReader br = new BufferedReader(new FileReader(file));
            //System.out.println("File loaded.");

            id = Integer.parseInt(br.readLine());

            String msg = "LOGIN " + String.valueOf(id);
            out.println(msg);
          
            br.close();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }

        MainFrame.mainController.PrintLog("[MesgRecv] Load ChatList");
        out.println("GETCHATLIST");
    }

    public void Disconnect () {isConnecting = false;}
}