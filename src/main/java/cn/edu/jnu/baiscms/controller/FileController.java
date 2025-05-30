package cn.edu.jnu.baiscms.controller;

import cn.edu.jnu.baiscms.common.AuthAccess;
import cn.edu.jnu.baiscms.common.Result;
import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip}")
    String ip;

    @Value("${server.port}")
    String port;

    // D:\temp\java\JavaWebLesson\files
    private final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        // 打印当前 IP 和端口
        System.out.println("当前服务地址：http://" + ip + ":" + port);
        // aa.jpg
        String originalFilename = file.getOriginalFilename();
        // aa
        String mainName = FileUtil.mainName(originalFilename);
        // jpg
        String extName = FileUtil.extName(originalFilename);

        // 检查上传目录是否存在
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);
        }

        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {
            originalFilename = System.currentTimeMillis()+"_"+mainName+"."+extName;
        }

        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);

        String url = "http://" + ip + ":" + port + "/file/download/"+originalFilename;

        return Result.success(url);
    }

    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        String filePath = ROOT_PATH + File.separator + fileName;

        if(!FileUtil.exist(filePath)) {
            return;
        }

        // 预览
        response.addHeader("Content-Disposition", "inline;filename="+ URLEncoder.encode(filePath, "UTF-8"));
        // 下载
        // response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filePath, "UTF-8"));

        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();;
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}

