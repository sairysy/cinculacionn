package com.virtualcode7ecuador.puercos.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuador.puercos.POO.cCita;
import com.virtualcode7ecuador.puercos.R;
import com.virtualcode7ecuador.puercos.View_Event_DateTime.cHoraFechaViews_;
import com.virtualcode7ecuador.puercos.WebServices.cCitasService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.virtualcode7ecuador.puercos.LoginActivity.Ousuario;

public class AgendarCitaActivity extends AppCompatActivity implements View.OnClickListener {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    public static final int REQUEST_CODE_TAKE_PHOTO = 0 /*1*/;
    private String mCurrentPhotoPath;
    private TextView textInputEditText_fecha;
    private TextView textInputEditText_hora;
    private TextInputEditText textInputEditText_actividad;
    private TextInputEditText textInputEditText_detalle_cita;
    private Uri photoURI;
    private AppCompatImageView imageView_;
    private String[] REQUIRED_PERMISSONS = new String[]{"android.permission.CAMERA"
            ,"android.permission.WRITE_EXTERNAL_STORAGE"};
    private int REQUEST_CODE_PERMISSIONS = 101;
    private cHoraFechaViews_ OcHoraFechaViews;
    private cCitasService OcitasService;
    private Button button_agendar;
    private Button button_cancelar;
    private ProgressDialog progressDialog;
    private String string_img_url;
    private String string_doctor_;
    private String string_actividad;

    private String zona;
    private String provincia;
    private String distrito;
    private String unidad;
    private String especialidad;
    private String detalle;

    private String fecha;
    private String hora;


    private TextInputEditText textInputEditText_especialidad;
    private TextInputEditText textInputEditText_especialista;


    private TextInputEditText mTextInputEditTextZona;
    private TextInputEditText mTextInputEditTextProvincia;
    private TextInputEditText mTextInputEditTextDistrito;
    private TextInputEditText mTextInputEditTextUnidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        string_img_url = getIntent().getStringExtra("url_img");
        string_doctor_ = getIntent().getStringExtra("doctor");
        string_actividad = getIntent().getStringExtra("actividad");

        zona = getIntent().getStringExtra("zona");
        provincia = getIntent().getStringExtra("provincia");
        distrito = getIntent().getStringExtra("distrito");
        unidad = getIntent().getStringExtra("unidad");
        fecha = getIntent().getStringExtra("fecha");
        hora = getIntent().getStringExtra("hora");
        especialidad = getIntent().getStringExtra("especialidad");
        detalle = getIntent().getStringExtra("detalle");



        imageView_ = findViewById(R.id._imagen_view_agendar_cita_);
        textInputEditText_fecha = findViewById(R.id._id_fecha_cita);
        textInputEditText_especialidad = findViewById(R.id._id_especialidad_cita);
        textInputEditText_especialista = findViewById(R.id._id_especialista_cita);
        textInputEditText_hora = findViewById(R.id._id_hora_cita);
        textInputEditText_actividad = findViewById(R.id._id_actividad_cita);
        textInputEditText_detalle_cita = findViewById(R.id._id_detalle_cita);
        button_agendar = findViewById(R.id._id_btn_agendar_cita);
        button_cancelar = findViewById(R.id._id_btn_cancelar_cita);


        mTextInputEditTextZona = findViewById(R.id.id_edittext_zona);
        mTextInputEditTextProvincia = findViewById(R.id.id_edittext_provincia);
        mTextInputEditTextDistrito = findViewById(R.id.id_editext_distrito);
        mTextInputEditTextUnidad = findViewById(R.id.id_edittext_unidad);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        Picasso.with(this).load(string_img_url).error(R.drawable.error_image_load).into(imageView_);
        textInputEditText_actividad.setEnabled(false);
        textInputEditText_especialista.setEnabled(false);
        textInputEditText_actividad.setText(string_actividad);
        textInputEditText_especialista.setText(string_doctor_);
        mTextInputEditTextDistrito.setText(distrito);
        mTextInputEditTextProvincia.setText(provincia);
        mTextInputEditTextZona.setText(zona);
        mTextInputEditTextUnidad.setText(unidad);
        textInputEditText_hora.setText(hora);
        textInputEditText_fecha.setText(fecha);
        textInputEditText_detalle_cita.setText(detalle);
        textInputEditText_especialidad.setText(especialidad);

