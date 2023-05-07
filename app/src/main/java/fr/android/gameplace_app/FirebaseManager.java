package fr.android.gameplace_app;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FirebaseManager {

    // Contexte de l'application
    Context context;

    // Instance de la base de données Firestore
    FirebaseFirestore firestore;

    public FirebaseManager(FirebaseFirestore firestore, Context context) {
        this.firestore = firestore;
        this.context = context;
    }

    // Méthode pour envoyer des données à Firestore
    public void SendData(){
        // Créer un objet Map pour stocker les données à envoyer
        Map<String,Object> users = new HashMap<>();
        users.put("firstName","testnewarchitecture");
        users.put("lastName","testnewarchitecture");
        users.put("age",32);

        // Envoyer les données à Firestore
        firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // Si l'envoi réussit, afficher un message de succès
                Toast.makeText(context, "Envoie Reussi", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Si l'envoi échoue, afficher un message d'erreur
                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Méthode pour récupérer des données à partir d'une collection Firestore
    public void getData(String collectionName) {

        // Récupérer tous les documents de la collection spécifiée
        firestore.collection(collectionName).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Si la requête réussit, récupérer la liste des documents
                        List<DocumentSnapshot> documentList = queryDocumentSnapshots.getDocuments();

                        // Traiter les données
                        for (DocumentSnapshot document : documentList) {
                            Object data = document.getData();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Si la requête échoue, afficher une erreur
                        Log.e(TAG, "Erreur lors de la récupération des données: " + e);
                    }
                });
    }


}
