package client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

// Please start Server class first and than ClientWithGui class

public class ClientWithGui extends Frame {

    public String request;

    public ClientWithGui() {

        Label firstNumberLabel = new Label("    First Number: ");
        Label secondNumberLabel = new Label("    Second Number: ");
        Label resultLabel = new Label("    Result: ");
        Label sumLabel = new Label();

        TextField firstNumberTF = new TextField();
        TextField secondNumberTF = new TextField();

        Button countButton = new Button("Count");
        Button exitButton = new Button("Exit");

        Panel panelCenter = new Panel(new GridLayout(3, 2));
        Panel panelPageEnd = new Panel(new GridLayout(1, 2));

        panelCenter.add(firstNumberLabel);       panelCenter.add(firstNumberTF);
        panelCenter.add(secondNumberLabel);      panelCenter.add(secondNumberTF);
        panelCenter.add(resultLabel);            panelCenter.add(sumLabel);
        
        panelPageEnd.add(countButton);
        panelPageEnd.add(exitButton);

        exitButton.addActionListener(e -> {System.exit(0);});

        countButton.addActionListener(e -> {
            
            try {
                System.out.println("Connecting to server...");
                
                try (Socket client = new Socket("localhost",8000)) {
                    System.out.println("Connected");
                    OutputStream outToServer = client.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);

                    Integer x = Integer.parseInt(firstNumberTF.getText());
                    Integer y = Integer.parseInt(secondNumberTF.getText());

                    out.writeInt(x);
                    out.writeInt(y);

                    InputStream inFromServer = client.getInputStream();
                    DataInputStream in = new DataInputStream(inFromServer);
                    
                    String result = String.valueOf(in.readInt());
                    System.out.println("Server response : " + result);
                    sumLabel.setText(" ( "+ x + " + " + y + " = " + result + " ) ");
                    System.out.println("----------------------------------------");
                    sumLabel.setBackground(Color.LIGHT_GRAY);
                }
            } catch (IOException ex) {
                System.out.println("Error: Client connection " + ex.getMessage());
            }
        });

        this.setSize(350, 160);
        this.setTitle("Sum two numbers");
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        this.setLocationRelativeTo(null);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelPageEnd, BorderLayout.PAGE_END);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        ClientWithGui gui = new ClientWithGui();
    }
}
