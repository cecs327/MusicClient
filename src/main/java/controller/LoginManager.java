//package controller;
//
//import model.User;
//import utility.Deserializer;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.List;
//
//public class LoginManager {
//
//    private String UserTextBoxEntry;
//    private String PasswordTextBoxEntry;
//
//    public LoginManager(String username, String password) {
//        UserTextBoxEntry = username;
//        PasswordTextBoxEntry = password;
//    }
//
//    public boolean JSONLoginValidation() throws IOException {
//        boolean isUser = false;
//        Deserializer deserializer = new Deserializer();
//        List<User> users = deserializer.deserializeUsers();
//        HashMap<String, User> userInfo = new HashMap<>();
//
//        for(User u : users){
//            if(userInfo.containsValue(u)){
//                throw new IllegalStateException("Duplicate User found");
//            }
//            userInfo.put(u.getUsername()+u.getPassword(), u);
//        }
//
//        User currentSession = null;
//        if((currentSession = userInfo.get(UserTextBoxEntry + PasswordTextBoxEntry)) == null){
//            System.out.println("Falied Login");
//            isUser = false;
//        }
//        else{
//            System.out.println("Login Successful");
//            isUser = true;
//        }
//        return isUser;
//    }
//}