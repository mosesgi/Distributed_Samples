
package com.moses.distributed.webServiceGenerated;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ServerServiceImpl", targetNamespace = "http://webservice.distributed.moses.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ServerServiceImpl {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sayHello", targetNamespace = "http://webservice.distributed.moses.com/", className = "com.moses.distributed.webServiceGenerated.SayHello")
    @ResponseWrapper(localName = "sayHelloResponse", targetNamespace = "http://webservice.distributed.moses.com/", className = "com.moses.distributed.webServiceGenerated.SayHelloResponse")
    @Action(input = "http://webservice.distributed.moses.com/ServerServiceImpl/sayHelloRequest", output = "http://webservice.distributed.moses.com/ServerServiceImpl/sayHelloResponse")
    public String sayHello(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