        /*OcHoraFechaViews = new cHoraFechaViews_(AgendarCitaActivity.this);
        OcHoraFechaViews.setDatePickerDialog(datePickerDialog);
        OcHoraFechaViews.setTimePickerDialog(timePickerDialog);
        OcHoraFechaViews.setTextInputEditText_fecha(textInputEditText_fecha);
        OcHoraFechaViews.setTextInputEditText_hora(textInputEditText_hora);*/






        /*textInputEditText_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OcHoraFechaViews.obtener_FechaPicker();
            }
        });
        textInputEditText_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OcHoraFechaViews.obtener_TimePicker();
            }
        });*/
        button_agendar.setOnClickListener(this);
        button_cancelar.setOnClickListener(this);
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        OcitasService = new cCitasService(this);
        imageView_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (allPermissionGranted()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //tomarFoto24();
                        dispatchTakePictureIntent();
                    } else {
                        //tomarFoto();
                        //dispatchTakePictureIntent();
                    }
                } else {
                    ActivityCompat.requestPermissions(AgendarCitaActivity.this, REQUIRED_PERMISSONS, REQUEST_CODE_PERMISSIONS);
                }
            }
        });
        super.onResume();
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                photoURI = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                //Uri photoURI = FileProvider.getUriForFile(AddActivity.this, "com.example.android.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {

            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                imageView_.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean allPermissionGranted() {
        for (String permission : REQUIRED_PERMISSONS)
        {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }
        return true;
    }

    private boolean verificarDatosLlenos()
    {
        if (!textInputEditText_fecha.getText().toString().isEmpty()
                &&!textInputEditText_hora.getText().toString().isEmpty()
                &&!textInputEditText_detalle_cita.getText().toString().isEmpty()
                &&!textInputEditText_actividad.getText().toString().isEmpty()
                &&!textInputEditText_especialidad.getText().toString().isEmpty()
                &&!textInputEditText_especialista.getText().toString().isEmpty())
        {
            return true;
        }else
        {
            return false;
        }
    }
    private void uploadFotoFirebase()
    {
        crearProgressDialogUploadFoto();
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a reference to "mountains.jpg"
        final StorageReference mountainsRef = storageRef.child((System.currentTimeMillis()/100)+".jpg");
        Bitmap bitmap = ((BitmapDrawable) imageView_.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                cerrarProgressDialog();
                if (taskSnapshot!=null) {
                    String path = "https://firebasestorage.googleapis.com" + taskSnapshot
                            .getUploadSessionUri().getPath() + mountainsRef.getPath() + "?alt=media&token=ff3e3e54-69b8-4a86-aef3-4d611e28f2f7";
                    Log.e("PATHIMG", path);
                    cCita oC = new cCita();
                    oC.setUrl_foto_cita(path);
                    oC.setFecha_cita(textInputEditText_fecha.getText().toString());
                    oC.setHora_cita(textInputEditText_hora.getText().toString());
                    oC.setActividad_cita(textInputEditText_actividad.getText().toString());
                    oC.setDetalle_cita(textInputEditText_detalle_cita.getText().toString());
                    oC.setDoctor(textInputEditText_especialista.getText().toString());
                    oC.setEspecialidad_(textInputEditText_especialidad.getText().toString());
                    oC.setZona(mTextInputEditTextZona.getText().toString());
                    oC.setProvincia(mTextInputEditTextProvincia.getText().toString());
                    oC.setDistrito(mTextInputEditTextDistrito.getText().toString());
                    oC.setUnidad(mTextInputEditTextUnidad.getText().toString());
                    OcitasService.AgendarSeguimiento(oC,Ousuario.getUsuario_());
                }else
                {
                    cerrarProgressDialog();
                    Toast.makeText(AgendarCitaActivity.this, "UploadTask Null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                cerrarProgressDialog();
                Toast.makeText(AgendarCitaActivity.this, "Error Firebase : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void cerrarProgressDialog()
    {
        progressDialog.cancel();
        progressDialog.dismiss();
    }
    private void crearProgressDialogUploadFoto()
    {
        progressDialog = new ProgressDialog(AgendarCitaActivity.this);
        progressDialog.setTitle("SUBIENDO FOTO");
        progressDialog.setMessage("Por favor espere");
        progressDialog.setIcon(R.drawable.logo_firebase);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id._id_btn_agendar_cita:
                if (verificarDatosLlenos())
                {
                    uploadFotoFirebase();
                }else
                {
                    Toast.makeText(this, "Existen Datos Vacios...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id._id_btn_cancelar_cita:
                finish();
                break;
        }
    }
}