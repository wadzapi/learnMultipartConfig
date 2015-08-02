package org.wadzapi;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/fileUpload")
@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB
        maxFileSize=1024*1024*50,          // 50 MB
        maxRequestSize=1024*1024*100)      // 100 MB
public class UploadServlet extends HttpServlet {

    private static Logger log = org.slf4j.LoggerFactory.getLogger(UploadServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String baseLogMsg = " загрузки файла через сервлет.";
        try {
            log.info("Начало" + baseLogMsg);
            for (Part uploadedFile : req.getParts()) {
                UploadFileUtils.printFileInfo(uploadedFile);
                //UploadFileUtils.saveFile(uploadedFile);
            }
            //getServletContext().getRequestDispatcher("/response.jsp").forward(req, resp);
            log.info("Конец" + baseLogMsg);
        } catch (Exception e) {
            log.info("Ошибка" + baseLogMsg);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, null);
        }
    }
}
