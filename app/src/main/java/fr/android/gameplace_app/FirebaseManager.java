package fr.android.gameplace_app;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class FirebaseManager {

    Context context;
    FirebaseFirestore firestore;
    public FirebaseManager(FirebaseFirestore firestore, Context context) {
        this.firestore = firestore;
        this.context = context;

    }

        public void SendData(){
            Map<String,Object> users = new HashMap<>();
            users.put("firstName","testnewarchitecture");
            users.put("lastName","testnewarchitecture");
            users.put("age",32);

            firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(context, "Envoie Reussi", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }

//        public void getData(){
//            firestore.collection("users").get()
//
//        }

}
