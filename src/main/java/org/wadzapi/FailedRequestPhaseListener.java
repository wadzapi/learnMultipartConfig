package org.wadzapi;

import org.apache.catalina.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.ExternalContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Слушатель фазы Restore view для отслеживания загружаемых файлов, превышающих максимально допустимый размер
 */
public class FailedRequestPhaseListener implements PhaseListener {

    private static final Logger logger = LoggerFactory.getLogger(UploadBean.class);

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        final String logMsg = "{0} обнаружения загружаемых файлов с размером, превышающим уставленный лимит на фазе {1}";
        logger.debug(MessageFormat.format(logMsg, "Начало", event.getPhaseId().getName()));
        ExternalContext ctx = event.getFacesContext().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
        HttpServletResponse response = (HttpServletResponse) ctx.getResponse();
        request.getParameter("none");
        if (request.getAttribute(Globals.PARAMETER_PARSE_FAILED_ATTR) != null) {
            String contentType = request.getContentType();
            final String sndErrorMsg = "Отправлен ответ с кодом HTTP-состояния {0} ({1})";
            //TODO После перехода на tomcat 7.0.64 возможно более точно определить причину поломки
            //Изменения войдут и в FailedRequestFilter - его применение более целесообразно (см. svn.apache.org/r1694437 или https://bz.apache.org/bugzilla/show_bug.cgi?id=58031)
            try {
                if (contentType != null && contentType.contains("multipart/form-data")) {
                    logger.warn("Ошибка при обработке параметров и данных Multipart-запроса");
                    response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
                    logger.debug(MessageFormat.format(sndErrorMsg, "SC_REQUEST_ENTITY_TOO_LARGE", HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE));
                } else {
                    logger.warn("Ошибка при обработке параметров и данных запроса");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    logger.debug(MessageFormat.format(sndErrorMsg, "SC_BAD_REQUEST", HttpServletResponse.SC_BAD_REQUEST));
                }
            } catch (IOException e) {
                logger.debug(MessageFormat.format(logMsg, "Ошибка", event.getPhaseId().getName()));
                e.printStackTrace();
            } finally {
                event.getFacesContext().responseComplete();
                logger.trace("Отправка responseComplete для текущего FacesContext-а");
            }
        }
        logger.debug(MessageFormat.format(logMsg, "Конец", event.getPhaseId().getName()));
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}