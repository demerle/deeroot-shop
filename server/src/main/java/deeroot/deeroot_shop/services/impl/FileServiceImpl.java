package deeroot.deeroot_shop.services.impl;

import java.util.UUID;
import java.util.Calendar;
import java.util.Date;



public class FileServiceImpl {





    /*


    public String generateS3FileName(String originalFileName) {
        String extension = FileNameUtils.getExtension(originalFileName);
        return "doc-" + UUID.randomUUID() + "." + extension;
    }

     */

    public String generateUrl(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 1);
    }

}
