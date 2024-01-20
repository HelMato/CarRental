package org.exaple;

public class User {
    private int id;
    public User(int id){
        this.id=id;
    }

    public int getId() {

        return id;
    }

    @Override
    public String toString(){
        return Integer.toString(id);
    }
}
