package org.wadzapi;

import org.slf4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class UploadFileUtils {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(UploadFileUtils.class);


    public static void printFileInfo(Part uploadedPart) {
        log.debug("Filename: " + uploadedPart.getName());
        log.debug("Content-type: " + uploadedPart.getContentType());
        log.debug("Size: " + uploadedPart.getSize());
        log.debug("Headers: ");
        for (String fileHeaderName : uploadedPart.getHeaderNames()) {
            log.debug(fileHeaderName + ": " + uploadedPart.getHeader(fileHeaderName));
        }
    }

    public static void saveFile(Part uploadedFile) throws IOException {
        InputStream fileStream = uploadedFile.getInputStream();
        //create buffered stream to ByteArrayInputStream and save to disk or DB
    }
}
