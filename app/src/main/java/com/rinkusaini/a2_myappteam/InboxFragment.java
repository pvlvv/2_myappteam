package com.rinkusaini.a2_myappteam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import  com.rinkusaini.a2_myappteam.msgcollection;

public class InboxFragment extends Fragment {
    FirebaseFirestore auth = FirebaseFirestore.getInstance();
    final DocumentReference msgref = auth.document("user/message");
    final CollectionReference userref = auth.collection("user");
    ListenerRegistration stop;

    String KEY_MSG = "message";
    TextView message;
    String msg;
    Button load;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inbox, container, false);

        message = v.findViewById(R.id.msg);

        load = v.findViewById(R.id.loadbutton);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadmsg();
            }
        });

//        Bundle bundle = this.getArguments();
//         msg = bundle.getString("key");
//         message.setText(msg);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        userref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null){
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        String data = "";
                        for (QueryDocumentSnapshot documentSnapshot: value){
                            msgcollection msgcollection = documentSnapshot.toObject(com.rinkusaini.a2_myappteam.msgcollection.class);
                            msgcollection.setDocId(documentSnapshot.getId());

                            String documentId = msgcollection.getDocId();
                            String name = msgcollection.getName();
                            String msg = msgcollection.getMsg();

                            data += "-" + name + "\n" + msg + "\n\n";
                        }
                        message.setText(data);
                        Toast.makeText(getActivity(), "Loaded", Toast.LENGTH_SHORT).show();
                    }

            }
        });


//        stop = msgref
//                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(value.exists()){
//                    msgcollection msgcollection = value.toObject(msgcollection.class);
//                    String msg = msgcollection.getMsg();
//                    message.setText(msg);
//
////                    String msg = value.getString(KEY_MSG);
////                    message.setText(msg);
////                    Toast.makeText(getActivity(), "successful", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public void onStop() {
        super.onStop();
        stop.remove();
    }

    public void loadmsg(){
        userref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    msgcollection msgcollection = documentSnapshot.toObject(com.rinkusaini.a2_myappteam.msgcollection.class);
                    msgcollection.setDocId(documentSnapshot.getId());

                    String documentId = msgcollection.getDocId();
                    String msg = msgcollection.getMsg();
                    String name = msgcollection.getName();
                    data += "-" + name + "\n" + msg + "\n\n";
                }
                message.setText(data);
                Toast.makeText(getActivity(), "Loaded", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
