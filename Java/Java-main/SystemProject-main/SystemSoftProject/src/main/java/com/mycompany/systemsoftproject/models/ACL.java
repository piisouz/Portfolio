package com.mycompany.systemsoftproject.models;

import java.util.HashMap;

public class ACL {
    // A simple map for permissions
    private HashMap<String, String> userPermissions = new HashMap<>();

    public ACL(String owner) {
        // give the person who created the file "ALL" permissions
        userPermissions.put(owner, "ALL");
    }

    // Does this user have the permission  just a simple check
    public boolean hasPermission(String username, Permission requestedPerm) {
        String userPerm = userPermissions.get(username);
        
        if (userPerm == null) return false; // User isnt on the list
        if (userPerm.equals("ALL")) return true; // Owners can do everything
        
        // Check if their permission string matches what we need
        return userPerm.equalsIgnoreCase(requestedPerm.toString());
    }

    // Just put the name and the permission string in the map
    public void grantPermission(String targetUser, Permission perm) {
        userPermissions.put(targetUser, perm.toString());
    }

    
    public void shareFile(String currentUser, String targetUser, Permission perm) {
        // 1. Check if they are the owner in the local map
        boolean isOwner = "ALL".equals(userPermissions.get(currentUser));

        // 2. Check the AuthService to see if the current session is an ADMIN
        boolean isAdmin = com.mycompany.systemsoftproject.services.AuthService.getCurrentUser().getRole() 
                          == com.mycompany.systemsoftproject.models.Role.ADMIN;

        // 3. Allow sharing if they are the Owner OR an Admin
        if (isOwner || isAdmin) { 
            grantPermission(targetUser, perm);
            System.out.println("Success: " + currentUser + " shared with " + targetUser);
        } else {
            System.out.println("Error: Only the owner can share this file.");
        }
    }
}
