package com.example.osteo_app;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsuarioDAO {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    public interface UsuarioCallback {
        void onUsuarioLoaded(Usuario usuario);
        void onCancelled(DatabaseError databaseError);
    }

    public interface UsuarioSaveCallback {
        void onUsuarioSaved();
        void onCancelled(DatabaseError databaseError);
    }

    public UsuarioDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private String getUserId() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            // Tratar caso o usuário não esteja logado
            // Por enquanto, vamos usar um ID fixo para desenvolvimento
            // O ideal é implementar um fluxo de autenticação completo
            return "user_teste_id";
        }
    }

    public void inserirUsuario(String nome, int idade, Integer anoDiagnostico,
                               String celular, String genero, String comorbidades, UsuarioSaveCallback callback) {
        String userId = getUserId();
        Usuario usuario = new Usuario(userId, nome, idade, anoDiagnostico, celular, genero, comorbidades);

        databaseReference.child(userId).setValue(usuario)
            .addOnSuccessListener(aVoid -> callback.onUsuarioSaved())
            .addOnFailureListener(e -> callback.onCancelled(DatabaseError.fromException(e)));
    }

    public void getUsuario(UsuarioCallback callback) {
        String userId = getUserId();
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                callback.onUsuarioLoaded(usuario);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancelled(databaseError);
            }
        });
    }

    public void excluirTodosUsuarios(UsuarioSaveCallback callback) {
        String userId = getUserId();
        databaseReference.child(userId).removeValue()
            .addOnSuccessListener(aVoid -> callback.onUsuarioSaved())
            .addOnFailureListener(e -> callback.onCancelled(DatabaseError.fromException(e)));
    }
}
