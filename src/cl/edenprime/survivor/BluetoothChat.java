/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cl.edenprime.survivor;

import java.io.PrintWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import cl.edenprime.survivor.modelo.Clase;
import cl.edenprime.survivor.modelo.Contacto;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

import com.google.gson.Gson;

/**
 * This is the main Activity that displays the current chat session.
 */
public class BluetoothChat extends Activity {
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private ListView mConversationView;
    //private EditText mOutEditText;
    private Button mSendButton;
    Gson gson = new Gson();
    PrintWriter out;
    ArrayList<Clase> clasesRecibidas;
    
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;

    
    private Context context;
    private String nombre;
    private String data;
    // private int cont;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");

        // Set up the window layout
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.bluetooth_main);
        context = this;
        

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
//        if (!dataS.getServiciosDatos().obtenerContactos().isEmpty()){
        	//HorarioList();
//        }

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth no esta habilitado", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");
        
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");
        gson = new Gson();
        
        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mConversationView = (ListView) findViewById(R.id.inBT);
        mConversationView.setAdapter(mConversationArrayAdapter);
        mConversationView.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> parent, View view,
        		final int position, long id){
        		 //Toast.makeText(getApplicationContext(), dataS.getServiciosDatos().obtenerContactos().get(position).getNombre(), Toast.LENGTH_SHORT).show();
        			timetable(position);
        	}
        });
        if (!dataS.getServiciosDatos().obtenerContactos().isEmpty()){
        	HorarioList();
        }
        // Initialize the compose field with a listener for the return key
       // mOutEditText = (EditText) findViewById(R.id.edit_text_out);
       // mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
            	if (dataS.getClases().size()==0){
            		Toast.makeText(getApplicationContext(),"Ud no tiene un horario creado para enviar", Toast.LENGTH_SHORT).show();
            	}else{
                sendMessage(gson.toJson(dataS.getClases()));

            	}
            }
        });

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
           // mOutEditText.setText(mOutStringBuffer);
        }
    }

    // The action listener for the EditText widget, to listen for the return key
    /*
    private TextView.OnEditorActionListener mWriteListener =
        new TextView.OnEditorActionListener() {
        @Override
		public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            if(D) Log.i(TAG, "END onEditorAction");
            return true;
        }
    };
    */

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
    	
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                	Toast.makeText( getApplicationContext(), "Conectado", Toast.LENGTH_SHORT).show();
//                    mTitle.setText("title_connected_to");
//                    mTitle.append(mConnectedDeviceName);
                    //mConversationArrayAdapter.clear();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
//                    mTitle.setText("title_connecting");
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    break;
                }
                break;
            case MESSAGE_WRITE:
                // byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                // String writeMessage = new String(writeBuf);
                //mConversationArrayAdapter.add("Me:  " + writeMessage);
//                Toast.makeText(getApplicationContext(), "Horario Enviado a "
//                        + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_READ:
            	//Type collectionType = new TypeToken<ArrayList<Clase>>(){}.getType();
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                //mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                
                data = readMessage;
                nombre = "";
                
                nameReq(data);
                //mConversationArrayAdapter.add(dataS.getServiciosDatos().obtenerContactos().get(0).getNombre()+":  " + dataS.getServiciosDatos().obtenerContactos().get(0).getData());
               
                
                
                 //clasesRecibidas = gson.fromJson(readMessage, collectionType );
                // Toast.makeText(getApplicationContext(), clasesRecibidas.get(0).getSala(), Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Conectado con "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mChatService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT no esta habilitado");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.scanbt:
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        return false;
    }
    */
    
    public void dataSend(String data) {
//			out.println(dataS.getClases());
		
	}
    
    public void nameReq(final String data2) {
    	final EditText input = new EditText(context);
    	// cont=0;
		input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder
			.setTitle(R.string.horarioGrade)
			.setView(input)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
		        @Override
				public void onClick(DialogInterface dialog, int whichButton) {
		            nombre = input.getText().toString().trim();
		            dataS.getServiciosDatos().agregarContacto(new Contacto(-1, nombre, data2));
		            HorarioList();
		           // mConversationArrayAdapter.add(dataS.getServiciosDatos().obtenerContactos().get(0).getNombre()+":  " + dataS.getServiciosDatos().obtenerContactos().get(0).getData()+"numero de contactos: "+cont);
		        }
		    });
		
		AlertDialog dialog = builder.create();
		dialog.show();
    }
    
    public void HorarioList(){
    	int k;
    	mConversationArrayAdapter.clear();
    	for (k=0;k<dataS.getServiciosDatos().obtenerContactos().size();k++){
    		//mConversationArrayAdapter.add(dataS.getServiciosDatos().obtenerContactos().get(k).getNombre()+":  " + dataS.getServiciosDatos().obtenerContactos().get(k).getData()+"numero de contacto: "+k);
    		mConversationArrayAdapter.add(dataS.getServiciosDatos().obtenerContactos().get(k).getNombre());
    	}
    	
    }
    
    public void BluetoothOpt(View V){
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		new AlertDialog.Builder(this)
		.setTitle("Opciones de Bluetooth")
		.setItems(R.array.bluetoothOption,
		new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialoginterface,int opt) {
			switch (opt){
				case 0:	{
					// Launch the DeviceListActivity to see devices and do scan
		            Intent serverIntent = new Intent(getApplicationContext(), DeviceListActivity.class);
		            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
				}		
				break;
				case 1: {
					// Ensure this device is discoverable by others
		            ensureDiscoverable();
				}
			}
		}
		})
		.show();		
	}
    
    public void launchTimeTable(View v) {
		Intent intent = new Intent(this, TimeActivity.class);
		this.startActivity(intent);
	}
    public void timetable(final int pos){
	    new AlertDialog.Builder(this)
		.setTitle("Ver")
		.setItems(R.array.timetableOption,
		new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialoginterface,int opt) {
			switch (opt){
				case 0:	{
					Intent i = new Intent(getApplicationContext(), TimeActivityExtern.class );
	                i.putExtra("data",dataS.getServiciosDatos().obtenerContactos().get(pos).getData());
	                i.putExtra("horarioName", dataS.getServiciosDatos().obtenerContactos().get(pos).getNombre());
	                startActivity(i);
				}		
				break;
				case 1: {
					Intent i = new Intent(getApplicationContext(), TimeActivityCombined.class );
	                i.putExtra("data",dataS.getServiciosDatos().obtenerContactos().get(pos).getData());
	                i.putExtra("horarioName", dataS.getServiciosDatos().obtenerContactos().get(pos).getNombre());
	                startActivity(i);
				}
			}
		}
		})
		.show();
    }
    
    	
}