/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.store;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author PhanDuy
 */
public class UserCache {
    public static Set<Integer> hashUserCache = new HashSet<>();
    
    public static boolean isExistInfo(int hash) {
        return hashUserCache.contains(hash);
    }
    
    public static void addUserCache(int hash) {
        hashUserCache.add(hash);
    }
}
