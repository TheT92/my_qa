package com.tian.my_qa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tian.my_qa.service.FileService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("image")
public class ImgUploadController {
    // @Value("${file-save-path}")
	// private String fileSavePath;
	@Autowired
	FileService fs;

    @PostMapping("upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestHeader(value = "token", required = true) MultipartFile file, HttpServletRequest req) {
        return fs.addImage(file);
		// SimpleDateFormat sdf = new SimpleDateFormat("/yyyy.MM.dd/");
		// Map<String,Object> result = new HashMap<>();
        // //获取文件的名字
		// String originName = file.getOriginalFilename();
		// System.out.println("originName:"+originName);
		// //判断文件类型
        // String regex = ".+\\.(jpg|jpeg|png)";
        // Pattern pattern = Pattern.compile(regex);
        // Matcher matcher = pattern.matcher(originName);
		// if(!matcher.matches()) {
		// 	result.put("status","error");
		// 	result.put("msg", "文件类型不对");
		// 	return result;
		// }
		// //给上传的文件新建目录
		// String format = sdf.format(new Date());
		// String realPath = fileSavePath + format;
		// System.out.println("realPath:"+realPath);
		// //若目录不存在则创建目录
		// File folder = new File(realPath);
		// if(! folder.exists()) {
		// 	folder.mkdirs();
		// }
		// //给上传文件取新的名字，避免重名
		// String newName = UUID.randomUUID().toString() + ".jpg";
		// try {
		// 	//生成文件，folder为文件目录，newName为文件名
		// 	file.transferTo(new File(folder,newName));
		// 	//生成返回给前端的url
		// 	// String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +"/images"+ format + newName;
        //     String url = "/images"+ format + newName;
		// 	System.out.println("url:"+url);
		// 	//返回URL
		// 	result.put("status", "success");
		// 	result.put("url", url);
 		// }catch (IOException e) {
		// 	// TODO Auto-generated catch block
 		// 	result.put("status", "error");
 		// 	result.put("msg",e.getMessage());
		// }
		// return result;
    }
}
