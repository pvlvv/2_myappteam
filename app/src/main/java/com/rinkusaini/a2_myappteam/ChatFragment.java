package com.rinkusaini.a2_myappteam;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rinkusaini.a2_myappteam.msgcollection;

import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.internal.MagicApiIntrinsics;

public class ChatFragment extends Fragment {

    final String TAG = "ChatFragment";
    String KEY_MSG = "message";
    TextView sendername;
    TextView msg;
    Button send;
    FirebaseFirestore auth = FirebaseFirestore.getInstance();
    final DocumentReference msgref = auth.document("user/message");
    final CollectionReference userref = auth.collection("user");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        msg = v.findViewById(R.id.chat_msgbox);
        send = v.findViewById(R.id.chat_sendbtn);
        sendername = v.findViewById(R.id.name);
        msgcollection msgcollection = new msgcollection();



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                msgcollection.setName(sendername.getText().toString());
                sendmsg();
//                Bundle bundle = new Bundle();
//                bundle.putString("key",msg.getText().toString());
//
//                InboxFragment fragment = new InboxFragment();
//                fragment.setArguments(bundle);
//                getChildFragmentManager().beginTransaction().replace(R.id.chat,fragment).commit();
            }
        });

        return v;
    }

    public void sendmsg(){
        String message = msg.getText().toString();
        String name = sendername.getText().toString();
        msgcollection msgcollection = new msgcollection(message, name);

        userref.add(msgcollection).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(), "Sent", Toast.LENGTH_SHORT).show();
                getChildFragmentManager().beginTransaction().replace(R.id.chat, new InboxFragment()).commit();
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
