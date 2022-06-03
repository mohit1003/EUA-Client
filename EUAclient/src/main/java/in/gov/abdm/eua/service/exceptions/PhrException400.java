package in.gov.abdm.eua.service.exceptions;

import org.hibernate.service.spi.ServiceException;

public class PhrException400 extends ServiceException {
    public PhrException400(String errorMessage) {
        super(errorMessage);
    }
}