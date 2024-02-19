package com.tian.my_qa.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tian.my_qa.dao.ImageDao;
import com.tian.my_qa.model.Image;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class FileService {
    @Value("${file-save-path}")
    private String fileSavePath;

    @Autowired
    ImageDao iDao;

    public ResponseEntity<Map<String, Object>> addImage(MultipartFile file) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("/yyyy.MM.dd/");
            // 获取文件的名字
            String originName = file.getOriginalFilename();
            System.out.println("originName:" + originName);
            // 判断文件类型
            String regex = ".+\\.(jpg|jpeg|png)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(originName);
            if (!matcher.matches()) {
                throw new TypeMismatchException(matcher, null);
            }
            // 给上传的文件新建目录
            String format = sdf.format(new Date());
            String realPath = fileSavePath + format;
            System.out.println("realPath:" + realPath);
            // 若目录不存在则创建目录
            File folder = new File(realPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // 给上传文件取新的名字，避免重名
            String newName = UUID.randomUUID().toString() + ".jpg";
            // 生成文件，folder为文件目录，newName为文件名
            file.transferTo(new File(folder, newName));
            // 生成返回给前端的url
            // String url = req.getScheme() + "://" + req.getServerName() + ":" +
            // req.getServerPort() +"/images"+ format + newName;
            String url = "/images" + format + newName;
            System.out.println("url:" + url);
            // 返回URL
            Map<String, Object> result = new HashMap<>();
            result.put("status", "success");
            result.put("url", url);

            Image image = new Image();
            image.setUrl(url);
            image.setPhysicalPath(realPath);
            image.setCreateTime(System.currentTimeMillis());
            iDao.save(image);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (TypeMismatchException e) {
            return new ResponseEntity<>(null, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        } catch (IOException e) {
            Map<String, Object> result = new HashMap<>();
            result.put("status", "error");
            result.put("msg", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
