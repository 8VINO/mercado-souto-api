package br.com.mercado_souto.util.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public class UploadImage {
    public static final String IMAGE_STORAGE_PATH = System.getenv("IMAGE_STORAGE_PATH");

    public static final String APP_BASE_URL= System.getenv("APP_BASE_URL");



    public static String upload(MultipartFile image) {
        boolean sucess = false;
        String fileName = null;

        if (image != null && !image.isEmpty()) {
            String dateTime = LocalDateTime.now().getYear() + "-"
                    + LocalDateTime.now().getMonthValue() + "-"
                    + LocalDateTime.now().getDayOfMonth() + "-"
                    + LocalDateTime.now().getHour() + "-"
                    + LocalDateTime.now().getMinute() + "-"
                    + LocalDateTime.now().getSecond() + " - ";
                fileName=dateTime+image.getOriginalFilename();
        
            try{
                File dir = new File(IMAGE_STORAGE_PATH);
                
                if(!dir.exists()){
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()+File.separator+fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(image.getBytes());
                stream.close();

                sucess=true;

            }catch(Exception e){
                System.out.println("Failed to save image");
            }
       }
       if(sucess){
        return APP_BASE_URL+"/images/"+fileName;
       } else{
        return null;
       }
    }
}
