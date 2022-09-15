package com.rinkusaini.a2_myappteam;

import com.google.firebase.firestore.Exclude;

public class msgcollection {
    String msg;
    String docId;
    String name;


    @Exclude
    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public msgcollection(){}

    public msgcollection(String msg, String name){
        this.msg = msg;
        this.name = name;
    }

    public String getMsg(){
        return msg;
    }
    public String getName(){
        return name;
    }
}
