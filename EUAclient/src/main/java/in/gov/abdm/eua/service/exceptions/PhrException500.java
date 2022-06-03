package in.gov.abdm.eua.service.exceptions;

import org.hibernate.service.spi.ServiceException;

public class PhrException500 extends ServiceException {
    public PhrException500(String errorMessage) {
        super(errorMessage);
    }
}