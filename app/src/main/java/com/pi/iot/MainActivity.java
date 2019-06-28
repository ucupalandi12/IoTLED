package com.pi.iot;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    //Inisialisasi
    private Switch led1;
    private Switch led2;
    private Switch led3;
    private EditText ip, port;
    private Button connect;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private String ipaddress;
    private int portnum;
    private TextView link;
    private Pattern pattern;
    private Matcher matcher;
    private Handler handler;
    private TCP tcp;
    private String normalText;
    private String data1;
    private String data2;
    private String data3;
    private String data4;
    private String data5;
    private String data6;
    private String data7;
    private String data8;
    private String data9;
    private String secretKey;
    private String TDES;
    private String SHARED_KEY = BuildConfig.SECRET_KEY;

    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    //OnCreate Layout untuk Project
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pattern = Pattern.compile(IPADDRESS_PATTERN);
        handler = new Handler();
        setContentView(R.layout.activity_main);

        led1 = (Switch) findViewById(R.id.led1);
        led2 = (Switch) findViewById(R.id.led2);
        led3 = (Switch) findViewById(R.id.led3);
        ip = (EditText) findViewById(R.id.ip);
        port = (EditText) findViewById(R.id.port);
        connect = (Button) findViewById(R.id.connect);

        changeSwitchesState(false);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connect.getText().toString().equalsIgnoreCase("Connect")) {
                    try {
                        ipaddress = ip.getText().toString();
                        if (!checkIP(ipaddress))
                            throw new UnknownHostException(port + "is not a valid IP address");
                        portnum = Integer.parseInt(port.getText().toString());
                        if (portnum > 65535 && portnum < 0)
                            throw new UnknownHostException(port + "is not a valid port number ");
                        Client client = new Client(ipaddress, portnum);
                        client.start();
                    } catch (UnknownHostException e) {
                        showErrorsMessages("Please enter a valid IP !! ");
                    } catch (NumberFormatException e) {
                        showErrorsMessages("Please enter valid port number !! ");
                    }
                } else {
                    connect.setText("Connect");
                    changeSwitchesState(false);
                    closeConnection();
                }
            }
        });

        led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    lightOn(1);
                } else {
                    lightOff(1);
                }
            }
        });

        led2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    lightOn(2);
                } else {
                    lightOff(2);
                }
            }
        });

        led3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    lightOn(3);
                } else {
                    lightOff(3);
                }
            }
        });

