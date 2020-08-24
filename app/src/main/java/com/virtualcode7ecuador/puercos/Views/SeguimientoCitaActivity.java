package com.virtualcode7ecuador.puercos.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuador.puercos.R;
import com.virtualcode7ecuador.puercos.WebServices.cCitasService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.virtualcode7ecuador.puercos.Fragment.AgendarCitaFragment.REQUEST_CODE_TAKE_PHOTO;

public class SeguimientoCitaActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int PICK_IMAGE =505 ;
    private Uri photoURI;
    private String mCurrentPhotoPath;
    private boolean banfoto=false;
    private ProgressDialog progressDialog;
    private int _id_auto_cita;
    private String string_detalle_actividad;
    private String string_url_foto;
    private String string_fecha_cita;
    private String string_hora_cita;
    private String string_actividad;
    private String string_doctor;
    private String string_departamento;
    //private ImageView imageView_cita;
    private ImageView imageView_seguimiento_;
    private Button btn_no_asistido;
    private Button btn_asistido;
    private TextView textView_fecha;
    private TextView textView_hora;
    private TextView textView_detalle;
    private TextView textView_activida;
    private cCitasService OcitasService;
    private TextView textView_doctor;
    private TextView textView_departamento;
    private AlertDialog alertDialog;
    private String[] REQUIRED_PERMISSONS = new String[]{"android.permission.CAMERA"
            ,"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
    private int REQUEST_CODE_PERMISSIONS = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_cita);
        llenarDatosIntent();
        //imageView_cita = findViewById(R.id.id_imageview_cita___);
        imageView_seguimiento_ = findViewById(R.id.id_imageview_seguimiento_cita___);
        textView_fecha = findViewById(R.id.id_textview_fecha_inicio___);
        textView_hora = findViewById(R.id.id_textview_hora_inicio___);
        textView_detalle = findViewById(R.id.id_textview_detalle_inicio___);
        textView_activida = findViewById(R.id.id_textview_actividad_inicio___);
        textView_doctor = findViewById(R.id.id_textview_doctor_inicio___);
        textView_departamento =  findViewById(R.id.id_textview_departamento_inicio___);
        btn_asistido = findViewById(R.id.id_btn_falta_cita);
        btn_no_asistido = findViewById(R.id.id_btn_asistido_cita);
        OcitasService = new cCitasService(SeguimientoCitaActivity.this);
        //Picasso.with(SeguimientoCitaActivity.this).load(string_url_foto).error(R.drawable.error_image_load).into(imageView_cita);
        btn_asistido.setOnClickListener(this);
        btn_no_asistido.setOnClickListener(this);
        imageView_seguimiento_.setOnClickListener(this);
        llenarTextViewCitas();
    }
    private void llenarDatosIntent()
    {
        _id_auto_cita = getIntent().getIntExtra("id_auto_cita",-1);
        string_detalle_actividad=getIntent().getStringExtra("detalle");
        string_url_foto=getIntent().getStringExtra("url_img");
        string_fecha_cita=getIntent().getStringExtra("fecha");
        string_hora_cita=getIntent().getStringExtra("hora");
        string_actividad=getIntent().getStringExtra("actividad");
        string_doctor=getIntent().getStringExtra("doctor");
        string_departamento=getIntent().getStringExtra("departamento");
    }
    private void llenarTextViewCitas()
    {
        textView_fecha.setText(""+string_fecha_cita);
        textView_hora.setText(""+string_hora_cita);
        textView_detalle.setText(string_detalle_actividad);
        textView_activida.setText(string_actividad);
        textView_doctor.setText(string_doctor);
        textView_departamento.setText(string_departamento);
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_btn_asistido_cita:
                uploadFotoFirebase();
                OcitasService.deleteObjRecycler(_id_auto_cita);
                break;
            case R.id.id_btn_falta_cita:
                OcitasService.update_cita_seguimiento(_id_auto_cita,null,2,this);
                OcitasService.deleteObjRecycler(_id_auto_cita);
                break;
            case R.id.id_imageview_seguimiento_cita___:
                if (allPermissionGranted()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //tomarFoto24();
                        AlertDialog.Builder builder = new AlertDialog.Builder(SeguimientoCitaActivity.this);
                        builder.setTitle("Escoga una Opcion");
                        builder.setPositiveButton("Camara", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dispatchTakePictureIntent();
                            }
                        });
                        builder.setNegativeButton("Galeria", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                startActivityForResult(intent,PICK_IMAGE);
                            }
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                    } else {
                        //tomarFoto();
                        //dispatchTakePictureIntent();
                    }
                } else {
                    ActivityCompat.requestPermissions(SeguimientoCitaActivity.this, REQUIRED_PERMISSONS, REQUEST_CODE_PERMISSIONS);
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(SeguimientoCitaActivity.this.getContentResolver(), photoURI);
                imageView_seguimiento_.setImageBitmap(bitmap);
                banfoto=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {
            banfoto=true;
            Picasso.with(SeguimientoCitaActivity.this).load(data.getData()).error(R.drawable.error_image_load).into(imageView_seguimiento_);
        }
    }
    private boolean allPermissionGranted() {
        for (String permission : REQUIRED_PERMISSONS) {
            if (ContextCompat.checkSelfPermission(SeguimientoCitaActivity.this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    private void uploadFotoFirebase()
    {
        crearProgressDialogUploadFoto();
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a reference to "mountains.jpg"
        final StorageReference mountainsRef = storageRef.child((System.currentTimeMillis()/100)+".jpg");
        Bitmap bitmap = ((BitmapDrawable) imageView_seguimiento_.getDrawable()).getBitmap();
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
                    /****/
                    OcitasService.update_cita_seguimiento(_id_auto_cita,path,1,SeguimientoCitaActivity.this);
                }else
                {
                    cerrarProgressDialog();
                    Toast.makeText(SeguimientoCitaActivity.this, "UploadTask Null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                cerrarProgressDialog();
                Toast.makeText(SeguimientoCitaActivity.this, "Error Firebase : "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        progressDialog = new ProgressDialog(SeguimientoCitaActivity.this);
        progressDialog.setTitle("SUBIENDO FOTO");
        progressDialog.setMessage("Por favor espere");
        progressDialog.setIcon(R.drawable.logo_firebase);
        progressDialog.setCancelable(false);
        progressDialog.show();
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
}