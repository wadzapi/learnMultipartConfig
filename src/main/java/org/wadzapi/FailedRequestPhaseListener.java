package org.wadzapi;

import org.apache.catalina.Globals;

import javax.faces.context.ExternalContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FailedRequestPhaseListener implements PhaseListener {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UploadBean.class);

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        logger.debug("before APPLY_REQUEST_VALUES");
        ExternalContext ctx = event.getFacesContext().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
        HttpServletResponse response = (HttpServletResponse) ctx.getResponse();
        request.getParameter("none");
        if (request.getAttribute(Globals.PARAMETER_PARSE_FAILED_ATTR) != null) {
            String contentType = request.getContentType();
            try {
                if (contentType != null && contentType.contains("multipart/form-data")) {
                    logger.debug("Multipart request parsing failed");
                    logger.debug("sendError SC_REQUEST_ENTITY_TOO_LARGE begin");
                    response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
                    logger.debug("sendError SC_REQUEST_ENTITY_TOO_LARGE end");
                } else {
                    logger.debug("Request parameters parsing failed");
                    logger.debug("sendError SC_BAD_REQUEST begin");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    logger.debug("sendError SC_BAD_REQUEST end");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                event.getFacesContext().responseComplete();
                logger.debug("finally in before APPLY_REQUEST_VALUES");
            }
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.APPLY_REQUEST_VALUES;
    }
}