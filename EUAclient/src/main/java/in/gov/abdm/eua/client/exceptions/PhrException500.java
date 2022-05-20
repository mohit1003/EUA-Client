package in.gov.abdm.eua.client.exceptions;

import org.hibernate.service.spi.ServiceException;

import javax.net.ssl.SSLException;

public class PhrException500 extends ServiceException {
    public PhrException500(String errorMessage) {
        super(errorMessage);
    }
}