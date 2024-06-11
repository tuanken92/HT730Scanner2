package com.example.ht730scanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    final static String[] symbologyName = {
            "",
            "Code 39",
            "Codabar",
            "Code 128",
            "D25",
            "IATA",
            "ITF",
            "Code 93",
            "UPCA",
            "UPCE",
            "EAN-8",
            "EAN-13",
            "Code 11",
            "",
            "MSI",
            "EAN-128",
            "UPCE1",
            "PDF-417",
            "",
            "Code 39 Full ASCII",
            "",
            "Trioptic",
            "Bookland",
            "Coupon Code",
            "",
            "ISBT-128",
            "Micro PDF",
            "Data Matrix",
            "QR Code",
            "",
            "Postnet (US)",
            "Planet (US)",
            "Code 32",
            "ISBT-128 Concat.",
            "Postal (Japan)",
            "Postal (Australia)",
            "Postal (Dutch)",
            "Maxicode",
            "Postbar (CA)",
            "Postal (UK)",
            "Macro PDF-417",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "RSS-14",
            "RSS Limited",
            "RSS Expanded",
            "Parameter (FNC3)",
            "",
            "",
            "",
            "Scanlet Webcode",
            "Cue CAT Code",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "UPCA + 2",
            "UPCE + 2",
            "EAN-8 + 2",
            "EAN-13 + 2",
            "",
            "",
            "",
            "",
            "UPCE1 + 2",
            "Composite(CC-A + EAN-128)",
            "Composite(CC-A + EAN-13)",
            "Composite(CC-A + EAN-8)",
            "Composite (CC-A +RSS Expanded)",
            "Composite (CC-A +RSS Limited)",
            "Composite(CC-A + RSS-14)",
            "Composite(CC-A + UPC-A)",
            "Composite(CC-A + UPC-E)",
            "Composite(CC-C + EAN-128)",
            "TLC-39",
            "",
            "",
            "",
            "",
            "",
            "",
            "Composite(CC-B + EAN-128)",
            "Composite(CC-B + EAN-13)",
            "Composite(CC-B + EAN-8)",
            "Composite (CC-B +RSS Expanded)",
            "Composite (CC-B +RSS Limited)",
            "Composite(CC-B + RSS-14)",
            "Composite(CC-B + UPC-A)",
            "Composite(CC-B + UPC-E)",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "UPCA + 5",
            "UPCE + 5",
            "EAN-8 + 5",
            "EAN-13 + 5",
            "",
            "",
            "",
            "",
            "UPCE1 + 5",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "Multipacket Format",
            "Macro Micro PDF"
    };

    final static String ACTION_START_SCANSERVICE = "unitech.scanservice.start";
    final static String ACTION_CLOSE_SCANSERVICE = "unitech.scanservice.close";
    final static String ACTION_SCAN2KEY_SETTING = "unitech.scanservice.scan2key_setting";
    final static String ACTION_INIT_INTENT = "unitech.scanservice.init";
    final static String ACTION_RECEIVE_DATA = "unitech.scanservice.data";
    final static String ACTION_RECEIVE_DATABYTES = "unitech.scanservice.databyte";
    final static String ACTION_RECEIVE_DATALENGTH = "unitech.scanservice.datalength";
    final static String ACTION_RECEIVE_DATATYPE = "unitech.scanservice.datatype";
    final static String ACTION_LOAD_SETTING = "unitech.scanservice.load_setting";
    final static String ACTION_SAVE_SETTING = "unitech.scanservice.save_setting";
    final static String ACTION_SET_PREAMBLE = "unitech.scanservice.preamble";
    final static String ACTION_SET_POSTAMBLE = "unitech.scanservice.postamble";
    final static String ACTION_SET_TERMINATOR = "unitech.scanservice.terminator";
    final static String ACTION_VIBRATION = "unitech.scanservice.vibration";
    final static String ACTION_SOUND = "unitech.scanservice.sound";
    final static String ACTION_FIELDSEPARATOR = "unitech.scanservice.fieldseparator";
    final static String ACTION_INTERCHAR_DELAY = "unitech.scanservice.interchar_delay";
    final static String ACTION_KEEPSCAN = "unitech.scanservice.keepscan";
    final static String ACTION_SHAKESCAN = "unitech.scanservice.shakescan";
    final static String ACTION_ENABLE_ALL = "unitech.scanservice.enable_all";
    final static String ACTION_DISABLE_ALL = "unitech.scanservice.disable_all";
    final static String ACTION_NFC_ENABLE = "unitech.scanservice.nfcenable";
    final static String ACTION_NFC_ORDER = "unitech.scanservice.nfcorder";
    final static String ACTION_NFC_IGNORE_RATE = "unitech.scanservice.nfcignorerate";
    final static String ACTION_SETTING = "unitech.scanservice.setting";
    final static String ACTION_SOFTWARE_SCANKEY = "unitech.scanservice.software_scankey";

    final static int CMD_START_SCANSERVICE = 1;             // 1.8
    final static int CMD_CLOSE_SCANSERVICE = 2;             // 1.7
    final static int CMD_OUTPUTMODE_INTENT = 3;             // 1.2
    final static int CMD_OUTPUTMODE_SCAN2KEY = 4;           // 1.1
    final static int CMD_LOAD_SCANNER_SETTINGS = 5;         // 1.6
    final static int CMD_SAVE_SCANNER_SETTINGS = 6;         // 1.5
    final static int CMD_SET_PREAMBLE = 7;                  // 1.9
    final static int CMD_SET_POSTAMBLE = 8;                 // 1.10
    final static int CMD_SET_TERMINATOR = 9;                // 1.11
    final static int CMD_SET_VIBRATION = 10;                // 1.12
    final static int CMD_SET_SOUND = 11;                    // 1.13
    final static int CMD_SET_EAN128_FIELD_SEPARATOR = 12;   // 1.14
    final static int CMD_SET_INTERCHARACTER_DELAY = 13;     // 1.15, 1.17
    final static int CMD_SET_KEEP_SCAN_TIMEOUT = 14;        // 1.16
    final static int CMD_SET_SHAKE_SCAN = 15;               // 1.18
    final static int CMD_ENABLE_ALL_SYMBOLOGIES = 16;       // 1.19
    final static int CMD_DISABLE_ALL_SYMBOLOGIES = 17;      // 1.20
    final static int CMD_ENABLE_DISABLE_NFC_READ = 18;      // 1.21
    final static int CMD_NFC_OUTPUT_ORDER = 19;             // 1.22
    final static int CMD_NFC_IGNORE_RATE = 20;              // 1.23
    final static int CMD_SEND_PARAM_COMMAND = 21;           // 1.24

    final static int PARAMTYPE_UNKNOWN = -1;
    final static int PARAMTYPE_NONE = 0;
    final static int PARAMTYPE_BOOLEAN = 1;
    final static int PARAMTYPE_INTEGER = 2;
    final static int PARAMTYPE_STRING = 3;
    final static int PARAMTYPE_SETTING = 4;

    private static int[] paramTypeArray = {
            -1,                                 // CMD_NONE
            0, 0, 1, 1, 3, 3, 3, 3,
            3, 1, 1, 3, 2, 2, 1, 0,
            0, 1, 1, 2, 4
    };

    private Spinner cbCommands;
    private TextView lblParameter_1;
    private Spinner cbParameter_1;
    private TextView lblParameter_2;
    private Spinner cbParameter_2;
    private TextView lblParameter_3;
    private Spinner cbParameter_3;
    private TextView lblParamValue;
    private EditText txtParamValue;
    private Button btnExecute;
    private TextView txtConsole;
    private TextView txtIntentData;
    private EditText txtScanToKeyData;

    private Vibrator mVibrator;
    private SoundPool soundpool = null;
    private int soundid;

    private String command;                     // cbCommands.getSelectedItem().toString()
    private int cmd;                            // cbCommands.getSelectedItemPosition()
    private int paramType = PARAMTYPE_UNKNOWN;

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            txtIntentData.append(action+"\n");
            Bundle bundle = intent.getExtras();
            if  (bundle == null ) return;
            switch (action) {
                case ACTION_RECEIVE_DATA:
                    soundpool.play(soundid, 1, 1, 0, 0, 1);
                    mVibrator.vibrate(100);
                    String barcodeStr = bundle.getString("text" );
                    txtIntentData.append(barcodeStr+"\n");
                    break;
                case ACTION_RECEIVE_DATABYTES:
                    byte[] barcodeBytes = bundle.getByteArray("text" );
                    Formatter hex = new Formatter();
                    for (byte b : barcodeBytes) {
                        hex.format("%02X ", b);
                    }
                    txtIntentData.append(hex.toString()+"\n");
                    break;
                case ACTION_RECEIVE_DATALENGTH:
                    int dataLength = bundle.getInt("text" );
                    txtIntentData.append(dataLength+"\n");
                    break;
                case ACTION_RECEIVE_DATATYPE:
                    int barcodeType = bundle.getInt("text" );
                    txtIntentData.append(symbologyName[barcodeType]+"\n");
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setupView();
    }

    private void setupView() {

        cbCommands = (Spinner) findViewById(R.id.cbCommands);
        fillCommands();

        lblParameter_1 = (TextView) findViewById(R.id.lblParameter_1);
        lblParameter_1.setVisibility(View.GONE);

        cbParameter_1 = (Spinner) findViewById(R.id.cbParameter_1);
        cbParameter_1.setVisibility(View.GONE);

        lblParameter_2 = (TextView) findViewById(R.id.lblParameter_2);
        lblParameter_2.setVisibility(View.GONE);

        cbParameter_2 = (Spinner) findViewById(R.id.cbParameter_2);
        cbParameter_2.setVisibility(View.GONE);

        lblParameter_3 = (TextView) findViewById(R.id.lblParameter_3);
        lblParameter_3.setVisibility(View.GONE);

        cbParameter_3 = (Spinner) findViewById(R.id.cbParameter_3);
        cbParameter_3.setVisibility(View.GONE);

        lblParamValue = (TextView) findViewById(R.id.lblParamValue);
        lblParamValue.setVisibility(View.GONE);

        txtParamValue = (EditText) findViewById(R.id.txtParamValue);
        txtParamValue.setVisibility(View.GONE);

        btnExecute = (Button) findViewById(R.id.btnExecute);
        btnExecute.setOnClickListener(this);
        btnExecute.setVisibility(View.GONE);

        txtConsole = (TextView) findViewById(R.id.txtConsole);
        txtConsole.setMovementMethod(new ScrollingMovementMethod());

        txtIntentData = (TextView) findViewById(R.id.txtIntentData);
        txtIntentData.setMovementMethod(new ScrollingMovementMethod());

        txtScanToKeyData = (EditText) findViewById(R.id.txtScan2KeyData);
        txtScanToKeyData.setMovementMethod(new ScrollingMovementMethod());

        Button btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
    }


    @SuppressWarnings("deprecation")
    private void initScan() {
        soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        soundid = soundpool.load("/etc/Scan_new.ogg", 1);
    }

    private void fillCommands() {

        String[] items = new String[]{
                "",
                "enable_scanservice",
                "close_scanservice",
                "outputmode_intent",
                "outputmode_scan2key",
                "load_scanner_settings",
                "save_scanner_settings",
                "set_preamble",
                "set_postamble",
                "set_terminator",
                "set_vibration",
                "set_sound",
                "set_EAN128_field_separator",
                "set_intercharacter_delay",
                "set_keep_scan_timeout",
                "set_shake_scan",
                "enable_all_symbologies",
                "disable_all_symbologies",
                "enable_nfc_read",
                "NFC_output_order",
                "NFC_ignore_rate",
                "send_param_command"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);

        cbCommands.setAdapter(adapter);
        cbCommands.setOnItemSelectedListener(this);
    }

    private String fullCommandToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(command);
        sb.append("(");

        switch (cmd) {
            case CMD_START_SCANSERVICE:
            case CMD_CLOSE_SCANSERVICE:
            case CMD_ENABLE_ALL_SYMBOLOGIES:
            case CMD_DISABLE_ALL_SYMBOLOGIES:
                break;

            case CMD_OUTPUTMODE_INTENT:
            case CMD_OUTPUTMODE_SCAN2KEY:
            case CMD_SET_VIBRATION:
            case CMD_SET_SOUND:
            case CMD_SET_SHAKE_SCAN:
            case CMD_ENABLE_DISABLE_NFC_READ:
                sb.append(cbParameter_1.getSelectedItemPosition() == 0 ? "true" : "false");
                break;

            case CMD_NFC_OUTPUT_ORDER:
                sb.append(cbParameter_1.getSelectedItemPosition() == 0 ? "Normal" : "Reverse");
                break;

            case CMD_SET_INTERCHARACTER_DELAY:
            case CMD_SET_KEEP_SCAN_TIMEOUT:
            case CMD_NFC_IGNORE_RATE:
                sb.append(Integer.valueOf(txtParamValue.getText().toString()));
                break;

            case CMD_LOAD_SCANNER_SETTINGS:
            case CMD_SAVE_SCANNER_SETTINGS:
            case CMD_SET_PREAMBLE:
            case CMD_SET_POSTAMBLE:
            case CMD_SET_TERMINATOR:
            case CMD_SET_EAN128_FIELD_SEPARATOR:
                sb.append("\"");
                sb.append(txtParamValue.getText());
                sb.append("\"");
                break;

            case CMD_SEND_PARAM_COMMAND:
                ValueTextPair parameter;
                parameter = (ValueTextPair) cbParameter_2.getSelectedItem();
                sb.append(parameter.getText());
                sb.append(", ");
                parameter = (ValueTextPair) cbParameter_3.getSelectedItem();
                sb.append(parameter.getText());
                break;
        }
        sb.append(")");
        return sb.toString();
    }

    void Log(String s) {
        if (s.length() == 0)
            return;

        txtConsole.append(s);
        try {
            File logfile = new File(Environment.getExternalStorageDirectory(),
                    "ScannerTest.txt");
            FileWriter writer = new FileWriter(logfile, true);
            writer.append(s);
            writer.flush();
            writer.close();
            MediaScannerConnection.scanFile(this,
                    new String[]{ logfile.getAbsolutePath() }, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {

        switch (parent.getId()) {
            case R.id.cbCommands:

                command = cbCommands.getSelectedItem().toString();
                cmd = pos;
                paramType = paramTypeArray[pos];

                lblParameter_1.setVisibility(View.GONE);
                cbParameter_1.setVisibility(View.GONE);
                lblParameter_2.setVisibility(View.GONE);
                cbParameter_2.setVisibility(View.GONE);
                lblParameter_3.setVisibility(View.GONE);
                cbParameter_3.setVisibility(View.GONE);
                lblParamValue.setVisibility(View.GONE);
                txtParamValue.setVisibility(View.GONE);
                btnExecute.setVisibility(View.GONE);

                if (paramType == PARAMTYPE_UNKNOWN)
                    return;

                if (paramType != PARAMTYPE_BOOLEAN && paramType != PARAMTYPE_SETTING) {
                    btnExecute.setVisibility(View.VISIBLE);
                }

                if (paramType == PARAMTYPE_NONE)
                    return;

                String[] items = null;

                // Fill cbParameters
                switch (paramType) {
                    case PARAMTYPE_BOOLEAN:
                        switch (pos) {
                            case CMD_NFC_OUTPUT_ORDER:
                                items = new String[]{"Normal", "Reverse"};
                                break;
                            default:
                                items = new String[]{"true", "false"};
                                break;
                        }
                        lblParameter_1.setText(R.string.select_a_parameter);
                        lblParameter_1.setVisibility(View.VISIBLE);
                        cbParameter_1.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
                        cbParameter_1.setAdapter(adapter);
                        cbParameter_1.setOnItemSelectedListener(this);
                        break;

                    case PARAMTYPE_INTEGER:
                        switch (pos) {
                            case CMD_SET_INTERCHARACTER_DELAY:
                                break;
                            case CMD_SET_KEEP_SCAN_TIMEOUT:
                                break;
                            case CMD_NFC_IGNORE_RATE:
                                break;
                        }
                        break;

                    case PARAMTYPE_STRING:
                        switch (pos) {
                            case CMD_LOAD_SCANNER_SETTINGS:
                            case CMD_SAVE_SCANNER_SETTINGS:
                                lblParamValue.setText(R.string.settings_file_location);
                                String str = txtParamValue.getText().toString();
                                if (str == null || str.isEmpty()) {
                                    txtParamValue.setText(R.string.default_settings_file_location);
                                }
                                break;
                            case CMD_SET_PREAMBLE:
                                lblParamValue.setText(R.string.preamble);
                                txtParamValue.setText("");
                                break;
                            case CMD_SET_POSTAMBLE:
                                lblParamValue.setText(R.string.postamble);
                                txtParamValue.setText("");
                                break;
                            case CMD_SET_TERMINATOR:
                                lblParamValue.setText(R.string.terminator);
                                txtParamValue.setText(R.string.default_terminator);
                                break;
                        }
                        lblParamValue.setVisibility(View.VISIBLE);
                        txtParamValue.setVisibility(View.VISIBLE);
                        break;

                    case PARAMTYPE_SETTING:
                        items = new String[]{
                                "",
                                "UPC_EAN",
                                "Code_128",
                                "Code_39",
                                "Code_93",
                                "Code_11",
                                "I2of5",
                                "D2of5",
                                "C2of5",
                                "M2of5",
                                "Codabar",
                                "MSI",
                                "GS1_DataBar",
                                "Postal_Codes",
                                "Composite",
                                "Symbologies_2D",
                                "Data_Options",
                                "Serial_Parameters",
                                "Scanner_Options"
                        };
                        lblParameter_1.setText(R.string.select_a_parameter_group);
                        lblParameter_1.setVisibility(View.VISIBLE);
                        cbParameter_1.setVisibility(View.VISIBLE);
                        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

                        cbParameter_1.setAdapter(adapter);
                        cbParameter_1.setOnItemSelectedListener(this);
                        break;
                }
                break;
            case R.id.cbParameter_1:
                if (cmd == CMD_SEND_PARAM_COMMAND) {
                    if (cbParameter_1.getSelectedItemPosition() > 0) {
                        String groupName = cbParameter_1.getSelectedItem().toString();
                        int resourceId = getResources().getIdentifier(groupName, "array", getPackageName());
                        String[] parameterString = getResources().getStringArray(resourceId);
                        int count = parameterString.length;
                        ArrayList<ValueTextPair> parameter = new ArrayList<>();
                        parameter.add(new ValueTextPair(0,""));
                        try {
                            JSONObject jData = null;
                            int hex;
                            String name;
                            for (int i = 0; i < count; i++) {
                                jData = new JSONObject(parameterString[i]);
                                hex = jData.getInt("Hex");
                                name = jData.getString("Parameter");
                                parameter.add(new ValueTextPair(hex, name));
                            }
                        } catch (JSONException e) {
                            txtConsole.setText(e.toString());
                        }
                        lblParameter_2.setText(R.string.select_a_parameter);
                        lblParameter_2.setVisibility(View.VISIBLE);
                        cbParameter_2.setVisibility(View.VISIBLE);
                        ArrayAdapter<ValueTextPair> adapter =
                                new ArrayAdapter<ValueTextPair>(this, android.R.layout.simple_list_item_1, parameter);
                        cbParameter_2.setAdapter(adapter);
                        cbParameter_2.setOnItemSelectedListener(this);
                    }
                } else {
                    btnExecute.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.cbParameter_2:
                if (cmd == CMD_SEND_PARAM_COMMAND) {
                    ValueTextPair parameter = (ValueTextPair) cbParameter_2.getSelectedItem();
                    String text = parameter.getText();
                    if (text != null && !text.isEmpty()) {

                        // Temporary, to win space. Will be restored after Execute.
                        txtIntentData.setVisibility(View.GONE);

                        lblParameter_3.setText(R.string.select_a_value);
                        lblParameter_3.setVisibility(View.VISIBLE);
                        cbParameter_3.setVisibility(View.VISIBLE);
                        String groupName = cbParameter_1.getSelectedItem().toString();
                        int resourceId = getResources().getIdentifier(groupName, "array", getPackageName());
                        String[] group = getResources().getStringArray(resourceId);
                        String parameterName = cbParameter_2.getSelectedItem().toString();
                        JSONObject jData = null;
                        try {

                            for (int i = 0; i < group.length; i++) {
                                jData = new JSONObject(group[i]);
                                if (jData.getString("Parameter").equals((parameterName))) break;
                            }
                            int type = jData.getInt("Type");
                            int min = jData.getInt("Min");
                            int max = jData.getInt("Max");

                            ArrayList<ValueTextPair> values = new ArrayList<>();

                            if (type == 1) {
                                values.add(new ValueTextPair(0, "Disable"));
                                values.add(new ValueTextPair(1, "Enable"));
                            } else if (type == 2) {
                                // Array type with different name for each option
                                JSONObject jData2;
                                jData2 = jData.getJSONObject("Tag");
                                items = new String[max - min];
                                for (int i = min; i <= max; i++) {
                                    String tag = jData2.getString(Integer.toString(i));
                                    if (tag != "") {
                                        values.add(new ValueTextPair(i, jData2.getString(Integer.toString(i))));
                                    }
                                }
                            } else if (type == 3) {
                                // Array of a range of numbers
                                for (int i = min; i <= max; i++) {
                                    values.add(new ValueTextPair(i, Integer.toString(i)));
                                }
                            }

                            ArrayAdapter<ValueTextPair> adapter =
                                    new ArrayAdapter<ValueTextPair>(this, android.R.layout.simple_list_item_1, values);
                            cbParameter_3.setAdapter(adapter);
                            cbParameter_3.setOnItemSelectedListener(this);

                            btnExecute.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            txtConsole.setText(e.toString());
                        }
                    }
                }
                break;
        }
    }

    public void onClick(View view) {

        // **** CLEAR CLICKED ****

        if (view.getId() == R.id.btnClear) {
            Log(txtConsole.getText().toString());
            txtConsole.setText("");
            txtIntentData.setText("");
            txtScanToKeyData.setText("");
            return;
        }
// _____________________________________________________________

        // **** EXECUTE clicked ****

        txtConsole.append("> "+fullCommandToString()+";\n");

        try {
            Bundle bundle = new Bundle();
            Intent intent;
            String action = "";
            boolean b = false;
            Integer n = -1;
            String s = "";

            // Get parameter
            switch(cmd) {
                case CMD_START_SCANSERVICE:
                case CMD_CLOSE_SCANSERVICE:
                case CMD_ENABLE_ALL_SYMBOLOGIES:
                case CMD_DISABLE_ALL_SYMBOLOGIES:
                    break;
                case CMD_OUTPUTMODE_SCAN2KEY:
                case CMD_OUTPUTMODE_INTENT:
                case CMD_SET_VIBRATION:
                case CMD_SET_SOUND:
                case CMD_SET_SHAKE_SCAN:
                case CMD_ENABLE_DISABLE_NFC_READ:
                case CMD_NFC_OUTPUT_ORDER:
                    b = Boolean.valueOf(cbParameter_1.getSelectedItem().toString());
                    break;
                case CMD_SET_INTERCHARACTER_DELAY:
                case CMD_SET_KEEP_SCAN_TIMEOUT:
                case CMD_NFC_IGNORE_RATE:
                    n = Integer.valueOf(cbParameter_1.getSelectedItem().toString());
                    break;
                case CMD_LOAD_SCANNER_SETTINGS:
                case CMD_SAVE_SCANNER_SETTINGS:
                case CMD_SET_PREAMBLE:
                case CMD_SET_POSTAMBLE:
                case CMD_SET_TERMINATOR:
                case CMD_SET_EAN128_FIELD_SEPARATOR:
                    s = txtParamValue.getText().toString();
            }

            // Create bundle and action
            switch (cmd) {
                case CMD_START_SCANSERVICE:
                    bundle.putBoolean("close", true);
                    action = ACTION_START_SCANSERVICE;
                    break;
                case CMD_CLOSE_SCANSERVICE:
                    bundle.putBoolean("close", true);
                    action = ACTION_CLOSE_SCANSERVICE;
                    break;
                case CMD_OUTPUTMODE_SCAN2KEY:
                    bundle.putBoolean("scan2key", b);
                    action = ACTION_SCAN2KEY_SETTING;
                    break;
                case CMD_OUTPUTMODE_INTENT:
                    bundle.putBoolean("enable", b);
                    action = ACTION_INIT_INTENT;
                    break;
                case CMD_LOAD_SCANNER_SETTINGS:
                    bundle.putString("Path", s);
                    action = ACTION_LOAD_SETTING;
                    break;
                case CMD_SAVE_SCANNER_SETTINGS:
                    bundle.putString("Path", s);
                    action = ACTION_SAVE_SETTING;
                    break;
                case CMD_SET_PREAMBLE:
                    bundle.putString("preamble", s);
                    action = ACTION_SET_PREAMBLE;
                    break;
                case CMD_SET_POSTAMBLE:
                    bundle.putString("postamble", s);
                    action = ACTION_SET_POSTAMBLE;
                    break;
                case CMD_SET_TERMINATOR:
                    bundle.putString("terminator", s);
                    action = ACTION_SET_TERMINATOR;
                    break;
                case CMD_SET_VIBRATION:
                    bundle.putBoolean("vibration", b);
                    action = ACTION_VIBRATION;
                    break;
                case CMD_SET_SOUND:
                    bundle.putBoolean("sound", b);
                    action = ACTION_SOUND;
                    break;
                case CMD_SET_EAN128_FIELD_SEPARATOR:
                    bundle.putString("fieldseparator", s);
                    action = ACTION_FIELDSEPARATOR;
                    break;
                case CMD_SET_INTERCHARACTER_DELAY:
                    bundle.putInt("intercharDelay", n);
                    action = ACTION_INTERCHAR_DELAY;
                    break;
                case CMD_SET_KEEP_SCAN_TIMEOUT:
                    bundle.putInt("keepscan", n);
                    action = ACTION_KEEPSCAN;
                    break;
                case CMD_SET_SHAKE_SCAN:
                    bundle.putBoolean("enable", b);
                    action = ACTION_SHAKESCAN;
                    break;
                case CMD_ENABLE_ALL_SYMBOLOGIES:
                    action = ACTION_ENABLE_ALL;
                    break;
                case CMD_DISABLE_ALL_SYMBOLOGIES:
                    action = ACTION_DISABLE_ALL;
                    break;
                case CMD_ENABLE_DISABLE_NFC_READ:
                    bundle.putBoolean("nfcenable", b);
                    action = ACTION_NFC_ENABLE;
                    break;
                case CMD_NFC_OUTPUT_ORDER:
                    bundle.putBoolean("nfcorder", b);
                    action = ACTION_NFC_ORDER;
                    break;
                case CMD_NFC_IGNORE_RATE:
                    bundle.putInt("nfcignorerate", n);
                    action = ACTION_NFC_IGNORE_RATE;
                    break;
                case CMD_SEND_PARAM_COMMAND:
                    String groupName = cbParameter_1.getSelectedItem().toString();
                    int resourceId = getResources().getIdentifier(groupName, "array", getPackageName());
                    String[] group = getResources().getStringArray(resourceId);

                    ValueTextPair parameter = (ValueTextPair) cbParameter_2.getSelectedItem();
                    int hex = parameter.getValue() & 0xFFFF;

                    // Extended data, default values
                    int INDEX = 2;                      // ScannerType. Assume 2D here.
                    boolean EXTEND;
                    byte EXT = (byte) 0x00;
                    byte NUM = (byte) 0x00;
                    byte VALUE = (byte) 0x00;

                    // Determine the value of the extended data
                    EXTEND = hex > 0xEF;
                    if (EXTEND) {
                        NUM = (byte)(hex & 0xFF);
                        EXT = (byte)(hex >> 8);
                    } else {
                        NUM = (byte)hex;
                    }

                    ValueTextPair selectedItem = (ValueTextPair) cbParameter_3.getSelectedItem();
                    VALUE = (byte) selectedItem.getValue();

                    bundle.putInt("INDEX", INDEX);
                    bundle.putBoolean("EXTEND", EXTEND);
                    bundle.putByte("EXT", EXT);
                    bundle.putByte("NUM", NUM);
                    bundle.putByte("VALUE", VALUE);
                    action = ACTION_SETTING;

                    lblParameter_2.setVisibility(View.GONE);
                    cbParameter_2.setVisibility(View.GONE);
                    lblParameter_3.setVisibility(View.GONE);
                    cbParameter_3.setVisibility(View.GONE);
                    txtIntentData.setVisibility(View.VISIBLE);

                    break;
            }
            intent = new Intent().setAction(action).putExtras(bundle);
            sendBroadcast(intent);

            btnExecute.setVisibility(View.GONE);

        } catch (Exception e) {
            txtConsole.append("EXCEPTION:"+e.toString());
        } catch (Error e) {
            txtConsole.append("ERROR:"+e.toString());
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log(txtConsole.getText().toString());
        txtConsole.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mScanReceiver);
        Log(txtConsole.getText().toString());
        txtConsole.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initScan();
        txtIntentData.setText("");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_RECEIVE_DATA);
        filter.addAction(ACTION_RECEIVE_DATABYTES);
        filter.addAction(ACTION_RECEIVE_DATALENGTH);
        filter.addAction(ACTION_RECEIVE_DATATYPE);
        registerReceiver(mScanReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}