//        tcp = "sourcePort:8080# destinationPort:8080# sequence:8# dataOffset:4# flags:1# message1:1# message2:0";
//        normalText = tcp;


        tcp = new TCP("8080", "8080", "8", "4", "1", "1", "0", "true", "16", "3020984DYEN29DMTIRELVO", "90");
        //Log.d("Tag dari ucup", "TCP Berhasil dibuat dgn message " + tcp.getMessage());
    }//end of oncreate

    private void closeConnection() { //bagian agar koneksi ditutup jika tidak digunakan
        try {
            out.writeObject("close");
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            showErrorsMessages(ex.getMessage());
        }
    }//end of closeConnection

    @Override
    protected void onStop() { //koneksi ditutup
        super.onStop();
        if (out != null) {
            closeConnection();
        }

    }
    //////////////switches related methods ///////////////////
    void checkSwitchStatus() {
        if (led1.isChecked()) {
            lightOn(1);
        } else {
            lightOff(1);
        }
        if (led2.isChecked()) {
            lightOn(2);
        } else {
            lightOff(2);
        }
        if (led3.isChecked()) {
            lightOn(3);
        } else {
            lightOff(3);
        }
    }

    void changeSwitchesState(boolean state) {
        led1.setEnabled(state);
        led2.setEnabled(state);
        led3.setEnabled(state);
    }

    ////////////////////// light on off methods /////////////
    void lightOn(int lednum) {
        try {
//            String[] tcpArray;
//            tcpArray = tcp.split("# ");
//            String message = tcpArray[5].split(":")[1];

            TriDES encryptedText = new TriDES(BuildConfig.SECRET_KEY);
            ArrayList<String> myData = new ArrayList<String>();
            data1 = encryptedText.encrypt(lednum+tcp.getRandomSourcePort());
            data2 = encryptedText.encrypt(lednum+tcp.getRandomDestinationPort());
            data3 = encryptedText.encrypt(lednum+tcp.getRandomSequence());
            data4 = encryptedText.encrypt(lednum+tcp.getRandomDataOffset());
            data5 = encryptedText.encrypt(lednum+tcp.getRandomFlags());
            data6 = encryptedText.encrypt(lednum+tcp.getAcknowledge());
            data7 = encryptedText.encrypt(lednum+tcp.getRandomWindowSize());
            data8 = encryptedText.encrypt(lednum+tcp.getChecksum());
            data9 = encryptedText.encrypt(lednum+tcp.getUrgentPointer());
            normalText = encryptedText.encrypt(lednum+tcp.getMessage1());
            myData.add(data1);
            myData.add(data2);
            myData.add(data3);
            myData.add(data4);
            myData.add(data5);
            myData.add(data6);
            myData.add(data7);
            myData.add(data8);
            myData.add(data9);
            myData.add(normalText);

//            for (int i = 0; i < myData.size(); i++) {
//                System.out.println(myData);
//
//            }
            Log.d("TAG", "lightOn: " + myData.indexOf(normalText));
//            Collections.shuffle(myData);
//            Collections.shuffle(myData, new Random(System.nanoTime()));
////            byte[] encryptedText = encrypt(lednum + message.toCharArray());
////            normalText = TriDES.encrypt(lednum + tcp.getMessage1());
//
////            System.out.println(dataToString);
            StringBuilder dataToStringOn = new StringBuilder();
            for (String s : myData) {
                dataToStringOn.append(s);
                dataToStringOn.append("\n");
            }
            out.writeObject(dataToStringOn.toString());
//            System.out.println(data1+data2+data3+data4+data5+normalText+data6+data7+data8+data9);
//            out.writeObject(normalText+"\n"+data1+"\n"+data2+"\n"+data3+"\n"+data4+"\n"+data5+"\n"+data6+"\n"+data7+"\n"+data8+"\n"+data9+"\n");
//            Log.d("TAG", "lightOn: "+ dataToString);
            out.flush();
            out.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorsMessages("Error while sending command!!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void lightOff(int lednum) {
        try {
            TriDES encryptedText = new TriDES(BuildConfig.SECRET_KEY);
            ArrayList<String> myData = new ArrayList<String>();

            data1 = encryptedText.encrypt(lednum+tcp.getRandomSourcePort());
            data2 = encryptedText.encrypt(lednum+tcp.getRandomDestinationPort());
            data3 = encryptedText.encrypt(lednum+tcp.getRandomSequence());
            data4 = encryptedText.encrypt(lednum+tcp.getRandomDataOffset());
            data5 = encryptedText.encrypt(lednum+tcp.getRandomFlags());
            data6 = encryptedText.encrypt(lednum+tcp.getAcknowledge());
            data7 = encryptedText.encrypt(lednum+tcp.getRandomWindowSize());
            data8 = encryptedText.encrypt(lednum+tcp.getChecksum());
            data9 = encryptedText.encrypt(lednum+tcp.getUrgentPointer());
            normalText = encryptedText.encrypt(lednum + tcp.getMessage());
            myData.add(data1);
            myData.add(data2);
            myData.add(data3);
            myData.add(data4);
            myData.add(data5);
            myData.add(data6);
            myData.add(data7);
            myData.add(data8);
            myData.add(data9);
            myData.add(normalText);

//            for (int i = 0; i < myData.size(); i++) {
//                System.out.println(myData.get(i));
//                Log.d("TAG", "lightOff: " + myData);
//
//            }
            Log.d("TAG", "lightOff: " + myData.indexOf(normalText));

//            System.out.println(normalText+data1+data2+data3+data4+data5+data6+data7+data8+data9);
//            System.out.println(dataToString);

//            Collections.shuffle(myData);
//            Collections.shuffle(myData, new Random(System.nanoTime()));
            StringBuilder dataToStringOff = new StringBuilder();
            for (String s : myData) {
                dataToStringOff.append(s);
                dataToStringOff.append("\n");
            }
            out.writeObject(dataToStringOff.toString());
//            Log.d("TAG", "lightOff: "+ dataToString);
//            System.out.println(data1+data2+data3+data4+data5+normalText+data6+data7+data8+data9);
//            out.writeObject(normalText+"\n"+data1+"\n"+data2+"\n"+data3+"\n"+data4+"\n"+data5+"\n"+data6+"\n"+data7+"\n"+data8+"\n"+data9+"\n");
            out.flush();
            out.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showErrorsMessages(String error) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Error!! ").setMessage(error).setNeutralButton("OK", null).create().show();
    }

    public boolean checkIP(final String ip) {
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /////////////// client thread ////////////////////////////
    private class Client extends Thread {
        private String ipaddress;
        private int portnum;

        public Client(String ipaddress, int portnum) {
            this.ipaddress = ipaddress;
            this.portnum = portnum;
        }

        @Override
        public void run() {
            super.run();
            connectToServer(ipaddress, portnum);
        }


        // bagian untuk testing koneksi dan pengiriman paket"
        public void connectToServer(String ip, int port) {

            try {
                socket = new Socket(InetAddress.getByName(ip), port);
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socket.getInputStream());
                //for (int i = 0; i < 1; i++) {
                //    System.out.println((String) in.readObject() + "\n");
                //}
                checkSwitchStatus();
                handler.post(new Runnable() {
                    public void run() {
                        connect.setText("Close");
                        changeSwitchesState(true);
                    }
                });
            } catch (InvalidClassException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    public void run() {
                        showErrorsMessages("Unknown host!!");
                    }
                });
            } //catch (ClassNotFoundException e) {
                //e.printStackTrace();
            //}

        }


    }//end of client class

//    public byte[] encrypt(byte[] plainTextBytes) throws Exception {
//
//        byte[] keyValue = Hex.decodeHex(SHARED_KEY.toCharArray());
//
//
//        final SecretKey key = new SecretKeySpec(keyValue, "DESede");
//        final Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//
//
//        final byte[] cipherText = cipher.doFinal(plainTextBytes);
//
//
//        return cipherText;
//    }
}
