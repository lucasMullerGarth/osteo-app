package com.example.osteo_app;

import android.util.Pair;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PainAssessmentDAO {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    public interface PainAssessmentCallback {
        void onAssessmentsLoaded(List<Pair<String, Integer>> assessments);
        void onCancelled(DatabaseError databaseError);
    }

    public interface PainAssessmentSaveCallback {
        void onAssessmentSaved();
        void onCancelled(DatabaseError databaseError);
    }

    public PainAssessmentDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference("pain_assessments");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private String getUserId() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return "user_teste_id";
        }
    }

    public void inserirAvaliacao(String data, int nivelDor, PainAssessmentSaveCallback callback) {
        String userId = getUserId();
        String key = databaseReference.child(userId).push().getKey();
        PainAssessment assessment = new PainAssessment(key, data, nivelDor);

        databaseReference.child(userId).child(key).setValue(assessment)
            .addOnSuccessListener(aVoid -> callback.onAssessmentSaved())
            .addOnFailureListener(e -> callback.onCancelled(DatabaseError.fromException(e)));
    }

    public void getAllPainAssessments(PainAssessmentCallback callback) {
        String userId = getUserId();
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Pair<String, Integer>> assessments = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PainAssessment assessment = snapshot.getValue(PainAssessment.class);
                    if (assessment != null) {
                        assessments.add(new Pair<>(assessment.getDataRegistro(), assessment.getEscalaDor()));
                    }
                }
                callback.onAssessmentsLoaded(assessments);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancelled(databaseError);
            }
        });
    }
}
