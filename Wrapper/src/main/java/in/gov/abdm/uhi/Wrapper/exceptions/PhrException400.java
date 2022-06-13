package in.gov.abdm.uhi.Wrapper.exceptions;

import org.hibernate.service.spi.ServiceException;

public class PhrException400 extends ServiceException {
    public PhrException400(String errorMessage) {
        super(errorMessage);
    }
}