package com.example.hexcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {

    Button button_up, button_down, button_fold, button_mode;
    TextView text_state;
    JoystickView joystick;
    ImageView img_logo;
    Payload payload = new Payload();
    Payload prevP = new Payload();

    Thread Thread1 = null;
    String SERVER_IP = "192.168.4.1";
    int SERVER_PORT = 666;

    //private BluetoothAdapter BA;
    //private Set<BluetoothDevice> pairedDevices;

    private Socket s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joystick = findViewById(R.id.joystick);
        button_up = findViewById(R.id.button_up);
        button_down = findViewById(R.id.button_down);
        button_fold = findViewById(R.id.button_fold);
        button_mode = findViewById(R.id.button_mode);
        text_state = findViewById(R.id.text_state);
        img_logo = findViewById(R.id.img_logo);


        img_logo.setOnClickListener(v -> {
            if(Thread1 == null) {
                Thread1 = new Thread(new Thread1());
                Thread1.start();
            }
        });

        /*BA = BluetoothAdapter.getDefaultAdapter();

        if (BA == null) {
            Toast.makeText(this, "Bluetooth not supported.", Toast.LENGTH_SHORT).show();
            finish();
        }

        if(BA.isEnabled()) {
            text_state.setText("Bluetooth on, click on image to connect");
        } else {
            text_state.setText("Bluetooth is turned off");
        }*/


        /*
        img_logo.setOnClickListener(v -> {
            if(outputStream == null) {
                try {
                    s = new Socket("192.168.4.1", 666);
                    outputStream = s.getOutputStream();
                    output = new PrintWriter(outputStream);
                    output.println("Ahoj si tam");
                    input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    //init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(outputStream != null) {
                    text_state.setText("Connected");
                    Toast.makeText(getApplicationContext(), "Connected to device successfully", Toast.LENGTH_SHORT).show();
                } else {
                    text_state.setText("Disconnected");
                    Toast.makeText(getApplicationContext(), "Failed connecting to device", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */

        button_fold.setOnClickListener(v -> {
            if(button_fold.getText().equals("unfold")) {
                button_fold.setText("fold");
                payload.Fold = 0;
            } else {
                button_fold.setText("unfold");
                payload.Fold = 1;
            }
            sendValues();
        });

        button_mode.setOnClickListener(v -> {
            if(button_mode.getText().equals("strafe")) {
                button_mode.setText("normal");
                payload.Strafe = 1;
            } else {
                button_mode.setText("strafe");
                payload.Strafe = 0;
            }
            sendValues();
        });

        button_up.setOnClickListener(v -> {
            if(payload.Height < 2) {
                payload.Height++;
            }
            sendValues();
        });

        button_down.setOnClickListener(v -> {
            if(payload.Height > 0) {
                payload.Height--;
            }
            sendValues();
        });

        joystick.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                payload.Angle = 0;
                payload.Strength = 0;
            }
            return false;
        });

        joystick.setOnMoveListener((angle, strength) -> {
            payload.Angle = angle;
            if(strength > 0 && strength <= 30) {
                payload.Strength = 1;
            } else if(strength > 30 && strength <= 70) {
                payload.Strength = 2;
            } else if(strength > 70 && strength <= 100) {
                payload.Strength = 3;
            } else {
                payload.Strength = 0;
            }
            sendValues();
        });
    }

    /*private void init() throws IOException {
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();
                if(bondedDevices.size() > 0) {
                    for (BluetoothDevice dev : bondedDevices) {
                        if (dev.getName().equals("ESP32Hexapod")) {
                            BluetoothSocket socket = dev.createRfcommSocketToServiceRecord(dev.getUuids()[0].getUuid());
                            socket.connect();
                            outputStream = socket.getOutputStream();
                            inStream = socket.getInputStream();
                            return;
                        }
                    }
                }
                Log.e("error", "No appropriate paired devices.");
            } else {
                Log.e("error", "Bluetooth is disabled.");
            }
        }
    }*/

    public void write(String s) throws IOException {
        //outputStream.write(s.getBytes());
        output.println(s);
        Log.d("payload", s);

    }

    /*public void run() {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytes = 0;
        int b = BUFFER_SIZE;

        while (true) {
            try {
                bytes = inStream.read(buffer, bytes, BUFFER_SIZE - bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    /*private void sendValues() {
        if(outputStream != null) {
            try {
                String parsed = payload.Strength + ";" + payload.Angle + ";" + payload.Fold + ";" + payload.Strafe + ";" + payload.Height + ";\n";
                write(parsed);
            } catch (Exception e) {
                text_state.setText("Disconnected");
                Toast.makeText(getApplicationContext(), "Failed sending data", Toast.LENGTH_SHORT).show();
                outputStream = null;
                e.printStackTrace();
            }
        }
    }*/

    /*@Override
    protected void onStart() {
        super.onStart();

        final long period = 50;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String message = payload.toString();
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                }
            }
        }, 0, period);
    }*/



    private PrintWriter output;
    private BufferedReader input;

    @Override
    protected void onStart() {
        super.onStart();

        final long period = 900;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                if(Thread1 == null) {
                    Thread1 = new Thread(new Thread1());
                    Thread1.start();
                }
                else {
                    String message = "i";
                    new Thread(new Thread3(message)).start();
                }
            }
        }, 0, period);
    }

    private void sendValues() {
        if(payload.equals(prevP)) {
            return;
        }

        String message = payload.toString();
        if (!message.isEmpty()) {
            new Thread(new Thread3(message)).start();
        }
        prevP.copy(payload);
    }

    class Thread1 implements Runnable {
        public void run() {
            Socket socket = null;
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text_state.setText("Connected");
                    }
                });
                new Thread(new Thread2()).start();
            } catch (IOException e) {
                Thread1 = null;
                text_state.setText("Disconnected");
                e.printStackTrace();
            }
        }
    }

    class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final String message = input.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //tvMessages.append("server: " + message + "\n");
                            }
                        });
                    } else {
                        Thread1 = new Thread(new Thread1());
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Thread3 implements Runnable {
        private String message;
        Thread3(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            if(output != null) {
                output.write(message);
                output.flush();
            }
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvMessages.append("client: " + message + "\n");
                    etMessage.setText("");
                }
            });*/
        }
    }
}