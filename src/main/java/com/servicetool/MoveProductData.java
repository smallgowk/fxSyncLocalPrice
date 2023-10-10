/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool;

/**
 *
 * @author PhanDuy
 */
public class MoveProductData {

    public static void main(String[] str) throws Exception {
//        Configs.priceRate = 2.5f;
//        Configs.priceLimit = 150;
//        DropshipConnectionPool.getInstance().initC3P0();
//        Connection connection = null;
//        PreparedStatement ps = null;
//        connection = DropshipConnectionPool.getInstance().getConnection();
//
//        File file = new File("C:\\Users\\PhanDuy\\DropShippingTool\\CacheClient\\Products");
//
//        String[] paths = file.list();
//
//        for (String s : paths) {
//            File fileFilder = new File("C:\\Users\\PhanDuy\\DropShippingTool\\CacheClient\\Products\\" + s);
//            String[] fileNames = fileFilder.list();
//
//            for (String fileName : fileNames) {
//                AliexOriginalInfo aliexOriginalInfo = Utils.getAliexBasicInfoCache("C:\\Users\\PhanDuy\\DropShippingTool\\CacheClient\\Products\\" + s + "\\" + fileName);
//                float price = aliexOriginalInfo.getProductPrice();
//                if(price < 0) {
//                    price = aliexOriginalInfo.getProductPrice();
//                }
//                try {
//                    System.out.println("========");
//                    System.out.println("Insert for " + aliexOriginalInfo.getId() + " with price " + price);
//                    ps = connection.prepareCall("insert into dropship_db.product (aliex_id,price) values(?, ?)");
//                    ps.setString(1, aliexOriginalInfo.getId());
//                    ps.setString(2, "" + price);
//                    ps.execute();
//                    System.out.println("D0NE");
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                
//            }
//        }
//        
//        ps.close();
//        connection.close();

    }
}
