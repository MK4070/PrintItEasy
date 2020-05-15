package com.example.printiteasy;

class User {
    private String DisplayName;
    private String Email;
    private long createdAt;

    public User(){};
    User(String displayName, String email, long createdAt){
        this.DisplayName= displayName;
        this.Email=email;
        this.createdAt=createdAt;
    }
    public String getDisplayName() {
        return DisplayName;
    }

    public String getEmail() {
        return Email;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
