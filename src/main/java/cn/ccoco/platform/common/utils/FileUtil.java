package cn.ccoco.platform.common.utils;

import cn.ccoco.platform.common.entity.CCocoConstant;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author CCoco
 */
@Slf4j
public class FileUtil {

    private static final int BUFFER = 1024 * 8;

    /**
     * 压缩文件或目录
     *
     * @param fromPath 待压缩文件或路径
     * @param toPath   压缩文件，如 xx.zip
     */
    public static void compress(String fromPath, String toPath) throws IOException {
        File fromFile = new File(fromPath);
        File toFile = new File(toPath);
        if (!fromFile.exists()) {
            throw new FileNotFoundException(fromPath + "不存在！");
        }
        try (
                FileOutputStream outputStream = new FileOutputStream(toFile);
                CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new CRC32());
                ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream)
        ) {
            String baseDir = "";
            compress(fromFile, zipOutputStream, baseDir);
        }
    }

    /**
     * 文件下载
     *
     * @param filePath 待下载文件路径
     * @param fileName 下载文件名称
     * @param delete   下载后是否删除源文件
     * @param webExchange ServerWebExchange
     * @throws Exception Exception
     */
    public static Mono<Void> download(String filePath, String fileName, Boolean delete, ServerWebExchange webExchange) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("文件未找到");
        }

        String fileType = getFileType(file);
        if (!fileTypeIsValid(fileType)) {
            throw new Exception("暂不支持该类型文件下载");
        }
        ServerHttpResponse response=webExchange.getResponse();
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "utf-8"));
        response.getHeaders().setContentType(MediaType.MULTIPART_FORM_DATA);
        return zeroCopyResponse.writeWith(file, 0, file.length());
//        try (InputStream inputStream = new FileInputStream(file); OutputStream os = response.getOutputStream()) {
//            byte[] b = new byte[2048];
//            int length;
//            while ((length = inputStream.read(b)) > 0) {
//                os.write(b, 0, length);
//            }
//        } finally {
//            if (delete) {
//                delete(filePath);
//            }
//        }
    }

    /**
     * 递归删除文件或目录
     *
     * @param filePath 文件或目录
     */
    public static void delete(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                Arrays.stream(files).forEach(f -> delete(f.getPath()));
            }
        }
        try {
            Files.delete(Paths.get(filePath));
        } catch (IOException e) {
            log.error("删除失败", e);
        }
    }

    /**
     * 获取文件类型
     *
     * @param file 文件
     * @return 文件类型
     * @throws Exception Exception
     */
    private static String getFileType(File file) throws Exception {
        Preconditions.checkNotNull(file);
        if (file.isDirectory()) {
            throw new Exception("file不是文件");
        }
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    /**
     * 校验文件类型是否是允许下载的类型
     * （出于安全考虑：https://github.com/wuyouzhuguli/ccoco-Shiro/issues/40）
     *
     * @param fileType fileType
     * @return Boolean
     */
    private static Boolean fileTypeIsValid(String fileType) {
        Preconditions.checkNotNull(fileType);
        fileType = StringUtils.lowerCase(fileType);
        return ArrayUtils.contains(CCocoConstant.VALID_FILE_TYPE, fileType);
    }

    private static void compress(File file, ZipOutputStream zipOut, String baseDir) throws IOException {
        if (file.isDirectory()) {
            compressDirectory(file, zipOut, baseDir);
        } else {
            compressFile(file, zipOut, baseDir);
        }
    }

    private static void compressDirectory(File dir, ZipOutputStream zipOut, String baseDir) throws IOException {
        File[] files = dir.listFiles();
        if (files != null && ArrayUtils.isNotEmpty(files)) {
            for (File file : files) {
                compress(file, zipOut, baseDir + dir.getName() + "/");
            }
        }
    }

    private static void compressFile(File file, ZipOutputStream zipOut, String baseDir) throws IOException {
        if (!file.exists()) {
            return;
        }
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            ZipEntry entry = new ZipEntry(baseDir + file.getName());
            zipOut.putNextEntry(entry);
            int count;
            byte[] data = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                zipOut.write(data, 0, count);
            }
        }
    }
}
