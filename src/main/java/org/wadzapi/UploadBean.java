/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wadzapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@ManagedBean
public class UploadBean
{
    private static Logger log = LoggerFactory.getLogger(UploadBean.class);
    static {
        log.trace("Constructing UploadBean");
    }

    private Part uploadedFile;

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void handleUploadedFile() {
        String baseLogMsg = " сохранения загруженного файла";
        if (uploadedFile == null) {
            return;
        }
        try {
            log.info("Начало" + baseLogMsg);
            printFileInfo(uploadedFile);
            log.info("Конец" + baseLogMsg);
        } catch (Exception e) {
            log.error("Ошибка" + baseLogMsg);
        }
    }


    private static void printFileInfo(Part uploadedPart) {
        log.debug("Filename: " + uploadedPart.getName());
        log.debug("Content-type: " + uploadedPart.getContentType());
        log.debug("Size: " + uploadedPart.getSize());
        log.debug("Headers: ");
        for (String fileHeaderName : uploadedPart.getHeaderNames()) {
            log.debug(fileHeaderName + ": " + uploadedPart.getHeader(fileHeaderName));
        }
    }

    private static void saveFile(Part uploadedFile) throws IOException {
        InputStream fileStream = uploadedFile.getInputStream();
        //create buffered stream to ByteArrayInputStream and save to disk or DB
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
